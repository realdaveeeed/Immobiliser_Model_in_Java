package com.EngineControlModule;

import com.CanBus.CanDevice;
import com.CanBus.CanManager;

public abstract class HS_CAN_DEVICE implements CanDevice {
    private final String canDeviceID;
    private final CanManager HSCHANNEL;

    public HS_CAN_DEVICE(String deviceID, CanManager HSCAN_CHANNEL){
        this.canDeviceID = deviceID;
        this.HSCHANNEL = HSCAN_CHANNEL;
    }

    public String getDeviceID(){ return this.canDeviceID; }
    public String getCanArea(){ return "HS_CAN"; }
    public CanManager getComChannel() { return HSCHANNEL; }
}
