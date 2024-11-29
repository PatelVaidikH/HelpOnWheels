package com.example.helponwheels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";  // Tag for logging
    private DatabaseReference myRef;  // Firebase reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Ensure you have implemented this in your project
        setContentView(R.layout.activity_main);

        // Adjust padding based on system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the emergency button by its ID
        Button emergencyButton = findViewById(R.id.Emergencybtn);

        // Set onClickListener for the emergency button
        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to Emergencypage
                Intent intent = new Intent(MainActivity.this, Emergencypage.class);
                startActivity(intent); // Start the Emergencypage activity
            }
        });

        // Find the service provider card by its ID
        CardView serviceProviderCard = findViewById(R.id.serviceProviderCard);

        // Set an OnClickListener for the service provider card
        serviceProviderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to Servicepage
                Intent intent = new Intent(MainActivity.this, Servicepage.class);
                startActivity(intent); // Start the Servicepage activity
            }
        });

        // Firebase Database interaction
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("test_message");  // Set a reference for testing

        // Write some test data to the database
        myRef.setValue("Firebase is connected!");  // Write "Firebase is connected!" to the database

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);  // Log the value retrieved from the database

                // Show the retrieved value as a Toast or Log for confirmation
                if (value != null) {
                    Log.d(TAG, "Data from DB: " + value);  // Log the value read from the database
                } else {
                    Log.d(TAG, "No data found in the database.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
