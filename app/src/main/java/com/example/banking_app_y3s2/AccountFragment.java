package com.example.banking_app_y3s2;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.banking_app_y3s2.databinding.FragmentAccountBinding;
import com.example.banking_app_y3s2.databinding.ItemAccountNumberBinding;
import com.example.banking_app_y3s2.utils.SessionManager;
import com.example.banking_app_y3s2.viewModel.UserViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;
    private SessionManager sessionManager;
    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        View view = binding.getRoot(); //get the root view of the binding
        TextView accNumTxt = view.findViewById(R.id.acc_num_txt);
        TextView balanceTxt = view.findViewById(R.id.balance_txt);
        TextView phoneTxt = view.findViewById(R.id.phoneTxt);



        //get saved token
        sessionManager = new SessionManager(requireContext());
        //bearer token
        String token = "Bearer " + sessionManager.getToken();
        String name = sessionManager.getName();
        String accountNumber = sessionManager.getAccount();
        accNumTxt.setText(accountNumber);
        binding.profileNameTv.setText(name);

        //avatar
        binding.profileIconTxt.setText(String.valueOf(name.charAt(0)));

        userViewModel.getCurrentUser(token).observe(getViewLifecycleOwner(), data -> {
            if (data == null) {
                Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show();
                return;
            }
//            if(data.getAccount() != null && data.getAccount().getAccountNumber() != null){
//                accNumTxt.setText(data.getAccount().getAccountNumber());
//            }
            if(data.getCustomer().getPhoneNumber() != null){
                phoneTxt.setText(data.getCustomer().getPhoneNumber());
            }
            if(data.getAccount().getCurrency() != null && data.getAccount().getCurrency().getSymbol() != null){
                //balance
                double balance = data.getAccount().getBalance();
                //symbol
                String symbol = data.getAccount().getCurrency().getSymbol();

                //set balance and symbol
                balanceTxt.setText(symbol + String.format("%.2f", balance));
            }

        });

        //languages
        binding.settingLanguagesInclude.getRoot().setOnClickListener(view1 -> {
            startActivity(new Intent(requireContext(), LanguageActivity.class));
        });


        //logout
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });


        return binding.getRoot();
    }

    private void logoutUser() {
        SharedPreferences preferences = requireContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String token = "Bearer " + preferences.getString("token", null);

        RetrofitInstance.getApiInterface().logoutUser(token).enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
                    // Clear saved token
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("token");
                    editor.remove("name");
                    editor.remove("acc_number");
                    editor.apply();

                    //redirect to login page
                    Intent intent = new Intent(getContext(), MainActivity.class);

                    //It clears all previous screens and starts fresh.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else {
                    Toast.makeText(requireContext(), "Logout failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Logout failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
