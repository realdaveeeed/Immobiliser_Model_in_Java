package com.EngineControlModule;

import com.CanBus.CanManager;

public class ManualTransmission extends TransmissionBlueprint{
    // gears = {'0','1','2','3','4','5','6','R'};

    public ManualTransmission(char gear,CanManager manager) {
        super('0',"MANUAL_TRANSMISSION",manager);
        super.setGear(gear);
    }

    @Override
    public boolean readyToStart() { return getGear() == getCorrectGear(); }
}
