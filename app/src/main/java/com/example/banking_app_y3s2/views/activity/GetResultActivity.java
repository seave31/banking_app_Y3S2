package com.example.banking_app_y3s2.views.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.banking_app_y3s2.R;
import com.example.banking_app_y3s2.databinding.ActivityGetResultBinding;

public class GetResultActivity extends AppCompatActivity {
    private ActivityGetResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivityGetResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //get data from intent
        String accountNumber = getIntent().getStringExtra("account_number");
        String username = getIntent().getStringExtra("username");
        binding.getText.setText(accountNumber);
        binding.getName.setText(username);
    }
}