package com.EngineControlModule;

import com.CanBus.CanBusMess;
import com.CanBus.CanManager;
import com.Tools.Logger;

import java.util.Random;

public class Engine extends HS_CAN_DEVICE{
    private CanManager COM;
    private final int LIFETIME;
    private int RPM;
    private final Random rand = new Random();

    public Engine(CanManager COM,int LIFETIME) {
        super("ENGINE",COM);
        this.COM = COM;
        this.LIFETIME = LIFETIME;
    }

    @Override
    public void receiveMess(CanBusMess message) {
        switch (message.getMessageType()) {
            case "ENGINE_START":
                Logger.log("["+getDeviceID()+"]: Engine started succesfully.");
                System.out.println("Engine started succesfully.\n");
                runEngine();
                break;
            case "INSTR_CLSTR_REPORT":
                RPM = refreshRPM();
                Logger.log("\n["+getDeviceID()+"]: Sending RPM data\n");
                COM.sendMess(new CanBusMess(getDeviceID(),"ALL","RPM_DATA", Integer.toString(RPM)));
                break;
        }
    }

    private void runEngine() {
        for (int curr = 0; curr <= LIFETIME; curr++){
            COM.sendMess(new CanBusMess(getDeviceID(),"ALL","INSTR_CLSTR_REFRESH"));
            try {Thread.sleep(2000);} catch (InterruptedException e) {}
        }
    }

    public int refreshRPM(){
        return rand.nextInt(750,1000);
    }
}
