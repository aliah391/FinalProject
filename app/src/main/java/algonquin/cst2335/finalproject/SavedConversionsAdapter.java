package algonquin.cst2335.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

/**
 * Adapter class to populate a RecyclerView with saved currency conversion queries.
 */
public class SavedConversionsAdapter extends ListAdapter<ConversionQuery, SavedConversionsAdapter.ViewHolder> {

    // Listener to handle item click events
    private OnItemClickListener listener;

    /**
     * Default constructor for this adapter, it uses a DiffUtil.ItemCallback.
     */
    public SavedConversionsAdapter() {
        super(DIFF_CALLBACK);
    }

    /**
     * Static instance of DiffUtil.ItemCallback used to calculate the minimum amount
     * of change to update the list.
     */
    private static final DiffUtil.ItemCallback<ConversionQuery> DIFF_CALLBACK = new DiffUtil.ItemCallback<ConversionQuery>() {

        @Override
        public boolean areItemsTheSame(@NonNull ConversionQuery oldItem, @NonNull ConversionQuery newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ConversionQuery oldItem, @NonNull ConversionQuery newItem) {
            return oldItem.fromCurrency.equals(newItem.fromCurrency)
                    && oldItem.toCurrency.equals(newItem.toCurrency)
                    && oldItem.amount == newItem.amount
                    && oldItem.result == newItem.result;
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConversionQuery currentQuery = getItem(position);
        String text = String.format(Locale.US,
                "%s %.2f to %s = %.2f",
                currentQuery.fromCurrency, currentQuery.amount, currentQuery.toCurrency, currentQuery.result);
        holder.textView.setText(text);
    }

    /**
     * Returns the ConversionQuery object at the specified position.
     *
     * @param position The position of the ConversionQuery to return.
     * @return The ConversionQuery object at the specified position.
     */
    public ConversionQuery getConversionAt(int position) {
        return getItem(position);
    }

    /**
     * Sets the OnItemClickListener to handle item click events.
     *
     * @param listener The listener that will handle item click events.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * Interface for defining a method to handle item click events.
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    /**
     * ViewHolder class to provide a reference to the views for each data item.
     * Complex data items may need more than one view per item, and you provide access
     * to all the views for a data item in a view holder.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            });
        }
    }
}
