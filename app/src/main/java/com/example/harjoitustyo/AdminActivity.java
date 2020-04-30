package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    ReadAndWriteFiles rawf = ReadAndWriteFiles.getInstance(this);
    Bank bank = Bank.getInstance();
    User user;
    private ArrayList<User> users = new ArrayList<>();
    private Spinner usersSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        usersSpinner = findViewById(R.id.usersSpinner);

        setUsersSpinner();
    }

    public void activateDeadCards(View v) {
        int count = 0;
        for (int u = 0; u < bank.getUserList().size(); u++) { //For-loop for adding all the cards where Dead is set to 1 to our deadCardList
            for (int a = 0; a < bank.getUserList().get(u).getAccounts().size(); a++) {
                for (int c = 0; c < bank.getUserList().get(u).getAccounts().get(a).getCards().size(); c++) {
                    if (bank.getUserList().get(u).getAccounts().get(a).getCards().get(c).getDead() == 1) {
                        bank.getUserList().get(u).getAccounts().get(a).getCards().get(c).setDead(0);
                        count++;
                    }
                }
            }
        }
        if (count > 0) {
            Toast.makeText(this, "Kaikki kuoletetut kortit aktivoitu uudestaan", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Asiakkailla ei ollut kuoletettuja kortteja", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUsersSpinner() {
        users = bank.getUserList();
        if (users.size() == 0) {
            List<String> emptyList = new ArrayList<>();
            emptyList.add("Pankilla ei ole vielä asiakkaita");
            ArrayAdapter<String> usersAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, emptyList);
            usersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            usersSpinner.setAdapter(usersAdapter);
        } else {
            ArrayAdapter<User> usersAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, users);
            usersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            usersSpinner.setAdapter(usersAdapter);
        }
    }

    public void addAccountToUser(View v) {
        user = (User) usersSpinner.getSelectedItem();
        Toast.makeText(this, "Lisätty normaali tili käyttäjälle " + user.getName(), Toast.LENGTH_SHORT).show();
        bank.getUserList().get(findUserId()).addAccount(user.getName(), bank.generateAccountNumber("Normaali"), "Normaali", 1);
        rawf.writeUsers();
    }

    public void deleteUser(View v) {
        user = (User) usersSpinner.getSelectedItem();
        Toast.makeText(this, "Käyttäjä " + user.getName() + " poistettu", Toast.LENGTH_SHORT).show();
        bank.getUserList().remove(findUserId());
        rawf.writeUsers();
        setUsersSpinner();
    }

    public void deleteAllUsers(View v) {
        if (bank.getUserList().size() > 0) {
            bank.getUserList().clear();
            rawf.writeUsers();
            setUsersSpinner();
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

    public void onBackPressed() {
        rawf.writeUsers();
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        Toast.makeText(this, "Kirjauduttu ulos", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
