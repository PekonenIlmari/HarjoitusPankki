package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CardInfoActivity extends AppCompatActivity {
    TextView cardNumberTextView, cardPayLimitTextView, cardRegionTextView;
    Button regionChangeButton;
    String strCardNum;
    int account_id;

    Card card;
    User user;
    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);

        strCardNum = getIntent().getStringExtra("CARD_NUM");
        user = (User) getIntent().getSerializableExtra("user");
        String temp_account_id = getIntent().getStringExtra("ACCOUNT_ID");
        account_id = Integer.parseInt(temp_account_id);
        card = user.getAccounts().get(account_id).getCards().get(findCardId());

        cardNumberTextView = findViewById(R.id.cardNumber);
        cardPayLimitTextView = findViewById(R.id.cardPayLimit);
        cardRegionTextView = findViewById(R.id.cardRegion);
        regionChangeButton = findViewById(R.id.regionChangeButton);

        setRegionChangeButton();
        setInfo();
    }

    private void setInfo() {
        cardNumberTextView.setText("Kortin numero: " + card.getCard_num());
        cardPayLimitTextView.setText("Kortin maksuraja: " + card.getPayment_limit() + "€");
        if (card.getRegion() == 1) {
            cardRegionTextView.setText("Kortin toimivuusalue: Kotimaa");
        } else if (card.getRegion() == 2) {
            cardRegionTextView.setText("Kortin toimivuusalue: Koko maailma");
        }
    }

    public void changeRegion(View v) {
        if (card.getRegion() == 1) {
            card.setRegion(2);
            setRegionChangeButton();
            bank.getUserList().set(findUserId(), user);
        } else if (card.getRegion() == 2) {
            card.setRegion(1);
            setRegionChangeButton();
            bank.getUserList().set(findUserId(), user);
        }
    }

    private void setRegionChangeButton() {
        if (card.getRegion() == 1) {
            regionChangeButton.setText("Muuta kortin toimivuusalueeksi koko maailma");
        } else if (card.getRegion() == 2) {
            regionChangeButton.setText("Muuta kortin toimivuusalueeksi kotimaa");
        }
    }

    private int findCardId() {
        int position = -1;

        for (int i = 0; i < user.getAccounts().get(account_id).getCards().size(); i++) {
            if (strCardNum.equals(user.getAccounts().get(account_id).getCards().get(i).getCard_num())) {
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

    public void onBackPressed() {
        Intent intent = new Intent(CardInfoActivity.this, AccountInfoActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("ACC_NUM", card.getAcc_num());
        startActivity(intent);
        finish();
    }
}
