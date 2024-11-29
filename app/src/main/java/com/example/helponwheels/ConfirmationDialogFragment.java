package com.example.helponwheels;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ConfirmationDialogFragment extends DialogFragment {

    private String providerName;
    private String selectedService;
    private String carModel;
    private String location;

    // Constructor to initialize the data
    public ConfirmationDialogFragment(String providerName, String selectedService, String carModel, String location) {
        this.providerName = providerName;
        this.selectedService = selectedService;
        this.carModel = carModel;
        this.location = location;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Confirm Booking");

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirmation, null);

        // Initialize TextViews
        TextView providerNameText = view.findViewById(R.id.providerNameText);
        TextView serviceSelectedText = view.findViewById(R.id.serviceSelectedText);
        TextView carModelText = view.findViewById(R.id.carModelText);
        TextView locationText = view.findViewById(R.id.locationText);
        Button confirmButton = view.findViewById(R.id.confirmButton);

        // Set text to the TextViews
        providerNameText.setText(providerName);
        serviceSelectedText.setText(selectedService);
        carModelText.setText(carModel);
        locationText.setText(location);

        // Set up the confirm button action
        confirmButton.setOnClickListener(v -> {
            // Save data to Firebase
            saveToFirebase(providerName, selectedService, carModel, location);

            // Create an Intent to navigate to the summary page
            Intent intent = new Intent(getActivity(), summarypage.class);

            // Pass the data to the summary page using Intent extras
            intent.putExtra("serviceProviderName", providerName);
            intent.putExtra("serviceType", selectedService);
            intent.putExtra("carModel", carModel);
            intent.putExtra("location", location);

            // Start the Summary Page activity
            startActivity(intent);

            // Dismiss the dialog after confirming
            dismiss();
        });

        dialog.setContentView(view);
        return dialog;
    }

    private void saveToFirebase(String providerName, String serviceType, String carModel, String location) {
        // Get a reference to the Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("requestTable");

        // Generate a unique ID for each request
        String requestId = databaseReference.push().getKey();

        // Create a map to hold the data
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("serviceProviderName", providerName);
        requestData.put("serviceType", serviceType);
        requestData.put("carModel", carModel);
        requestData.put("location", location);

        // Save the data under the unique ID
        if (requestId != null) {
            databaseReference.child(requestId).setValue(requestData)
                    .addOnSuccessListener(aVoid -> {
                        // Data saved successfully
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                    });
        }
    }
}
