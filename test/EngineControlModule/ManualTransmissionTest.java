package EngineControlModule;

import com.CanBus.CanManager;
import com.EngineControlModule.ManualTransmission;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManualTransmissionTest {
    @Test
    public void ManualTransmissionTest(){
        assertTrue(new ManualTransmission('0',new CanManager("TEST_CAN")).readyToStart());
        assertFalse(new ManualTransmission('1',new CanManager("TEST_CAN")).readyToStart());
        assertFalse(new ManualTransmission('2',new CanManager("TEST_CAN")).readyToStart());
        assertFalse(new ManualTransmission('3',new CanManager("TEST_CAN")).readyToStart());
        assertFalse(new ManualTransmission('4',new CanManager("TEST_CAN")).readyToStart());
        assertFalse(new ManualTransmission('5',new CanManager("TEST_CAN")).readyToStart());
        assertFalse(new ManualTransmission('6',new CanManager("TEST_CAN")).readyToStart());
    }

}