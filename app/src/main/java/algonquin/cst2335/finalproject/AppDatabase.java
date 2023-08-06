package algonquin.cst2335.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * This is a Room database class that serves as an abstract layer for the underlying SQLite database.
 * The Room persistence library creates an object-relational mapping to the underlying SQLite database.
 * This class includes methods that we can use to access the database.
 *
 * @author Nikita
 * @version 1.0
 * @since 2023-08-05
 */
@Database(entities = {SavedImages.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * This method gives us access to SavedImagesDao which includes methods that offer abstract access to
     * your app's database.
     *
     * @return SavedImagesDao instance
     */
    public abstract SavedImagesDao savedImagesDao();
}