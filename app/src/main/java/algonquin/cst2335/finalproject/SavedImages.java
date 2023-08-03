package algonquin.cst2335.finalproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SavedImages {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imageUrl;
    private int width;
    private int height;

    public SavedImages(String imageUrl, int width, int height) {
        this.imageUrl = imageUrl;
        this.width = width;
        this.height = height;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}