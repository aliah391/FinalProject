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

/**
 * NewActivity is an activity class where users can input the width and height for a new image.
 * The inputted dimensions are saved to SharedPreferences and used to start ImageActivity.
 * This class also provides an option to view saved images in SavedImagesActivity.
 *
 * @author Nikita
 * @version 1.0
 * @since 2023-08-05
 */
public class NewActivity extends AppCompatActivity {

    /**
     * EditText field for entering image width.
     */
    private EditText widthEditText;

    /**
     * EditText field for entering image height.
     */
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

    /**
     * Displays a help dialog with information about the activity.
     */
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

        widthEditText = findViewById(R.id.widthEditText);
        heightEditText = findViewById(R.id.heightEditText);
        Button generateButton = findViewById(R.id.generateButton);
        Button showSavedImagesButton = findViewById(R.id.showSavedImagesButton);

        SharedPreferences prefs = getSharedPreferences("ImagePrefs", MODE_PRIVATE);
        String savedWidth = prefs.getString("Width", "");
        String savedHeight = prefs.getString("Height", "");

        widthEditText.setText(savedWidth);
        heightEditText.setText(savedHeight);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Width", widthString);
                editor.putString("Height", heightString);
                editor.apply();

                Toast.makeText(NewActivity.this, getString(R.string.toast_message_3), Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Image is being generated...", Snackbar.LENGTH_LONG).show();

                Intent intent = new Intent(NewActivity.this, ImageActivity.class);
                intent.putExtra("Width", width);
                intent.putExtra("Height", height);
                startActivity(intent);
            }
        });

        showSavedImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewActivity.this, getString(R.string.toast_message_4), Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Opening saved images...", Snackbar.LENGTH_LONG).show();

                Intent intent = new Intent(NewActivity.this, SavedImagesActivity.class);
                startActivity(intent);
            }
        });
    }
}