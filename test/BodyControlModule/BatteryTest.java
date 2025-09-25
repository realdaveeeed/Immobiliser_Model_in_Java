package BodyControlModule;

import com.BodyControlModule.Battery;
import com.CanBus.CanManager;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BatteryTest {
    @Test
    public void isBattVoltOKTest(){
        var battery = new Battery(new CanManager("TEST_CAN"));
        assertFalse(battery.isBattVoltOK(12.5,13));
        assertFalse(battery.isBattVoltOK(12,13));
        assertTrue(battery.isBattVoltOK(13.5,13));
    }
}