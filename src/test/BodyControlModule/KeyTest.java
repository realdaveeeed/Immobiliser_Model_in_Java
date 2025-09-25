package test.BodyControlModule;

import com.BodyControlModule.Key;
import com.Tools.Encrypt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {
    @Test
    void keyEncryptionTest(){
        var key = new Key("JUNITEST");
        var encrypted = Encrypt.encrypt("JUNITEST");

        assertEquals(encrypted,key.getEncryptedKey());
        assertNotEquals("JUNITTEST",key.getEncryptedKey());
    }

}