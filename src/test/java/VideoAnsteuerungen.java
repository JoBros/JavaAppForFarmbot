import Main.Functions.Communictaion.toServer;
import javafx.scene.chart.ScatterChart;
import org.junit.Test;
import Main.Functions.Communictaion.toServer;
import org.junit.Test;
import java.io.IOException;
import static java.lang.Thread.sleep;

public class VideoAnsteuerungen {
        @Test
        public void HarkenTest() throws InterruptedException {
            //hier Ablaufplan:

            links();




        }
        public void links(){
            toServer x;
            x = new toServer();
            System.out.println("starten der Anwendung");
            try {
                for (int i = 0; i < 15; i++) {
                    x.schreibeNachricht(x.getZ_Ray_ComPort(), "D");
                    System.out.println("XRichtung: " + x.leseNachricht(x.getZ_Ray_ComPort()));
                }
                System.out.println("next");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void hoch(){
            toServer x = null;
            //Database db = new Database();
            x = new toServer();
            System.out.println("starten der Anwendung");
            try {
                for(int steps = 0; steps < 100; steps++) {
                    x.schreibeNachricht(x.getY_Ray_ComPort(), "Q");
                    System.out.println("ZRichtung: " + x.leseNachricht(x.getY_Ray_ComPort()) + String.valueOf(steps));

            }}catch (Exception e){
                System.out.println("Hier könnte Ihre Werbung stehen!");
                e.printStackTrace();
            }
        }
    public void runter(){
        toServer x = null;
        //Database db = new Database();
        x = new toServer();
        System.out.println("starten der Anwendung");
        try {
            for(int steps = 0; steps < 100; steps++) {
                x.schreibeNachricht(x.getY_Ray_ComPort(), "E");
                System.out.println("ZRichtung: " + x.leseNachricht(x.getY_Ray_ComPort()) + String.valueOf(steps));

            }}catch (Exception e){
            System.out.println("Hier könnte Ihre Werbung stehen!");
            e.printStackTrace();
        }
    }
    public void schritt(){
            toServer x = null;
            //Database db = new Database();
            x = new toServer();
            System.out.println("starten der Anwendung");
            try {
                for(int steps = 0; steps < 1000; steps++){
                    System.out.println("starten der Anwendung1");
                    x.schreibeNachricht(x.getX_Ray_ComPort(), "D");
                    System.out.println("starten der Anwendung2");
                    System.out.println("XRichtung: " +x.leseNachricht(x.getX_Ray_ComPort()));
                }
            } catch (Exception e) {
                System.out.println("Hier könnte ihre Werbung stehen!");
            }
        try {
            sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        x = new toServer();
        System.out.println("starten der Anwendung");
        try {
            for (int i = 0; i < 120; i++) {
                System.out.println("starten der Anwendung1");
                x.schreibeNachricht(x.getY_Ray_ComPort(), "A");
                System.out.println("starten der Anwendung2");
                System.out.println("XRichtung: " + x.leseNachricht(x.getX_Ray_ComPort()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void harken(){
        toServer x = null;
        //Database db = new Database();
        x = new toServer();
        System.out.println("starten der Anwendung");
        try {
            x.schreibeNachricht(x.getH_Ray_ComPort(), "w");
            sleep(8000);
            x.leseNachricht(x.getH_Ray_ComPort());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void AusgleichenSaat() {//Machen!
    }


    public void saehen(){
        toServer x = null;
        //Database db = new Database();
        x = new toServer();
        System.out.println("starten der Anwendung");
        try {
            for (int i = 0; i < 120; i++) {
                System.out.println("starten der Anwendung1");
                x.schreibeNachricht(x.getW_Ray_ComPort(), "S");
                System.out.println("starten der Anwendung2");
                System.out.println("XRichtung: " + x.leseNachricht(x.getW_Ray_ComPort()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ausgleichWasser(){
    }
    public void wasser(){
        toServer x = null;
        //Database db = new Database();
        x = new toServer();
        System.out.println("starten der Anwendung");
        try {
            for (int i = 0; i < 700; i++) {
                System.out.println("starten der Anwendung1");
                x.schreibeNachricht(x.getW_Ray_ComPort(), "W");
                System.out.println("starten der Anwendung2");
                System.out.println("XRichtung: " + x.leseNachricht(x.getW_Ray_ComPort()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
