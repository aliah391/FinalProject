package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.finalproject.room.MyDatabase;
import algonquin.cst2335.finalproject.room.ScoreDatabase;
import algonquin.cst2335.finalproject.room.ScoreEntity;

public class HighScoresActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ScoreDatabase scoreDatabase;
    private ScoreAdapter scoreAdapter;
    private TextView tvNoDataFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        initialise();
    }

    private void initialise() {
        tvNoDataFound = findViewById(R.id.tvNoDataFound);
        scoreDatabase = MyDatabase.getInstance(getApplicationContext());
        List<ScoreEntity> scoreEntities = scoreDatabase.getScoreDao().getAll();
        if (scoreEntities.isEmpty()) {
            tvNoDataFound.setVisibility(View.VISIBLE);
        } else {
            tvNoDataFound.setVisibility(View.GONE);
        }
        scoreAdapter = new ScoreAdapter(scoreEntities);
        recyclerView = findViewById(R.id.rvHighScores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(scoreAdapter);
    }

}

