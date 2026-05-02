package com.example.banking_app_y3s2.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.banking_app_y3s2.ResponseData;
import com.example.banking_app_y3s2.repository.UserRepository;

public class AuthViewModel extends ViewModel {
    private UserRepository repository = new UserRepository();

    public LiveData<ResponseData> login(String phone, String pin) {
        return repository.login(phone, pin);
    }
}
