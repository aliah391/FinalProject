package algonquin.cst2335.finalproject.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Database class that defines the database and its version.
 */
@Database(entities = {ScoreEntity.class}, version = 1, exportSchema = false)
public abstract class ScoreDatabase extends RoomDatabase {

    /**
     * Get the Data Access Object (DAO) for ScoreEntity operations.
     *
     * @return The ScoreDao for interacting with ScoreEntity.
     */
    public abstract ScoreDao getScoreDao();
}
