package com.example.banking_app_y3s2.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banking_app_y3s2.utils.LocaleHelper;
import com.example.banking_app_y3s2.databinding.ActivityLanguageBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class LanguageActivity extends AppCompatActivity {
    private ActivityLanguageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //leading icon
        MaterialToolbar toolbar = binding.toolbar;
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });


        //english selection
        binding.englishLanguagesInclude.getRoot().setOnClickListener(view -> {
            LocaleHelper.setLocale(this, "en");
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });


        //khmer selection
        binding.khmerLanguagesInclude.getRoot().setOnClickListener(view -> {
            LocaleHelper.setLocale(this, "km");
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.setLocale(base, LocaleHelper.loadLanguage(base)));
    }


}