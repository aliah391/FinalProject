package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algonquin.cst2335.finalproject.databinding.ActivityCurrencyConverterBinding;

public class CurrencyConverter extends AppCompatActivity {

    private EditText amountEditText;
    private Spinner fromCurrencySpinner;
    private Spinner toCurrencySpinner;
    private Button convertButton;
    private TextView resultTextView;
    private Button saveButton;
    private Button viewSavedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCurrencyConverterBinding binding = ActivityCurrencyConverterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Init UI elements
        amountEditText = findViewById(R.id.editText_amount);
        fromCurrencySpinner = findViewById(R.id.spinner_from_currency);
        toCurrencySpinner = findViewById(R.id.spinner_to_currency);
        convertButton = findViewById(R.id.button_convert);
        resultTextView = findViewById(R.id.textView_result);
        saveButton = findViewById(R.id.button_save);
        viewSavedButton = findViewById(R.id.button_view_saved);

        // Let's assume these are the available currencies
        List<String> currencies = new ArrayList<>();
        currencies.add("USD");
        currencies.add("CAD");
        currencies.add("EUR");
        currencies.add("JPY");

        // Setting up adapters for spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        fromCurrencySpinner.setAdapter(adapter);
        toCurrencySpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement save conversion logic here
                Toast.makeText(CurrencyConverter.this, "Conversion saved", Toast.LENGTH_SHORT).show();
            }
        });

        viewSavedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement view saved conversions logic here
                Toast.makeText(CurrencyConverter.this, "Viewing saved conversions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void convertCurrency() {
        String amount = amountEditText.getText().toString();
        String fromCurrency = fromCurrencySpinner.getSelectedItem().toString();
        String toCurrency = toCurrencySpinner.getSelectedItem().toString();

        String url = "https://currency-converter-by-api-ninjas.p.rapidapi.com/v1/convertcurrency" + fromCurrency + "&want=" + toCurrency + "&amount=" + amount;

        // The headers you have to provide in your request to rapid API
        HashMap<String, String> headers = new HashMap<>();
        headers.put("x-rapidapi-host", "currency-converter-by-api-ninjas.p.rapidapi.com");
        headers.put("x-rapidapi-key", "64e435c587msha30972a0430b370p1c114cjsnaeb4e6d490a4");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Log the whole response
                            Log.d("API Response", "Response: " + response.toString());

                            // TODO: once you see the structure, replace the field names here
                            double convertedAmount = response.getDouble("amount");
                            resultTextView.setText("Converted Amount: " + convertedAmount);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorResponse = new String(error.networkResponse.data);
                            Log.e("Error response", errorResponse);
                        }
                        // Log the error
                        Log.e("API Error", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }
        };

// Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}