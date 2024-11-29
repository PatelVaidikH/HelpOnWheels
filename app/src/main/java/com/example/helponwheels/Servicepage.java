package com.example.helponwheels;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
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

public class Servicepage extends AppCompatActivity {

    private Spinner selectService;
    private Spinner selectCarModel;
    private EditText confirmLocationInput;
    private Button bookNowButton, useGpsButton;
    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicepage);

        // Initialize the spinners and buttons
        selectService = findViewById(R.id.selectService);
        selectCarModel = findViewById(R.id.selectCarModel);
        confirmLocationInput = findViewById(R.id.confirmLocationInput);
        bookNowButton = findViewById(R.id.bookNowButton);
        useGpsButton = findViewById(R.id.useGpsButton);

        findViewById(R.id.navbar_profile).setOnClickListener(v -> {
            // Redirect to Profile.java
            Intent intent = new Intent(Servicepage.this, profile.class);
            startActivity(intent);
        });

        findViewById(R.id.navbar_home).setOnClickListener(v -> {
            // Redirect to MainActivity.java
            Intent intent = new Intent(Servicepage.this, MainActivity.class);
            startActivity(intent);
        });

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Get the options for services from strings.xml
        String[] services = getResources().getStringArray(R.array.service_options);

        // Create an ArrayAdapter for the services
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectService.setAdapter(serviceAdapter);

        // Get the options for car models from strings.xml
        String[] carModels = getResources().getStringArray(R.array.car_models);

        // Create an ArrayAdapter for the car models
        ArrayAdapter<String> carModelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carModels);
        carModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCarModel.setAdapter(carModelAdapter);

        // Set up the "Book Now" button click listener
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String providerName = "Patel Towing Service";
                String selectedService = selectService.getSelectedItem().toString();
                String carModel = selectCarModel.getSelectedItem().toString();
                String location = confirmLocationInput.getText().toString();

                // Show confirmation dialog
                ConfirmationDialogFragment dialog = new ConfirmationDialogFragment(providerName, selectedService, carModel, location);
                dialog.show(getSupportFragmentManager(), "confirmationDialog");
            }
        });

        // Set up the "Use GPS" button click listener
        useGpsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the location permission is granted
                if (ContextCompat.checkSelfPermission(Servicepage.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Servicepage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                } else {
                    fetchLocation();
                }
            }
        });
    }

    // Fetch the current location of the user and convert it to an address
    private void fetchLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Get the latitude and longitude from the location object
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            // Create a Geocoder object to convert latitude and longitude to an address
                            Geocoder geocoder = new Geocoder(Servicepage.this);
                            try {
                                // Get the list of addresses based on the latitude and longitude
                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                                if (addresses != null && !addresses.isEmpty()) {
                                    // Get the first address from the list
                                    Address address = addresses.get(0);
                                    // Format the address (you can customize the format as per your needs)
                                    String addressText = address.getAddressLine(0); // You can get more details like city, country, etc.

                                    // Display the address in the confirmLocationInput field
                                    confirmLocationInput.setText(addressText);
                                } else {
                                    Toast.makeText(Servicepage.this, "No address found for this location", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(Servicepage.this, "Unable to fetch address", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Servicepage.this, "Unable to fetch location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Handle the result of the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, fetch the location
                fetchLocation();
            } else {
                Toast.makeText(this, "Permission denied. Unable to fetch location", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
