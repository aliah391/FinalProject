package algonquin.cst2335.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface defining methods for downloading quiz questions and providing a LiveData stream
 * of question data.
 */
interface ApiInterface {
    /**
     * Download quiz questions from the specified category and with the given count.
     *
     * @param context The application context.
     * @param categoryId The category ID for the quiz questions.
     * @param count The number of questions to download.
     */
    void downloadQuiz(Context context, String categoryId, String count);

    /**
     * Get a LiveData stream of the list of quiz question models.
     *
     * @return A LiveData object containing the list of quiz question models.
     */
    MutableLiveData<List<QuestionModel>> getQuestionListLiveData();
}

/**
 * Implementation of the ApiInterface for downloading quiz questions from an API.
 */
class ApiInterfaceImpl implements ApiInterface {
    private final MutableLiveData<List<QuestionModel>> questionListLiveData = new MutableLiveData<>();

    @Override
    public MutableLiveData<List<QuestionModel>> getQuestionListLiveData() {
        return questionListLiveData;
    }

    @Override
    public void downloadQuiz(Context context, String categoryId, String count) {
        // Construct the URL for API request
        @SuppressLint("DefaultLocale") String Url = String.format("https://opentdb.com/api.php?amount=%s&category=%s&type=multiple", count, categoryId);
        Log.e(">>>>", "Url ::" + Url);

        // Create a request queue using Volley
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Create a StringRequest for API request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(">>>>>", "onResponse ::\n" + response);
                ArrayList<QuestionModel> questionModels = new ArrayList<QuestionModel>();
                try {
                    // Parse the JSON response
                    JSONObject jsonObject = new JSONObject(response);
                    int responseCode = jsonObject.optInt("response_code");
                    JSONArray resultArray = jsonObject.optJSONArray("results");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject object = resultArray.getJSONObject(i);
                        QuestionModel questionModel = new QuestionModel();
                        questionModel.category = object.getString("category");
                        questionModel.type = object.getString("type");
                        questionModel.difficulty = object.getString("difficulty");
                        questionModel.question = object.getString("question");
                        questionModel.correct_answer = object.getString("correct_answer");
                        JSONArray jsonArray = object.getJSONArray("incorrect_answers");
                        ArrayList<String> stringArrayList = new ArrayList<>();
                        // Checking whether the JSON array has some value or not
                        for (int j = 0; j < jsonArray.length(); j++) {
                            stringArrayList.add(jsonArray.optString(j));
                        }
                        questionModel.incorrect_answers = stringArrayList;
                        questionModels.add(questionModel);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                questionListLiveData.postValue(questionModels);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(">>>>>", "onErrorResponse ::" + error.toString());
            }
        });
        // Add the request to the request queue
        requestQueue.add(stringRequest);
    }
}
