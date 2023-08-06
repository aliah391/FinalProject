package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import java.util.List;

public class QuizStartActivity extends AppCompatActivity {
    private QuizViewModel quizViewModel;
    private final int[] categoryIds = {
            22, 23, 24, 25, 30
    };
    private int categoryId = 11235;
    private Spinner spinnerTopic;
    private EditText etNoOfQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_start);
        initialise();
    }

    private void initialise() {
        quizViewModel = new QuizViewModel();
        spinnerTopic = findViewById(R.id.spinnerTopic);
        etNoOfQuestion = findViewById(R.id.etNumQuestions);
        spinnerTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e(">>>>>", "Category Ids ::" + categoryIds[i]);
                categoryId = categoryIds[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.e(">>>>>", "Nothing Selected");
            }
        });
        quizViewModel.getQuestionsLiveData().observe(this, new Observer<List<QuestionModel>>() {
            @Override
            public void onChanged(List<QuestionModel> questionModels) {
                Log.e(">>>>>", "getQuestionsLiveData() ::" + questionModels.toString());
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                MySharedPreferences.saveQuiz(QuizStartActivity.this, String.valueOf(categoryId), questionModels);
                Intent intent = new Intent(QuizStartActivity.this, QuizActivity.class);
                intent.putExtra(Constants.KEY_CATEGORY, String.valueOf(categoryId));
                startActivity(intent);
            }
        });

        findViewById(R.id.btnDownloadStartQuiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isValid()) {
                        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                        quizViewModel.downloadQuiz(QuizStartActivity.this, String.valueOf(categoryId), etNoOfQuestion.getText().toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.ivScoreBoard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuizStartActivity.this, HighScoresActivity.class));
            }
        });
    }

    private boolean isValid() {
        if (etNoOfQuestion.getText().toString().trim().isEmpty()) {
            Toast.makeText(QuizStartActivity.this, "Please enter no of questions", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
