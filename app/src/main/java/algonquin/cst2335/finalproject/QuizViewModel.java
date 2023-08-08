package algonquin.cst2335.finalproject;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
/**
 * The ViewModel class responsible for managing quiz-related data and operations.
 */
public class QuizViewModel {
    private ApiInterface apiInterface;
    private MutableLiveData<List<QuestionModel>> questionListLiveData;

    /**
     * Get the LiveData object containing the list of quiz questions.
     *
     * @return A LiveData object containing the list of quiz questions.
     */
    public LiveData<List<QuestionModel>> getQuestionsLiveData() {
        return questionListLiveData;
    }

    /**
     * Initializes the QuizViewModel and sets up the API interface and LiveData object.
     */
    QuizViewModel() {
        apiInterface = new ApiInterfaceImpl();
        questionListLiveData = apiInterface.getQuestionListLiveData();
    }

    /**
     * Initiates the process to download quiz questions from the API.
     *
     * @param context    The context of the application.
     * @param categoryId The ID of the selected quiz category.
     * @param count      The number of quiz questions to download.
     */
    void downloadQuiz(Context context, String categoryId, String count) {
        apiInterface.downloadQuiz(context, categoryId, count);
    }
}





