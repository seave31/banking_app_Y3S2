package com.example.banking_app_y3s2;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.banking_app_y3s2.databinding.FragmentTranDetailBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TranDetailBottomSheet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TranDetailBottomSheet extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String refId, sender, receiver, remark, date, senderAcc, receiverAcc, direction, transactionType;
    private String amount;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentTranDetailBottomSheetBinding binding;

    public TranDetailBottomSheet() {
        // Required empty public constructor
    }

    public static TranDetailBottomSheet newInstance(String param1, String param2) {
        TranDetailBottomSheet fragment = new TranDetailBottomSheet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if(getArguments() != null){
            refId = getArguments().getString("refId");
            sender = getArguments().getString("sender");
            receiver = getArguments().getString("receiver");
            remark = getArguments().getString("remark");
            date = getArguments().getString("date");
            senderAcc = getArguments().getString("senderAcc");
            receiverAcc = getArguments().getString("receiverAcc");
            amount = getArguments().getString("amount");
            direction = getArguments().getString("direction");
            transactionType = getArguments().getString("transactionType");
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //connect with xml layout
        binding = FragmentTranDetailBottomSheetBinding.inflate(inflater, container, false);

        binding.transactionIdTv.setText("Reference ID: " + refId);
        binding.senderTv.setText("Sender: " + sender + " (" + senderAcc + ")");
        binding.receiverTv.setText("Receiver: " + receiver+ " (" + receiverAcc + ")");
        binding.dateTv.setText("Date: " + date);
        binding.typeTv.setText(transactionType);

        //remark handling
        if(remark == null){
            binding.remarkTv.setVisibility(View.GONE);
        }else {
            binding.remarkTv.setText("Remark: " + remark);
        }

        if(direction.equals("outgoing")){
            binding.iconIv.setImageResource(android.R.drawable.arrow_down_float);
            binding.iconIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.soft_red_text));
            binding.iconIv.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_soft_icon_red));

            //text
            binding.amountTv.setText("-" + amount);
            binding.amountTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_red_text));
        }else {
            binding.iconIv.setImageResource(android.R.drawable.arrow_up_float);
            binding.iconIv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.soft_green_text));
            binding.iconIv.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_soft_icon_green));

            //text
            binding.amountTv.setText("+" + amount);
            binding.amountTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_green_text));
        }


        return binding.getRoot();
    }
}