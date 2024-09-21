package com.example.helponwheels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // Import CardView
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Adjust padding based on system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the emergency button by its ID
        Button emergencyButton = findViewById(R.id.Emergencybtn); // Assuming button2 is the emergency button's ID

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
    }
}
