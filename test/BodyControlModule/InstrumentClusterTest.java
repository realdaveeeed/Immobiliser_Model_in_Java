package BodyControlModule;

import com.BodyControlModule.InstrumentCluster;
import com.CanBus.CanBusMess;
import com.CanBus.CanManager;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InstrumentClusterTest {
    private final InstrumentCluster instrumentCluster = new InstrumentCluster(new CanManager("TEST_CAN"));
    @Test
    public void testBatteryVoltageReadoutUpdate() {
        Float correctVoltage = 13.9f;
        CanBusMess testMess = new CanBusMess("BATTERY","LS_CAN","BATT_DATA",Float.toString(correctVoltage));
        instrumentCluster.updateBatteryReadout(testMess);
        assertEquals(correctVoltage,instrumentCluster.getBatteryVoltage());
    }

    @Test
    public void testEngineRPMReadoutUpdate() {
        int correctRPM = 983;
        CanBusMess testMess = new CanBusMess("BATTERY","LS_CAN","RPM_DATA",Integer.toString(correctRPM));
        instrumentCluster.updateRPMReadout(testMess);
        assertEquals(correctRPM,instrumentCluster.getEngineRPM());
    }

    @Test
    public void testTransmissionpDataReadoutUpdate() {
        int correctSpeed = 0;
        char correctGear = 'M';
        CanBusMess testMess = new CanBusMess("BATTERY","LS_CAN","TRANS_DATA",Integer.toString(correctSpeed)+","+Character.toString(correctGear));
        instrumentCluster.updateTransmissionReadout(testMess);
        assertEquals(correctSpeed,instrumentCluster.getSpeed());
        assertEquals(correctGear,instrumentCluster.getGearData());
    }

}