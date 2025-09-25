package com.EngineControlModule;

import com.CanBus.CanBusMess;
import com.CanBus.CanManager;
import com.Exception.ImmobiliserTrippedException;
import com.Tools.Logger;

public class EngineControlUnit implements com.CanBus.CanDevice {

    //PARTS
    private Immobiliser immobiliser;

    //COM
    private CanManager JUNC_CAN;
    public final CanManager HS_CAN = new CanManager("HIGH_SPEED_CANBUS");
    private final String canDeviceID = "ENGINE_CONTROL_MODULE";
    private final String CAN_AREA = "HS_CAN";
    private boolean beforeStartImmob = false;
    public boolean engineStartFailed;

    public EngineControlUnit(char typeOfTransmission,char defGear, CanManager canbus, String immobCodePhrase) {
        this.JUNC_CAN = canbus;
        HS_CAN.addDevice(this);
        HS_CAN.addDevice(new Immobiliser(immobCodePhrase,HS_CAN));
        HS_CAN.addDevice(new Alarm(HS_CAN,10));
        HS_CAN.addDevice(new Engine(HS_CAN,10));
        if (typeOfTransmission == 'A'){ HS_CAN.addDevice(new AutomaticTransmission(defGear,HS_CAN)); }
        else { HS_CAN.addDevice(new ManualTransmission(defGear,HS_CAN)); }

    }
    public void sendMessToAll(CanBusMess message){
        HS_CAN.sendMess(new CanBusMess(canDeviceID, "HS_CAN", message.getMessageType(), message.getData()));
        JUNC_CAN.sendMess(new CanBusMess(canDeviceID, "LS_CAN", message.getMessageType(), message.getData()));
    }

    public boolean initEngineStart() throws ImmobiliserTrippedException {
        if (!beforeStartImmob){
            Logger.log("[" + canDeviceID + "]: Starting engine.....");
            HS_CAN.sendMess(new CanBusMess(canDeviceID, "HS_CAN", "ENGINE_START"));
            engineStartFailed = false;
            return true;
        }else{
            Logger.log("[" + canDeviceID + "]: Immobiliser tripped.");
            System.out.println(canDeviceID + ": Immobiliser tripped, starting is disabled..");
            engineStartFailed = true;
            return false;
        }
    }
    public boolean engineStartSequence(CanBusMess message) throws ImmobiliserTrippedException {
        Logger.log("[" + canDeviceID + "]:Got " + message.getMessageType()+", SELF_CHECK requesting..");
        sendMessToAll(new CanBusMess(canDeviceID, "HS_CAN", "SELF_CHECK"));
        Logger.log("[" + canDeviceID + "]: IMMOB_CHECK to Immobiliser.");
        if (!beforeStartImmob){ HS_CAN.sendMess(new CanBusMess(canDeviceID, "HS_CAN", "IMMOB_CHECK")); }
        if (!initEngineStart()){
            throw new ImmobiliserTrippedException();
        }
        return true;
    }
    public String getCanArea() {return CAN_AREA;}

    @Override
    public void receiveMess(CanBusMess message){
        if (message.getDestinationArea().equals("ALL")) {
            //Avoid infinite loop
            if (!message.getSenderID().equals("BODY_CONTROL_MODULE")) {
                Logger.log("["+canDeviceID+"]: Broadcasting "+message.getMessageType()+" to JUNCTION.");
                JUNC_CAN.sendMess(new CanBusMess(canDeviceID, "LS_CAN", message.getMessageType(), message.getData()));
            }
        } else {
            switch (message.getMessageType()) {
                case "INIT_START":
                    try{engineStartSequence(message);} catch (ImmobiliserTrippedException e){System.exit(1);}
                    break;
                case "IMMOB_TRIP":
                    beforeStartImmob = true;
                    break;
                case "IMMOB_OK":
                    beforeStartImmob = false;
                    break;
                default:
                    Logger.log("[" + canDeviceID + "]: Broadcasting " + message.getMessageType());
                    HS_CAN.sendMess(new CanBusMess(canDeviceID, "HS_CAN", message.getMessageType(), message.getData()));
                    break;
            }
        }
    }

    public String getDeviceID(){ return canDeviceID; }
}
