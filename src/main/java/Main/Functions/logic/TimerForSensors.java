package Main.Functions.logic;
import Constants.NETWORK;
import Constants.TIMERFORSENSOR;
import Main.Functions.Communictaion.Database;
import Main.Functions.Communictaion.toServer;
import Main.Logger;
import Main.Model.Pflanze;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.Animation;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TimerForSensors implements Runnable{
    static public class toServer {

        static Socket W_Ray_ComPort = null;
        //Method Areas
        public toServer(){
            try {
                if (W_Ray_ComPort == null || !W_Ray_ComPort.isConnected()){
                    W_Ray_ComPort = new Socket(NETWORK.SENSORCHIP, NETWORK.W_RAY_PORT);
                    System.out.println(W_Ray_ComPort.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public String leseNachricht(java.net.Socket socket) throws IOException{
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));
            char[] buffer = new char[200];
            int anzahlZeichen = bufferedReader.read(buffer, 0, 200); // blockiert bis Nachricht empfangen
            String nachricht = new String(buffer, 0, anzahlZeichen);
            //X_Ray_ComPort.close();
            return nachricht;
        }

        public void schreibeNachricht(java.net.Socket socket, String nachricht) throws IOException {
            PrintWriter printWriter =
                    new PrintWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream()));
            printWriter.print(nachricht);
            printWriter.flush();
        }
        public Socket getW_Ray_ComPort() { return W_Ray_ComPort;   }
    }


        //Database db = new Database();
        //toServer toS = new toServer();

        Timer timeline;
        @Override
        public void run(){
            timersToDo();
        }

        public void timersToDo(){
            timeline = new Timer();
            TimerTask t = new TimerTask() {
                @Override
                public void run() {
                    try {
                        timerDo();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            timeline.scheduleAtFixedRate(t, TIMERFORSENSOR.delay, TIMERFORSENSOR.period);//Er wird hiermit sekündlich ausgeführt.

        }
        public void timerDo() throws IOException, SQLException {
            new Logger().logInfo("Sensordaten abrufen.");
            Database db = new Database();
            toServer toS = new toServer();
            String getted = null;
            toS.schreibeNachricht(toS.getW_Ray_ComPort(), "V");
            getted =  toS.leseNachricht(toS.getW_Ray_ComPort());
            db.eintragMessdaten(getted);
            System.out.println("Sekunde");
            System.out.println(LocalDateTime.now());
            db = new Database();
            toS = new toServer();
            getted = null;
            toS.schreibeNachricht(toS.getW_Ray_ComPort(), "L");
            getted =  toS.leseNachricht(toS.getW_Ray_ComPort());
            db.eintragMessdaten(getted);
            System.out.println("Sekunde");
            System.out.println(LocalDateTime.now());
            db = new Database();
            toS = new toServer();
            getted = null;
            toS.schreibeNachricht(toS.getW_Ray_ComPort(), "T");
            getted =  toS.leseNachricht(toS.getW_Ray_ComPort());
            db.eintragMessdaten(getted);
            System.out.println("Sekunde");
            System.out.println(LocalDateTime.now());
            new Logger().logInfo("Sensordaten erfolgreich abgerufen.");
        }
    }

