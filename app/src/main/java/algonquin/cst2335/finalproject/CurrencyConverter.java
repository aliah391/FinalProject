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

public class CurrencyConverter extends AppCompatActivity {

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
        setContentView(R.layout.activity_currency_converter);

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

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

        List<Currency> currencies = new ArrayList<>();
        currencies.add(new Currency("AUD", getResources().getString(R.string.aud_description))); // Австралийский доллар
        currencies.add(new Currency("BRL", getResources().getString(R.string.brl_description))); // Бразильский реал
        currencies.add(new Currency("CAD", getResources().getString(R.string.cad_description))); // Канадский доллар
        currencies.add(new Currency("CHF", getResources().getString(R.string.chf_description))); // Швейцарский франк
        currencies.add(new Currency("CNY", getResources().getString(R.string.cny_description))); // Китайский юань
        currencies.add(new Currency("EUR", getResources().getString(R.string.eur_description))); // Евро
        currencies.add(new Currency("GBP", getResources().getString(R.string.gbp_description))); // Фунт стерлингов Великобритании
        currencies.add(new Currency("HKD", getResources().getString(R.string.hkd_description))); // Гонконгский доллар
        currencies.add(new Currency("IDR", getResources().getString(R.string.idr_description))); // Индонезийская рупия
        currencies.add(new Currency("INR", getResources().getString(R.string.inr_description))); // Индийская рупия
        currencies.add(new Currency("JPY", getResources().getString(R.string.jpy_description))); // Японская иена
        currencies.add(new Currency("KRW", getResources().getString(R.string.krw_description))); // Южнокорейская вона
        currencies.add(new Currency("MXN", getResources().getString(R.string.mxn_description))); // Мексиканское песо
        currencies.add(new Currency("MYR", getResources().getString(R.string.myr_description))); // Малайзийский ринггит
        currencies.add(new Currency("NOK", getResources().getString(R.string.nok_description))); // Норвежская крона
        currencies.add(new Currency("NZD", getResources().getString(R.string.nzd_description))); // Новозеландский доллар
        currencies.add(new Currency("PEN", getResources().getString(R.string.pen_description))); // Перуанский новый соль
        currencies.add(new Currency("RUB", getResources().getString(R.string.rub_description))); // Российский рубль
        currencies.add(new Currency("SAR", getResources().getString(R.string.sar_description))); // Саудовский риял
        currencies.add(new Currency("SEK", getResources().getString(R.string.sek_description))); // Шведская крона
        currencies.add(new Currency("SGD", getResources().getString(R.string.sgd_description))); // Сингапурский доллар
        currencies.add(new Currency("THB", getResources().getString(R.string.thb_description))); // Таиландский бат
        currencies.add(new Currency("TWD", getResources().getString(R.string.twd_description))); // Новый тайваньский доллар
        currencies.add(new Currency("TRY", getResources().getString(R.string.try_description))); // Турецкая лира
        currencies.add(new Currency("USD", getResources().getString(R.string.usd_description))); // Доллар США
        currencies.add(new Currency("VND", getResources().getString(R.string.vnd_description))); // Вьетнамский донг
        currencies.add(new Currency("ZAR", getResources().getString(R.string.zar_description))); // Южноафриканский рэнд


        ArrayAdapter<Currency> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
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
                    Toast.makeText(CurrencyConverter.this, getString(R.string.toast_conversion_saved), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CurrencyConverter.this, getString(R.string.toast_no_conversion_to_save), Toast.LENGTH_SHORT).show();

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

        String savedAmount = sharedPreferences.getString("amount", "");
        amountEditText.setText(savedAmount);
    }

    private void convertCurrency() {
        Log.d("CurrencyConverter", "Entered convertCurrency()");

        String amount = amountEditText.getText().toString();
        String fromCurrency = ((Currency)fromCurrencySpinner.getSelectedItem()).getCode();
        String toCurrency = ((Currency)toCurrencySpinner.getSelectedItem()).getCode();

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
        int id = item.getItemId();
        if (id == R.id.action_help) {
            showHelpDialog();
            return true;
        } else if (id == R.id.back_main_menu) {
            // здесь перейдите на главный экран
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.help_dialog_title))
                .setMessage(getString(R.string.help_dialog_message))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

}
