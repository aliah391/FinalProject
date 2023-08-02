package algonquin.cst2335.finalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    // Declare the SharedPreferences object
    private SharedPreferences sharedPreferences;

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
        setContentView(R.layout.activity_currency_converter); // replace with your layout

        // Initialize the SharedPreferences with a custom name "MyPreferences"
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // If you want to display the title
        getSupportActionBar().setTitle("Currency Converter");

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

        viewSavedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SavedConversionsActivity
                Intent intent = new Intent(CurrencyConverter.this, SavedConversionsActivity.class);
                startActivity(intent);
            }
        });

        // Retrieve the saved amount from SharedPreferences and set it in the EditText
        String savedAmount = sharedPreferences.getString("amount", "");
        amountEditText.setText(savedAmount);
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
                    // Inside the onResponse() method of convertCurrency()
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("API Response", "Response: " + response.toString());

                            double conversionRate = response.getJSONObject("rates").getJSONObject(toCurrency).getDouble("rate");
                            double amountToConvert = Double.parseDouble(amount);
                            double convertedAmount = amountToConvert * conversionRate;

                            lastConversion = new ConversionQuery(fromCurrency, toCurrency, amountToConvert, convertedAmount);
                            resultTextView.setText(getString(R.string.converted_amount, convertedAmount));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, error -> {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        String errorResponse = new String(error.networkResponse.data);
                    }
                    Log.e("API Error", error.toString());
                });

        Volley.newRequestQueue(this).add(jsonObjectRequest);

        // Save the amount to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("amount", amount);
        editor.apply();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_currency, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_help) {
            showHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Help")
                .setMessage("Welcome to Currency Converter!\n" +
                        "\n" +
                        "1. Enter the amount you wish to convert in the input field.\n" +
                        "2. Select the source currency from the 'Source Currency' dropdown list.\n" +
                        "3. Select the destination currency from the 'Destination Currency' dropdown list.\n" +
                        "4. Click the 'Convert' button to see the result.\n" +
                        "5. If you wish to save the conversion, click the 'Save Conversion' button.\n" +
                        "6. To view saved conversions, click the 'View Saved Conversions' button.\n" +
                        "\n" +
                        "Enjoy your usage!\n")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

}
