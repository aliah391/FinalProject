package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class MySharedPreferences {
    public static String APP_NAME = "MYAPP";

    public static void saveQuiz(Context context, String key, List<QuestionModel> questionModelList) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, gson.toJson(questionModelList)).apply();
    }

    public static List<QuestionModel> getQuiz(Context context, String key) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return gson.fromJson(sharedPreferences.getString(key, ""), new TypeToken<List<QuestionModel>>() {
        }.getType());
    }

}
