package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.databinding.AviationBinding;
import algonquin.cst2335.finalproject.databinding.NameofflightBinding;


public class Aviation extends AppCompatActivity {
    AviationBinding binding;
    AviationViewModel Amodel;
    private ArrayList<NameOfflight>details = new ArrayList<>();
    public Aviation(){}
    MyRowHolder holder;
    private RequestQueue queue = null;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);

        binding = AviationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Amodel = new ViewModelProvider(this).get(AviationViewModel.class);
        details = Amodel.details.getValue();
        if(details == null){
            Amodel.details.postValue(details = new ArrayList<NameOfflight>());
        }



        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.button.setOnClickListener(click ->{

            String airportCode = binding.airportCode.getText().toString();
            String stringURL = "http://api.aviationstack.com/v1/flights?access_key=d88afd4e913c4d7e46737a949c4c94ec&dep_iata=" + URLEncoder.encode(airportCode);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> {
                        try {

                            JSONArray dataArray = response.getJSONArray("data");
                            // int lengthOfArray = dataArray.getInt(-1);

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject position = dataArray.getJSONObject(i);
                                JSONObject airlineInfo = position.getJSONObject("airline");
                                String flightName = airlineInfo.getString("name");

//                        JSONObject destination = position.getJSONObject("arrival");
//                        String arrival =  destination.getString("airport");
//                        String arrivalTerminal = destination.getString("terminal");
//                        String arrivalGate = destination.getString("gate");
//                        String arrivalDelay = destination.getString("delay");
                                NameOfflight fName = new NameOfflight(flightName);
                                details.add(fName);
                                myAdapter.notifyDataSetChanged();
                                myAdapter.notifyItemInserted(details.size() - 1);

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    (error) -> {
                        int i = 0;
                    });
            queue.add(request);


        });



        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                NameofflightBinding binding = NameofflightBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.nameOfFlight.setText("");//set text here

                NameOfflight detail = details.get(position);// retrieve oject at this position
                holder.nameOfFlight.setText(detail.getFlightName());// set text above

            }

            @Override
            public int getItemCount() {
                return details.size();
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });
        }
 class MyRowHolder extends RecyclerView.ViewHolder{

        TextView nameOfFlight;
        public MyRowHolder(View itemview){
            super(itemview);
            nameOfFlight = itemview.findViewById(R.id.nameofflight);
        }


 }


    }

