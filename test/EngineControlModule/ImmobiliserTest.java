package EngineControlModule;

import com.CanBus.CanManager;
import com.EngineControlModule.Immobiliser;
import com.Tools.Encrypt;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmobiliserTest {
    @Test
    public void testImmobiliserKeyMatching(){
        assertFalse(new Immobiliser("JUNITTEST",new CanManager("TEST_CAN")).checkCodePhrase("JUNITTEST"));
        assertFalse(new Immobiliser("BAD",new CanManager("TEST_CAN")).checkCodePhrase("GOOD"));
        assertTrue(new Immobiliser("JUNITTEST",new CanManager("TEST_CAN")).checkCodePhrase(Encrypt.encrypt("JUNITTEST")));
    }

}