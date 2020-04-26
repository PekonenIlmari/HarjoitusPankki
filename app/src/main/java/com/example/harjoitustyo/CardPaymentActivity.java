package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.lang.UScript;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CardPaymentActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CardRecyclerAdapter mAdapter; //For managing single cards in RecyclerView
    private RecyclerView.LayoutManager mLayoutManager; //For managing how the cards are in RecyclerView

    String tempAccNum, tempCardNum;

    TextView accountNumberInfo, accountAmountInfo, cardPaymentLimitInfo;
    EditText paymentReceiver, paymentAmount;

    Bank bank = Bank.getInstance();
    User user;

    private ArrayList<Account> accountList = new ArrayList<>();
    private ArrayList<Card> cardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);

        accountNumberInfo = findViewById(R.id.accountNumberInfo);
        accountAmountInfo = findViewById(R.id.accountAmountInfo);
        cardPaymentLimitInfo = findViewById(R.id.cardPaymentLimitInfo);
        paymentReceiver = findViewById(R.id.paymentReceiver);
        paymentAmount = findViewById(R.id.amountEditText);

        user = (User) getIntent().getSerializableExtra("user");
        accountList = user.getAccounts();

        initializeCardList();

        buildRecyclerView();
    }

    private void initializeCardList() {
        for (int i = 0; i < user.getAccounts().size(); i++) {
            for (int x = 0; x < user.getAccounts().get(i).getCards().size(); x++) {
                cardList.add(user.getAccounts().get(i).getCards().get(x));
            }
        }
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
                tempAccNum = cardList.get(position).getAcc_num();
                tempCardNum = cardList.get(position).getCard_num();
                accountNumberInfo.setText("Tilinumero: " + tempAccNum);
                accountAmountInfo.setText("Tilin kate: " + String.format("%.2f", accountList.get(findAccountId()).getAmount()) + "€");
                cardPaymentLimitInfo.setText("Kortin maksuraja: " + cardList.get(position).getPayment_limit() + "€");
            }
        });
    }

    public void makePayment(View v) {
        float amount = Float.parseFloat(paymentAmount.getText().toString());
        String receiver = paymentReceiver.getText().toString();

        if(amount > accountList.get(findAccountId()).getAmount()) {
            Toast.makeText(this, "Tilin kate ei riitä, pienennä maksun määrää tai siirrä tilille lisää rahaa", Toast.LENGTH_LONG).show();
        } else if (amount > cardList.get(findCardId()).getPayment_limit()) {
            Toast.makeText(this, "Maksu on suurempi kuin kortin maksuraja, pienennä maksun määrää tai muuta maksurajaa", Toast.LENGTH_LONG).show();
        } else {
            accountList.get(findAccountId()).setAmount(accountList.get(findAccountId()).getAmount() - amount);
            Toast.makeText(this, "Maksettu " + amount + "€, Maksun saaja: " + receiver, Toast.LENGTH_LONG).show();
            bank.getUserList().set(findUserId(), user);
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }

    private int findCardId() {
        int position = -1;

        for (int i = 0; i < accountList.size(); i++) {
            if (tempCardNum.equals(cardList.get(i).getCard_num())) {
                position = i;
            }
        }
        return position;
    }

    private int findAccountId() {
        int position = -1;

        for (int i = 0; i < accountList.size(); i++) {
            if (tempAccNum.equals(accountList.get(i).getAcc_number())) {
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
    public void onBackPressed() {
        Intent intent = new Intent(CardPaymentActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}