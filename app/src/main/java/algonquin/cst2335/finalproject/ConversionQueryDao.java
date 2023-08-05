package algonquin.cst2335.finalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) for the ConversionQuery class.
 * This interface provides methods for interacting with ConversionQuery data that's stored in a database.
 */
@Dao
public interface ConversionQueryDao {

    /**
     * Retrieves all ConversionQuery objects from the database.
     *
     * @return LiveData object containing a list of all ConversionQueries
     */
    @Query("SELECT * FROM conversion_query")
    LiveData<List<ConversionQuery>> getAllQueries();

    /**
     * Inserts a new ConversionQuery object into the database.
     *
     * @param query the ConversionQuery object to be inserted
     */
    @Insert
    void insert(ConversionQuery query);

    /**
     * Deletes a specific ConversionQuery object from the database.
     *
     * @param query the ConversionQuery object to be deleted
     */
    @Delete
    void delete(ConversionQuery query);
}
