package com.example.harjoitustyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AccountsActivity extends AppCompatActivity {
    private ArrayList<Account> accountList;
    private RecyclerView mRecyclerView;
    private AccountsRecyclerAdapter mAdapter; //For managing single cards in RecyclerView
    private RecyclerView.LayoutManager mLayoutManager; //For managing how the accounts are in RecyclerView
    User user;
    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        user = (User) getIntent().getSerializableExtra("user");

        accountList = user.getAccounts();
        buildRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.accounts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.normalAccountItem:
                addNormalAccount();
                return true;
            case R.id.creditAccountItem:
                addCreditAccount();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.accountRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AccountsRecyclerAdapter(accountList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AccountsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String tempAccNum = accountList.get(position).getAcc_number();
                Intent intent = new Intent(AccountsActivity.this, AccountInfoActivity.class);
                intent.putExtra("ACC_NUM", tempAccNum);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    public void addNormalAccount() {
        String acc_num = bank.generateAccountNumber("Normaali");
        user.addAccount(user.getName(), acc_num, "Normaali", 1);
        mAdapter.notifyDataSetChanged();
        bank.getUserList().set(findUserId(), user);
    }

    public void addCreditAccount() {
        String acc_num = bank.generateAccountNumber("Luotto");
        user.addAccount(user.getName(), acc_num, "Luotto", 1);
        mAdapter.notifyDataSetChanged();
        bank.getUserList().set(findUserId(), user);
    }

    private int findUserId() {
        int position = -1;

        for (int i = 0; i < bank.getUserList().size(); i++) {
            if (user.getName().equals(bank.getUserList().get(i).getName())) {
                position = i;
            }
        }
        return position;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AccountsActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
