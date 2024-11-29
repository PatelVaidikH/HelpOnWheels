package com.example.helponwheels;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helponwheels.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServicePageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference requestRef;

    private TextView customer_name;
    private TextView service_type;
    private TextView car_model;
    private TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_page);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // Initialize TextViews
        customer_name = findViewById(R.id.customer_name);
        service_type = findViewById(R.id.service_type);
        car_model = findViewById(R.id.car_model);
        location = findViewById(R.id.location);

        // Initialize the Log Out button
        Button logoutButton = findViewById(R.id.logout_button);
        if (logoutButton != null) {
            logoutButton.setOnClickListener(v -> logout());
        }

        // Fetch all request data in a single call
        fetchRequestData();
    }

    private void fetchRequestData() {
        // Reference to the specific request in Firebase
        String requestID = "-OCt87MuyjmzRfZIX7CT"; // This should be dynamic based on the current request
        requestRef = firebaseDatabase.getReference("/requestTable/" + requestID);

        // Listen for changes on this request
        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Retrieve all data from the snapshot
                String customerName = snapshot.child("customerName").getValue(String.class);
                String serviceType = snapshot.child("serviceType").getValue(String.class);
                String carModel = snapshot.child("carModel").getValue(String.class);
                String locationValue = snapshot.child("location").getValue(String.class);

                // Update the TextViews with the data
                if (customerName != null) customer_name.setText("Customer Name: " + customerName);
                if (serviceType != null) service_type.setText("Service Type: " + serviceType);
                if (carModel != null) car_model.setText("Car Model: " + carModel);
                if (locationValue != null) location.setText("Location: " + locationValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
            }
        });
    }

    private void logout() {
        // Sign out the user from Firebase Authentication
        mAuth.signOut();

        // Redirect to Login screen
        Intent intent = new Intent(ServicePageActivity.this, Loginpage.class);
        startActivity(intent);

        // Finish current activity so user can't navigate back to the profile
        finish();
    }
}
