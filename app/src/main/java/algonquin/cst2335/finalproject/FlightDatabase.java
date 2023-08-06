package algonquin.cst2335.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {NameOfflight.class}, version = 1)
public abstract class FlightDatabase extends RoomDatabase {
    public FlightDatabase() { }
    public abstract FlightDAO flightDAO();
}
