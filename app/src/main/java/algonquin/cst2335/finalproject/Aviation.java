package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

import algonquin.cst2335.finalproject.databinding.AviationBinding;
import algonquin.cst2335.finalproject.databinding.NameOfFlightBinding;


public class Aviation extends AppCompatActivity {
    AviationBinding binding;
    FlightDetails fDAO;
    FlightDatabase fd;
    private String airportCode;
    private RequestQueue queue = null;


    private ArrayList<String> detailsTemp = new ArrayList<>();
   // private ArrayList<FlightDetails> details = new ArrayList<>();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(this);

        binding = AviationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String defaultValue;
//        fd = Room.databaseBuilder(getApplicationContext(), FlightDatabase.class, "database-name").build();
//         fDAO= fd.flightDAO();


        binding.button.setOnClickListener(blk ->{
          airportCode = (binding.airportCode.getText().toString());

            String stringURL = "http://api.aviationstack.com/v1/flights?access_key=d88afd4e913c4d7e46737a949c4c94ec&dep_iata="+ URLEncoder.encode(airportCode);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
            (reponse)->{
                try {

                    JSONArray dataArray = reponse.getJSONArray("data");
                    JSONObject position = dataArray.getJSONObject(0);
                    JSONObject airlineInfo = position.getJSONObject("airline");
                    String flightName = airlineInfo.getString("name");

                    JSONObject destination = position.getJSONObject("arrival");
                    String arrival =  destination.getString("airport");
                    String arrivalTerminal = destination.getString("terminal");
                    String arrivalGate = destination.getString("gate");
                    String arrivalDelay = destination.getString("delay");
                    SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    //prefs.getString("flight" ,String defaultValue);
                    String arrivalInfo = prefs.getString("airport","");
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.apply();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            },
                    (error)-> {}    );
            queue.add(request);
            //retreive a list of flights
            FlightDetails fdetails = new FlightDetails();
            detailsTemp.add(airportCode);



        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new RecyclerView.Adapter<MyFlightHolder>() {
            @NonNull
            @Override
            public MyFlightHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                NameOfFlightBinding binding = NameOfFlightBinding.inflate(getLayoutInflater());
                return new MyFlightHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyFlightHolder holder, int position) {
                holder.nameOfFlight.setText("");
                String obj = detailsTemp.get(position);
                holder.nameOfFlight.setText(obj);
            }

            @Override
            public int getItemCount() {
                return detailsTemp.size();
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });




    }

    class MyFlightHolder extends RecyclerView.ViewHolder{
        int position= getAbsoluteAdapterPosition();
        TextView nameOfFlight;
        public MyFlightHolder(@NonNull View itemView) {
            super(itemView);

            nameOfFlight = itemView.findViewById(R.id.nameOfFlight);
            itemView.setOnClickListener(clk ->{
                Snackbar.make(nameOfFlight, position, Snackbar.LENGTH_LONG).show();

               // String messag= "I am a toast";

              //  Toast.makeText(this, messag, Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(Aviation.this);
            builder.setMessage("Are you sure you wwant to delete"+ nameOfFlight.getText());
            builder.setTitle("Flight to be deleted");
            builder.setPositiveButton("Yes", (dialog, cl)->{});
            builder.setNegativeButton("no", (dialog, cl) ->{});

        });
            nameOfFlight=itemView.findViewById((R.id.nameOfFlight));
        }


    }




}

