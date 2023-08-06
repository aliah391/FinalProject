package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.DefaultRetryPolicy;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.databinding.AviationBinding;
import algonquin.cst2335.finalproject.databinding.NameofflightBinding;


public class Aviation extends AppCompatActivity {
    AviationBinding binding;
    AviationViewModel Amodel;
    FlightDatabase fd;
    FlightDAO fDAO;
    private ArrayList<NameOfflight>details = new ArrayList<>();
    private ArrayList<NameOfflight> flightDetails = new ArrayList<>();
    public Aviation(){}
    MyRowHolder holder;
    private RequestQueue queue = null;
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
   // ArrayList<String> flightinfo= new ArrayList<>();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);

         fd = Room.databaseBuilder(getApplicationContext(), FlightDatabase.class, "FlightDetails").build();
        fDAO=fd.flightDAO();

        binding = AviationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Amodel = new ViewModelProvider(this).get(AviationViewModel.class);

        details = Amodel.details.getValue();
        flightDetails = Amodel.flightDetails.getValue();


        if(details == null){
            Amodel.details.postValue(details = new ArrayList<NameOfflight>());
        }
        if(flightDetails==null){
            Amodel.flightDetails.postValue(flightDetails = new ArrayList<NameOfflight>());
        }


        Amodel.selectedMessage.observe(this,(newSelection)->{
           FlightDetailsFragment flightfragment = new FlightDetailsFragment(newSelection);
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            tx.add(R.id.fragmentContainer, flightfragment);
            tx.addToBackStack("");
            tx.commit();
                });

//        Executor thread = Executors.newSingleThreadExecutor();
//        thread.execute({
//
//        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.button.setOnClickListener(click ->{

            String airportCode = binding.airportCode.getText().toString();
            String stringURL = "http://api.aviationstack.com/v1/flights?access_key=d88afd4e913c4d7e46737a949c4c94ec&dep_iata=" + URLEncoder.encode(airportCode);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> {

                        try {
                            Log.d("Aviation", "JSON Response"+response.toString());
                            JSONArray dataArray = response.getJSONArray("data");
                            // int lengthOfArray = dataArray.getInt(-1);

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject position = dataArray.getJSONObject(i);
                                JSONObject airlineInfo = position.getJSONObject("airline");
                                String flightName = airlineInfo.getString("name");

                        JSONObject destination = position.getJSONObject("arrival");
                        String arrival =  destination.getString("airport");
                        JSONObject departure = position.getJSONObject("departure");
                        String Terminal = departure.getString("terminal");
                        String Gate = departure.getString("gate");
                        String Delay = departure.getString("delay");

                                if (flightName.equalsIgnoreCase("empty")){
                                    flightName ="Not available";
                                    NameOfflight fName = new NameOfflight(flightName);
                                    details.add(fName);
                                    NameOfflight moredetails= new NameOfflight(flightName, arrival, Terminal, Gate, Delay);
                                    flightDetails.add(moredetails);
                                }else{
                                    NameOfflight fName = new NameOfflight(flightName);
                                    details.add(fName);
                                    NameOfflight moredetails= new NameOfflight(flightName, arrival, Terminal, Gate, Delay);
                                    flightDetails.add(moredetails);
                                }
                               myAdapter.notifyDataSetChanged();
                                myAdapter.notifyItemInserted(details.size() - 1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, (error) -> {
                        Log.e("Aviation", "Error retrieving Json response:"+error.toString());
            });
            request.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
            nameOfFlight = itemView.findViewById(R.id.nameofflight);
            itemView.setOnClickListener(click ->{
                int position = getAbsoluteAdapterPosition();
                NameOfflight selected = details.get(position);
                NameOfflight info = flightDetails.get(position);

                Amodel.selectedMessage.postValue(selected);
                Amodel.selectedMessage.postValue(info);
            });
        }


 }


    }

