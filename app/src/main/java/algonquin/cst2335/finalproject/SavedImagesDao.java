package algonquin.cst2335.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * SavedImagesDao is a Data Access Object (DAO) for accessing the data in the SavedImages table in the Room database.
 * It includes methods for getting all the saved images, inserting a new image, and deleting an image.
 *
 * @author Nikita
 * @version 1.0
 * @since 2023-08-05
 */
@Dao
public interface SavedImagesDao {

    /**
     * Retrieves all images from the SavedImages table.
     *
     * @return A list of all saved images.
     */
    @Query("SELECT * FROM SavedImages")
    List<SavedImages> getAll();

    /**
     * Inserts an image into the SavedImages table.
     *
     * @param image The image to insert.
     */
    @Insert
    void insert(SavedImages image);

    /**
     * Deletes an image from the SavedImages table.
     *
     * @param image The image to delete.
     */
    @Delete
    void delete(SavedImages image);
}