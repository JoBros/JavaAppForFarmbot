import Main.Functions.Communictaion.toServer;
import org.junit.Test;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class HarkenTest {

    @Test
    public void HarkenTest(){

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
        for(int i = 0; i < 120; i++){
            System.out.println("starten der Anwendung1");
            x.schreibeNachricht(x.getY_Ray_ComPort(), "A");
            System.out.println("starten der Anwendung2");
            System.out.println("XRichtung: " +x.leseNachricht(x.getX_Ray_ComPort()));
        }
        sleep(1000);

        for(int steps = 0; steps < 100; steps++) {
            x.schreibeNachricht(x.getZ_Ray_ComPort(), "Q");
            System.out.println("ZRichtung: " + x.leseNachricht(x.getZ_Ray_ComPort()) + String.valueOf(steps));
        }
        //x.schreibeNachricht(x.getH_Ray_ComPort(), "w");
        //sleep(8000);
        //x.leseNachricht(x.getH_Ray_ComPort());
        x = new toServer();
        for(int steps = 0; steps < 150; steps++) {
            x.schreibeNachricht(x.getZ_Ray_ComPort(), "E");
            System.out.println("ZRichtung" + x.leseNachricht(x.getZ_Ray_ComPort()) + String.valueOf(steps));

        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
