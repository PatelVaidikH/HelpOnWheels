package com.example.helponwheels;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Servicepage extends AppCompatActivity {

    private Spinner selectService;
    private Spinner selectCarModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicepage); // Ensure this matches your layout file name

        // Initialize the spinners
        selectService = findViewById(R.id.selectService);
        selectCarModel = findViewById(R.id.selectCarModel);

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
    }
}
