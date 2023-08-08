package algonquin.cst2335.finalproject.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a score record in the database.
 */
@Entity(tableName = "Score")
public class ScoreEntity {

    /** The primary key and non-null field representing the name associated with the score. */
    @PrimaryKey @NonNull
    public String name;

    /** The score value associated with the record. */
    public int score;

    /** The timestamp indicating when the score was recorded. */
    public long timestamp;
}
