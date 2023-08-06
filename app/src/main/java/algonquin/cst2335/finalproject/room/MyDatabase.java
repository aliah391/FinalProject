package algonquin.cst2335.finalproject.room;

import android.content.Context;

import androidx.room.Room;

public class MyDatabase {
    public static ScoreDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, ScoreDatabase.class, "QuizDatabase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
