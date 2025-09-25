package com.EngineControlModule;

import com.CanBus.CanManager;
import com.CanBus.CanBusMess;
import com.Tools.Encrypt;
import com.Tools.Logger;

public class Immobiliser extends HS_CAN_DEVICE{
    private final String codePhrase;
    private boolean immobilised = false;
    private final CanManager COM;

    public Immobiliser(String codePhrase, CanManager com) {
        super("IMMOBILISER", com);
        this.codePhrase = Encrypt.encrypt(codePhrase);
        this.COM = com;
    }
    public boolean keyDetectedMessage(String data){
        Logger.log("[" + getDeviceID() + "]: Checking key...");

        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        if (!checkCodePhrase(data)) {
            Logger.log("[" + getDeviceID() + "]: Key is invalid, starting is unavaliable");
            immobilised = true;
            COM.sendMess(new CanBusMess(getDeviceID(), "ALL", "KEY_FAILED"));
            return false;
        } else {
            Logger.log("[" + getDeviceID() + "]: Key is valid!");
            COM.sendMess(new CanBusMess(getDeviceID(), "ALL", "KEY_OK"));
            return true;
        }
    }

    public boolean immobCheck(boolean immobilised){
        if(!immobilised){
            Logger.log("[" + getDeviceID() + "]: Enginge start is avaliable..");
            COM.sendMess(new CanBusMess(getDeviceID(), "HS_CAN", "IMMOB_OK"));
            return true;
        }
        Logger.log("[" + getDeviceID() + "]: Enginge start is unavaliable..");
        COM.sendMess(new CanBusMess(getDeviceID(), "HS_CAN", "IMMOB_TRIP"));
        return false;
    }
    public void immobTrip() {
        immobilised = true;
        Logger.log("[" + getDeviceID() + "]: Immobiliser is tripped, starting will not be avaliable");
        System.out.print("\nImmobilier is tripped, engine start is disabled.\n");
    }

    public boolean checkCodePhrase(String codephrase){
        if (codephrase.equals(codePhrase)) {
            return true;
        }
        return false;
    }

    @Override
    public void receiveMess(CanBusMess message){
        switch (message.getMessageType()) {
            case "KEY_DETECTED":
                keyDetectedMessage(message.getData());
                break;
            case "IMMOB_CHECK":
                immobCheck(immobilised);
                break;
            case "IMMOB_TRIP":
                immobTrip();
                break;
            case "SELF_CHECK_FAILED":
                if (!immobilised){
                    Logger.log("[" + getDeviceID() + "]: SELF_CHECK_FAILED, tripping..");
                    COM.sendMess(new CanBusMess(getDeviceID(),"ALL","IMMOB_TRIP"));
                    break;
                }
                Logger.log("[" + getDeviceID() + "]: "+message.getMessageType()+" message is ignored.");
                break;
        }

    }

    public String getDeviceID(){ return super.getDeviceID(); }
}
