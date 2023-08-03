package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedImagesDao {
    @Query("SELECT * FROM SavedImages")
    List<SavedImages> getAll();

    @Insert
    void insert(SavedImages image);

    @Delete
    void delete(SavedImages image);

}