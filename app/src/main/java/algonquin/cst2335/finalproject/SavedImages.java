package algonquin.cst2335.finalproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * SavedImages is an entity class for Room persistence library.
 * This class represents a table in the SQLite database.
 * The class is used to store the url, width, and height of a saved image.
 *
 * @author Nikita
 * @version 1.0
 * @since 2023-08-05
 */
@Entity
public class SavedImages {

    /**
     * The unique ID of the image.
     */
    @PrimaryKey(autoGenerate = true)
    private int id;

    /**
     * The URL of the image.
     */
    private String imageUrl;

    /**
     * The width of the image.
     */
    private int width;

    /**
     * The height of the image.
     */
    private int height;

    /**
     * Constructor to create a new SavedImages object.
     *
     * @param imageUrl The URL of the image.
     * @param width    The width of the image.
     * @param height   The height of the image.
     */
    public SavedImages(String imageUrl, int width, int height) {
        this.imageUrl = imageUrl;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the URL of the image.
     *
     * @return The URL of the image.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the URL of the image.
     *
     * @param imageUrl The new URL of the image.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the width of the image.
     *
     * @return The width of the image.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the image.
     *
     * @param width The new width of the image.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height of the image.
     *
     * @return The height of the image.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the image.
     *
     * @param height The new height of the image.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the URL of the image.
     *
     * @return The URL of the image.
     */
    public String getUrl() {
        return imageUrl;
    }

    /**
     * Gets the ID of the image.
     *
     * @return The ID of the image.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the image.
     *
     * @param id The new ID of the image.
     */
    public void setId(int id) {
        this.id = id;
    }
}