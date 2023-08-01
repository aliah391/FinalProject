package algonquin.cst2335.finalproject;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ConversionQuery.class}, version = 1)
public abstract class ConversionQueryDatabase extends RoomDatabase {
    public abstract ConversionQueryDao conversionQueryDao();

    private static volatile ConversionQueryDatabase INSTANCE;

    // Define the ExecutorService as a singleton to prevent having multiple instances
    // of the database opened at the same time.
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

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
