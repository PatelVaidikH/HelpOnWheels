package com.example.helponwheels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class ServicePageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_page); // Ensure this matches your actual layout file name

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Handle edge-to-edge layout adjustments
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the Log Out button
        Button logoutButton = findViewById(R.id.logout_button);

        // Check if the button is correctly initialized
        if (logoutButton != null) {
            // Set an OnClickListener to the button
            logoutButton.setOnClickListener(v -> {
                // Perform log out logic here
                logout();
            });
        }
    }

    private void logout() {
        // Sign out the user from Firebase Authentication
        mAuth.signOut();

        // Redirect to Login screen
        Intent intent = new Intent(ServicePageActivity.this, Loginpage.class); // Ensure LoginPage.class is correct
        startActivity(intent);

        // Finish current activity so user can't navigate back to the profile
        finish();
    }
}
