package com.example.banking_app_y3s2;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.banking_app_y3s2.databinding.FragmentHomeBinding;
import com.example.banking_app_y3s2.utils.SessionManager;
import com.example.banking_app_y3s2.viewModel.UserViewModel;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private UserViewModel viewModel;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        sessionManager = new SessionManager(requireContext());
        String token = "Bearer " + sessionManager.getToken();
        Log.d("TOKEN", "Token: " + token);

        String username =  sessionManager.getName();
//        String accountNumber =  sessionManager.getAccount();
        binding.usernameTv.setText(username);
        binding.profileIconTx.setText(String.valueOf(username.charAt(0)));

        viewModel.getCurrentUser(token).observe(getViewLifecycleOwner(), data -> {

            if (data == null) {
                Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show();
                return;
            }

            if (data.getAccount() == null) {
                Toast.makeText(requireContext(), "Account not found", Toast.LENGTH_SHORT).show();
                return;
            }


            double balance = data.getAccount().getBalance();

            String symbol = "";
            if (data.getAccount().getCurrency() != null) {
                symbol = data.getAccount().getCurrency().getSymbol();
            }

            binding.moneyAmountTv.setText(symbol + String.format("%.2f", balance));
            binding.accNumTv.setText(data.getAccount().getAccountNumber());
        });

        return binding.getRoot();
    }
}
