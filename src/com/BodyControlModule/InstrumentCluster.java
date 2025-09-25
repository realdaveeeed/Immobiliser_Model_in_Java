package com.BodyControlModule;

import com.CanBus.CanBusMess;
import com.CanBus.CanManager;
import com.Tools.Logger;

public class InstrumentCluster extends LS_CAN_DEVICE {

    private final CanManager COM;
    private boolean immobiliserSymbol = false;

    private int engineRPM;
    private float battVoltage;
    private int speed = 0;
    private char gearData;
    public InstrumentCluster(CanManager canbus) {
        super("INSTRUMENT_CLUSTER", canbus);
        this.COM = canbus;

    }

    private void instrumentClusterReadout(){
        System.out.print("\nInstrument Cluster\nRPM: "+engineRPM+" rpm/min\nBattery Voltage: "+battVoltage+"V\nSpeed: "+speed+" km/h \nTransmission Gear: "+gearData+"\n\n");
    }

    public void updateBatteryReadout(CanBusMess x) { battVoltage = Float.parseFloat(x.getData()); }

    public void updateRPMReadout(CanBusMess x) { engineRPM = Integer.parseInt(x.getData()); }

    public void updateTransmissionReadout(CanBusMess x) {
        String[] data = x.getData().split(",");
        speed = Integer.parseInt(data[0]);
        gearData = data[1].charAt(0);
    }

    public int getEngineRPM() { return engineRPM; }

    public float getBatteryVoltage() { return battVoltage; }

    public int getSpeed() { return speed; }

    public char getGearData() { return gearData; }

    @Override
    public void receiveMess(CanBusMess message) {
        switch (message.getMessageType()){
            case "IMMOB_TRIP":
                immobiliserSymbol = true;
                Logger.log("["+getDeviceID()+"]: Immobilsed Symbol is active");
                System.out.println("\n*Immobiliser symbol is active on the instrument cluster*");
                break;
            case "INSTR_CLSTR_REFRESH":
                Logger.log("["+getDeviceID()+"]: Requesting data.");
                COM.sendMess(new CanBusMess(getDeviceID(),"ALL","INSTR_CLSTR_REPORT"));
                instrumentClusterReadout();
                break;
            case "BATT_DATA":
                updateBatteryReadout(message);
                break;
            case "RPM_DATA":
                updateRPMReadout(message);
                break;
            case "TRANS_DATA":
                updateTransmissionReadout(message);
                break;
        }
    }
}
