package com.CanBus;

import com.Tools.Logger;

import java.util.ArrayList;

public class CanManager implements CanBus{
    private final ArrayList<CanDevice> parts = new ArrayList<>();
    private final String name;

    public CanManager(String name){this.name=name;}

    @Override
    public void addDevice(CanDevice device){ parts.add(device); }

    public void sendMess(CanBusMess message){
        Logger.log("["+name+"]: "+message.getSenderID()+" to "+message.getDestinationArea()+", CODE: "+message.getMessageType());
        for(CanDevice device : parts) {
            if (!message.getSenderID().equals(device.getDeviceID())) {
                if (message.getDestinationArea().equals(device.getCanArea())){
                    device.receiveMess(message);
                } else if (message.getDestinationArea().equals("ALL")) {
                    device.receiveMess(message);
                }
            }
        }
    }
    public CanDevice getDevice(int index){
        return parts.get(index);
    }
}
