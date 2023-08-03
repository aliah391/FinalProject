package algonquin.cst2335.finalproject;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SavedImagesActivity extends AppCompatActivity implements ImageListAdapter.OnImageClickListener {

    private RecyclerView recyclerView;
    private ImageListAdapter adapter;


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
                .setMessage(R.string.help_message_saved_images_activity)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_images);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<SavedImages> savedImages = loadImages(); // loadImages() should return a list of saved images.
        adapter = new ImageListAdapter(this, savedImages, this);
        recyclerView.setAdapter(adapter);

        Toast.makeText(this, getString(R.string.toast_message_5), Toast.LENGTH_SHORT).show();
        Snackbar.make(recyclerView, getString(R.string.snackbar_saved_images_loaded), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onImageClick(SavedImages image) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title))
                .setMessage(getString(R.string.dialog_message))
                .setPositiveButton(getString(R.string.dialog_positive), (dialog, which) -> {
                    deleteImage(image); // deleteImage() should delete the image from your database
                    adapter.notifyDataSetChanged();
                    Snackbar.make(recyclerView, getString(R.string.snackbar_message), Snackbar.LENGTH_LONG).show();
                })
                .setNegativeButton(getString(R.string.dialog_negative), (dialog, which) -> dialog.dismiss())
                .show();
    }

    private List<SavedImages> loadImages() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "images").build();
        SavedImagesDao dao = db.savedImagesDao();

        List<SavedImages> savedImages = new ArrayList<>();
        new Thread(() -> {
            savedImages.addAll(dao.getAll());
        }).start();

        return savedImages;
    }

    private void deleteImage(SavedImages image) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "images").build();
        SavedImagesDao dao = db.savedImagesDao();

        new Thread(() -> {
            dao.delete(image);
        }).start();

        adapter.removeImage(image);
    }
}