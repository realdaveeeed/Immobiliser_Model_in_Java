package com.BodyControlModule;

import com.CanBus.CanBusMess;
import com.CanBus.CanManager;
import com.Tools.Logger;

public class BodyControlModule implements com.CanBus.CanDevice{

    //Communication channels
    private CanManager JUNC_CAN;
    private final CanManager LS_CAN = new CanManager("LOW_SPEED_CANBUS");

    //Communication identifiers
    private final String canDeviceID = "BODY_CONTROL_MODULE";
    private final String CAN_AREA = "LS_CAN";

    private Key key;

    public BodyControlModule(CanManager canbus,int numberOfDoors) {
        this.JUNC_CAN = canbus;
        LS_CAN.addDevice(this);
        for (int i = 0; i < numberOfDoors; i++) {
            LS_CAN.addDevice(new Door(LS_CAN, i+1));
        }
        LS_CAN.addDevice(new InstrumentCluster(LS_CAN));
        LS_CAN.addDevice(new Battery(LS_CAN));
    }

    public void tryStart(){
        JUNC_CAN.sendMess(new CanBusMess(getDeviceID(),"HS_CAN","INIT_START"));
    }

    public void insertKey(Key key){
        this.key = key;
        JUNC_CAN.sendMess(new CanBusMess(canDeviceID,"HS_CAN","KEY_DETECTED",key.getEncryptedKey()));
    }
    public void broadcastOnLS(CanBusMess message){
        Logger.log("[" + canDeviceID + "]: Broadcasting " + message.getMessageType() + " on LS_CAN.");
        LS_CAN.sendMess(new CanBusMess(canDeviceID, "LS_CAN", message.getMessageType(), message.getData()));
    }
    @Override
    public void receiveMess(CanBusMess message) {
        if (message.getDestinationArea().equals("ALL")) {
                //Avoid an infinite loop
                if (!message.getSenderID().equals("ENGINE_CONTROL_MODULE")) {
                    Logger.log("["+canDeviceID+"]: Broadcasting "+message.getMessageType()+" to JUNCTION.");
                    JUNC_CAN.sendMess(new CanBusMess(canDeviceID, "HS_CAN", message.getMessageType(), message.getData()));
                }
        } else { broadcastOnLS(message); }
    }

    public String getCanArea(){ return this.CAN_AREA; }
    public String getDeviceID(){ return this.canDeviceID; }
}
