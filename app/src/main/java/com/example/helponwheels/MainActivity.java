package com.example.helponwheels;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth; // Firebase Auth instance
    private DatabaseReference mDatabase; // Firebase Database reference
    private TextView userNameTextView; // TextView to display user name
    private TextView userTypeTextView; // TextView to display user type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase  Auth and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("userTable");

        // Find the TextViews by their ID
        userNameTextView = findViewById(R.id.textView2); // Assuming textView2 is where username is displayed

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
}
