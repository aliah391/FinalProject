package algonquin.cst2335.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.databinding.ActivityCurrencyConverterBinding;

public class CurrencyConverter extends AppCompatActivity {

    private EditText amountEditText;
    private Spinner fromCurrencySpinner;
    private Spinner toCurrencySpinner;
    private Button convertButton;
    private TextView resultTextView;
    private Button saveButton;
    private Button viewSavedButton;

    private RecyclerView recyclerView;
    private SavedConversionsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ConversionQueryViewModel queryViewModel;
    private ConversionQuery lastConversion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCurrencyConverterBinding binding = ActivityCurrencyConverterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        queryViewModel = new ViewModelProvider(this).get(ConversionQueryViewModel.class);

        recyclerView = findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SavedConversionsAdapter();
        recyclerView.setAdapter(adapter);

        amountEditText = findViewById(R.id.editText_amount);
        fromCurrencySpinner = findViewById(R.id.spinner_from_currency);
        toCurrencySpinner = findViewById(R.id.spinner_to_currency);
        convertButton = findViewById(R.id.button_convert);
        resultTextView = findViewById(R.id.textView_result);
        saveButton = findViewById(R.id.button_save);
        viewSavedButton = findViewById(R.id.button_view_saved);

        List<String> currencies = new ArrayList<>();
        currencies.add("USD");
        currencies.add("CAD");
        currencies.add("EUR");
        currencies.add("JPY");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        fromCurrencySpinner.setAdapter(spinnerAdapter);
        toCurrencySpinner.setAdapter(spinnerAdapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastConversion != null) {
                    queryViewModel.insert(lastConversion);
                    Toast.makeText(CurrencyConverter.this, "Conversion saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CurrencyConverter.this, "No conversion to save", Toast.LENGTH_SHORT).show();
                }
            }
        });

        queryViewModel.getAllQueries().observe(this, new Observer<List<ConversionQuery>>() {
            @Override
            public void onChanged(List<ConversionQuery> queries) {
                adapter.submitList(queries);
            }
        });

        viewSavedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SavedConversionsActivity
                Intent intent = new Intent(CurrencyConverter.this, SavedConversionsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void convertCurrency() {
        Log.d("CurrencyConverter", "Entered convertCurrency()");

        String amount = amountEditText.getText().toString();
        String fromCurrency = fromCurrencySpinner.getSelectedItem().toString();
        String toCurrency = toCurrencySpinner.getSelectedItem().toString();

        String url = "https://api.getgeoapi.com/v2/currency/convert?format=json&from="
                + fromCurrency + "&to=" + toCurrency + "&amount=" + amount + "&api_key=bc8f732a47c2574bc2dac6ef67c7833c9ef17882&format=json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @SuppressLint("StringFormatInvalid")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("API Response", "Response: " + response.toString());

                            double convertedAmount = response.getJSONObject("rates").getJSONObject(toCurrency).getDouble("rate");
                            lastConversion = new ConversionQuery(fromCurrency, toCurrency, Double.parseDouble(amount), convertedAmount);
                            resultTextView.setText(getString(R.string.converted_amount, convertedAmount));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            String errorResponse = new String(error.networkResponse.data);
                        }
                        Log.e("API Error", error.toString());
                    }
                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
