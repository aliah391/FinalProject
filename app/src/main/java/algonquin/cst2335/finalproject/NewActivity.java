package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class NewActivity extends AppCompatActivity {

    private EditText widthEditText;
    private EditText heightEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
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
                    Toast.makeText(NewActivity.this, "Please enter width and height", Toast.LENGTH_SHORT).show();
                    return;
                }

                int width = Integer.parseInt(widthString);
                int height = Integer.parseInt(heightString);

                if (width <= 0 || height <= 0) {
                    Toast.makeText(NewActivity.this, "Width and height must be positive numbers", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save current entered values to SharedPreferences
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Width", widthString);
                editor.putString("Height", heightString);
                editor.apply();

                // Show Toast and Snackbar
                Toast.makeText(NewActivity.this, "Image is being generated...", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(NewActivity.this, "Opening saved images...", Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Opening saved images...", Snackbar.LENGTH_LONG).show();



                // Start new SavedImagesActivity
                Intent intent = new Intent(NewActivity.this, SavedImagesActivity.class);
                startActivity(intent);
            }
        });
    }
}