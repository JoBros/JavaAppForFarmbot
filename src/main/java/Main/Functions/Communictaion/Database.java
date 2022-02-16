package Main.Functions.Communictaion;

import Constants.AktualKoodinates;
import Constants.DATABASE;
import Constants.NETWORK;
import Main.Functions.Koodinates;
import Main.Logger;
import Main.Model.Pflanze;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    //Constants for Connection
    final String toTable =  DATABASE.db_ip + ":3306/Farmbot";
    final String user = DATABASE.username;
    final String password = DATABASE.password;

    //Member Variables
    Connection myConn;



    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            new Logger().logError(e.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            new Logger().logError(e.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Logger().logError(e.toString());
        }
        //Connect
        try {
           System.out.println("jdbc:mysql:/" + toTable);
            myConn = DriverManager.getConnection("jdbc:mysql://"+ toTable +"?" + "user=" + user + "&password="+password +"");//DriverManager.getConnection("jdbc:mysql:/" + toTable, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            new Logger().logError(throwables.toString());
        }
    }
    
    public void eintragMessdaten(String st) throws SQLException {
        String help = "";
        for(int j = 0; j < st.length(); j++){
            char r = st.charAt(j);
            if( r != '\r' && r != '\n') {
                help = help + r;
                continue;
            }

        }

        if(help.equals("")){
            return;
        }
        JSONObject obj = null;
        try {
            JSONParser parser = new JSONParser();
            obj = (JSONObject) parser.parse(help.toString());
        } catch (Exception e) {
            e.printStackTrace();
            new Logger().logError(e.toString());
            return;
        }
        String value = obj.get("V").toString();
        String sensor = (String) obj.get("T");
        String t = "INSERT INTO Farmbot.SensorDat (sensor, value, created_at) VALUES ('" + sensor + "'," + value + " ,current_timestamp);";
        //String t = "INSERT INTO SensorDat ('value', 'sensor') VALUES ("+ value +", '"+sensor+"');";
        Statement myStat = myConn.createStatement();
        int reSe = myStat.executeUpdate(t);
    }

    private String getRay(String t) {
        return NETWORK.X_RAY_IP + NETWORK.X_RAY_PORT;
    }

    private String getUnit(Koodinates k) {
        return "%";
    }

    //INSERT INTO `Messdaten` (`K`, `Date`, `X`, `Y`, `Z`, `T`, `Value_1`, `Value_2`, `Value_3`, `Value_4`) VALUES (NULL, CURRENT_TIMESTAMP, '30', '80', '150', 'Temp', '30', 'C', '-', '-');

    public Object[] readAllFromTable(String table){
        try{
            //Statement
            Statement myStat = myConn.createStatement();

            //Execute SQL Query
            ResultSet reSe = myStat.executeQuery("select * from " + table );
            /*
            Remember should implement Object in every Used Class to could get all the Variables Back.
            */
            ArrayList<Object> o = new ArrayList<Object>();
            while(reSe.next()){
                //adde hier je Tabelle einen sortiertyp!
                if( table.equals("SpecificTable")){
                    o.add(specificTable(reSe));
                }
                if(table.equals("PflanzenPos")){
                    o.add(getPlants(reSe));
                }
            }
            return o.toArray();
        }catch(Exception e){
            e.printStackTrace();
            new Logger().logError(e.toString());
            return null;
        }
    }

    private Pflanze getPlants(ResultSet reSe) throws SQLException {
        return new Pflanze( reSe.getInt("x"), reSe.getInt("y"), reSe.getString("bez") );
    }

    //füge hier einfach die anderen Tabellen als Objekte hinzu!

    private Object specificTable(ResultSet reSe) throws SQLException {
        //Beispielsortiertyp, klar der Returnwert muss ein echtes Objekt per Uppcast sein!
        String id = reSe.getString("ID");
        String logAs = reSe.getString("LogAs");
        return null;
    }

}
