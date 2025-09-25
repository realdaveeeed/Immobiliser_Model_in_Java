package com.BodyControlModule;

import com.CanBus.CanBusMess;
import com.CanBus.CanManager;
import com.Tools.Logger;

public class Door extends LS_CAN_DEVICE {
    private boolean isOpen;
    private boolean isLocked;
    private CanManager canbus;
    private final int doorId;
    private boolean ignoreSelfCheck;

    public Door(CanManager canbus, int id) {

        super("DOOR", canbus);
        this.isOpen = false;
        this.isLocked = false;
        this.canbus = canbus;
        this.doorId = id;
    }
    public boolean selfCheck(){
        if (!ignoreSelfCheck) {
            if(isOpen ){
                Logger.log("["+getDeviceID()+"_"+doorId+"]: Door is open.");
                canbus.sendMess(new CanBusMess(getDeviceID(),"ALL","SELF_CHECK_FAILED"));
                return false;
            }else {
                Logger.log("["+getDeviceID()+"_"+doorId+"]: Door is OK.");
                return true;
            }
        } else {
            Logger.log("["+getDeviceID()+"_"+doorId+"]: Request ignored.");
            return false;
        }
    }
    @Override
    public void receiveMess(CanBusMess message) {
        switch(message.getMessageType()){
            case "SELF_CHECK":
                selfCheck();
                break;
            case "SELF_CHECK_FAILED":
                ignoreSelfCheck = true;
                break;
        }
    }
    public int getDoorId() { return doorId; }

    public String toString(){return super.getDeviceID()+"_"+getDoorId();}
}
