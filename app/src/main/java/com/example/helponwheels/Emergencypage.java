package com.example.helponwheels;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class Emergencypage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencypage);

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
}
