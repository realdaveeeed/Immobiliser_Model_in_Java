package com.CanBus;

public interface CanDevice{
    void receiveMess(CanBusMess message);
    String getDeviceID();
    String getCanArea();
}
