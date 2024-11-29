package com.example.helponwheels;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class summarypage extends AppCompatActivity {

    private TextView serviceProviderNameTextView;
    private TextView serviceTypeTextView;
    private TextView carModelTextView;
    private TextView locationTextView;
    private TextView etaTextView;
    private Button backToHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge feature (ensure layout supports it)
        EdgeToEdge.enable(this);

        // Set content view
        setContentView(R.layout.activity_summarypage);

        // Initializing the TextViews
        serviceProviderNameTextView = findViewById(R.id.serviceProviderNameTextView);
        serviceTypeTextView = findViewById(R.id.serviceTypeTextView);
        carModelTextView = findViewById(R.id.carModelTextView);
        locationTextView = findViewById(R.id.locationTextView);
        etaTextView = findViewById(R.id.etaTextView);
        backToHomeButton = findViewById(R.id.backToHomeButton);

        backToHomeButton.setOnClickListener(v -> {
            // Create an Intent to go back to MainActivity
            Intent intent = new Intent(summarypage.this, MainActivity.class);

            // Optionally, clear the activity stack to prevent the user from going back to the SummaryPage
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Start MainActivity
            startActivity(intent);
            finish();  // Finish the current activity (SummaryPage)
        });

        // Retrieve data passed from the previous activity or fragment
        String serviceProviderName = getIntent().getStringExtra("serviceProviderName");
        String serviceType = getIntent().getStringExtra("serviceType");
        String carModel = getIntent().getStringExtra("carModel");
        String location = getIntent().getStringExtra("location");
        String eta = getIntent().getStringExtra("eta");

        // Set the received data to the TextViews, with default values if data is missing
        serviceProviderNameTextView.setText(serviceProviderName != null ? serviceProviderName : "Not Available");
        serviceTypeTextView.setText(serviceType != null ? serviceType : "Not Available");
        carModelTextView.setText(carModel != null ? carModel : "Not Available");
        locationTextView.setText(location != null ? location : "Not Available");
//        etaTextView.setText(eta != null ? eta : "Not Available");

        // Handling system UI insets for padding (edge-to-edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Get system bar insets (status bar, navigation bar, etc.)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Apply padding based on system bar insets for full-screen behavior
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            // Return the insets to indicate we've handled them
            return insets;
        });
    }
}
