package com.example.helponwheels;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Servicepage extends AppCompatActivity {

    private Spinner selectService;
    private Spinner selectCarModel;
    private EditText confirmLocationInput;
    private Button bookNowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicepage); // Ensure this matches your layout file name

        // Initialize the spinners and button
        selectService = findViewById(R.id.selectService);
        selectCarModel = findViewById(R.id.selectCarModel);
        confirmLocationInput = findViewById(R.id.confirmLocationInput);
        bookNowButton = findViewById(R.id.bookNowButton);

        // Get the options for services from strings.xml
        String[] services = getResources().getStringArray(R.array.service_options);

        // Create an ArrayAdapter for the services
        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                services
        );

        // Specify the layout to use when the list of choices appears
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the service spinner
        selectService.setAdapter(serviceAdapter);

        // Get the options for car models from strings.xml
        String[] carModels = getResources().getStringArray(R.array.car_models);

        // Create an ArrayAdapter for the car models
        ArrayAdapter<String> carModelAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                carModels
        );

        // Specify the layout to use when the list of choices appears
        carModelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the car model spinner
        selectCarModel.setAdapter(carModelAdapter);

        // Set up the "Book Now" button click listener
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected items and location
                String providerName = "Patel Towing Service"; // You may want to retrieve this dynamically
                String selectedService = selectService.getSelectedItem().toString();
                String carModel = selectCarModel.getSelectedItem().toString();
                String location = confirmLocationInput.getText().toString();

                // Create and show the confirmation dialog
                ConfirmationDialogFragment dialog = new ConfirmationDialogFragment(providerName, selectedService, carModel, location);
                dialog.show(getSupportFragmentManager(), "confirmationDialog");
            }
        });
    }
}
