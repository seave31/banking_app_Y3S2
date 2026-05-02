package com.example.banking_app_y3s2.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.banking_app_y3s2.models.Transaction;
import com.example.banking_app_y3s2.models.TransactionResponse;
import com.example.banking_app_y3s2.repository.TransactionRepository;

import java.util.List;

public class TransactionViewModel extends ViewModel {
    private TransactionRepository repository = new TransactionRepository();

    public LiveData<List<Transaction>> getTransactionHistory(String token){
        return repository.getTransactionHistory(token);
    }
}
