package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WireTransferActivity extends AppCompatActivity {
    TextView infoText, fromAccountMoneyTextView;
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
        fromAccountMoneyTextView = findViewById(R.id.fromAccountMoneyTextView);

        fromAccountMoneyTextView.setText("Maksutilin saldo: ");

        infoText.setText("Siirrä rahaa toiselle käyttäjälle");

        user = (User) getIntent().getSerializableExtra("user");

        setSpinners();
    }

    public void setSpinners() {
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

        for (int i = 0; i < bank.getUserList().size(); i++) {
            if (bank.getUserList().get(i).getUserName().equals(user.getUserName())) {
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

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (user.getAccounts().size() > 0) {
                    fromAccountMoneyTextView.setText("Maksutilin saldo: " + String.format("%.2f", user.getAccounts().get(position).getAmount()) + "€");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getSelectedAccounts() {
        fromAcc = (Account) fromSpinner.getSelectedItem();
        toAcc = (Account) toSpinner.getSelectedItem();
    }

    public void transferMoney(View v) {
        if (fromAccountList.size() > 0 && toAccountList.size() > 0) {
            getSelectedAccounts();
            String tempAmount = transferAmount.getText().toString();
            if (tempAmount.matches("^[0-9.]+$")) { //Checking that inserted text contains only numbers or decimal separator
                float transferableAmount = Float.parseFloat(tempAmount.replaceAll("\\s+", "")); //Removing all the whitespaces and changing type to float
                String strAmount = String.format("%.2f", transferableAmount);
                if (fromAcc.getCanPay() == 1) {
                    if (transferableAmount > fromAcc.getAmount()) {
                        Toast.makeText(this, "Tilin kate ei riitä, siirrä vähemmän rahaa", Toast.LENGTH_SHORT).show();
                    } else if (transferableAmount > 0) {
                        fromAcc.setAmount(fromAcc.getAmount() - transferableAmount);
                        toAcc.setAmount((toAcc.getAmount() + transferableAmount));
                        fromAcc.addAccountActivity("Tilisiirto", toAcc.getAcc_owner(), "-" + strAmount);
                        toAcc.addAccountActivity("Tilisiirto", "-", "+" + strAmount);
                        user.setLatestAction("Tilisiirto -" + String.format("%.2f", transferableAmount) + "€");
                        bank.getUserList().set(findUserId(), user);
                        fromAccountMoneyTextView.setText("Maksutilin saldo: " + String.format("%.2f", fromAcc.getAmount()) + "€");
                        Toast.makeText(this, "Maksettu " + transferableAmount + "€ käyttäjälle " + toAcc.getAcc_owner(), Toast.LENGTH_SHORT).show();
                        transferAmount.setText("");
                        rawf.writeUsers();
                    } else {
                        Toast.makeText(this, "Et voi maksaa 0€", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Maksaminen on estetty valitulta tililtä", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Maksettava määrä ei voi sisältää kirjaimia", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Luo ensin tilejä tai pyydä kaveriasi tekemään tili ennen kuin voit maksaa", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(WireTransferActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
