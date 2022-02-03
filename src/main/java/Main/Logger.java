package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;

public class Logger {
    //Logger Schnittstelle Step1 -> JKA und CKU am 03.02.2021

    //Variables Zone

    String s = "Log.txt";
    static PrintWriter pWriter = null;

    //Method Zone

    public Logger(){
        //Step Logfile anlegen
        try{
            if(pWriter == null) {
                pWriter = new PrintWriter(new FileWriter(s, true));
                pWriter.println("Boot:" + LocalDateTime.now().toString());
                pWriter.flush();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }finally {
            pWriter.flush();
            pWriter.close();
        }
    }
    public void logError(String t){
        try {
            pWriter = new PrintWriter(new FileWriter(s, true));
            pWriter.println("Error: " + LocalDateTime.now().toString() + " " + t);
            pWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            pWriter.flush();
            pWriter.close();
        }
        return;
    }
    public void logWarn(String t){
        try {
            pWriter = new PrintWriter(new FileWriter(s, true));
            pWriter.println("Warn: " + LocalDateTime.now().toString() + " " + t);
            pWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            pWriter.flush();
            pWriter.close();
        }
        return;
    }
    public void logInfo(String t){
        try {
            pWriter = new PrintWriter(new FileWriter(s, true));
            pWriter.println("Info: " + LocalDateTime.now().toString() + " " + t);
            pWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            pWriter.flush();
            pWriter.close();
        }
        return;
    }
}
