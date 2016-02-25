package com.kv.iprojectlib.plugin.bluetooth.device;

import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.kv.iprojectlib.plugin.bluetooth.device.service.BluetoothService;
import com.kv.iprojectlib.plugin.bluetooth.device.syscfg.Constant;
import com.kv.iprojectlib.plugin.bluetooth.device.ui.DeviceListAdapter;
import com.kv.iprojectlib.plugin.bluetooth.device.ui.DeviceListDlg;
import com.kv.iprojectlib.plugin.bluetooth.device.ui.bean.DeviceBean;

public class BlueToothHelper {

    private static final String TAG = BlueToothHelper.class.getSimpleName();

    private static final boolean DEBUG = true;

    private static final int REQUEST_ENABLE_BT = 0x12;

    private Activity mAct;

    private BluetoothAdapter mBluetoothAdapter;

    private List<byte[]> printDataArray;

    //service
    private BluetoothService bluetoothService;

    //device list ui
    DeviceListDlg deviceListDlg;

    DeviceListAdapter matchListAdapter;

    DeviceListAdapter scanListAdapter;

    private boolean hasRegistReceiver = false;

    private IReceiveMsgListener receiveMsgListener;

    private IGetDeviceListener getDeviceListener;

    private boolean isPrint = true;

    private boolean isOn = true;

    private boolean isActive2Work = false;

    private Handler blutoothHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case BluetoothService.MESSAGE_STATE_CHANGE:
                switch (msg.arg1) {
                case BluetoothService.STATE_CONNECT_OK:
                    if (isActive2Work) {
                        if (isPrint) {
                            handlePrint(printDataArray);
                        } else {
                            showToast(Constant.MSG_CONNECT_OK);
                        }
                    } else {
                        showToast(Constant.MSG_CONNECT_OK);
                        if (getDeviceListener != null) {
                            getDeviceListener.onGetDeviceListener(bluetoothService.getLastBluetoothDevice());
                        }
                    }
                    break;
                case BluetoothService.STATE_CONNECT_FAILED:
                    log("STATE_CONNECT_FAILED");
                    if (!isActive2Work && !isPrint) {
                        reConnect();
                    } else {
                        showToast(Constant.MSG_CONNECT_FAIL);
                    }
                    break;
                case BluetoothService.STATE_CONNECT_OFF:
                    log("STATE_CONNECT_OFF");
                    break;
                case BluetoothService.STATE_CONNECTING:
                    log("STATE_CONNECTING");
                    break;
                case BluetoothService.STATE_LISTEN:
                case BluetoothService.STATE_NONE:
                    log("STATE_LISTEN STATE_NONE");
                    break;
                }
                break;
            case BluetoothService.MESSAGE_WRITE:
                log("MESSAGE_WRITE");
                break;
            case BluetoothService.MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                String readMessage = new String(readBuf, 0, msg.arg1);
                if (receiveMsgListener != null && isOn) {
                    receiveMsgListener.onReceiveMsg(readMessage.replaceAll("\r\n", ""));
                }
                log("MESSAGE_READ");
                break;
            case BluetoothService.MESSAGE_DEVICE_NAME:
                String mConnectedDeviceName = msg.getData().getString(BluetoothService.DEVICE_NAME);
                log("MESSAGE_DEVICE_NAME");
                break;
            case BluetoothService.MESSAGE_TOAST:
                log("MESSAGE_TOAST");
                break;
            }
        }

    };

    public BlueToothHelper(boolean isPrint) {
        this.isPrint = isPrint;
    }

    public BlueToothHelper(Activity mAct, boolean isPrint) {
        this.mAct = mAct;
        this.isPrint = isPrint;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothService = new BluetoothService(mAct, blutoothHander, isPrint);

        matchListAdapter = new DeviceListAdapter(mAct);
        scanListAdapter = new DeviceListAdapter(mAct);
    }

    private void reConnect() {
        onDestroy();
        showToast(Constant.MSG_RECONNECT_BL);
        handleFindDevice(getDeviceListener, mAct);
    }

    public void onActivityForResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                if (!isActive2Work) {
                    handleFindDevice(getDeviceListener, mAct);
                } else {
                    if (isPrint) {
                        executePrint(printDataArray);
                    } else {
                        //蓝牙扫描枪
                        startReceiveMsg(mAct, receiveMsgListener);
                    }
                }
            } else {
                showToast(Constant.MSG_BL_NO_OPEN);
            }
        }
    }

    public void onDestroy() {
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
        if (hasRegistReceiver) {
            mAct.unregisterReceiver(mReceiver);
            hasRegistReceiver = false;
        }
        if (bluetoothService != null) {
            bluetoothService.stop();
        }
        receiveMsgListener = null;
    }

    /**
     * 调试日志.
     * @param msg
     */
    private void log(String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    /**
     * 显示toast信息.
     * @param msg
     */
    private void showToast(String msg) {
        Toast.makeText(mAct, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 蓝牙是否打开了
     * @return
     */
    private boolean isBlueToothOpen() {

        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        if (mBluetoothAdapter == null) { //不支持蓝牙
            showToast(Constant.MSG_NO_SUPPORT_BL);
            return false;
        }
        
        if (!mBluetoothAdapter.isEnabled()) { //蓝牙未开启
            showToast(Constant.MSG_BL_NO_OPEN);            
            
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mAct.startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            return false;
        }

        return true;
    }

    /**
     * 执行打印.
     * @param byteData
     */
    public void executePrint(List<byte[]> byteDataArray) {
        this.printDataArray = byteDataArray;
        isActive2Work = true;
        
        BluetoothDevice bluetoothDevice = bluetoothService.getLastBluetoothDevice();
        if (bluetoothDevice == null) {
            showToast(Constant.MSG_CHECK_PRINT_SETTING);
            return;
        }
            
        if (isBlueToothOpen()) {
            definePrint();
        }
    }

    /**
     * 开始监听读.
     * @param receiveMsgListener
     */
    public void startReceiveMsg(Activity mAct, IReceiveMsgListener receiveMsgListener) {
        this.mAct = mAct;
        this.isActive2Work = true;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        matchListAdapter = new DeviceListAdapter(mAct);
        scanListAdapter = new DeviceListAdapter(mAct);

        this.receiveMsgListener = receiveMsgListener;
        this.isOn = true;
        
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        
        matchListAdapter = new DeviceListAdapter(mAct);
        scanListAdapter = new DeviceListAdapter(mAct);
        
        if (bluetoothService == null) {
            bluetoothService = new BluetoothService(mAct.getApplicationContext(), blutoothHander, isPrint);
        }
        BluetoothDevice bluetoothDevice = bluetoothService.getLastBluetoothDevice();
        if (bluetoothDevice == null) {
            showToast(Constant.MSG_CHECK_GUN_SETTING);
            return;
        }
        
        if (isBlueToothOpen()) {
            defineGun();
        }
    }

    public void onResume() {
        isOn = true;
    }

    public void onPause() {
        isOn = false;
    }

    /**
     * Define Print.
     */
    private void definePrint() {
        BluetoothDevice bluetoothDevice = bluetoothService.getLastBluetoothDevice();
        if (bluetoothDevice != null) {
            if (bluetoothService.getState() == BluetoothService.STATE_CONNECT_OK) { //如果连接是保持的。
                if (isPrint) {
                    handlePrint(printDataArray);
                }
                return;
            } else {
                bluetoothService.connect(bluetoothDevice);
            }
        }
    }
    
    /**
     * Define Gun.
     */
    private void defineGun() {
        BluetoothDevice bluetoothDevice = bluetoothService.getLastBluetoothDevice();
        if (bluetoothDevice != null) {
            if (bluetoothService.getState() == BluetoothService.STATE_CONNECT_OK) { //如果连接是保持的。

            } else {
                bluetoothService.connect(bluetoothDevice);
            }
        }
    }

    /**
     * 处理寻找设备
     */
    public void handleFindDevice(IGetDeviceListener getDeviceListener, Activity mAct) {
        this.getDeviceListener = getDeviceListener;
        isActive2Work = false;
        if (!isPrint) {
            this.mAct = mAct;
            if (bluetoothService == null) {
                bluetoothService = new BluetoothService(mAct.getApplicationContext(), blutoothHander, isPrint);
            }
            
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            
            matchListAdapter = new DeviceListAdapter(mAct);
            scanListAdapter = new DeviceListAdapter(mAct);
        }
        if (isBlueToothOpen()) {
            deviceListDlg = new DeviceListDlg(mAct);

            deviceListDlg.showDeviceListDlg();
            deviceListDlg.matchPairListView.setAdapter(matchListAdapter);
            deviceListDlg.scanPairListView.setAdapter(scanListAdapter);

            resetBlueDlg();
            doDiscovery();

            deviceListDlg.matchPairListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View contentView, int position, long id) {
                    deviceListDlg.dismissDlg();
                    DeviceBean deviceBean = (DeviceBean) matchListAdapter.getItem(position);
                    if (BluetoothAdapter.checkBluetoothAddress(deviceBean.devcieMac)) {
                        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(deviceBean.devcieMac);
                        bluetoothService.connect(device);
                    }
                    resetBlueDlg();
                }

            });

            deviceListDlg.scanPairListView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View contentView, int position, long id) {
                    deviceListDlg.dismissDlg();
                    DeviceBean deviceBean = (DeviceBean) scanListAdapter.getItem(position);
                    if (BluetoothAdapter.checkBluetoothAddress(deviceBean.devcieMac)) {
                        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(deviceBean.devcieMac);
                        bluetoothService.connect(device);
                    }
                    resetBlueDlg();
                }

            });

            deviceListDlg.reDiscoveryBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    resetBlueDlg();
                    doDiscovery();
                }
            });

            deviceListDlg.dialog.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    resetBlueDlg();
                }
            });
        }
    }

    private void resetBlueDlg() {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        deviceListDlg.matchTextView.setVisibility(View.GONE);
        deviceListDlg.progressBar.setVisibility(View.VISIBLE);

        matchListAdapter.cleanBean();
        scanListAdapter.cleanBean();

        //reset receiver
        if (hasRegistReceiver) {
            mAct.unregisterReceiver(mReceiver);
            hasRegistReceiver = false;
        }
    }

    private void doDiscovery() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mAct.registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mAct.registerReceiver(mReceiver, filter);
        hasRegistReceiver = true;

        //paired
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            deviceListDlg.matchTextView.setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                matchListAdapter.addBean(new DeviceBean(device.getName(), device.getAddress()));
            }
        }

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
    }

    private void handlePrint(List<byte[]> message) {
        if (bluetoothService.getState() != BluetoothService.STATE_CONNECT_OK) {
            return;
        }

        if (message != null) {
            for (int i = 0; i < message.size(); i++) {
                if (message.get(i) != null && message.get(i).length > 0) {
                    // 如果不休眠，则会出现乱码
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        log(e.toString());
                    }
                    bluetoothService.write(message.get(i));
                }
            }
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    scanListAdapter.addBean(new DeviceBean(device.getName(), device.getAddress()));
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (deviceListDlg != null) {
                    deviceListDlg.progressBar.setVisibility(View.INVISIBLE);
                }
                if (scanListAdapter.getCount() == 0) {
                    showToast(Constant.MSG_NO_DEVICE);
                }
            }
        }
    };

    public void unRegisterReiveMsgListener() {
        receiveMsgListener = null;
    }

    public static interface IReceiveMsgListener {
        public void onReceiveMsg(String msg);
    }

    public static interface IGetDeviceListener {
        public void onGetDeviceListener(BluetoothDevice device);
    }
}