package com.example.banking_app_y3s2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    private TextInputEditText etDob; // if you have one

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_create_account);

        binding=  ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.createAccountRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //leading icon
        MaterialToolbar toolbar = binding.toolbar;
        toolbar.setNavigationOnClickListener(v-> {
            finish();  // go back to previous screen
        });

        //continue btn
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccountActivity.this, CreateBankAccountActivity.class);
                intent.putExtra("NAME", binding.etName.getText().toString());
                intent.putExtra("EMAIL", binding.etEmail.getText().toString());
                intent.putExtra("PHONE", binding.etPhone.getText().toString());
                intent.putExtra("PASSWORD", binding.etPassword.getText().toString());
                intent.putExtra("DOB", binding.etDob.getText().toString());
                intent.putExtra("ADDRESS", binding.etAddress.getText().toString());
                startActivity(intent );
            }
        });

        //dob
        dobLayout = binding.dobLayout;
        etDob = binding.etDob;
        dobLayout.setEndIconOnClickListener(v -> showDatePicker());

        // Or when clicking the text field
        etDob.setOnClickListener(v -> showDatePicker());
        etDob.setFocusable(false); // Prevent keyboard


    }

    private void showDatePicker() {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date of Birth")
                .build();

        picker.show(getSupportFragmentManager(), "DATE_PICKER");

        picker.addOnPositiveButtonClickListener(selection -> {

            SimpleDateFormat outputFormat =
                    new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            String date = outputFormat.format(new Date(selection));

            etDob.setText(date);
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        //pass the new localized Context to Android
        super.attachBaseContext(LocaleHelper.setLocale(base, LocaleHelper.loadLanguage(base)));
    }
}
