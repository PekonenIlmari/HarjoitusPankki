package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WireTransferActivity extends AppCompatActivity {
    TextView infoText;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    EditText transferAmount;
    Account fromAcc, toAcc;
    User user;
    Bank bank = Bank.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);

        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        transferAmount = findViewById(R.id.amountEditText);
        infoText = findViewById(R.id.infoTextView);

        infoText.setText("Siirrä rahaa toiselle käyttäjälle");

        user = (User) getIntent().getSerializableExtra("user");

        setSpinners();
    }

    public void setSpinners() {
        List<Account> fromAccountList;
        fromAccountList = user.getAccounts();

        if (fromAccountList.size() == 0) {
            List<String> emptyList = new ArrayList<>();
            emptyList.add("Sinulla ei ole yhtäkään tiliä");
            ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, emptyList);
            fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fromSpinner.setAdapter(fromAdapter);
        } else {
            ArrayAdapter<Account> fromAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, fromAccountList);
            fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            fromSpinner.setAdapter(fromAdapter);
        }

        List<Account> toAccountList = new ArrayList<>();
        for (int i = 0; i < bank.getUserList().size(); i++) {
            if (bank.getUserList().get(i).getName().equals(user.getName())) {
                continue;
            }
            for (int x = 0; x < bank.getUserList().get(i).getAccounts().size(); x++) {

                toAccountList.add(bank.getUserList().get(i).getAccounts().get(x));
            }
        }

        if (toAccountList.size() == 0) {
            List<String> emptyList = new ArrayList<>();
            emptyList.add("Palvelun muilla käyttäjillä ei ole tilejä");
            ArrayAdapter<String> toAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, emptyList);
            toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            toSpinner.setAdapter(toAdapter);
        } else {
            ArrayAdapter<Account> toAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, toAccountList);
            toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            toSpinner.setAdapter(toAdapter);
        }
    }

    public void getSelectedAccounts() {
        fromAcc = (Account) fromSpinner.getSelectedItem();
        toAcc = (Account) toSpinner.getSelectedItem();
    }

    public void transferMoney(View v) {
        getSelectedAccounts();
        float transferableAmount = Float.parseFloat(transferAmount.getText().toString());
        if (transferableAmount > fromAcc.getAmount()) {
            Toast.makeText(this, "Tilin kate ei riitä, siirrä vähemmän rahaa", Toast.LENGTH_SHORT).show();
        } else {
            fromAcc.setAmount(fromAcc.getAmount() - transferableAmount);
            toAcc.setAmount((toAcc.getAmount() + transferableAmount));
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WireTransferActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
