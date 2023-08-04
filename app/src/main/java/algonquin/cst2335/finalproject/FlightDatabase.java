package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Dao
@Database(entities = {FlightDetails.class}, version = 1)

public abstract class FlightDatabase extends RoomDatabase {
    public abstract FlightDAO flightDAO();


}
