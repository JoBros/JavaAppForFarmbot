package Main;


import Main.Functions.Communictaion.Database;
import Main.Functions.Communictaion.toServer;
import Main.Functions.logic.TimerForSensors;
import Main.Functions.worker.worker;

public class Main {

    public static void main(String[] args) {
        //Logger Schnittstelle Step1 -> JKA und CKU am 03.02.2021
        Logger lt = new Logger();

        //Aufbauen der Connection Step2 -> JKA und CKU am 03.02.2021
        Database db = new Database();
        toServer ts = new toServer();

        //SensorChip soll mit einem Timer abgefragt werden:
        TimerForSensors tfs = new TimerForSensors();
        tfs.run();

        //Hiermit werden live Eingaben aus dem Webserver abgearbeitet
        worker w = new worker();
        w.work();

    }
}