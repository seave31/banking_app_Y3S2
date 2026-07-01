package com.example.banking_app_y3s2.views.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.banking_app_y3s2.R;
import com.example.banking_app_y3s2.views.adapter.TransactionAdapter;
import com.example.banking_app_y3s2.databinding.FragmentHomeBinding;
import com.example.banking_app_y3s2.models.Transaction;
import com.example.banking_app_y3s2.utils.SessionManager;
import com.example.banking_app_y3s2.viewModel.TransactionViewModel;
import com.example.banking_app_y3s2.viewModel.UserViewModel;
import com.example.banking_app_y3s2.views.activity.ScanQrActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private UserViewModel viewModel;
    private SessionManager sessionManager;
    private TransactionViewModel transactionViewModel;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean nightMODE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        sessionManager = new SessionManager(requireContext());
        String token = "Bearer " + sessionManager.getToken();
        Log.d("TOKEN", "Token: " + token);

        String username = sessionManager.getName();
        String accountNumber = sessionManager.getAccount();
        binding.usernameTv.setText(username);
        binding.profileIconTx.setText(String.valueOf(username.charAt(0)));
        binding.accNumTv.setText(accountNumber);


        //get user data from api
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
        });

        //qr code
        binding.qrIconIv.setOnClickListener(view -> {
            //send data as json(why json: bc of Flexibility)
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("account_number", accountNumber);
                jsonObject.put("username", username);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            //convert json into string
            String data = jsonObject.toString();
            //Put String into Bundle
            Bundle bundle = new Bundle();
            bundle.putString("data", data);

            QrDialogFragment qrDialogFragment = new QrDialogFragment(); //create an obj of QrDialogFragment
            //(send data to dialog fragment) it requires bundle type
            qrDialogFragment.setArguments(bundle);
            //show dialog fragment
            qrDialogFragment.show(getParentFragmentManager(), "qr_dialog"); //show it
        });


        //recent transaction history
        binding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        transactionViewModel.getTransactionHistory(token).observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                List<Transaction> previewList;

                //data has more than 3
                if (data.size() > 5) {
                    previewList = data.subList(0, 5);
                } else {
                    previewList = data;
                }

                binding.homeRecyclerView.setAdapter(new TransactionAdapter(previewList));
            }
        });

        //view profile
        binding.profileIconTx.setOnClickListener(view -> {
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
            bottomNavigationView.setSelectedItemId(R.id.nav_account);
        });

        //see all
        binding.viewAll.setOnClickListener(view -> {
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
            bottomNavigationView.setSelectedItemId(R.id.nav_history);
        });

                    //(fast actions)
        //scan qr btn
        binding.scanQr.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ScanQrActivity.class);
            startActivity(intent);
        });
        //send money
        binding.sendMoneyFeatureIc.setOnClickListener(view -> {
            BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
            bottomNavigationView.setSelectedItemId(R.id.nav_send);
        });


        //dark mode light mode

        // open a SharedPreferences file named "MODE".

        sharedPreferences = requireContext().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night", false);

        if (nightMODE) {
//      The switch is set to ON
//            binding.dar.setChecked(true);
//      The app theme switches to Dark Mode
            binding.darkLightModeIcon.setImageResource(R.drawable.ic_light);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        binding.darkLightModeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                If night mode is ON → turn OFF
                if (nightMODE) {
                    //turn to light mode
                    binding.darkLightModeIcon.setImageResource(R.drawable.ic_light);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);
                } else {
//                 If night mode is OFF → turn ON
                    //turn to dark mode
                    binding.darkLightModeIcon.setImageResource(R.drawable.ic_dark);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);
                }
//                save the value:
                editor.apply();
                nightMODE = !nightMODE;
            }
        });



        return binding.getRoot();
    }
}
