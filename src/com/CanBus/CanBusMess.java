package com.CanBus;

public class CanBusMess {
    private final String senderID;
    private final String destinationArea;
    private final String messageType;
    private final String data;

    public CanBusMess(String senderID,String destinationArea, String messageType, String data) {
        this.senderID = senderID;
        this.messageType = messageType;
        this.data = data;
        this.destinationArea = destinationArea;
    }

    public CanBusMess(String senderID, String destinationArea, String messageType) {
        this.senderID = senderID;
        this.messageType = messageType;
        this.destinationArea = destinationArea;
        this.data = "";
    }

    public String getMessageType(){ return messageType; }
    public String getData(){ return this.data; }
    public String getDestinationArea(){ return this.destinationArea; }
    public String getSenderID(){ return this.senderID; }
}
