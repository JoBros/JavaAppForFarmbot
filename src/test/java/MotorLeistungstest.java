import Constants.NETWORK;
import Main.Functions.Communictaion.toServer;
import Main.Logger;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class MotorLeistungstest {
        @Test
        public void MotorTestVersuchsreiheEins_ErsterTest() {
            toServer t = null;
            //Database db = new Database();

            t = new toServer();
            try {
                int i = 0;
                while (true ) {
                    t.schreibeNachricht(t.getX_Ray_ComPort(), "A");
                    t.leseNachricht(t.getX_Ray_ComPort());
                    i++;
                    t.schreibeNachricht(t.getX_Ray_ComPort(), "D");
                    t.leseNachricht(t.getX_Ray_ComPort());
                    i++;
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    public class toServer {
        //Deklarations
        Socket test_Ray_ComPort = null;
        //Method Areas

        public toServer(){
            try {
                if (test_Ray_ComPort == null || !test_Ray_ComPort.isConnected()){
                    test_Ray_ComPort = new Socket("192.168.186.36", 9012);
                    System.out.println(test_Ray_ComPort.toString());
                    new Logger().logInfo(test_Ray_ComPort.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
                new Logger().logError("Chip nicht Connected: " + e.toString());
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

        //Getter and Setter Methods
        public Socket getX_Ray_ComPort() { return test_Ray_ComPort;   }
    }

}
