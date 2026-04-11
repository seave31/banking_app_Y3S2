package com.example.banking_app_y3s2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.banking_app_y3s2.databinding.FragmentSendBinding;
import com.example.banking_app_y3s2.utils.SessionManager;
import com.example.banking_app_y3s2.viewModel.SendViewModel;
import com.example.banking_app_y3s2.viewModel.UserViewModel;

public class SendFragment extends Fragment {
    private FragmentSendBinding binding;
    private UserViewModel userViewModel;
    private SendViewModel sendViewModel;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSendBinding.inflate(inflater, container, false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        sendViewModel = new ViewModelProvider(this).get(SendViewModel.class);

        sessionManager = new SessionManager(requireContext());
        String token = "Bearer " + sessionManager.getToken();

        //get data from api
        userViewModel.getCurrentUser(token).observe(getViewLifecycleOwner(), data -> {
            if (data == null) {
                return;
            }
            if (data.getAccount() == null) {
                return;
            }
            if(data.getAccount().getCurrency() != null && data.getAccount().getCurrency().getSymbol() != null){
                //balance
                double balance = data.getAccount().getBalance();
                //symbol
                String symbol = data.getAccount().getCurrency().getSymbol();

                //set balance and symbol
                binding.balanceTxt.setText(symbol + String.format("%.2f", balance));
            }

        });

        //submit button
        binding.sendMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get input
                String targetAccountNumberTv = binding.accountNumberEt.getText().toString();
                String amountStr = binding.amountEt.getText().toString();
                String remark = binding.remarkEt.getText().toString();
                if(remark.isEmpty()){
                    remark = "";
                }

                // Validation
                if (targetAccountNumberTv.isEmpty()) {
                    binding.accountNumberEt.setError("Account number required");
                    return;
                }

                if (amountStr.isEmpty()) {
                    binding.amountEt.setError("Amount required");
                    return;
                }

                double amount;
                try {
                    amount = Double.parseDouble(amountStr);
                } catch (Exception e) {
                    binding.amountEt.setError("Invalid amount");
                    return;
                }
                if (amount <= 0) {
                    binding.amountEt.setError("Amount must be greater than 0");
                    return;
                }

                //call api
                sendViewModel.sendMoney(token, targetAccountNumberTv, amount, remark);
                Toast.makeText(requireContext(), "Money is sent successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}
