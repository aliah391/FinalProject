package algonquin.cst2335.finalproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    private String imageUrl;
    private int imageWidth;
    private int imageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        imageWidth = intent.getIntExtra("Width", 0);
        imageHeight = intent.getIntExtra("Height", 0);

        ImageView imageView = findViewById(R.id.imageView);
        Button saveButton = findViewById(R.id.saveButton);

        // Generate URL and load image using Picasso
        imageUrl = "https://placebear.com/" + imageWidth + "/" + imageHeight;
        Picasso.get().load(imageUrl).into(imageView);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ImageActivity.this);
                builder.setMessage("Do you want to save this image?")
                        .setPositiveButton("Yes", (dialog, id) -> {
                            // Save the image
                            // This code is dependent on your method of saving images
                            saveImageToDatabase(imageUrl, imageWidth, imageHeight);
                            Snackbar.make(v, "Image saved successfully", Snackbar.LENGTH_LONG).show();
                        })
                        .setNegativeButton("No", (dialog, id) -> dialog.dismiss())
                        .show();
            }
        });
    }

    private void saveImageToDatabase(String imageUrl, int width, int height) {
        // TODO: Save the image to your database using the provided information
    }
}
