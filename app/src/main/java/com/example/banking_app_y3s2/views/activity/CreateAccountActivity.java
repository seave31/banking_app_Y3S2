package com.example.banking_app_y3s2.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.banking_app_y3s2.utils.LocaleHelper;
import com.example.banking_app_y3s2.R;
import com.example.banking_app_y3s2.databinding.ActivityCreateAccountBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateAccountActivity extends AppCompatActivity {
    private ActivityCreateAccountBinding binding;
    private TextInputLayout dobLayout;
    private TextInputEditText etDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.createAccountRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup Toolbar back navigation
        MaterialToolbar toolbar = binding.toolbar;
        toolbar.setNavigationOnClickListener(v -> {
            // Use the back dispatcher to handle navigation consistently
            getOnBackPressedDispatcher().onBackPressed();
        });

        // Setup "Log In" text click to go back
        binding.tvLogin.setOnClickListener(v -> {
            finish();
        });

        // Continue button navigation
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateFields()) {
                    return;
                }

                Intent intent = new Intent(CreateAccountActivity.this, CreateBankAccountActivity.class);
                intent.putExtra("NAME", binding.etName.getText().toString());
                intent.putExtra("EMAIL", binding.etEmail.getText().toString());
                intent.putExtra("PHONE", binding.etPhone.getText().toString());
                intent.putExtra("PASSWORD", binding.etPassword.getText().toString());
                intent.putExtra("DOB", binding.etDob.getText().toString());
                intent.putExtra("ADDRESS", binding.etAddress.getText().toString());
                startActivity(intent);
            }
        });

        // Date of Birth setup
        dobLayout = binding.dobLayout;
        etDob = binding.etDob;
        dobLayout.setEndIconOnClickListener(v -> showDatePicker());
        etDob.setOnClickListener(v -> showDatePicker());
        etDob.setFocusable(false);
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date of Birth")
                .build();

        picker.show(getSupportFragmentManager(), "DATE_PICKER");

        picker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String date = outputFormat.format(new Date(selection));
            etDob.setText(date);
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.setLocale(base, LocaleHelper.loadLanguage(base)));
    }

    private boolean validateFields() {
        boolean isValid = true;

        if (binding.etName.getText() == null || binding.etName.getText().toString().trim().isEmpty()) {
            binding.etName.setError("Name is required");
            isValid = false;
        }
        if (binding.etEmail.getText() == null || binding.etEmail.getText().toString().trim().isEmpty()) {
            binding.etEmail.setError("Email is required");
            isValid = false;
        }
        if (binding.etPhone.getText() == null || binding.etPhone.getText().toString().trim().isEmpty()) {
            binding.etPhone.setError("Phone is required");
            isValid = false;
        }
        if (binding.etPassword.getText() == null || binding.etPassword.getText().toString().trim().isEmpty()) {
            binding.etPassword.setError("Password is required");
            isValid = false;
        }
        if (binding.etDob.getText() == null || binding.etDob.getText().toString().trim().isEmpty()) {
            binding.dobLayout.setError("Date of birth is required");
            isValid = false;
        } else {
            binding.dobLayout.setError(null);
        }
        if (binding.etAddress.getText() == null || binding.etAddress.getText().toString().trim().isEmpty()) {
            binding.etAddress.setError("Address is required");
            isValid = false;
        }

        return isValid;
    }
}
