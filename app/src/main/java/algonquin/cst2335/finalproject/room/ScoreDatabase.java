package algonquin.cst2335.finalproject.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ScoreEntity.class}, version = 1,exportSchema = false)
public abstract class ScoreDatabase extends RoomDatabase {
    public abstract ScoreDao getScoreDao();
}
