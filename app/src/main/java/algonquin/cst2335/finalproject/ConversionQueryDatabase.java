package algonquin.cst2335.finalproject;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Database for storing ConversionQuery data in a Room database.
 * This class provides methods for accessing the database and performing operations on it.
 */
@Database(entities = {ConversionQuery.class}, version = 1)
public abstract class ConversionQueryDatabase extends RoomDatabase {
    /**
     * Returns the ConversionQueryDao for accessing the ConversionQuery data in the database.
     *
     * @return ConversionQueryDao instance for accessing ConversionQuery data
     */
    public abstract ConversionQueryDao conversionQueryDao();

    // Singleton instance of the ConversionQueryDatabase
    private static volatile ConversionQueryDatabase INSTANCE;

    // Number of threads for database write executor
    private static final int NUMBER_OF_THREADS = 4;

    // Executor service for database operations
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Returns the singleton instance of the ConversionQueryDatabase.
     * If the database does not exist, it is created.
     *
     * @param context the context of the application
     * @return singleton instance of the ConversionQueryDatabase
     */
    public static ConversionQueryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ConversionQueryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ConversionQueryDatabase.class, "conversion_query_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
