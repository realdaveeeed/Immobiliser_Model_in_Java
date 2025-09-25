package com.Tools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Logger {
    public static boolean displayLogs = false;
    private static ArrayList<String> messageLogs = new ArrayList<String>();
    private final static LocalDate today = LocalDate.now();
    private final static String logName = "IMMOBILISER_SIMULATION_"+today.getYear()+"_"+today.getMonth()+"_"+today.getDayOfMonth()+".txt";
    public static void log(String message){
        messageLogs.add(message);
        if(displayLogs){ System.out.println(message);}
    }

    public static void saveLogs() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logName))) {
            writer.write("Simulation-log: \n\n");
            for (String log : messageLogs) {
                writer.write(log);
                writer.newLine();
            }
            writer.write("---END OF LOG----\n");
            System.out.println("\nLog saved to '" + logName+"'");
        } catch (IOException e) { System.err.println("Failed to save logs: " + e.getMessage()); }
    }
}
