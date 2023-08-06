package algonquin.cst2335.finalproject;



import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;

import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Button bearImageButton = findViewById(R.id.Bear_Image);
        bearImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start new NewActivity
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(intent);

        binding.Trivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,QuizStartActivity.class));



            ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
           // setSupportActionBar(binding.toolbar);
        binding.Aviation.setOnClickListener(click->{
          Intent nextPage= new Intent(MainActivity.this, Aviation.class);
          startActivity(nextPage);

        });


        }
//
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            super.onCreateOptionsMenu(menu);
//            getMenuInflater().inflate(R.menu.my_menu , menu);
//            return true;
//        }
//
//        @Override
//        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//
//
//            if(item.getItemId()==R.id.AviationItem) {
//                String message =" Aviation";
//                Toast.makeText(this, "You clicked"+ message, Toast.LENGTH_SHORT).show();
//                Intent nextPage= new Intent(MainActivity.this, Aviation.class);
//                startActivity(nextPage);
//
//            }
//
//
//
//
//
//            return true;
//        }

            }
        });
    }

}