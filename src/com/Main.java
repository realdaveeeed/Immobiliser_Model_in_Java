package com;

import com.BodyControlModule.Key;
import com.Tools.Logger;

import java.util.Scanner;

public class Main {
    private static String temp;
    public static boolean saveFile = false;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Logger.displayLogs = args[0].equals("true");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.print("No args detected, do you want to see logs in the console? (y/n): ");
            temp = sc.nextLine();
            if (temp.equals("y")) { Logger.displayLogs = true; }
            else if (temp.equals("n")) { Logger.displayLogs = false;}
        }
        System.out.print("Would you like to save the logs? (y/n): ");
        String temp = sc.nextLine();
        if (temp.equals("y")) { saveFile = true; }

        System.out.print("Please enter the code phrase you want to use for the immobiliser: ");
        String codePhrase = sc.nextLine();
        Vehicle testVeh = new Vehicle(codePhrase, 5,'M','0');

        System.out.print("Please enter the code phrase you want to use for the keyfob: ");
        codePhrase = sc.nextLine();
        Key key = new Key(codePhrase);
        testVeh.unlockVehicle();
        System.out.print("\n");

        System.out.print("Key inserted");
        testVeh.insertKey(key);
        while(true){
            System.out.print("\nAlarm is armed, you have 20 seconds to start!\nTo start the vehicle, type 'START':  ");
            temp = sc.nextLine();
            System.out.print("\n");
            if (temp.equals("START")) {
                testVeh.tryStart();
                break;
            }
        }
        sc.close();
        if (saveFile) { Logger.saveLogs(); }
    }
}