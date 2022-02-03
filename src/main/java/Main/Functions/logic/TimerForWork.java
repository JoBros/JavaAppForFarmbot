package Main.Functions.logic;

import Constants.TIMERFORSENSOR;
import Main.Functions.Communictaion.Database;
import Main.Functions.Communictaion.toServer;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class TimerForWork implements Runnable{
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
        Database db = new Database();
        toServer toS = new toServer();
        String getted = null;

    }
    private boolean toGrundposition(){
        try{
            toServer x = new toServer();
            x.schreibeNachricht(x.getZ_Ray_ComPort(), "G");
            System.out.println("ZRichtung " + x.leseNachricht(x.getZ_Ray_ComPort()));
            x.schreibeNachricht(x.getX_Ray_ComPort(), "G");
            System.out.println("XRichtung " + x.leseNachricht(x.getX_Ray_ComPort()));
            x.schreibeNachricht(x.getY_Ray_ComPort(), "G");
            System.out.println("YRichtung " + x.leseNachricht(x.getY_Ray_ComPort()));
        }catch(Exception Jonas){
            System.out.println("Es kam zu einem Fehler, nochmal");
            return false;
        }
        return true;
    }

    private boolean haken() {
        toServer t = new toServer();
        try {
            t.schreibeNachricht(t.getW_Ray_ComPort(), "H");
            t.leseNachricht(t.getW_Ray_ComPort());
        }catch (Exception e){
            return false;
        }
        return true;
    }
    private boolean gießen(int time) throws IOException {
        toServer t = new toServer();
        for(int i = 0; i < time; i ++) {
            try {
                t.schreibeNachricht(t.getW_Ray_ComPort(), "W");
                t.leseNachricht(t.getW_Ray_ComPort());
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
    private void toX(int i) throws IOException {
        toServer t = new toServer();
        fahre(i,t.getX_Ray_ComPort(), 'x' );
    }
    private void toY(int i) throws IOException {
        toServer t = new toServer();
        fahre(i,t.getY_Ray_ComPort(), 'y' );
    }
    private void toZ(int i) throws IOException {
        toServer t = new toServer();
        fahre(i,t.getZ_Ray_ComPort(), 'z' );
    }
    private void fahre(int i, Socket sock, char achs) throws IOException {
        String hin, her;
        switch(achs){
            case 'x':
            case 'y':
                hin = "A";
                her = "D";
                break;
            case 'z':
                hin = "E";
                her = "Q";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + achs);
        }
        int pos = 0;
        toServer t = new toServer();
        while(true){
            if(pos == 0) {
                t.schreibeNachricht(sock, hin);
                String s = t.leseNachricht(sock);
                pos = Integer.getInteger(s);
            }else if(pos < i){
                t.schreibeNachricht(sock, hin);
                String s = t.leseNachricht(sock);
                pos = Integer.getInteger(s);
            }else if(pos > i){
                t.schreibeNachricht(sock, her);
                String s = t.leseNachricht(sock);
                pos = Integer.getInteger(s);
            }
            else {
                return;
            }
        }
    }
}
