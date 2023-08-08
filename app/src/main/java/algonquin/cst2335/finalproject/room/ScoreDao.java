package algonquin.cst2335.finalproject.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) interface for interacting with the ScoreEntity table in the database.
 */
@Dao
public interface ScoreDao {

    /**
     * Insert a new score or update an existing one into the ScoreEntity table.
     *
     * @param scoreEntity The score entity to be inserted or updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScore(ScoreEntity scoreEntity);

    /**
     * Retrieve a list of the latest scores from the ScoreEntity table.
     *
     * @return A list of ScoreEntity objects representing the latest scores.
     */
    @Query("select * from Score order by timestamp desc limit 10")
    List<ScoreEntity> getAll();
}
