package algonquin.cst2335.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Class stores the database of the saved details
 */
@Database(entities = {NameOfflight.class}, version = 1)
public abstract class FlightDatabase extends RoomDatabase {
    /**
     * No args- constructor that allows for the class to be called
     */
    public FlightDatabase() { }

    /**
     * Calls the flightDAO class
     * @return type Flight DAO
     */
    public abstract FlightDAO flightDAO();


}
