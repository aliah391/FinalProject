package algonquin.cst2335.finalproject.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Score")
public class ScoreEntity {
    @PrimaryKey @NonNull
    public String name;
    public int score;

    public long timestamp;
}
