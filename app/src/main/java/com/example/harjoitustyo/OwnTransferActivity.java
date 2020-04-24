package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class OwnTransferActivity extends AppCompatActivity {
    private Spinner fromSpinner;
    private Spinner toSpinner;
    EditText transferAmount;
    Account fromAcc, toAcc;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_transfer);

        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        transferAmount = findViewById(R.id.amountEditText);

        user = (User) getIntent().getSerializableExtra("user");

        setSpinners();
    }

    public void setSpinners() {
        List<Account> fromAccountList;
        fromAccountList = user.getAccounts();

        ArrayAdapter<Account> fromAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, fromAccountList);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromSpinner.setAdapter(fromAdapter);

        List<Account> toAccountList;
        toAccountList = user.getAccounts();

        ArrayAdapter<Account> toAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, toAccountList);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        toSpinner.setAdapter(toAdapter);
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
        Intent intent = new Intent(OwnTransferActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
