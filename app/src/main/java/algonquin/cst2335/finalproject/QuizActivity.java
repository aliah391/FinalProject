package algonquin.cst2335.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Retrieve selected topic and number of questions from the intent
        int selectedTopic = getIntent().getIntExtra("selectedTopic", -1);
        int numQuestions = getIntent().getIntExtra("numQuestions", 10);

        // Fetch quiz questions from the server based on the selected topic and number of questions
        // Display quiz questions on the UI and handle user answers
        // Compute and store the user's score along with their name in the local SQLite database
    }
}
