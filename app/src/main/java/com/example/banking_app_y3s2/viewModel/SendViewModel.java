package com.example.banking_app_y3s2.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.banking_app_y3s2.models.TransactionResponse;
import com.example.banking_app_y3s2.repository.TransactionRepository;
import com.example.banking_app_y3s2.repository.UserRepository;

public class SendViewModel extends ViewModel {
    private TransactionRepository repository = new TransactionRepository();

    public LiveData<TransactionResponse> sendMoney(String token, String targetAccountNumber, double amount, String remark){
        return repository.sendMoney(token, targetAccountNumber, amount, remark);
    }
}
