package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface FlightDAO {

    @Insert
    public long insertFlightDetails(FlightDetails flightToInsert);

    @Query("Select * from FlightDetails")
    public List<FlightDetails> getAllFlights();

    @Delete
    public void deleteFlights(FlightDetails flightToDelete);

}
