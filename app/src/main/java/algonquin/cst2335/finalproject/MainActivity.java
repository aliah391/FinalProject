package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       /* new AlertDialog.Builder(this)
                .setTitle("Conversion Result")
                .setMessage("Converted Amount: ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show(); */

        Spinner spinner = (Spinner) findViewById(R.id.spinner_from_currency);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinnerDestination = (Spinner) findViewById(R.id.spinner_to_currency);
        ArrayAdapter<CharSequence> adapterDestination = ArrayAdapter.createFromResource(this,
                R.array.currency_array, android.R.layout.simple_spinner_item);
        adapterDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestination.setAdapter(adapterDestination);

    }
}
