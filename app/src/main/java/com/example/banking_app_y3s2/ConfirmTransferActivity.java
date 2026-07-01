package com.example.banking_app_y3s2;

import static android.view.View.GONE;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.banking_app_y3s2.databinding.ActivityConfirmTransferBinding;
import com.example.banking_app_y3s2.utils.SessionManager;
import com.example.banking_app_y3s2.viewModel.SendViewModel;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConfirmTransferActivity extends AppCompatActivity {
    private ActivityConfirmTransferBinding binding;
    private SendViewModel sendViewModel;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmTransferBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sendViewModel = new ViewModelProvider(this).get(SendViewModel.class);
        sessionManager = new SessionManager(this);


        //get token
        String token = "Bearer " + sessionManager.getToken();

        //get data from intent
        String targetAccountNumber = getIntent().getStringExtra("targetAccountNumber");
        double amount = getIntent().getDoubleExtra("amount", 0.0);
        String remark = getIntent().getStringExtra("remark");
        String targetAccName = getIntent().getStringExtra("accName");

        //get the current data and hour
        LocalDateTime now = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
        }

        String formattedDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formattedDate = now.format(formatter);
        }


        //if there is no remark, hide the remark label and value
        if(remark.isEmpty()){
            binding.remarkLabel.setVisibility(GONE);
            binding.remarkValue.setVisibility(GONE);
        }

        //set data to view
        binding.fromAcc.setText(sessionManager.getAccount());
        binding.fromName.setText(sessionManager.getName());
        binding.toName.setText(targetAccName);
        binding.toAcc.setText(targetAccountNumber);
        binding.totalValue.setText("$" + String.format("%.2f", amount));
        binding.dateValue.setText(formattedDate);
        binding.remarkValue.setText(remark);


        //swipe button
        binding.slideToPay.setOnSlideCompleteListener(slideToActView -> {
            sendViewModel.sendMoney(token, targetAccountNumber, amount, remark);

            Intent intent = new Intent();
            intent.putExtra("transfer_success", true);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        //pass the new localized Context to Android
        super.attachBaseContext(LocaleHelper.setLocale(base, LocaleHelper.loadLanguage(base)));
    }
}
