package algonquin.cst2335.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavedConversionsActivity extends AppCompatActivity {

    private ConversionQueryViewModel queryViewModel;
    private SavedConversionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_conversions);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new SavedConversionsAdapter();
        recyclerView.setAdapter(adapter);

        queryViewModel = new ViewModelProvider(this).get(ConversionQueryViewModel.class);

        queryViewModel.getAllQueries().observe(this, queries -> adapter.submitList(queries));
    }
}
