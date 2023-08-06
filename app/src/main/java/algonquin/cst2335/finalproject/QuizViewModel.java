package algonquin.cst2335.finalproject;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class QuizViewModel {
    private ApiInterface apiInterface;
    private MutableLiveData<List<QuestionModel>> questionListLiveData;

    public LiveData<List<QuestionModel>> getQuestionsLiveData() {
        return questionListLiveData;
    }

    QuizViewModel() {
        apiInterface = new ApiInterfaceImpl();
        questionListLiveData = apiInterface.getQuestionListLiveData();
    }

    void downloadQuiz(Context context, String categoryId, String count) {
        apiInterface.downloadQuiz(context, categoryId, count);
    }
}
