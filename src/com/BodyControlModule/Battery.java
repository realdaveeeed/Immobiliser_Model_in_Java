package com.BodyControlModule;

import com.CanBus.CanManager;
import com.CanBus.CanBusMess;
import com.Tools.Logger;

import java.util.Random;

public class Battery extends LS_CAN_DEVICE{
    private final Random rand = new Random();
    private CanManager COM;
    private float battVoltage;
    private final double THRESHOLD = 12.3;
    //Charge is between 13.6 - 14.0
    public Battery(CanManager COM) {
        super("BATTERY",COM);
        this.COM = COM;
        this.battVoltage = rand.nextFloat(12.3f,12.9f);
    }

    public boolean selfCheck(){
        if (!isBattVoltOK(battVoltage,THRESHOLD)){
            Logger.log("["+getDeviceID()+"]: Battery voltage is below " + THRESHOLD+"V");
            COM.sendMess(new CanBusMess(getDeviceID(),"ALL","SELF_CHECK_FAILED"));
            return false;
        }
            Logger.log("["+getDeviceID()+"]: Battery voltage is OK");
            return true;
    }

    public void instrumentClusterReport(){
        battVoltage = refreshBatteryVoltage();
        Logger.log("\n["+getDeviceID()+"]: Sending battery voltage data\n");
        COM.sendMess(new CanBusMess(getDeviceID(),"LS_CAN","BATT_DATA", Double.toString(battVoltage)));
    }

    @Override
    public void receiveMess(CanBusMess msg) {
        switch (msg.getMessageType()){
            case "SELF_CHECK":
                selfCheck();
                break;
            case "INSTR_CLSTR_REPORT":
                instrumentClusterReport();
                break;
        }
    }
    private float refreshBatteryVoltage() {
        return rand.nextFloat(13.8f,14.0f);
    }

    public boolean isBattVoltOK(double current, double threshold){ return current>threshold; }
}
