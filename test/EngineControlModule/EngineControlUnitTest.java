package EngineControlModule;

import static org.junit.jupiter.api.Assertions.*;
import com.BodyControlModule.Key;
import com.CanBus.CanBusMess;
import com.EngineControlModule.EngineControlUnit;
import com.Exception.ImmobiliserTrippedException;
import com.Vehicle;
import org.junit.jupiter.api.Test;

public class EngineControlUnitTest {
    @Test
    public void ECUOKTest() {
        var testVeh = new Vehicle("TEST",5,'M','0');
        System.out.print(Vehicle.canbus.getDevice(0).getDeviceID());
        assertEquals("ENGINE_CONTROL_MODULE",Vehicle.canbus.getDevice(0).getDeviceID());

        EngineControlUnit ecu = (EngineControlUnit) Vehicle.canbus.getDevice(0);
        //assertDoesNotThrow(ecu.initEngineStart());
    }

    @Test
    public void ECUFailedKeyTest() throws Exception{
        var testVeh = new Vehicle("TEST",5,'M','0');
        testVeh.insertKey(new Key("TESTINIT"));
        EngineControlUnit ecu = (EngineControlUnit) Vehicle.canbus.getDevice(0);
        assertThrows(ImmobiliserTrippedException.class,() -> ecu.engineStartSequence(new CanBusMess("TESTER","TEST_CAN","INIT_START")));
    }

}