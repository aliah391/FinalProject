package algonquin.cst2335.finalproject.room;

import android.content.Context;

import androidx.room.Room;

/**
 * A utility class for managing the instance of the ScoreDatabase using Room.
 */
public class MyDatabase {

    /**
     * Get the instance of the ScoreDatabase.
     *
     * @param context The application context.
     * @return The instance of ScoreDatabase.
     */
    public static ScoreDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, ScoreDatabase.class, "QuizDatabase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
