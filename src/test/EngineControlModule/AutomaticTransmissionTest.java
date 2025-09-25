package test.EngineControlModule;

import com.CanBus.CanManager;
import com.EngineControlModule.AutomaticTransmission;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AutomaticTransmissionTest {
    @Test
    public void AutomaticTransmissionTest(){
        assertTrue(new AutomaticTransmission('P',new CanManager("TEST_CAN")).readyToStart());
        assertFalse(new AutomaticTransmission('D',new CanManager("TEST_CAN")).readyToStart());
        assertTrue(new AutomaticTransmission('N',new CanManager("TEST_CAN")).readyToStart());

    }

}