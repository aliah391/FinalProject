package algonquin.cst2335.finalproject;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



            // Initialize SharedPreferences
            sharedPreferences = getSharedPreferences("QuizPreferences", MODE_PRIVATE);

            // Load previous settings if available
            int selectedTopic = sharedPreferences.getInt("selectedTopic", -1);
            int numQuestions = sharedPreferences.getInt("numQuestions", 10);

            // Add logic to handle topic selection and number of questions input
            // Save the selected topic and number of questions in SharedPreferences
            // Start the quiz activity with the selected topic and number of questions
        }
    }
