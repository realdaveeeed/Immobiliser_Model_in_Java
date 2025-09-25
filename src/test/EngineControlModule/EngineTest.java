package test.EngineControlModule;

import com.CanBus.CanManager;
import com.EngineControlModule.Engine;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EngineTest {
    @Test
    public void testEngine() {
        var testEngine = new Engine(new CanManager("TEST_CAN"),1);
        assertTrue(isBetween750and1000(testEngine.refreshRPM()));
        assertTrue(isBetween750and1000(testEngine.refreshRPM()));
        assertTrue(isBetween750and1000(testEngine.refreshRPM()));
        assertTrue(isBetween750and1000(testEngine.refreshRPM()));
    }
    public boolean isBetween750and1000(int x) {return 750  <= x && x <= 1000;}
}