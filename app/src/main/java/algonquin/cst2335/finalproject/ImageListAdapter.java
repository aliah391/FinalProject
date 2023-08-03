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

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private final Context context;
    private final List<SavedImages> savedImages;
    private final OnImageClickListener listener;

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

    public void removeImage(SavedImages image) {
        savedImages.remove(image);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }

        void bind(final SavedImages image, final OnImageClickListener listener) {
            Picasso.get().load(image.getImageUrl()).into(imageView);
            String dimensions = image.getWidth() + "x" + image.getHeight();
            textView.setText(dimensions);
            itemView.setOnClickListener(v -> listener.onImageClick(image));
        }
    }

    public interface OnImageClickListener {
        void onImageClick(SavedImages image);
    }
}
