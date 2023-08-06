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
import java.util.Collections;
import java.util.List;

import algonquin.cst2335.finalproject.databinding.ActivityCurrencyConverterBinding;

/**
 * Activity for converting currencies. Allows users to convert between different
 * currencies and view/save their conversions. Uses an API to perform the currency
 * conversion.
 */
public class CurrencyConverter extends AppCompatActivity {

    /**
     * Shared preferences to save data across app sessions.
     */
    private SharedPreferences sharedPreferences;

    /**
     * EditText for the user to input the amount to be converted.
     */
    private EditText amountEditText;

    /**
     * Spinner to select the currency to convert from.
     */
    private Spinner fromCurrencySpinner;

    /**
     * Spinner to select the currency to convert to.
     */
    private Spinner toCurrencySpinner;

    /**
     * Button to trigger the conversion operation.
     */
    private Button convertButton;

    /**
     * TextView to display the result of the conversion.
     */
    private TextView resultTextView;

    /**
     * Button to save the current conversion.
     */
    private Button saveButton;

    /**
     * Button to navigate to the saved conversions screen.
     */
    private Button viewSavedButton;

    /**
     * RecyclerView to display a list of saved conversions.
     */
    private RecyclerView recyclerView;

    /**
     * Adapter for the RecyclerView.
     */
    private SavedConversionsAdapter adapter;

    /**
     * Layout manager for the RecyclerView.
     */
    private RecyclerView.LayoutManager layoutManager;

    /**
     * ViewModel to handle data related to the conversion queries.
     */
    private ConversionQueryViewModel queryViewModel;

    /**
     * The last completed conversion query.
     */
    private ConversionQuery lastConversion = null;

    /**
     * This method initializes the activity when it is first created.
     * It inflates the activity's layout, sets up shared preferences, and configures the action bar.
     * It also initializes a ViewModel for storing conversion queries.
     * The method then sets up a RecyclerView for displaying saved conversions. This involves
     * initializing a LinearLayoutManager and a SavedConversionsAdapter, and associating them
     * with the RecyclerView.
     * Finally, the method finds and keeps references to various other UI elements, including
     * EditTexts, Spinners, Buttons, and a TextView. These references will be used in other parts of
     * the application to read user input and control the display.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCurrencyConverterBinding binding = ActivityCurrencyConverterBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_currency_converter);

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getResources().getString(R.string.app_name_currency));

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

        /*
         * This block initializes a list of different currencies, where each currency is represented
         * as a `Currency` object. The `Currency` object takes two arguments: the currency code
         * and the corresponding description. Each currency is added to the list of currencies.
         * For instance, "AUD" stands for Australian Dollar, "BRL" for Brazilian Real, "CAD" for
         * Canadian Dollar, "CHF" for Swiss Franc, and so on. The descriptions for each currency
         * are fetched from the string resources.
         * Here is the list of all the currencies being added:
         * "AUD" - Australian Dollar
         * "BRL" - Brazilian Real
         * "CAD" - Canadian Dollar
         * "CHF" - Swiss Franc
         * "CNY" - Chinese Yuan
         * "EUR" - Euro
         * "GBP" - British Pound Sterling
         * "HKD" - Hong Kong Dollar
         * "IDR" - Indonesian Rupiah
         * "INR" - Indian Rupee
         * "JPY" - Japanese Yen
         * "KRW" - South Korean Won
         * "MXN" - Mexican Peso
         * "MYR" - Malaysian Ringgit
         * "NOK" - Norwegian Krone
         * "NZD" - New Zealand Dollar
         * "PEN" - Peruvian Sol
         * "RUB" - Russian Ruble
         * "SAR" - Saudi Riyal
         * "SEK" - Swedish Krona
         * "SGD" - Singapore Dollar
         * "THB" - Thai Baht
         * "TWD" - New Taiwan Dollar
         * "TRY" - Turkish Lira
         * "USD" - United States Dollar
         * "VND" - Vietnamese Dong
         * "ZAR" - South African Rand
         */
        List<Currency> currencies = new ArrayList<>();
        currencies.add(new Currency("AUD", getResources().getString(R.string.aud_description)));
        currencies.add(new Currency("BRL", getResources().getString(R.string.brl_description)));
        currencies.add(new Currency("CAD", getResources().getString(R.string.cad_description)));
        currencies.add(new Currency("CHF", getResources().getString(R.string.chf_description)));
        currencies.add(new Currency("CNY", getResources().getString(R.string.cny_description)));
        currencies.add(new Currency("EUR", getResources().getString(R.string.eur_description)));
        currencies.add(new Currency("GBP", getResources().getString(R.string.gbp_description)));
        currencies.add(new Currency("HKD", getResources().getString(R.string.hkd_description)));
        currencies.add(new Currency("IDR", getResources().getString(R.string.idr_description)));
        currencies.add(new Currency("INR", getResources().getString(R.string.inr_description)));
        currencies.add(new Currency("JPY", getResources().getString(R.string.jpy_description)));
        currencies.add(new Currency("KRW", getResources().getString(R.string.krw_description)));
        currencies.add(new Currency("MXN", getResources().getString(R.string.mxn_description)));
        currencies.add(new Currency("MYR", getResources().getString(R.string.myr_description)));
        currencies.add(new Currency("NOK", getResources().getString(R.string.nok_description)));
        currencies.add(new Currency("NZD", getResources().getString(R.string.nzd_description)));
        currencies.add(new Currency("PEN", getResources().getString(R.string.pen_description)));
        currencies.add(new Currency("RUB", getResources().getString(R.string.rub_description)));
        currencies.add(new Currency("SAR", getResources().getString(R.string.sar_description)));
        currencies.add(new Currency("SEK", getResources().getString(R.string.sek_description)));
        currencies.add(new Currency("SGD", getResources().getString(R.string.sgd_description)));
        currencies.add(new Currency("THB", getResources().getString(R.string.thb_description)));
        currencies.add(new Currency("TWD", getResources().getString(R.string.twd_description)));
        currencies.add(new Currency("TRY", getResources().getString(R.string.try_description)));
        currencies.add(new Currency("USD", getResources().getString(R.string.usd_description)));
        currencies.add(new Currency("VND", getResources().getString(R.string.vnd_description)));
        currencies.add(new Currency("ZAR", getResources().getString(R.string.zar_description)));

        /*
         * This method initializes an ArrayAdapter with the list of Currency objects, and sets this adapter
         * on the "from" and "to" currency Spinners. It sets onClickListeners on the "convert", "save", and
         * "view saved" buttons. It also retrieves and sets the previously saved amount from shared
         * preferences, if any, into the amount EditText.
         */
        ArrayAdapter<Currency> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        fromCurrencySpinner.setAdapter(spinnerAdapter);
        toCurrencySpinner.setAdapter(spinnerAdapter);

        /*
         * This method is triggered when the "convert" button is clicked. It invokes the method to convert
         * the currency based on the values selected in the "from" and "to" currency Spinners and the amount
         * entered in the EditText.
         */
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });

        /*
         * This method is triggered when the "save" button is clicked. If a conversion has been done, it
         * saves the conversion to the database via the ViewModel. Otherwise, it shows a Toast message
         * indicating that there is no conversion to save.
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastConversion != null) {
                    queryViewModel.insert(lastConversion);
                    Toast.makeText(CurrencyConverter.this, getString(R.string.toast_conversion_saved), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CurrencyConverter.this, getString(R.string.toast_no_conversion_to_save), Toast.LENGTH_SHORT).show();

                }
            }
        });

        /*
         * This method is triggered when the "view saved" button is clicked. It starts the
         * SavedConversionsActivity to display the list of saved conversions.
         */
        viewSavedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SavedConversionsActivity
                Intent intent = new Intent(CurrencyConverter.this, SavedConversionsActivity.class);
                startActivity(intent);
            }
        });

        String savedAmount = sharedPreferences.getString("amount", "");
        amountEditText.setText(savedAmount);
    }

    /**
     * This method initiates a currency conversion operation. It first extracts the user's input
     * values from the user interface, including the amount to be converted and the source and target
     * currencies. It then constructs a URL to make a GET request to the currency conversion API.
     * Upon receiving a response from the API, the method calculates the converted amount by
     * multiplying the original amount by the conversion rate retrieved from the API. The converted
     * amount is then displayed in the result TextView.
     * If an error occurs during the API call, the error is logged.
     * After the conversion, the method saves the last conversion and the entered amount to shared
     * preferences for potential future use.
     * This method uses the Volley library to handle the HTTP request and response.
     */
    private void convertCurrency() {
        Log.d("CurrencyConverter", "Entered convertCurrency()");

        String amount = amountEditText.getText().toString();
        String fromCurrency = ((Currency) fromCurrencySpinner.getSelectedItem()).getCode();
        String toCurrency = ((Currency) toCurrencySpinner.getSelectedItem()).getCode();

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

    /**
     * This method inflates the menu for the currency converter activity.
     *
     * @param menu The options menu in which items are placed
     * @return boolean Return true for the menu to be displayed
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_currency, menu);
        return true;
    }

    /**
     * This method handles menu item clicks. If the "help" option is selected, it shows a help dialog.
     * If the "back to main menu" option is selected, it navigates to the main activity.
     *
     * @param item The menu item that was selected
     * @return boolean Return false to allow normal menu processing to proceed, true to consume it here
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_help) {
            showHelpDialog();
            return true;
        } else if (id == R.id.back_main_menu) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method shows a help dialog with a pre-defined title and message when called.
     */
    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.help_dialog_title))
                .setMessage(getString(R.string.help_dialog_message))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

}
