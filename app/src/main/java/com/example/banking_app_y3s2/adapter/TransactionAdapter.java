package com.example.banking_app_y3s2.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.banking_app_y3s2.R;
import com.example.banking_app_y3s2.TranDetailBottomSheet;
import com.example.banking_app_y3s2.models.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder>{
    private List<Transaction> transactionList;

    //constructor
    public TransactionAdapter(List<Transaction> transactionList){
        this.transactionList = transactionList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Transaction transaction = transactionList.get(position);

        //transaction date
        String dateStr = transaction.getCreatedAt();
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault());
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = inputFormat.parse(dateStr);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());

            String formatted = outputFormat.format(date);

            holder.dateTv.setText(formatted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //
        if(transaction.getDirection().equals("outgoing")){
            holder.fromTv.setText(transaction.getReceiver());

            //amount
            String amount = "-$" + String.format("%.2f", transaction.getAmount());
            holder.amountTv.setText(amount);

            //icon
            holder.iconIv.setImageResource(android.R.drawable.arrow_down_float);
            holder.iconIv.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.soft_red_text));
            holder.iconIv.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.bg_soft_icon_red));
            //set color
            holder.fromTv.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.soft_red_text));
            holder.amountTv.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.soft_red_text));
            holder.amountTv.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.soft_red_bg));
            //status dot
            holder.statusDot.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.bg_dot_red));
        }else {
            holder.fromTv.setText(transaction.getSender());
            //amount
            holder.amountTv.setText("+$" + String.format("%.2f", transaction.getAmount()));
            //icon
            holder.iconIv.setImageResource(android.R.drawable.arrow_up_float);
            holder.iconIv.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.soft_green_text));
            holder.iconIv.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.bg_soft_icon_green));

            holder.fromTv.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.soft_green_text));
            holder.amountTv.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.soft_green_text));
            holder.amountTv.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.soft_green_bg));
            //status dot
            holder.statusDot.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.bg_dot_green));
        }



        //on click item
        holder.itemView.setOnClickListener(view -> {

            TranDetailBottomSheet sheet = new TranDetailBottomSheet();

            //container that holds data
            Bundle bundle = new Bundle();


            //pack data into a bundle
            bundle.putString("date", holder.dateTv.getText().toString());
            bundle.putString("amount", "$" + String.format("%.2f", transaction.getAmount()));
            bundle.putString("sender", transaction.getSender());
            bundle.putString("receiver", transaction.getReceiver());
            bundle.putString("remark", transaction.getRemark());
            bundle.putString("senderAcc", transaction.getSenderAcc());
            bundle.putString("receiverAcc", transaction.getTargetAccountNumber());
            bundle.putString("refId", transaction.getRefCode());
            bundle.putString("direction", transaction.getDirection());
            bundle.putString("transactionType", transaction.getTransactionType());

            //send to bottom sheet
            sheet.setArguments(bundle);
            sheet.show(
                    ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager(), "TranDetailBottomSheet");


        });


    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //initialize the views item
        TextView fromTv;
        TextView amountTv;
        TextView dateTv;
        ImageView iconIv;
        View statusDot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fromTv = itemView.findViewById(R.id.from_tv);
            amountTv = itemView.findViewById(R.id.amount_txt);
            dateTv = itemView.findViewById(R.id.tran_date_txt);
            iconIv = itemView.findViewById(R.id.icon_iv);
            statusDot = itemView.findViewById(R.id.statusDot);
        }
    }

}

