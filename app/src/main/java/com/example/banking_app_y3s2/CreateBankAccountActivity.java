package com.example.banking_app_y3s2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.banking_app_y3s2.databinding.ActivityCreateAccountBinding;
import com.example.banking_app_y3s2.databinding.ActivityCreateBankAccountBinding;
import com.example.banking_app_y3s2.viewModel.AuthViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

public class CreateBankAccountActivity extends AppCompatActivity {
    private AuthViewModel viewModel;
    private ActivityCreateBankAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_create_bank_account);

        binding=  ActivityCreateBankAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.createBankAccRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //leading icon
        MaterialToolbar toolbar = binding.toolbar;
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

        //receive data from the previous screen
        String name = getIntent().getStringExtra("NAME");
        String email = getIntent().getStringExtra("EMAIL");
        String phone = getIntent().getStringExtra("PHONE");
        String password = getIntent().getStringExtra("PASSWORD");
        String dob = getIntent().getStringExtra("DOB");
        String address = getIntent().getStringExtra("ADDRESS");
        String firstname = "";
        String lastname = "";
        if (name != null && !name.trim().isEmpty()) {
            String[] parts = name.trim().split("\\s+");
            firstname = parts[0];
            lastname = parts.length > 1 ? parts[1] : "";
        }




        //acc types drop down
        MaterialAutoCompleteTextView accountType = findViewById(R.id.dropdownAccountType);
        String[] accountTypes = {
                "Saving account",
                "Current account",
                "Fixed deposit",
                "Business account"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                accountTypes
        );

        accountType.setAdapter(adapter);
        accountType.setText(accountTypes[0], false);
        accountType.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                accountType.showDropDown();
            }
        });

        //currencies dropdown
        MaterialAutoCompleteTextView currency = findViewById(R.id.dropdownCurrency);
        String[] currencies = {
                "USD - US Dollar",
                "KHR - Riel",
        };

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencies);
        currency.setAdapter(adapter2);
        currency.setText(currencies[0], false);
        currency.setOnFocusChangeListener((v, hasFocus)->{
            if(hasFocus){
                currency.showDropDown();
            }
        });

        final String fName = name;
        final String fEmail = email;
        final String fPhone = phone;
        final String fPassword = password;
        final String fDob = dob;
        final String fAddress = address;
        final String fFirstName = firstname;
        final String fLastName = lastname;


        //assign viewModel
        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        binding.btnCreateAccount.setOnClickListener(view -> {
            Log.d("REGISTER", "name=" + fName);
            Log.d("REGISTER", "email=" + fEmail);
            Log.d("REGISTER", "phone=" + fPhone);
            Log.d("REGISTER", "dob=" + fDob);
            Log.d("REGISTER", "address=" + fAddress);
            Log.d("REGISTER", "account_type=" + accountType.getText().toString());
            viewModel.register(
                    fName,
                    fEmail,
                    fPhone,
                    fPassword,
                    fFirstName,
                    fLastName,
                    fDob,
                    fPhone,
                    fAddress,
                    accountType.getText().toString(),
                    1,
                    "Active"
            ).observe(this, data -> {
                Log.d("REGISTER", "response = " + data);

                if (data != null) {
                    Toast.makeText(this, "Created Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Create account Failed", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}