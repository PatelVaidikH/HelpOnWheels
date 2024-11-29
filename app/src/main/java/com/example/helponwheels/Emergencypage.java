package com.example.helponwheels;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Emergencypage extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private EditText incidentLocationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencypage);

        // Initialize spinners
        initializeSpinners();

        // Initialize location provider
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // EditText for location
        incidentLocationEditText = findViewById(R.id.incidentLocationEditText);

        // Fetch Location Button
        Button fetchLocationButton = findViewById(R.id.fetchLocationButton);
        fetchLocationButton.setOnClickListener(v -> fetchCurrentLocation());

        findViewById(R.id.navbar_profile).setOnClickListener(v -> {
            // Redirect to Profile.java
            Intent intent = new Intent(Emergencypage.this, profile.class);
            startActivity(intent);
        });

        findViewById(R.id.navbar_home).setOnClickListener(v -> {
            // Redirect to MainActivity.java
            Intent intent = new Intent(Emergencypage.this, MainActivity.class);
            startActivity(intent);
        });
    }


    private void initializeSpinners() {
        // Incident Type Spinner
        Spinner incidentTypeSpinner = findViewById(R.id.incidentTypeSpinner);
        ArrayAdapter<CharSequence> incidentAdapter = ArrayAdapter.createFromResource(this,
                R.array.incident_type_options, android.R.layout.simple_spinner_item);
        incidentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incidentTypeSpinner.setAdapter(incidentAdapter);

        // Vehicle Type Spinner
        Spinner vehicleTypeSpinner = findViewById(R.id.vehicleTypeSpinner);
        ArrayAdapter<CharSequence> vehicleAdapter = ArrayAdapter.createFromResource(this,
                R.array.vehicle_type_options, android.R.layout.simple_spinner_item);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleTypeSpinner.setAdapter(vehicleAdapter);
    }

    private void fetchCurrentLocation() {
        // Check for location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Fetch location
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    updateLocationEditText(location);
                } else {
                    Toast.makeText(this, "Unable to fetch location", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateLocationEditText(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (!addresses.isEmpty()) {
                String address = addresses.get(0).getAddressLine(0);
                incidentLocationEditText.setText(address);
            } else {
                incidentLocationEditText.setText("Location found, but address unavailable.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            incidentLocationEditText.setText("Error fetching address.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
