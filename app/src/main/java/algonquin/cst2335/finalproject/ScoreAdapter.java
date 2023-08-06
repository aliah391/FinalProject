package algonquin.cst2335.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.finalproject.room.ScoreEntity;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {
    private List<ScoreEntity> scoreEntityList;

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

    class ScoreViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtName;
        private final TextView txtScore;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.tvName);
            txtScore = itemView.findViewById(R.id.tvScore);
        }
    }
}
