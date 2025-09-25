package com.EngineControlModule;

import com.CanBus.CanBusMess;
import com.CanBus.CanManager;
import com.Tools.Logger;
import com.Main;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Alarm extends HS_CAN_DEVICE implements Runnable{
    private AtomicInteger countdown;
    private AtomicBoolean alarmTriggered;
    private AtomicBoolean alarmActive;
    private final CanManager COM;
    private Thread alarmThread;

    public Alarm(CanManager COM,int alarmDuration){
        super("ALARM",COM);
        this.COM = COM;
        this.alarmTriggered = new AtomicBoolean(false);
        this.alarmActive = new AtomicBoolean(false);
        this.countdown = new AtomicInteger(alarmDuration);
        initAlarm();
    }
    private void initAlarm(){
        alarmThread = new Thread(() -> {
            while (alarmActive.get()) {
                try {
                    Thread.sleep(1000);
                    int remaining = countdown.decrementAndGet();

                    if (remaining <= 0) {
                        triggerAlarm();
                        alarmActive.set(false);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
    @Override
    public void receiveMess(CanBusMess message) {
        switch(message.getMessageType()){
            case "KEY_OK":
                run();
                break;
            case "SELF_CHECK":
                close();
                break;
        }
    }

    private void triggerAlarm() {
        Logger.log("["+getDeviceID()+"]: Triggered, starting is unavaliable!");
        System.out.println("\nThe alarm went of, starting is unavaliable!");
        for (int i = 0; i < 10; i++) {
            System.out.println("Beep boop beep boop\n");
            try{Thread.sleep(400);} catch (InterruptedException e){}
        }
        System.out.print("Booom");
        if (Main.saveFile) {Logger.saveLogs();}
        System.exit(1);
    }

    @Override
    public void run() {
        if (!alarmActive.get()) {
            alarmTriggered.set(true);
            alarmActive.set(true);
            alarmThread.start();
        }
    }

    public void close(){
        alarmActive.set(false);
        Logger.log("["+getDeviceID()+"]: Alarm disarmed, "+countdown.get()+" second(s) left on the countdown!");
    }
}