package algonquin.cst2335.finalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ConversionQueryDao {
    @Query("SELECT * FROM conversion_query")
    LiveData<List<ConversionQuery>> getAllQueries();

    @Insert
    void insert(ConversionQuery query);

    @Delete
    void delete(ConversionQuery query);

}
