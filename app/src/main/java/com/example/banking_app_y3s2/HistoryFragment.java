package com.example.banking_app_y3s2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.banking_app_y3s2.adapter.TransactionAdapter;
import com.example.banking_app_y3s2.databinding.FragmentHistoryBinding;
import com.example.banking_app_y3s2.utils.SessionManager;
import com.example.banking_app_y3s2.viewModel.TransactionViewModel;

public class HistoryFragment extends Fragment {
    private FragmentHistoryBinding binding;
    private TransactionViewModel viewModel;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        sessionManager = new SessionManager(requireContext());

        //get token
        String token = "Bearer " + sessionManager.getToken();

        //set up recycler view layout
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //get data from api
        viewModel.getTransactionHistory(token).observe(getViewLifecycleOwner(), data->{
            if(data != null){
                binding.recyclerView.setAdapter(new TransactionAdapter(data));
            }
        });


        return binding.getRoot();
    }
}
