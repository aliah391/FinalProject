package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Interface that sets methods for the implementing class to override
 */
@Dao
public interface FlightDAO {
    /**
     * Inserts data into the database
     * @param m a value that is stored
     * @return a value of type long
     */
    @Insert
 public long insertFlightDetails(NameOfflight m);

    /**
     * Displays all the data stored in the database
     * @return a list of the data stored
     */
    @Query("Select * from NameOfflight")
    public List<NameOfflight> getAllFlightDetails();

    /**
     * Deletes the selected data from the database
     * @param m value to be deleted
     */
    @Delete
    void  deleteFlightDetails(NameOfflight m);
}
