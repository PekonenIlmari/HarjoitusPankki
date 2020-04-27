package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CardInfoActivity extends AppCompatActivity implements AllChangeDialog.NewAllChangeDialogListener {
    TextView cardNumberTextView, cardPayLimitTextView, cardRegionTextView, cardDeadTextView;
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
        cardDeadTextView = findViewById(R.id.cardDead);
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
        if (card.getDead() == 0) {
            cardDeadTextView.setText("Kortin tila: Käytössä");
        } else {
            cardDeadTextView.setText("Kortin tila: Kuoletettu");
        }
    }

    public void openPayLimitDialog(View v) {
        AllChangeDialog acd = AllChangeDialog.newInstance(8);
        acd.show(getSupportFragmentManager(), "Kortin maksurajan muutos");
    }

    public void openConfirmCardRemoveDialog(View v) {
        AllChangeDialog acd = AllChangeDialog.newInstance(9);
        acd.show(getSupportFragmentManager(), "Kortin poiston muutos");
    }

    public void killCard(View v) {
        System.out.println("KUOLETETAAN");
        card.setDead(1);
        bank.getUserList().set(findUserId(), user);
        cardDeadTextView.setText("Kortin tila: Kuoletettu");
        Toast.makeText(this, "Jos haluat kortin uudestaan käyttöön, ota yhteyttä pankkiin", Toast.LENGTH_LONG).show();
    }

    public void changeRegion(View v) {
        if (card.getRegion() == 1) {
            card.setRegion(2);
            setRegionChangeButton();
            cardRegionTextView.setText("Kortin toimivuusalue: Koko maailma");
            bank.getUserList().set(findUserId(), user);
        } else if (card.getRegion() == 2) {
            card.setRegion(1);
            setRegionChangeButton();
            cardRegionTextView.setText("Kortin toimivuusalue: Kotimaa");
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
            user.getAccounts().get(account_id).getCards().remove(findCardId());
            bank.getUserList().set(findUserId(), user);
            Intent intent = new Intent(CardInfoActivity.this, AccountInfoActivity.class);
            intent.putExtra("ACC_NUM", card.getAcc_num());
            intent.putExtra("user", user);
            startActivity(intent);
            Toast.makeText(this, "Kortti poistettu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void changedUsername(String username) {

    }

    @Override
    public void addedAmount(float amount) {

    }

    @Override
    public void changedPayLimit(int paylimit) {
        if (paylimit > 0) {
            card.setPayment_limit(paylimit);
            bank.getUserList().set(findUserId(), user);
            cardPayLimitTextView.setText("Kortin maksuraja: " + card.getPayment_limit() + "€");
            Toast.makeText(this, "Maksurajan muutos onnistui",Toast.LENGTH_SHORT).show();
        } else if (paylimit == -1){
            Toast.makeText(this, "Maksuraja ei voi olla negatiivinen",Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(CardInfoActivity.this, AccountInfoActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("ACC_NUM", card.getAcc_num());
        startActivity(intent);
        finish();
    }
}
