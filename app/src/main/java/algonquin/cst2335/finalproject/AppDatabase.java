package algonquin.cst2335.finalproject;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SavedImages.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SavedImagesDao savedImagesDao();
}
