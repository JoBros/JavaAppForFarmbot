package Main.Functions.worker;

/*
 * 1. Task aus Liste nehmen
 * 1.a Task interpretieren
 * 2. Kommunikation zum Chip aufbauen
 * 3. Task in für Chip verständilches Fromat übersetzen
 * 4. Task versenden
 * 5. Auf Antwort warten
 * 6. Neuen Befehl abarbeiten/erwarten
 */

import Main.Functions.Communictaion.toServer;
import Main.Functions.Koodinates;
import Main.Functions.Read;
import Main.Functions.engine;
import Main.Logger;
import Main.Model.Tasks;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class worker{
    Tasks tasks;
    Koodinates k = null;
    engine e = new engine(0,0,0);

    boolean engineStop = false;


    public void work() {
        this.tasks = new Tasks();
        System.out.println("Das System wurde gestartet!");
        dateieinlesenWorker();
        Runnable task1 = () -> {
            toServer x = null;
                x = new toServer();
            while(ifWork()) {
                k = getNext();
                try {
                    if (k.getX() != 0 || k.getY() != 0 || k.getZ() != 0 || (!k.getT().equals(""))) {
                        System.out.println(k.getX());
                        if (k.getY() != 0) {
                            try {
                                System.out.print(k.getY() < 0 ? "A" : "D");
                                x.schreibeNachricht(x.getY_Ray_ComPort(), k.getY() < 0 ? "A" : "D");
                                String t = null;
                                while(t == null) {
                                    t = x.leseNachricht(x.getY_Ray_ComPort());
                                    System.out.println("YRichtung" + t);
                                }
                                new Logger().logError("YRichtung complete");
                            } catch (IOException ioException) {
                              ioException.printStackTrace();
                              new Logger().logError(ioException.getCause().toString());
                              new Logger().logError(ioException.getCause().toString());
                           }
                        }
                        if (k.getX() != 0) {
                            try {
                                x.schreibeNachricht(x.getX_Ray_ComPort(), k.getX() > 0 ? "A" : "D");
                                String t = null;
                                while(t == null) {
                                    t = x.leseNachricht(x.getX_Ray_ComPort());
                                    System.out.println("XRichtung" + t);
                                }
                                new Logger().logError("XRichtung complete");
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                                new Logger().logError(ioException.getCause().toString());
                                new Logger().logError(ioException.getCause().toString());
                            }
                        }
                        if (k.getZ() != 0) {
                            try {
                                x.schreibeNachricht(x.getZ_Ray_ComPort(),k.getZ() > 0 ? "Q" : "E");
                                String t = null;
                                while(t == null) {
                                    t = x.leseNachricht(x.getZ_Ray_ComPort());
                                    System.out.println("ZRichtung" + t);
                                }
                                new Logger().logError("ZRichtung complete");
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                                new Logger().logError(ioException.getCause().toString());
                                new Logger().logError(ioException.getCause().toString());
                            }
                        }
                        if(!k.getT().equals("")){
                            try {
                                String t = k.getT();
                                switch (t) {
                                    case "w":
                                        x.schreibeNachricht(x.getW_Ray_ComPort(), "W");
                                        String message = null;
                                        while(message == null) {
                                            message = x.leseNachricht(x.getW_Ray_ComPort());
                                        }
                                        System.out.print(message);
                                        new Logger().logInfo(message);
                                        break;
                                    case "h":
                                        for(int steps = 0; steps < 250; steps++) {
                                            x.schreibeNachricht(x.getZ_Ray_ComPort(), "Q");
                                            String message2 = null;
                                            while(message2 == null) {
                                                message2 =  x.leseNachricht(x.getZ_Ray_ComPort());
                                            }
                                            System.out.println("ZRichtung" + message2);
                                        }
                                        x.schreibeNachricht(x.getH_Ray_ComPort(), "w");
                                        String message2 = null;
                                        while(message2 == null) {
                                           try {
                                               x.leseNachricht(x.getH_Ray_ComPort());
                                           } catch (Exception w) {
                                               new Logger().logError("Zeitüberschreitung beim Harken.");
                                           }
                                        }
                                        for(int steps = 0; steps < 350; steps++) {
                                            x.schreibeNachricht(x.getZ_Ray_ComPort(), "E");
                                            System.out.println("-ZRichtung" + x.leseNachricht(x.getZ_Ray_ComPort()));
                                        }
                                        break;
                                    case "P":
                                        x.schreibeNachricht(x.getW_Ray_ComPort(), "S");
                                        x.leseNachricht(x.getW_Ray_ComPort());
                                        break;
                                    default:
                                        break;
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                                new Logger().logError(e.getCause().toString());
                                new Logger().logError(e.getCause().toString());
                            }
                        }
                    } else {
                        sleep(25);
                    }
                } catch(Exception e){
                    System.out.println(e.getCause().toString());
                    new Logger().logError(e.getCause().toString());
                }
                }
        };

        task1.run();
        System.out.println("Das System wurde gestartet!");
        Thread thread = new Thread(task1);
        try {
            thread.join();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        thread.start();

    }

    protected void dateieinlesenWorker(){
        Runnable task2 = () -> {
            System.out.println("Daten eingelesen");
            ArrayList<String> tasklist = new ArrayList<>();
            while(true) {
                try {
                    for (int i = 1; i < 7; i++) {
                        String h = new Read().ladeDatei(String.valueOf(i) + ".control");
                        String t = "";
                        if (!h.equals("")) {
                            new Logger().logInfo(h);
                            int x = 0, y = 0, z = 0;
                            if (h.equals("W")) {
                                x = 1;
                            }
                            if (h.equals("S")) {
                                x = -1;
                            }
                            if (h.equals("A")) {
                                y = 1;
                            }
                            if (h.equals("D")) {
                                y = -1;
                            }
                            if (h.equals("Q")) {
                                z = 1;
                            }
                            if (h.equals("E")) {
                                z = -1;
                            }
                            if( h.equals("w")){// Wasserpumpe einschalten
                                t = "w";
                            }
                            if( h.equals("h")){// Harke ausfahren und einfahren
                                t = "h";
                            }
                            if( h.equals("P")){
                                t = "P";
                            }
                            dotasking(new Koodinates((e.getX() + x), e.getY() + y, e.getZ() + z, t, ""));
                        }
                        sleep(10);
                    }
                }catch (Exception e){

                }
            }
        };
        //task2.run();
        System.out.println("Das System wurde gestartet!");
        Thread thread = new Thread(task2);
        try {
            thread.join();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        thread.start();

    }

    private void dotasking(Koodinates koodinates) {
       tasks.add(koodinates);
    }

    protected Koodinates getNext() {
        Koodinates k = tasks.getNext();
        return k;
    }

    protected boolean compX(Koodinates k) {
        if(k.getX() != 0){
            return true;
        }
        return false;
    }
    protected boolean compY(Koodinates k) {
        if(k.getY() != 0){
            return true;
        }
        return false;
    }
    protected boolean compZ(Koodinates k) {
        if(k.getZ() != 0){
            return true;
        }
        return false;
    }

    protected boolean compDoing(Koodinates k){
        if(k.getT().equals("")){
            return false;
        }
        return true;
    }

    public boolean ifWork(){
        return true;
    }
    public void stopWorker(){

    }
}
