package algonquin.cst2335.finalproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;

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

        adapter.setOnItemClickListener(new SavedConversionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ConversionQuery queryToBeDeleted = adapter.getConversionAt(position);


                new AlertDialog.Builder(SavedConversionsActivity.this)
                        .setTitle(getString(R.string.dialog_title_delete_conversion))
                        .setMessage(getString(R.string.dialog_message_delete_conversion))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete the conversion
                                queryViewModel.delete(queryToBeDeleted);
                                Toast.makeText(SavedConversionsActivity.this, getString(R.string.toast_conversion_deleted), Toast.LENGTH_SHORT).show();

                                Snackbar.make(recyclerView, getString(R.string.snackbar_conversion_deleted), Snackbar.LENGTH_LONG)
                                        .setAction(getString(R.string.snackbar_undo_action), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                queryViewModel.insert(queryToBeDeleted);
                                                Toast.makeText(SavedConversionsActivity.this, getString(R.string.toast_conversion_restored), Toast.LENGTH_SHORT).show();
                                            }
                                        }).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
    }
}