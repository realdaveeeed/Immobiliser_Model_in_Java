package com.EngineControlModule;

import com.CanBus.CanBusMess;
import com.CanBus.CanManager;
import com.Tools.Logger;

public abstract class TransmissionBlueprint extends HS_CAN_DEVICE{
    private char gear;
    private final int speed = 0;
    private final char correctGear;
    private final CanManager COM;

    public TransmissionBlueprint(char correctGear,String transName, CanManager COM){
        super(transName, COM);
        this.COM = COM;
        this.correctGear = correctGear;
    }

    @Override
    public void receiveMess(CanBusMess message){
        switch (message.getMessageType()) {
            case "SELF_CHECK":
                if(readyToStart()){ Logger.log("["+getDeviceID()+"]: Transmission OK"); }
                else{
                    Logger.log("["+getDeviceID()+"]: Transmission self check failed");
                    COM.sendMess(new CanBusMess(getDeviceID(),"ALL","SELF_CHECK_FAILED"));
                }
                break;
            case "INSTR_CLSTR_REPORT":
                Logger.log("\n["+getDeviceID()+"]: Sending Transmission data\n");
                COM.sendMess(new CanBusMess(getDeviceID(),"ALL","TRANS_DATA",Integer.toString(speed)+","+Character.toString(gear)));
                break;
        }
    }

    protected void setGear(char gear){
        this.gear = gear;
    }

    protected char getGear(){ return gear; }

    protected char getCorrectGear(){ return correctGear; }

    public abstract boolean readyToStart();
}
