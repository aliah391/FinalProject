package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;

/**
 * The MainActivity is the main class for the application and extends AppCompatActivity.
 * <p>
 * <p>
 * This activity sets up the view and binds the button click to start the CurrencyConverter activity.
 *
 * @author Alliah Smith, Irina Dontsova, Nikita Senkov, Loveleen LoveLeen
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method is called when the activity is starting.
     * This is where most initialization should go: calling setContentView(int) to inflate
     * the activity's UI, setting up listeners, etc.
     * <p>
     * In this method, the ActivityMainBinding is inflated and a click listener is set on the Currency button
     * to start the CurrencyConverter activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.Currency.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CurrencyConverter.class);
            startActivity(intent);
        });
    }
}
