package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;


import algonquin.cst2335.finalproject.databinding.FlightdetailsfragmentBinding;

public class FlightDetailsFragment extends Fragment {
    FlightDatabase fdatabase;
    FlightDAO flightdao;
    FlightDetails selected;
    public FlightDetailsFragment(FlightDetails a){
        selected= a;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);

        FlightdetailsfragmentBinding binding = FlightdetailsfragmentBinding.inflate(inflater);
//
//        binding.nameOfFlightText.setText(selected.);
//        binding.destinationText.setText(selected.);
//        binding.gateText.setText(selected);
//        binding.delayText.setText(selected.);
//        binding.TerminalText.setText(selected.);

        binding.saveData.setOnClickListener(clk -> {

           fdatabase = Room.databaseBuilder(getContext().getApplicationContext(), FlightDatabase.class, "database-name").build();
         flightdao= fdatabase.flightDAO();
        } );
        return binding.getRoot();

    }
}
