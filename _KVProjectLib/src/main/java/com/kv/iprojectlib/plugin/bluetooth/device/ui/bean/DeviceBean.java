package com.kv.iprojectlib.plugin.bluetooth.device.ui.bean;

import java.io.Serializable;

public class DeviceBean implements Serializable{
    
    private static final long serialVersionUID = 1471137285874504074L;

    public String deviceName;
    public String devcieMac;
    
    public DeviceBean() {
    
    }
    
    public DeviceBean(String deviceName, String devcieMac) {
        this.deviceName = deviceName;
        this.devcieMac = devcieMac;
    }
}
