
package com.kv.iprojectlib.plugin.bluetooth.device.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * This class does all the work for setting up and managing Bluetooth
 * connections with other devices. It has a thread that listens for
 * incoming connections, a thread for connecting with a device, and a
 * thread for performing data transmissions when connected.
 */
public class BluetoothService {
    // Debugging
    private static final String TAG = "BluetoothService";
    private static final boolean D = true;
    public static boolean IS_KEEP_DEVICES = true;
    // Name for the SDP record when creating server socket
    private static final String NAME = "BTPrinter";
    //UUID must be this
    // Unique UUID for this application
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // Member fields
    private BluetoothAdapter bluetoothAdapter;
    private Handler mHandler;
    private AcceptThread mAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;
	private Context context;

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECT_OK = 3;  // now connect to a remote device success
    public static final int STATE_CONNECT_FAILED = 4;  // now  connect to a remote device fail
    public static final int STATE_CONNECT_OFF = 5;  // now off line 
    
    // Message types sent from the BluetoothService Handler
    public static final int MESSAGE_STATE_CHANGE = 11;
    public static final int MESSAGE_READ = 12;
    public static final int MESSAGE_WRITE = 13;
    public static final int MESSAGE_DEVICE_NAME = 14;
    public static final int MESSAGE_TOAST = 15;
 		
    // Key names received from the BluetoothService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    
    public static final String SP_FILE_PRINTER = "printer_device";
    public static final String SP_FILE_GUN = "gun_device";
    public static final String SP_PRINTER_NAME = "printerName";
    public static final String SP_PRINTER_MAC = "printerMac";

    private boolean mIsPrint = false;
    
    /**
     * Constructor. Prepares a new BTPrinter session.
     * @param context  The UI Activity Context
     * @param handler  A Handler to send messages back to the UI Activity
     */
    public BluetoothService(Context context, Handler handler, boolean isPrint) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mIsPrint = isPrint;
        mState = STATE_NONE;
        mHandler = handler;
        this.context = context;
    }
    

    /**
     * Set the current state of the connection
     * @param state  An integer defining the current connection state
     */
    private synchronized void setState(int state) {
        if (D) Log.e(TAG, "setState() " + mState + " -> " + state);
        mState = state;

        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }

    /**
     * Return the current connection state. */
    public synchronized int getState() {
        return mState;
    }

    /**
     * Start the service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume() */
    public synchronized void start() {
        Log.e(TAG, "#start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        // Start the thread to listen on a BluetoothServerSocket
        if (mAcceptThread == null) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        }
        setState(STATE_LISTEN);
    }

    /**
     * Start the ConnectThread to initiate a connection to a remote device.
     * @param device  The BluetoothDevice to connect
     */
    public synchronized void connect(BluetoothDevice device) {
        if (D) Log.e(TAG, "connect to: " + device);

        if(getState() == STATE_CONNECT_OK) {
        	this.stop();
        }
        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

      
        
        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    /**
     * Start the ConnectedThread to begin managing a Bluetooth connection
     * @param socket  The BluetoothSocket on which the connection was made
     * @param device  The BluetoothDevice that has been connected
     */
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if (D) Log.e(TAG, "#connected--");

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        // Cancel the accept thread because we only want to connect to one device
        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
        
        //保存当前已连接的打印机，供下次使用
        
        if(IS_KEEP_DEVICES){
            if (mIsPrint) {
                SharedPreferences  sp = context.getSharedPreferences(SP_FILE_PRINTER, Context.MODE_PRIVATE);
                sp.edit().putString(SP_PRINTER_NAME, device.getName())
                .putString(SP_PRINTER_MAC, device.getAddress()).commit();
            } else {
                SharedPreferences  sp = context.getSharedPreferences(SP_FILE_GUN, Context.MODE_PRIVATE);
                sp.edit().putString(SP_PRINTER_NAME, device.getName())
                .putString(SP_PRINTER_MAC, device.getAddress()).commit();
            }
        }
        
        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        setState(STATE_CONNECT_OK);
    }

    /**
     * Stop all threads
     */
    public synchronized void stop() {
         Log.e(TAG, "#stop");
        setState(STATE_NONE);
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}
        if (mAcceptThread != null) {mAcceptThread.cancel(); mAcceptThread = null;}
    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     */
    public void write(byte[] out) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECT_OK) return;
            r = mConnectedThread;
        }
        // Perform the write unsynchronized
//        r.write(new byte[]{27,64});
        r.write(out);
//        r.write(new byte[]{10});
//        r.write(new byte[]{27,100,4});
    }

    public BluetoothDevice getLastBluetoothDevice() {
        String address = "";
        if(mIsPrint) {
            SharedPreferences sp = context.getSharedPreferences(SP_FILE_PRINTER, Context.MODE_PRIVATE);
            address = sp.getString(SP_PRINTER_MAC, "");
        } else {
            SharedPreferences sp = context.getSharedPreferences(SP_FILE_GUN, Context.MODE_PRIVATE);
            address = sp.getString(SP_PRINTER_MAC, "");
        }

    	
		Log.e(TAG, "#getLastBluetoothDevice address =" + address);
		if (BluetoothAdapter.checkBluetoothAddress(address)) {
			BluetoothDevice device = bluetoothAdapter
					.getRemoteDevice(address); 
			return device;
		}
		
		return null;
    	
    }
    /**
     * Indicate that the connection attempt failed and notify the UI Activity.
     */
    private void connectionFailed() {
    	Log.e(TAG, "connectionFailed--");
        setState(STATE_LISTEN);
        
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(MESSAGE_STATE_CHANGE,STATE_CONNECT_FAILED,-1);
        mHandler.sendMessage(msg);
        
        this.stop();
    }

    /**
     * Indicate that the connection was lost and notify the UI Activity.
     */
    private void connectionLost() {
    	Log.e(TAG, "connectionLost--");
        //setState(STATE_LISTEN);
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(MESSAGE_STATE_CHANGE,STATE_CONNECT_OFF,-1);
        mHandler.sendMessage(msg);
    }

    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    private class AcceptThread extends Thread {
        // The local server socket
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;

            // Create a new listening server socket
            try {
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "listen() failed", e);
            }
            mmServerSocket = tmp;
            Log.e(TAG, "mmServerSocket=" + mmServerSocket);
        }

        @Override
		public void run() {
            if (D) Log.e(TAG, "BEGIN mAcceptThread" + this);
            setName("AcceptThread");
            BluetoothSocket socket = null;

            // Listen to the server socket if we're not connected
            while (mState != STATE_CONNECT_OK) {
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "accept() failed", e);
                    break;
                }

                // If a connection was accepted
                if (socket != null) {
                    synchronized (BluetoothService.this) {
                        switch (mState) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:
                            // Situation normal. Start the connected thread.
                            connected(socket, socket.getRemoteDevice());
                            break;
                        case STATE_NONE:
                        case STATE_CONNECT_OK:
                            // Either not ready or already connected. Terminate new socket.
                            try {
                                socket.close();
                            } catch (IOException e) {
                                Log.e(TAG, "Could not close unwanted socket", e);
                            }
                            break;
                        }
                    }
                }
            }
            if (D) Log.i(TAG, "END mAcceptThread");
        }

        public void cancel() {
            if (D) Log.e(TAG, "cancel " + this);
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of server failed", e);
            }
        }
    }


    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "create() failed", e);
            }
            mmSocket = tmp;
            Log.e(TAG, "tmp=" + tmp );
        }

        @Override
		public void run() {
            Log.i(TAG, "BEGIN mConnectThread");
            setName("ConnectThread");

            // Always cancel discovery because it will slow down a connection
            bluetoothAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
            	e.printStackTrace();
                connectionFailed();
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() socket during connection failure", e2);
                }
                // Start the service over to restart listening mode
                BluetoothService.this.start();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BluetoothService.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    /**
     * This thread runs during a connection with a remote device.
     * It handles all incoming and outgoing transmissions.
     */
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.e(TAG, "create ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        @Override
		public void run() {
            Log.e(TAG, "BEGIN mConnectedThread");
            int len;

            // Keep listening to the InputStream while connected
            while (true) {
                try {
                	byte[] buffer = new byte[256];
                    // Read from the InputStream
                    len = mmInStream.read(buffer);
                    if(len > 0) {
	                    // Send the obtained bytes to the UI Activity
	                    mHandler.obtainMessage(MESSAGE_READ, len, -1, buffer).sendToTarget();
	                    Log.e(TAG, "inputStream -- buffer =" + new String(buffer, 0,len));
                    } else {
                        if (mIsPrint) {
                            Log.e(TAG, "-ConnectedThread-disconnected");
                            connectionLost();
                            //add by chongqing jinou
                            if(mState != STATE_NONE) {
                                Log.e(TAG, "-ConnectedThread -disconnected2");
                            // Start the service over to restart listening mode
                                BluetoothService.this.start();
                            }
                            break;
                        }
                    }
                } catch (IOException e) {
                        Log.e(TAG, "ConnectedThread -disconnected3", e);
                        connectionLost();
                        //add by chongqing jinou
                        if(mState != STATE_NONE)
                        {
                            // Start the service over to restart listening mode
                            BluetoothService.this.start();
                        }
                        break;
                }
            }
        }

        /**
         * Write to the connected OutStream.
         * @param buffer  The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);
                Log.e("BTPWRITE", new String(buffer,"GBK"));
                // Share the sent message back to the UI Activity
                mHandler.obtainMessage(MESSAGE_WRITE, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
}
