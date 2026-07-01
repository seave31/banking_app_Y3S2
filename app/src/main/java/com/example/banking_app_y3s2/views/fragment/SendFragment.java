package com.example.banking_app_y3s2.views.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.banking_app_y3s2.R;
import com.example.banking_app_y3s2.databinding.FragmentSendBinding;
import com.example.banking_app_y3s2.utils.SessionManager;
import com.example.banking_app_y3s2.viewModel.UserViewModel;
import com.example.banking_app_y3s2.views.activity.ConfirmTransferActivity;
import com.google.android.material.snackbar.Snackbar;

public class SendFragment extends Fragment {
    private FragmentSendBinding binding;
    private UserViewModel userViewModel;
    private SessionManager sessionManager;
    private ActivityResultLauncher<Intent> launcher; //“receiver” that waits for result from another Activity

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSendBinding.inflate(inflater, container, false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        sessionManager = new SessionManager(requireContext());


        //get balance from api
        loadBalance();

            //quick amount selected
        //10 dollars chip
        binding.usd10Chip.setOnClickListener(view -> {
            String amount = "$ 10.00";
            binding.amountEt.setText(amount);
        });
        //25 dollars chip
        binding.usd25Chip.setOnClickListener(view -> {
            String amount = "$ 25.00";
            binding.amountEt.setText(amount);
        });
        //50 dollars chip
        binding.usd50Chip.setOnClickListener(view -> {
            String amount = "$ 50.00";
            binding.amountEt.setText(amount);
        });
        //100 dollars chip
        binding.usd100Chip.setOnClickListener(view -> {
            String amount = "$ 100.00";
            binding.amountEt.setText(amount);
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

                //remove the $ and space from the string(amountStr)
                String amountWithoutSymbol  = amountStr.replace("$", "").trim();
                double amount;
                try {
                    // Convert the amount from string to a double
                    amount = Double.parseDouble(amountWithoutSymbol);
                } catch (Exception e) {
                    binding.amountEt.setError("Invalid amount");
                    return;
                }
                if (amount <= 0) {
                    binding.amountEt.setError("Amount must be greater than 0");
                    return;
                }



                //call api
                String finalRemark = remark;
                userViewModel.getUserByAccNum(targetAccountNumberTv).observe(getViewLifecycleOwner(), data ->{
                    if(data.getMessage().equals("success")){
                        //navigate to confirm transfer activity only when data is ready
                        Intent intent = new Intent(requireContext(), ConfirmTransferActivity.class);

                        intent.putExtra("targetAccountNumber", data.getAccountNumber());
                        intent.putExtra("accName", data.getAccountName());
                        intent.putExtra("amount", amount);
                        intent.putExtra("remark", finalRemark);
//                        startActivity(intent);
                        launcher.launch(intent); //wait for result
                    } else {
                        //account not found
                        Snackbar snackbar = Snackbar.make(view, getString(R.string.acc_not_found), Snackbar.LENGTH_LONG);
                        // background color
//                        snackbar.setBackgroundTint(getResources().getColor(R.color.background));
//                        snackbar.setTextColor(getResources().getColor(R.color.textColor));
                        snackbar.show();
                        binding.accountNumberEt.setText("");
                        binding.amountEt.setText("");
                    }

                });


            }
        });

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result-> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();

                        if(data != null && data.getBooleanExtra( //Did the previous screen send transfer_success = true?
                                "transfer_success", false //false → default value if key not found
                        )){
                            clearFields();
                            loadBalance();
                        }

                    }
                }
        );

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null){
            String accNum = getArguments().getString("account_number");
            String username = getArguments().getString("username");
            if(accNum != null) {
                Log.d("===========accNum", accNum);
                binding.accountNumberEt.setText(accNum);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBalance();  // refresh after transfer
    }

    private void loadBalance (){
        String token = "Bearer " + sessionManager.getToken();
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
    }
    private void clearFields() {
        binding.accountNumberEt.setText("");
        binding.amountEt.setText("");
        binding.remarkEt.setText("");
    }


}
