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

interface ApiInterface {
    void downloadQuiz(Context context, String categoryId, String count);

    MutableLiveData<List<QuestionModel>> getQuestionListLiveData();
}

class ApiInterfaceImpl implements ApiInterface {
    private final MutableLiveData<List<QuestionModel>> questionListLiveData = new MutableLiveData<>();

    @Override
    public MutableLiveData<List<QuestionModel>> getQuestionListLiveData() {
        return questionListLiveData;
    }

    @Override
    public void downloadQuiz(Context context, String categoryId, String count) {
        @SuppressLint("DefaultLocale") String Url = String.format("https://opentdb.com/api.php?amount=%s&category=%s&type=multiple", count, categoryId);
        Log.e(">>>>", "Url ::" + Url);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(">>>>>", "onResponse ::\n" + response);
                ArrayList<QuestionModel> questionModels = new ArrayList<QuestionModel>();
                try {
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
                        //Checking whether the JSON array has some value or not
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
        requestQueue.add(stringRequest);
    }

}
