package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;


/**
 * The MainActivity is the main class for the application and extends AppCompatActivity.
 * <p>
 * <p>
 * This activity sets up the view and binds the button click to start the CurrencyConverter activity.
 *
 * @author Alliah Smith, Irina Dontsova, Nikita Senkov, Loveleen LoveLeen
 * @version 1.0*/
 
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

        Button bearImageButton = findViewById(R.id.Bear_Image);
        bearImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(intent);
            }
        });

        binding.Trivia.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, QuizStartActivity.class));
        });

        binding.Aviation.setOnClickListener(click -> {
            Intent nextPage = new Intent(MainActivity.this, Aviation.class);
            startActivity(nextPage);
        });
    }
}
//
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            super.onCreateOptionsMenu(menu);
//            getMenuInflater().inflate(R.menu.my_menu , menu);
//            return true;
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//
//
//            if(item.getItemId()==R.id.AviationItem) {
//                String message =" Aviation";
//                Toast.makeText(this, "You clicked"+ message, Toast.LENGTH_SHORT).show();
//                Intent nextPage= new Intent(MainActivity.this, Aviation.class);
//                startActivity(nextPage);
//
//            }
//
//
//
//
//
//            return true;
//        }
