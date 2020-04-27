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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AccountInfoActivity extends AppCompatActivity implements AllChangeDialog.NewAllChangeDialogListener {
    private RecyclerView mRecyclerView;
    private CardRecyclerAdapter mAdapter; //For managing single cards in RecyclerView
    private RecyclerView.LayoutManager mLayoutManager; //For managing how the cards are in RecyclerView

    private ArrayList<Account> accountList = new ArrayList<>();
    private ArrayList<Card> cardList = new ArrayList<>();
    TextView type, acc_num, amount;
    String strAccount;
    Button payableButton;
    Account account;
    User user;
    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        strAccount = getIntent().getStringExtra("ACC_NUM");
        user = (User) getIntent().getSerializableExtra("user");
        accountList = user.getAccounts();
        account = accountList.get(findAccountId());
        cardList = account.getCards();

        type = findViewById(R.id.accountTypeInfo);
        acc_num = findViewById(R.id.accountNumberInfo);
        amount = findViewById(R.id.accountAmountInfo);
        payableButton = findViewById(R.id.payableButton);

        setPayableButton();

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
                AllChangeDialog acdMoney = AllChangeDialog.newInstance(7);
                acdMoney.show(getSupportFragmentManager(), "Rahan lisäys tilille");
                return true;
            case R.id.deleteAccountItem:
                AllChangeDialog acdDelete = AllChangeDialog.newInstance(6);
                acdDelete.show(getSupportFragmentManager(), "Tilin poiston varmennus");
                return true;
            case R.id.addDebitCard:
                addCard();
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

        mAdapter.setOnItemClickListener(new CardRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String tempCardNum = cardList.get(position).getCard_num();
                Intent intent = new Intent(AccountInfoActivity.this, CardInfoActivity.class);
                intent.putExtra("CARD_NUM", tempCardNum);
                intent.putExtra("user", user);
                String tempAcc = String.valueOf(findAccountId());
                intent.putExtra("ACCOUNT_ID", tempAcc);
                startActivity(intent);
            }
        });
    }

    private void setPayableButton() {
        if (account.getCanPay() == 1) {
            payableButton.setText("Käytössä");
        } else {
            payableButton.setText("Ei käytössä");
        }
    }

    public void setAccountCanPay(View v) {
        if (account.getType().equals("Normaali")) {
            if (account.getCanPay() == 1) {
                account.setCanPay(0);
                bank.getUserList().set(findUserId(), user);
                setPayableButton();
            } else {
                account.setCanPay(1);
                bank.getUserList().set(findUserId(), user);
                setPayableButton();
            }
        } else {
            Toast.makeText(this, "Et voi ottaa säästötilille käyttöön maksamista", Toast.LENGTH_SHORT).show();
        }
    }

    public void addCard() {
        if (account.getType().equals("Normaali")) {
            String card_num = bank.generateCardNumber();
            account.addCard(user.getName(), strAccount, card_num, 1);
            bank.getUserList().set(findUserId(), user);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Et voi lisätä säästötilille maksukortteja", Toast.LENGTH_SHORT).show();
        }
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
    public void addedAmount(float amount) {
        if (amount > 0) {
            account.setAmount(account.getAmount() + amount);
            bank.getUserList().set(findUserId(), user);
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
            Toast.makeText(this, "Rahan lisäys tilille onnistui",Toast.LENGTH_SHORT).show();
        } else if (amount == -1){
            Toast.makeText(this, "Et voi lisätä negatiivista määrää tilille",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void changedPhoneNumber(String phoneNum) {

    }

    @Override
    public void changedPassword(String password) {

    }

    @Override
    public void changedAddress(String address) {

    }

    @Override
    public void confirmedCode(int code) {
        if (code == 1) {
            user.accounts.remove(findAccountId());
            bank.getUserList().set(findUserId(), user);
            Intent intent = new Intent(AccountInfoActivity.this, AccountsActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            Toast.makeText(this, "Tili poistettu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void changedUsername(String username) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AccountInfoActivity.this, AccountsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
