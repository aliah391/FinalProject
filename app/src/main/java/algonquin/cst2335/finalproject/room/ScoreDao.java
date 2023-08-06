package algonquin.cst2335.finalproject.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScore(ScoreEntity scoreEntity);

    @Query("select * from Score order by timestamp desc limit 10")
    List<ScoreEntity> getAll();
}
