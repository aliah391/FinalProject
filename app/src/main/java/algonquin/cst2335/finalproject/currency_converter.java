package algonquin.cst2335.finalproject;

public class currency_converter {
}

//
//        import androidx.annotation.NonNull;
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.recyclerview.widget.RecyclerView;
//        import androidx.room.Query;
//
//        import android.app.AlertDialog;
//        import android.os.Bundle;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.ArrayAdapter;
//        import android.widget.Spinner;
//        import android.widget.Toast;
//
//        import java.util.List;
//
//        import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;
//        import algonquin.cst2335.finalproject.databinding.ViewSavedConversionsBinding;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        Spinner spinner = binding.spinnerFromCurrency;
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.currency_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        Spinner spinnerDestination = binding.spinnerToCurrency;
//        ArrayAdapter<CharSequence> adapterDestination = ArrayAdapter.createFromResource(this,
//                R.array.currency_array, android.R.layout.simple_spinner_item);
//        adapterDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerDestination.setAdapter(adapterDestination);
//    }
//}
//
//class SavedConversionsActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private SavedQueriesAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        ViewSavedConversionsBinding binding = ViewSavedConversionsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        recyclerView = binding.recyclerView;
//        // TODO: Retrieve savedQueriesList from your database
//        List<Query> savedQueriesList = null;
//        adapter = new SavedQueriesAdapter(savedQueriesList);
//        recyclerView.setAdapter(adapter);
//    }
//}
//
////class SavedQueriesAdapter extends RecyclerView.Adapter<SavedQueriesAdapter.ViewHolder> {
////
////    private List<Query> savedQueriesList;
////
////    public SavedQueriesAdapter(List<Query> savedQueriesList) {
////        this.savedQueriesList = savedQueriesList;
////    }
//
////    @NonNull
////    @Override
////    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.query_item, parent, false);
////        return new ViewHolder(view);
////    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.itemView.setOnLongClickListener(v -> {
//            new AlertDialog.Builder(v.getContext())
//                    .setTitle("Delete Query")
//                    .setMessage("Are you sure you want to delete this query?")
//                    .setPositiveButton("Yes", (dialog, which) -> {
//                        savedQueriesList.remove(position);
//                        notifyItemRemoved(position);
//                        Toast.makeText(v.getContext(), "Query removed", Toast.LENGTH_SHORT).show();
//                    })
//                    .setNegativeButton("No", null)
//                    .show();
//            return true;
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        if (savedQueriesList != null) {
//            return savedQueriesList.size();
//        } else {
//            return 0;
//        }
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        //
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            //
//        }
//    }
//}
