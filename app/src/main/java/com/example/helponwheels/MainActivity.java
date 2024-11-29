package com.example.helponwheels;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth; // Firebase Auth instance
    private DatabaseReference mDatabase; // Firebase Database reference
    private TextView userNameTextView; // TextView to display user name
    private DatabaseReference myRef;  // Firebase reference for test message
    private GoogleMap myMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);  // Ensure you have implemented this in your project
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("userTable");

        // Adjust padding based on system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the TextViews and other UI components by their ID
        userNameTextView = findViewById(R.id.textView2);  // Assuming textView2 is where username is displayed
        Button emergencyButton = findViewById(R.id.Emergencybtn);  // Emergency button
        CardView serviceProviderCard = findViewById(R.id.serviceProviderCard);  // Service provider card
        LinearLayout navbarProfile = findViewById(R.id.navbar_profile);  // Profile navigation

        // Firebase Database interaction
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("test_message");  // Set a reference for testing

        // Write some test data to the database
        myRef.setValue("Firebase is connected!");  // Write "Firebase is connected!" to the database

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);  // Log the value retrieved from the database

                if (value != null) {
                    Log.d(TAG, "Data from DB: " + value);  // Log the value read from the database
                } else {
                    Log.d(TAG, "No data found in the database.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // Set onClickListener for the emergency button
        emergencyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Emergencypage.class);
            startActivity(intent);  // Start the Emergencypage activity
        });

        // Set onClickListener for the service provider card
        serviceProviderCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Servicepage.class);
            startActivity(intent);  // Start the Servicepage activity
        });

        // Add the profile icon navigation
        navbarProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, profile.class);
            startActivity(intent);  // Start the Profile activity
        });

        // Initialize FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Get the current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid(); // Get the user's unique ID

            // Reference to the specific user in the database using the user ID
            DatabaseReference userRef = mDatabase.child(userId);

            // Fetch user data from the database
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Extract the data from the snapshot
                        String userName = dataSnapshot.child("userName").getValue(String.class);

                        // Update the UI with the fetched data
                        userNameTextView.setText(userName); // Set the username in TextView
                    } else {
                        Log.d(TAG, "User data not found in database");
                        Toast.makeText(MainActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Failed to read value.", databaseError.toException());
                }
            });
        } else {
            Log.d(TAG, "User is not logged in");
            Toast.makeText(MainActivity.this, "User is not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        // Request location permissions if needed
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        // Try to get the user's current location
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        // User's location found, move the camera to their location
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        myMap.addMarker(new com.google.android.gms.maps.model.MarkerOptions().position(userLocation).title("You are here"));
                        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                    } else {
                        // Location not found, default to Vadodara
                        LatLng vadodara = new LatLng(22.310696, 73.192635);
                        myMap.addMarker(new com.google.android.gms.maps.model.MarkerOptions().position(vadodara).title("Marker in Vadodara"));
                        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vadodara, 15));
                    }
                })
                .addOnFailureListener(this, e -> {
                    Log.e(TAG, "Failed to get location: " + e.getMessage());
                    // Default to Vadodara on failure
                    LatLng vadodara = new LatLng(22.310696, 73.192635);
                    myMap.addMarker(new com.google.android.gms.maps.model.MarkerOptions().position(vadodara).title("Marker in Vadodara"));
                    myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vadodara, 15));
                });
    }
}
