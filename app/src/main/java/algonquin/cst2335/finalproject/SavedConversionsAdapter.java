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

public class SavedConversionsAdapter extends ListAdapter<ConversionQuery, SavedConversionsAdapter.ViewHolder> {
    private OnItemClickListener listener;

    public SavedConversionsAdapter() {
        super(DIFF_CALLBACK);
    }

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

    public ConversionQuery getConversionAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

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



