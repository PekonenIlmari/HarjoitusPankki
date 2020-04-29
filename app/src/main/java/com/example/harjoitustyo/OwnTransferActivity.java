package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OwnTransferActivity extends AppCompatActivity {
    TextView infoText;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    EditText transferAmount;
    Account fromAcc, toAcc;
    User user;
    Bank bank = Bank.getInstance();
    ReadAndWriteFiles rawf;

    List<Account> fromAccountList = new ArrayList<>();
    List<Account> toAccountList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);

        rawf = ReadAndWriteFiles.getInstance(this);

        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        transferAmount = findViewById(R.id.amountEditText);
        infoText = findViewById(R.id.infoTextView);

        infoText.setText("Siirrä rahaa omien tilien välillä");

        user = (User) getIntent().getSerializableExtra("user");

        setSpinners();
    }

    public void setSpinners() {
        List<String> emptyList = new ArrayList<>();
        emptyList.add("Sinulla ei ole yhtäkään tiliä");

        fromAccountList = user.getAccounts();

        if (fromAccountList.size() == 0) {
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

        toAccountList = user.getAccounts();

        if (toAccountList.size() == 0) {
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
        if (fromAccountList.size() > 0 && toAccountList.size() > 0) {
            getSelectedAccounts();
            String tempAmount = transferAmount.getText().toString();
            if (tempAmount.matches("^[0-9.]+$")) {
                float transferableAmount = Float.parseFloat(tempAmount.replaceAll("\\s+", ""));
                String strAmount = String.format("%.2f", transferableAmount);
                if (transferableAmount > fromAcc.getAmount()) {
                    Toast.makeText(this, "Tilin kate ei riitä, siirrä vähemmän rahaa", Toast.LENGTH_SHORT).show();
                } else {
                    fromAcc.setAmount(fromAcc.getAmount() - transferableAmount);
                    toAcc.setAmount((toAcc.getAmount() + transferableAmount));
                    fromAcc.addAccountActivity("Oma Siirto", toAcc.getAcc_number(), "-" + strAmount);
                    toAcc.addAccountActivity("Oma Siirto", "-", "+" + strAmount);
                    bank.getUserList().set(findUserId(), user);
                    transferAmount.setText("");
                    Toast.makeText(this, "Siirretty " + transferableAmount + "€ tilille " + toAcc.getAcc_number(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Siirrettävä määrä ei voi sisältää kirjaimia", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Luo ensin tilejä, jotta voit siirtää rahaa", Toast.LENGTH_SHORT).show();
        }
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
    public boolean dispatchTouchEvent(MotionEvent ev) { //For dismissing the keyboard when clicking somewhere else
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        rawf.writeUsers();
        Intent intent = new Intent(OwnTransferActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
