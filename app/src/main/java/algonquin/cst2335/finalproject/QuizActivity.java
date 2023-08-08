package algonquin.cst2335.finalproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import algonquin.cst2335.finalproject.room.MyDatabase;
import algonquin.cst2335.finalproject.room.ScoreDatabase;
import algonquin.cst2335.finalproject.room.ScoreEntity;
/**
 * This activity displays a quiz with questions and options for the user to answer.
 * The user's score is calculated and can be saved to a database.
 */
public class QuizActivity extends AppCompatActivity {
    private String categoryId;
    private TextView txtQuestion;
    private RadioButton option1, option2, option3, option4;
    private RadioGroup radioGroup;
    private EditText edtName;
    private int currentQuestionIndex = 0;
    List<QuestionModel> questionModelList;
    private ScoreDatabase database;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initialise();
    }
    /**
     * Initializes the QuizActivity by setting up UI elements and listeners.
     */
    private void initialise() {
        // Initialize database and retrieve question data
        // Initialize UI components and set click listeners
        // Load question data and display the first question
        database = MyDatabase.getInstance(getApplicationContext());
        categoryId = getIntent().getStringExtra(Constants.KEY_CATEGORY);
        Log.e(">>>>>", "Category Ids ::" + categoryId);
        questionModelList = MySharedPreferences.getQuiz(QuizActivity.this, categoryId);
        Log.e(">>>>>", "Questions ::" + questionModelList);
        txtQuestion = findViewById(R.id.tvQuestion);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        radioGroup = findViewById(R.id.radioGroupOptions);
        edtName = findViewById(R.id.edtName);

        findViewById(R.id.btnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuestionIndex == questionModelList.size() - 1) {
                    // Finish
                    findViewById(R.id.rl_score).setVisibility(View.VISIBLE);
                    calculateScore();

                } else if (currentQuestionIndex == questionModelList.size() - 2) {
                    // Finish
                    ((Button) findViewById(R.id.btnNext)).setText(getString(R.string.finish));
                    currentQuestionIndex++;
                    showQuestion();

                } else {
                    ((Button) findViewById(R.id.btnNext)).setText(getString(R.string.str_next));
                    currentQuestionIndex++;
                    showQuestion();
                }

            }
        });

        findViewById(R.id.btnPrevious).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (currentQuestionIndex == 0) {
                    // Start

                } else {
                    currentQuestionIndex--;
                    showQuestion();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                Log.e(">>>>>", "Position ::" + radioGroup.getId());
                QuestionModel questionModel = questionModelList.get(currentQuestionIndex);
                if (id == R.id.option1) {
                    Log.e(">>>>>", "option1");
                    questionModel.user_selected_answer = questionModel.incorrect_answers.get(0);
                    questionModel.user_selected_option = 0;
                } else if (id == R.id.option2) {
                    Log.e(">>>>>", "option2");
                    questionModel.user_selected_answer = questionModel.incorrect_answers.get(1);
                    questionModel.user_selected_option = 1;
                } else if (id == R.id.option3) {
                    Log.e(">>>>>", "option3");
                    questionModel.user_selected_answer = questionModel.incorrect_answers.get(2);
                    questionModel.user_selected_option = 2;
                } else if (id == R.id.option4) {
                    Log.e(">>>>>", "option4");
                    questionModel.user_selected_answer = questionModel.incorrect_answers.get(3);
                    questionModel.user_selected_option = 3;
                }
            }
        });

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    ScoreEntity scoreEntity = new ScoreEntity();
                    scoreEntity.name = edtName.getText().toString();
                    scoreEntity.score = score;
                    scoreEntity.timestamp = System.currentTimeMillis();

                    database.getScoreDao().insertScore(scoreEntity);
                    finish();
                }
            }
        });
        showQuestion();
    }
    /**
     * Displays the current question on the screen along with answer options.
     */
    @SuppressLint("DefaultLocale")
    void showQuestion() {
        QuestionModel questionModel = questionModelList.get(currentQuestionIndex);
        txtQuestion.setText(String.format("%d. %s", currentQuestionIndex + 1, questionModel.question));
        ArrayList<String> options = questionModel.incorrect_answers;
        options.add(questionModel.correct_answer);
        Collections.shuffle(options);
        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));
        option4.setText(options.get(3));
        showSelectedAnswer(questionModel);
    }
    /**
     * Displays the previously selected answer for a question.
     *
     * @param questionModel The QuestionModel object representing the current question.
     */
    void showSelectedAnswer(QuestionModel questionModel) {
        if (questionModel.user_selected_option == 0) {
            option1.setChecked(true);
        } else if (questionModel.user_selected_option == 1) {
            option2.setChecked(true);
        } else if (questionModel.user_selected_option == 2) {
            option3.setChecked(true);
        } else if (questionModel.user_selected_option == 3) {
            option4.setChecked(true);
        } else {
            radioGroup.clearCheck();
        }
    }
    /**
     * Calculates the user's score based on selected answers and displays it.
     */
    void calculateScore() {
        for (QuestionModel questionModel : questionModelList) {
            if (questionModel.user_selected_answer.equals(questionModel.correct_answer)) {
                score++;
            }
        }
        ((TextView) findViewById(R.id.txtTotal)).setText(String.valueOf(score));
    }
    /**
     * Validates user input before saving the score to the database.
     *
     * @return True if the input is valid, false otherwise.
     */
    private boolean isValid() {
        if (edtName.getText().toString().trim().isEmpty()) {
            Toast.makeText(QuizActivity.this, "Please enter name", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
