package com.example.helponwheels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView userNameTextView, userEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Initialize FirebaseAuth and Database instances
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Get references to the TextViews
        userNameTextView = findViewById(R.id.user_name);
        userEmailTextView = findViewById(R.id.user_email);

        // Handle edge-to-edge layout adjustments
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Load user data from Firebase Realtime Database
        loadUserData();

        // Initialize the Log Out button
        Button logoutButton = findViewById(R.id.logoutButton);

        // Set an OnClickListener to the button
        logoutButton.setOnClickListener(v -> {
            // Perform log out logic here
            logout();
        });
    }

    private void loadUserData() {
        // Get current user's UID
        String userId = mAuth.getCurrentUser().getUid();

        // Reference to the "userTable" in Firebase Realtime Database
        DatabaseReference userRef = mDatabase.child("userTable").child(userId);

        // Attach a listener to get the user data
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get the user's name and email from the database
                    String userName = dataSnapshot.child("userName").getValue(String.class); // Corrected field name
                    String userEmail = dataSnapshot.child("userEmail").getValue(String.class); // Corrected field name

                    // Log values to debug
                    Log.d("Profile", "Name: " + userName + ", Email: " + userEmail);

                    // Set the retrieved values in the TextViews
                    userNameTextView.setText(userName);
                    userEmailTextView.setText(userEmail);
                } else {
                    Log.d("Profile", "User data not found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle possible errors here
                Log.e("Profile", "Error retrieving user data: " + databaseError.getMessage());
            }
        });
    }

    private void logout() {
        // Sign out the user from Firebase Authentication
        mAuth.signOut();

        // Redirect to Login screen
        Intent intent = new Intent(profile.this, Loginpage.class);
        startActivity(intent);

        // Finish current activity so user can't navigate back to the profile
        finish();
    }
}
