package com.kv.iprojectlib.plugin.bluetooth.device.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kv.iprojectlib.plugin.bluetooth.device.ui.bean.DeviceBean;


public class DeviceListAdapter extends BaseAdapter {

    private Activity mAct;
    private List<DeviceBean> deviceList = new ArrayList<DeviceBean>();
    
    public DeviceListAdapter(Activity mAct) {
        this.mAct = mAct;
    } 
     
    @Override
    public int getCount() {
        return deviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    
    public void addBean(DeviceBean deviceBean) {
        if (!deviceList.isEmpty()) {
            for (DeviceBean db : deviceList) {
                if (deviceBean.devcieMac.equals(db.devcieMac)) {
                    return;
                }
            }
        }
        deviceList.add(deviceBean);
        notifyDataSetChanged();
    }
    
    public void cleanBean() {
        deviceList.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        DeviceListItemView deviceListItemView;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            deviceListItemView = new DeviceListItemView();
            convertView = deviceListItemView.getView(mAct);
            viewHolder.deviceNameTV = (TextView) convertView.findViewById(DeviceListItemView.DEVICE_NAME);
            viewHolder.deviceMacTV = (TextView) convertView.findViewById(DeviceListItemView.DEVICE_MAC);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        DeviceBean deviceBean = deviceList.get(position);
        viewHolder.deviceNameTV.setText(deviceBean.deviceName);
        viewHolder.deviceMacTV.setText(deviceBean.devcieMac);
        
        return convertView;
    }
    
    

    class ViewHolder {
        public TextView deviceNameTV;
        public TextView deviceMacTV;
    }
}
