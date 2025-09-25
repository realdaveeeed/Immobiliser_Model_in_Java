package com;

import com.BodyControlModule.BodyControlModule;
import com.CanBus.CanManager;
import com.EngineControlModule.EngineControlUnit;
import com.BodyControlModule.Key;
import com.Tools.Logger;

public class Vehicle {

    public static CanManager canbus;
    private final EngineControlUnit ecu;
    private final BodyControlModule bcm;
    private boolean isUnlocked = false;

    public Vehicle(String immobCodePhrase, int numberOfDoors,char typeOfTransmission, char defGear){
        Logger.log("Creating Vehicle..");
        this.canbus = new CanManager("JUNCTION_CANBUS"); // JUNCTION COMMUNICATION MANAGER
        Logger.log("[Vehicle]: "+"Creating Junction Communication channel");
        this.ecu = new EngineControlUnit(typeOfTransmission,defGear,canbus,immobCodePhrase);
        Logger.log("[Vehicle]: "+"Creating Engine Control Unit and High-Speed Communication channel");
        this.canbus.addDevice(ecu); // ECU
        this.bcm = new BodyControlModule(canbus,numberOfDoors);
        Logger.log("[Vehicle]: "+"Creating Body Control Module and Low-Speed Communication channel");
        this.canbus.addDevice(bcm);
        System.out.print("\n\n");

    }
    public void insertKey(Key key){
       bcm.insertKey(key);
    }
    public void tryStart(){
        bcm.tryStart();
    }
    public void unlockVehicle(){
        Logger.log("[Vehicle]: Unlocking Vehicle");
        isUnlocked = true;
    }
}
