package algonquin.cst2335.finalproject;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.snackbar.Snackbar;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import java.util.List;

import algonquin.cst2335.finalproject.databinding.AviationBinding;
import algonquin.cst2335.finalproject.databinding.NameofflightBinding;


import algonquin.cst2335.finalproject.databinding.AviationBinding;
//import algonquin.cst2335.finalproject.databinding.NameOfFlightBinding;



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
       setSupportActionBar(binding.toolbar2);

        aviationModel.selectedMessage.observe(this, (newValue)->{
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            FlightDetailsFragment flightFragment = new FlightDetailsFragment( newValue );
            tx.add(R.id.fragmentContainer, flightFragment);
            tx.commit();
        });

        Amodel = new ViewModelProvider(this).get(AviationViewModel.class);
        details = Amodel.details.getValue();
        if(details == null){
            Amodel.details.postValue(details = new ArrayList<NameOfflight>());
        }
  AviationViewModel.selectedMessage.observe(this, (newMessageValue) -> {

            });


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.button.setOnClickListener(click ->{

            String airportCode = binding.airportCode.getText().toString();

        binding = AviationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       String message = "Flights are loading";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
          


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
          
            itemView.setOnClickListener(clk -> {
                AlertDialog.Builder builder = new AlertDialog.Builder((Aviation.this));
                builder.setMessage("Do you want to see the flight details on "); // add in the name of the ffflight details
                builder.setTitle("Verification");
                builder.setNegativeButton("no", (dialog, which) -> {

                });
                builder.setPositiveButton("yes", (dialog, which) -> {
                    int position = getAbsoluteAdapterPosition();
                    FlightDetails selectedFlight = detailsTemp.get(position);
                    AviationViewModel.selectedMessage.postValue(selectedFlight);
                    /*
                    arrayname flightdetails = detailsTemp.get(position);
                    intent new page
                    link all the details to the new page
                     */
                });


            });
            // nameOfFlight=itemView.findViewById((R.id.nameOfFlight));
        }
        }


 }

//                 if (detailsTemp.get(position).airportCodeButton) {
//                     return 0;
//                 } else {
//                     return 1;
//                 }

//             }
//         });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder((Aviation.this));
            builder.setMessage("The application allows you to view the flight that are departing from an airport and view, save and delete selected information");
            builder.setTitle("Help using the Aviation Application");
            builder.setPositiveButton("I understand", (dialog, click) -> {});
            builder.setNegativeButton("I don't understand", (dialog, click) -> {
                builder.setMessage("Steps to using this application\n1. Enter an airportCode to view all departing flights\n" +
                        "2. Click the search button\n3. A list of flights will be displayed, select the flight you would like to view\n" +
                        "4. The flight details will be displayed. If you would like to save the flight information click the 'Save details' button\n" +
                        "If you would like to view the flights that have been saved, click the 'View Saved Flight Details' icon\n" +
                        "5. If you would like to delete the flight details from the flights you have saved, click the flight and confirm delete");
            });
        }return true;




    }

       // RecyclerView FlightText;

        public MyFlightHolder(@NonNull View itemView) {
            super(itemView);

            FlightText = itemView.findViewById(R.id.recyclerView);
          



    }



}

