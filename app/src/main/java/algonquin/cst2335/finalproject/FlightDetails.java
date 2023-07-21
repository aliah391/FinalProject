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

@ColumnInfo(name="departure")
    String departureinfo;
    @PrimaryKey(autoGenerate = true)
            @ColumnInfo(name="id")
int id;


    public String getDate(){return  date;}
    public String getTime(){return time;}
    public String getDepInfo(){return departureinfo;}


    public FlightDetails(String d, String t, String di){
        date = d;
        time = t;
        departureinfo = di;
    }
    public FlightDetails(){

    }

}
