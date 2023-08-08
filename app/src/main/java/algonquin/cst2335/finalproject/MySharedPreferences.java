package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Utility class for managing shared preferences related to the quiz.
 */
public class MySharedPreferences {
    public static String APP_NAME = "MYAPP";

    /**
     * Save a list of quiz question models in shared preferences using Gson serialization.
     *
     * @param context The application context.
     * @param key The key for storing the data.
     * @param questionModelList The list of QuestionModel objects to be saved.
     */
    public static void saveQuiz(Context context, String key, List<QuestionModel> questionModelList) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, gson.toJson(questionModelList)).apply();
    }

    /**
     * Retrieve a list of quiz question models from shared preferences using Gson deserialization.
     *
     * @param context The application context.
     * @param key The key for retrieving the data.
     * @return The list of QuestionModel objects retrieved from shared preferences.
     */
    public static List<QuestionModel> getQuiz(Context context, String key) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return gson.fromJson(sharedPreferences.getString(key, ""), new TypeToken<List<QuestionModel>>() {
        }.getType());
    }
}