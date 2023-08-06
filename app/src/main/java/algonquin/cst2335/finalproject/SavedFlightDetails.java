package algonquin.cst2335.finalproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.databinding.AviationBinding;

public class SavedFlightDetails extends AppCompatActivity {

AviationBinding binding = AviationBinding.inflate(getLayoutInflater());
Aviation aviation;
FlightDAO flightDAO;
FlightDatabase flightDatabase;
RecyclerView.Adapter myAdapter;
AviationViewModel Amodel;
 List<NameOfflight> saved = new ArrayList<>();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->
        {
            binding.viewdSavedDetails.setOnClickListener(click ->{

            //  aviation  saved = flightDAO.getAllFlightDetails();

            });
        });
    }
}
