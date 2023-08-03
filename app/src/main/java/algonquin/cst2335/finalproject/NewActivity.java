package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class NewActivity extends AppCompatActivity {

    private EditText widthEditText;
    private EditText heightEditText;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_help) {
            displayHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.help_title)
                .setMessage(R.string.help_message_new_activity)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);



        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
// Link the UI elements
        widthEditText = findViewById(R.id.widthEditText);
        heightEditText = findViewById(R.id.heightEditText);
        Button generateButton = findViewById(R.id.generateButton);
        Button showSavedImagesButton = findViewById(R.id.showSavedImagesButton);

        // Load previously entered values from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("ImagePrefs", MODE_PRIVATE);
        String savedWidth = prefs.getString("Width", "");
        String savedHeight = prefs.getString("Height", "");

        // Set saved values to EditTexts
        widthEditText.setText(savedWidth);
        heightEditText.setText(savedHeight);

        // OnClickListener for the generateButton
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input
                String widthString = widthEditText.getText().toString();
                String heightString = heightEditText.getText().toString();

                if (widthString.isEmpty() || heightString.isEmpty()) {
                    Toast.makeText(NewActivity.this, getString(R.string.toast_message_1), Toast.LENGTH_SHORT).show();
                    return;
                }

                int width = Integer.parseInt(widthString);
                int height = Integer.parseInt(heightString);

                if (width <= 0 || height <= 0) {
                    Toast.makeText(NewActivity.this, getString(R.string.toast_message_2), Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save current entered values to SharedPreferences
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Width", widthString);
                editor.putString("Height", heightString);
                editor.apply();

                // Show Toast and Snackbar
                Toast.makeText(NewActivity.this, getString(R.string.toast_message_3), Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Image is being generated...", Snackbar.LENGTH_LONG).show();

                // Start new ImageActivity
                Intent intent = new Intent(NewActivity.this, ImageActivity.class);
                intent.putExtra("Width", width);
                intent.putExtra("Height", height);
                startActivity(intent);
            }
        });

        // OnClickListener for the showSavedImagesButton
        showSavedImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show Toast and Snackbar
                Toast.makeText(NewActivity.this, getString(R.string.toast_message_4), Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Opening saved images...", Snackbar.LENGTH_LONG).show();



                // Start new SavedImagesActivity
                Intent intent = new Intent(NewActivity.this, SavedImagesActivity.class);
                startActivity(intent);
            }
        });
    }
}