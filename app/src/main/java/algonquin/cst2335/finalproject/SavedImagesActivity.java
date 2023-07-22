package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SavedImagesActivity extends AppCompatActivity implements ImageListAdapter.OnImageClickListener {

    private RecyclerView recyclerView;
    private ImageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_images);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<SavedImages> savedImages = loadImages(); // loadImages() should return a list of saved images.
        adapter = new ImageListAdapter(this, savedImages, this);
        recyclerView.setAdapter(adapter);

        Toast.makeText(this, "Saved images loaded", Toast.LENGTH_SHORT).show();
        Snackbar.make(recyclerView, "Saved images loaded", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onImageClick(SavedImages image) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete image?")
                .setMessage("Do you want to delete this image?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteImage(image); // deleteImage() should delete the image from your database
                    adapter.notifyDataSetChanged();
                    Snackbar.make(recyclerView, "Image deleted successfully", Snackbar.LENGTH_LONG).show();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private List<SavedImages> loadImages() {
        // This method should load the list of saved images from your database.
        // The implementation depends on how you're saving the images.
        // Here's an example implementation that returns a list with dummy data:
        List<SavedImages> savedImages = new ArrayList<>();
        savedImages.add(new SavedImages("https://placebear.com/200/200", 200, 200));
        savedImages.add(new SavedImages("https://placebear.com/300/300", 300, 300));
        return savedImages;
    }

    private void deleteImage(SavedImages image) {
        // This method should delete the given image from your database.
        // The implementation depends on how you're saving the images.
        // Here's an example implementation that removes the image from the list:
        adapter.removeImage(image);
    }
}