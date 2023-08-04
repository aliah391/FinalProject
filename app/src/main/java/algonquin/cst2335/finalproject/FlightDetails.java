package algonquin.cst2335.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FlightDetails {
@ColumnInfo(name="date")
    String date;
@ColumnInfo(name="time")
    String time;
@ColumnInfo(name="flight name")
  String  flightName;
@ColumnInfo(name="departure")
    String departureinfo;
    @PrimaryKey(autoGenerate = true)
            @ColumnInfo(name="id")
int id;
    @ColumnInfo(name="airportCode button")
    Boolean airportCodeButton;


    public String getDate(){return  date;}
    public String getTime(){return time;}
    public String getFlightName(){return flightName;}
    public String getDepInfo(){return departureinfo;}

public FlightDetails(String f, Boolean a){
        flightName = f;
        airportCodeButton = a;
}
    public FlightDetails(String d, String t, String di){
        date = d;
        time = t;
        departureinfo = di;
    }
    public FlightDetails(){

    }

}
