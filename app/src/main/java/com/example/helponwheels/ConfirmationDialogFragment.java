package com.example.helponwheels;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfirmationDialogFragment extends DialogFragment {

    private String providerName;
    private String selectedService;
    private String carModel;
    private String location;

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

        confirmButton.setOnClickListener(v -> {
            // Handle confirmation action
            dismiss(); // Dismiss the dialog
            // You can add any further actions here (like booking confirmation logic)
        });

        dialog.setContentView(view);
        return dialog;
    }
}
