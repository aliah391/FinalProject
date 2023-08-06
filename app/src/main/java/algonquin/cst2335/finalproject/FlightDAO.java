package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface FlightDAO {
    @Insert
 public long insertFlightDetails(NameOfflight m);
    @Query("Select * from NameOfflight")
    public List<NameOfflight> getAllFlightDetails();
    @Delete

    void  deleteFlightDetails(NameOfflight m);
}
