package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.databinding.AviationBinding;
import algonquin.cst2335.finalproject.databinding.NameofflightBinding;


public class Aviation extends AppCompatActivity {
    /**
     * Accessing the xml file
     */
    AviationBinding binding;
    /**
     * Accessing the View model for the aviation app
     */
    AviationViewModel Amodel;
    /**
     * Allows for access of the flight database class
     */
    FlightDatabase fd;
    /**
     * Allows access of the Flight DAO interface
     */
    FlightDAO fDAO;
    /**
     *Creates an array List of type NameOfflight
     */
    private ArrayList<NameOfflight>details = new ArrayList<>();
    /**
     * Creates an array list of type Nameofflight
     */
    private ArrayList<NameOfflight> flightDetails = new ArrayList<>();

    /**
     * No-args constructor allows for the implementation of of the class
     */
    public Aviation(){}

    /**
     * Allows access to the MyRowholder class
     */
    MyRowHolder holder;
    /**
     * Instantiate the ResquestQueue class
     */
    private RequestQueue queue = null;
    /**
     * Instantiate a recycle view of type MyRowHolder
     */
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    SharedPreferences prefs;
    /***
     * This method binds the xml to the java and carries out different actions based on the
     * occurence of events.The method implements a  fragment, JSON parsing, arrays and recycle view.
     * Together all elemnts creat an application that allows for the data to be requested and viewed.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.
     *
     */
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);



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

prefs =getSharedPreferences("Aviation", Context.MODE_PRIVATE);
String storedAirportCode = prefs.getString("AirprtCode","");
binding.airportCode.setText(storedAirportCode);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.button.setOnClickListener(click ->{

            String airportCode = binding.airportCode.getText().toString();
            String stringURL = "http://api.aviationstack.com/v1/flights?access_key=d88afd4e913c4d7e46737a949c4c94ec&dep_iata=" + URLEncoder.encode(airportCode);
            SharedPreferences.Editor editor= prefs.edit();
            editor.putString("AirportCode", airportCode);
            editor.apply();

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
            /**
             * Method links to the xml file that formats the data being displayed once clicked
             * @return a binding to the xml file
             */
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                NameofflightBinding binding = NameofflightBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            /**
             * Method sets and displays text in the recycle view
             * @param holder   The ViewHolder which should be updated to represent the contents of the
             *                 item at the given position in the data set.
             * @param position The position of the item within the adapter's data set.
             */
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.nameOfFlight.setText("");//set text here
                NameOfflight detail = details.get(position);// retrieve oject at this position
                holder.nameOfFlight.setText(detail.getFlightName());// set text above


            }

            /**
             * Method returns the size of the array storing the flight name
             * @return the size of the array storing the flight name
             */
            @Override
            public int getItemCount() {
                return details.size();
            }

            /**
             * Method returns the absolute position in the query
             * @param position position to query
             * @return the absolute position
             */
            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });
        }

    /**
     * Class sets the recycle view
     */
    class MyRowHolder extends RecyclerView.ViewHolder{
        /**
         * Accesses a textview item by giving it a name to be referred to
         */
        TextView nameOfFlight;

        /**
         * The method accesses the an item usignthe id and allows actions to be done if the
         * item is selected
         * @param itemview
         */
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

