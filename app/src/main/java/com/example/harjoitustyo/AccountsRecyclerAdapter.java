package com.example.harjoitustyo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AccountsRecyclerAdapter extends RecyclerView.Adapter<AccountsRecyclerAdapter.AccountViewHolder> {
    private ArrayList<Account> accountList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {
        public TextView accountTypeLine, infoLine;

        public AccountViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            accountTypeLine = itemView.findViewById(R.id.accountTypeLine);
            infoLine = itemView.findViewById(R.id.infoLine);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public AccountsRecyclerAdapter(ArrayList<Account> accountList) {
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item, parent, false);
        AccountViewHolder avh = new AccountViewHolder(v, listener);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account currentAccount = accountList.get(position);
        float tempAmount = currentAccount.getAmount();
        String amount = String.format("%.2f", tempAmount);

        holder.accountTypeLine.setText(currentAccount.getType());
        holder.infoLine.setText("Saldo: " + amount + "€" + ", Tilinumero: " + currentAccount.getAcc_number());
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }


}
