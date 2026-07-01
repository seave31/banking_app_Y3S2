package com.example.banking_app_y3s2.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.banking_app_y3s2.R;
import com.example.banking_app_y3s2.databinding.ActivityCreateBankAccountBinding;
import com.example.banking_app_y3s2.utils.LocaleHelper;
import com.example.banking_app_y3s2.viewModel.AuthViewModel;
import com.google.android.material.appbar.MaterialToolbar;

public class CreateBankAccountActivity extends AppCompatActivity {
    private AuthViewModel viewModel;
    private ActivityCreateBankAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityCreateBankAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.createBankAccRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup Toolbar back button
        MaterialToolbar toolbar = binding.toolbar;
        toolbar.setNavigationOnClickListener(v -> finish());

        // Setup Go Back button
        binding.btnBack.setOnClickListener(v -> finish());

        // Receive data from previous screen
        String name = getIntent().getStringExtra("NAME");
        String email = getIntent().getStringExtra("EMAIL");
        String phone = getIntent().getStringExtra("PHONE");
        String password = getIntent().getStringExtra("PASSWORD");
        String dob = getIntent().getStringExtra("DOB");
        String address = getIntent().getStringExtra("ADDRESS");

        // Display user info in header
        binding.tvUserFullname.setText(name);
        binding.tvUserPhone.setText(phone);

        // Account Type Dropdown
        String[] accountTypes = getResources().getStringArray(R.array.account_types);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, accountTypes);
        binding.dropdownAccountType.setAdapter(typeAdapter);
//        binding.dropdownAccountType.setText(accountTypes[0], false);
        binding.dropdownAccountType.setOnClickListener(v-> binding.dropdownAccountType.showDropDown());

        // Currency Dropdown
        String[] currencies = getResources().getStringArray(R.array.currencies);
        ArrayAdapter<String> currAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, currencies);
        binding.dropdownCurrency.setAdapter(currAdapter);
//        binding.dropdownCurrency.setText(currencies[0], false);
        binding.dropdownCurrency.setOnClickListener(v -> binding.dropdownCurrency.showDropDown());

        // ViewModel setup
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.btnCreateAccount.setOnClickListener(view -> {
            String firstName = "";
            String lastName = "";
            if (name != null && !name.trim().isEmpty()) {
                String[] parts = name.trim().split("\\s+");
                firstName = parts[0];
                lastName = parts.length > 1 ? parts[1] : "";
            }

            String status = binding.switchStatus.isChecked() ? "Active" : "Inactive";

            viewModel.register(
                    name,
                    email,
                    phone,
                    password,
                    firstName,
                    lastName,
                    dob,
                    phone,
                    address,
                    binding.dropdownAccountType.getText().toString(),
                    1,
                    status
            ).observe(this, data -> {
                if (data != null) {
                    Toast.makeText(this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                    // Navigate to Login after success
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Failed to create account. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.setLocale(base, LocaleHelper.loadLanguage(base)));
    }
}
