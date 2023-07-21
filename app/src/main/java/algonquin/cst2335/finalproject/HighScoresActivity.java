package algonquin.cst2335.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

    public class HighScoresActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_high_scores);

            // Query the local SQLite database for the top 10 high scores
            // Display the high scores on the UI (e.g., in a ListView or RecyclerView)
        }
    }

