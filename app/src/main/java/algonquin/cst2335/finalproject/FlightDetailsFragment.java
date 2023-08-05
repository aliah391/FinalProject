package algonquin.cst2335.finalproject;

import androidx.fragment.app.Fragment;




    import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import algonquin.cst2335.finalproject.databinding.FlightdetailsfragmentBinding;
    public class FlightDetailsFragment extends Fragment {
        FlightDatabase fdatabase;
        FlightDAO flightdao;
        NameOfflight selected;
        public FlightDetailsFragment(NameOfflight a){
            selected= a;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            FlightdetailsfragmentBinding binding = FlightdetailsfragmentBinding.inflate(inflater);

        binding.nameOfFlightText.setText(selected.flightName);
        binding.destinationText.setText(selected.destination);
        binding.gateText.setText(selected.gate);
        binding.delayText.setText(selected.delay);
        binding.TerminalText.setText(selected.Terminal);
        binding.databaseid.setText("ID="+selected.id);

//            binding.saveData.setOnClickListener(clk -> {
//
//               // fdatabase = Room.databaseBuilder(getContext().getApplicationContext(), FlightDatabase.class, "database-name").build();
//               // flightdao= fdatabase.flightDAO();
//            } );
            return binding.getRoot();

        }
    }



