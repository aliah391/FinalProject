package algonquin.cst2335.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.finalproject.room.ScoreEntity;

/**
 * Adapter class for displaying a list of score entries in a RecyclerView.
 */
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private List<ScoreEntity> scoreEntityList;

    /**
     * Constructs a new ScoreAdapter with the provided list of score entities.
     *
     * @param scoreEntityList The list of score entities to display.
     */
    public ScoreAdapter(List<ScoreEntity> scoreEntityList) {
        this.scoreEntityList = scoreEntityList;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        ScoreEntity scoreEntity = scoreEntityList.get(position);
        holder.txtName.setText(scoreEntity.name);
        holder.txtScore.setText(String.valueOf(scoreEntity.score));
    }

    @Override
    public int getItemCount() {
        return scoreEntityList.size();
    }

    /**
     * ViewHolder class representing a single score entry item in the RecyclerView.
     */
    class ScoreViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtName;
        private final TextView txtScore;

        /**
         * Constructs a new ScoreViewHolder with the given itemView.
         *
         * @param itemView The view representing a single score entry item.
         */
        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.tvName);
            txtScore = itemView.findViewById(R.id.tvScore);
        }
    }
}