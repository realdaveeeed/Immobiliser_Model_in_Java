package com.CanBus;

public interface CanBus {
    void addDevice(CanDevice device);
    void sendMess(CanBusMess message);
}