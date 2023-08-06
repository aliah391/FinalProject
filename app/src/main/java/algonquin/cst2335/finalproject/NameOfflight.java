package algonquin.cst2335.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NameOfflight {
    @ColumnInfo(name = "Flight name")
    String flightName;
    @ColumnInfo(name= "Delay")
    String delay;
    @ColumnInfo(name="Gate")
    String gate;
    @ColumnInfo(name="Terminal")
    String Terminal;
    @ColumnInfo(name="Destination")
    String destination;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public long id;
    public NameOfflight(String nof){
        flightName=nof;
    }

    public  NameOfflight( String nof,String D, String t, String g, String d){
        flightName=nof;
        destination=D;
        Terminal=t;
        gate=g;
        delay=d;

    }

    public String getFlightName(){return flightName;}
//    public String getDelay(String d){return delay=d;}
//    public String getGate(String g){return gate=g;}
//    public String getTerminal(String t){return Terminal=t;}
//    public String getDestination(String D){return destination=D;}


}
