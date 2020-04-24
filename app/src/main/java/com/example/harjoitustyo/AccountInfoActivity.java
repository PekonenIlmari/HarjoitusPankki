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
import android.widget.TextView;

import java.util.ArrayList;

public class AccountInfoActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CardRecyclerAdapter mAdapter; //For managing single cards in RecyclerView
    private RecyclerView.LayoutManager mLayoutManager; //For managing how the cards are in RecyclerView

    private ArrayList<Account> accountList;
    private ArrayList<Card> cardList;
    TextView type, acc_num, amount;
    String strAccount;
    Account account;
    User user;
    Bank bank = Bank.getInstance();
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        strAccount = getIntent().getStringExtra("ACC_NUM");
        user = (User) getIntent().getSerializableExtra("user");
        accountList = user.getAccounts();
        position = findAccountId();
        account = accountList.get(position);
        cardList = account.getCards();

        type = findViewById(R.id.accountTypeInfo);
        acc_num = findViewById(R.id.accountNumberInfo);
        amount = findViewById(R.id.accountAmountInfo);

        buildRecyclerView();
        showInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.account_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMoney:
                account.setAmount(account.getAmount() + 100);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                return true;
            case R.id.deleteAccountItem:
                user.accounts.remove(position);
                Intent intent = new Intent(AccountInfoActivity.this, AccountsActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                return true;
            case R.id.addDebitCard:
                addCard("Debit");
                return true;
            case R.id.addCreditCard:
                addCard("Credit");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.cardRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CardRecyclerAdapter(cardList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        /*mAdapter.setOnItemClickListener(new AccountsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String tempAccNum = accountList.get(position).getAcc_number();
                Intent intent = new Intent(AccountsInfoActivity.this, CardInfoActivity.class);
                intent.putExtra("ACC_NUM", tempAccNum);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });*/
    }

    public void addCard(String type) {
        String card_num = bank.generateCardNumber(type);
        account.addCard(strAccount, card_num, type);
        mAdapter.notifyDataSetChanged();
    }

    private void showInfo() {
        type.setText("Tilin tyyppi: " + account.getType());
        acc_num.setText("Tilinumero: " + account.getAcc_number());
        amount.setText("Tilin saldo: " + String.format("%.2f", account.getAmount()) + "€");
    }

    private int findAccountId() {
        int position = -1;

        for (int i = 0; i < accountList.size(); i++) {
            if (strAccount.equals(accountList.get(i).getAcc_number())) {
                position = i;
            }
        }
        return position;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AccountInfoActivity.this, AccountsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
