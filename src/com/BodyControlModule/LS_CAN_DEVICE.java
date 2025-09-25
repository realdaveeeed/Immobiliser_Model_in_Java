package com.BodyControlModule;

import com.CanBus.CanDevice;
import com.CanBus.CanManager;

public abstract class LS_CAN_DEVICE implements CanDevice {
    private final String canDeviceID;
    private final CanManager LSCHANNEL;

    public LS_CAN_DEVICE(String deviceID, CanManager LSCAN_CHANNEL){
        this.canDeviceID = deviceID;
        this.LSCHANNEL = LSCAN_CHANNEL;
    }

    public String getDeviceID(){ return this.canDeviceID; }
    public String getCanArea(){ return "LS_CAN"; }
    public CanManager getComChannel() { return LSCHANNEL; }
}
