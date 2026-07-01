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

    public LiveData<ResponseData> register(String name, String email, String phone, String password, String firstname, String lastname, String dob, String phoneNumber, String address, String accountType, int currencyId, String accountStatus){
        return repository.register(name, email, phone, password, firstname, lastname, dob, phoneNumber, address, accountType, currencyId, accountStatus);
    }
}
