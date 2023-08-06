package algonquin.cst2335.finalproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class accepts the data from the json object
 */
@Entity
public class NameOfflight {
    /**
     * Name of flight
     */
    @ColumnInfo(name = "Flight name")
    String flightName;
    /**
     * How late is the flight
     */
    @ColumnInfo(name= "Delay")
    String delay;
    /**
     * The gate of the plane
     */
    @ColumnInfo(name="Gate")
    String gate;
    /**
     * The drop off terminal
     */
    @ColumnInfo(name="Terminal")
    String Terminal;
    /**
     * The flights destination
     */
    @ColumnInfo(name="Destination")
    String destination;
    /**
     * Id used in the database
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public long id;

    /**
     * Constructor accepts the name of flight
     * @param nof is the name of flight
     */
    public NameOfflight(String nof){
        flightName=nof;
    }

    /**
     * Constructor sets flight name, destination, gate, terminal and delay of the flight
     * @param nof flight name
     * @param D destination
     * @param t terminal
     * @param g gate
     * @param d delay
     */
    public  NameOfflight( String nof,String D, String t, String g, String d){
        flightName=nof;
        destination=D;
        Terminal=t;
        gate=g;
        delay=d;

    }

    /**
     * Returns the name of flight that has been set
     * @return the name of flight
     */
    public String getFlightName(){return flightName;}


}
