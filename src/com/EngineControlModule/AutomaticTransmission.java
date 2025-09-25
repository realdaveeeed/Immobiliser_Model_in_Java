package com.EngineControlModule;

import com.CanBus.CanManager;

public class AutomaticTransmission extends TransmissionBlueprint{
    // gears = {'P','N','D','R','S'};

    public AutomaticTransmission(char gear, CanManager manager) {
        super('P',"AUTOMATIC_TRANSMISSION",manager);
        super.setGear(gear);
    }

    @Override
    public boolean readyToStart() {
        return getGear() == 'P' || getGear() == 'N';
    }
}
