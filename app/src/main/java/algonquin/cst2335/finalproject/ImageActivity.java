package algonquin.cst2335.finalproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

/**
 * ImageActivity is an activity class responsible for displaying and saving images.
 * The images are loaded from a URL using Volley and can be saved into Room database.
 * This class also implements a menu with a 'Help' item.
 *
 * @author Nikita
 * @version 1.0
 * @since 2023-08-05
 */
public class ImageActivity extends AppCompatActivity {

    private String imageUrl;
    private int imageWidth;
    private int imageHeight;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (item.getItemId() == R.id.action_help) {
            displayHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method displays a help dialog with information about the current activity.
     */
    private void displayHelpDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.help_title)
                .setMessage(R.string.help_message_image_activity)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = getIntent();
        imageWidth = intent.getIntExtra("Width", 0);
        imageHeight = intent.getIntExtra("Height", 0);

        ImageView imageView = findViewById(R.id.imageView);
        Button saveButton = findViewById(R.id.saveButton);

        // Generate URL and load image using Volley
        imageUrl = "https://placebear.com/" + imageWidth + "/" + imageHeight;
        loadImageWithVolley(imageUrl, imageView);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ImageActivity.this);
                builder.setMessage(getString(R.string.dialog_message_1))
                        .setPositiveButton(getString(R.string.dialog_positive), (dialog, id) -> {
                            // Save the image
                            // This code is dependent on your method of saving images
                            saveImageToDatabase(imageUrl, imageWidth, imageHeight);
                            Snackbar.make(v, getString(R.string.snackbar_message_1), Snackbar.LENGTH_LONG).show();
                        })
                        .setNegativeButton(getString(R.string.dialog_negative), (dialog, id) -> dialog.dismiss())
                        .show();
            }
        });
    }

    /**
     * This method uses Volley to load an image from a URL and display it in an ImageView.
     *
     * @param imageUrl The URL of the image to load.
     * @param imageView The ImageView where the image will be displayed.
     */
    private void loadImageWithVolley(String imageUrl, ImageView imageView) {
        ImageRequest imageRequest = new ImageRequest(imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error if image retrieval fails
                        Snackbar.make(imageView,getString(R.string.snackbar_message_2), Snackbar.LENGTH_SHORT).show();
                    }
                });
        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(imageRequest);
    }





    /**
     * This method saves an image to the Room database in a background thread.
     *
     * @param imageUrl The URL of the image.
     * @param width The width of the image.
     * @param height The height of the image.
     */
    private void saveImageToDatabase(String imageUrl, int width, int height) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "images").build();
        SavedImagesDao dao = db.savedImagesDao();

        new Thread(() -> {
            dao.insert(new SavedImages(imageUrl, width, height));
        }).start();
    }
}