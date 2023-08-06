package algonquin.cst2335.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * ImageListAdapter is an adapter class for managing a collection of SavedImages in a RecyclerView.
 * This class is responsible for creating the view holders and binding the view holders to their data.
 * It uses the Picasso library for image loading.
 *
 * @author Nikita
 * @version 1.0
 * @since 2023-08-05
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private final Context context;
    private final List<SavedImages> savedImages;
    private final OnImageClickListener listener;

    /**
     * Constructor for the ImageListAdapter.
     *
     * @param context Context in which the adapter is being used.
     * @param savedImages List of SavedImages objects to be displayed.
     * @param listener Listener for image click events.
     */
    public ImageListAdapter(Context context, List<SavedImages> savedImages, OnImageClickListener listener) {
        this.context = context;
        this.savedImages = savedImages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SavedImages image = savedImages.get(position);
        holder.bind(image, listener);
    }

    @Override
    public int getItemCount() {
        return savedImages.size();
    }

    /**
     * Removes a SavedImages object from the adapter and updates the RecyclerView.
     *
     * @param image The SavedImages object to be removed.
     */
    public void removeImage(SavedImages image) {
        savedImages.remove(image);
        notifyDataSetChanged();
    }

    /**
     * ViewHolder class that describes an item view and metadata about its place within the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        /**
         * Constructor for the ViewHolder.
         *
         * @param itemView The View that you inflate in your custom ViewHolder.
         */
        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }

        /**
         * Binds a SavedImages object to the ViewHolder and sets up the click listener.
         *
         * @param image The SavedImages object to be bound.
         * @param listener The listener for image click events.
         */
        void bind(final SavedImages image, final OnImageClickListener listener) {
            Picasso.get().load(image.getImageUrl()).into(imageView);
            String dimensions = image.getWidth() + "x" + image.getHeight();
            textView.setText(dimensions);
            itemView.setOnClickListener(v -> listener.onImageClick(image));
        }
    }

    /**
     * Listener interface for image click events.
     */
    public interface OnImageClickListener {
        /**
         * This method is invoked when an image is clicked.
         *
         * @param image The SavedImages object that was clicked.
         */
        void onImageClick(SavedImages image);
    }
}
