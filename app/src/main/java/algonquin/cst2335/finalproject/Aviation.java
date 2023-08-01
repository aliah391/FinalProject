package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.databinding.AviationBinding;


public class Aviation extends AppCompatActivity {
    AviationBinding binding;
    AviationViewModel Amodel;
    private ArrayList<FlightDetails>details = new ArrayList<>();
    public Aviation(){}

    private RecyclerView.Adapter myAdapter;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AviationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Amodel = new ViewModelProvider(this).get(AviationViewModel.class);
    details = Amodel.details.getValue();
        if(details == null){
            Amodel.details.postValue(details = new ArrayList<FlightDetails>());
        }


        binding.button.setOnClickListener(click ->{
            String airportCode = binding.airportCode.getText().toString();

        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
        }
 class MyRowHolder extends RecyclerView.ViewHolder{

        public MyRowHolder(View itemview){
            super(itemview);
        }
        }


    }

