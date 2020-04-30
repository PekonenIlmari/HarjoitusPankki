package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

public class UserSettingsActivity extends AppCompatActivity implements AllChangeDialog.NewAllChangeDialogListener {
    TextView userNameText, nameText, addressText, phoneText;
    Bank bank = Bank.getInstance();
    User user;
    ReadAndWriteFiles rawf;
    PasswordHasher ph = PasswordHasher.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        rawf = ReadAndWriteFiles.getInstance(this);

        user = (User) getIntent().getSerializableExtra("user");

        userNameText = findViewById(R.id.userName);
        nameText = findViewById(R.id.userWholeName);
        addressText = findViewById(R.id.address);
        phoneText = findViewById(R.id.phoneNumber);

        userNameText.setText("Käyttäjanimesi: " + user.getUserName());
        nameText.setText("Nimesi: " + user.getName());
        addressText.setText("Osoitteesi: " + user.getAddress());
        phoneText.setText("Puhelinnumerosi: " + user.getPhone());
    }

    public void openPhoneDialog(View v) { //Open phone number changing dialog
        AllChangeDialog acd = AllChangeDialog.newInstance(1);
        acd.show(getSupportFragmentManager(), "Puhelinnumeron vaihto ikkuna");

    }

    public void openAddressDialog(View v) { //Open address changing dialog
        AllChangeDialog acd = AllChangeDialog.newInstance(3);
        acd.show(getSupportFragmentManager(), "Osoitteen vaihto ikkuna");
    }

    public void openPasswordDialog(View v) { //Open password changing dialog
        AllChangeDialog acd = AllChangeDialog.newInstance(2);
        acd.show(getSupportFragmentManager(), "Puhelinnumeron vaihto ikkuna");
    }

    public void openUserNameDialog(View v) { //Open username changing dialog
        AllChangeDialog acd = AllChangeDialog.newInstance(5);
        acd.show(getSupportFragmentManager(), "Puhelinnumeron vaihto ikkuna");
    }

    @Override
    public void changedPhoneNumber(String phoneNum) { //Change phone number based on the code
        if (!phoneNum.equals("ERROR")) {
            user.setPhone(phoneNum);
            bank.getUserList().set(findUserId(), user);
            rawf.writeUsers();
            phoneText.setText("Puhelinnumerosi: " + user.getPhone());
            Toast.makeText(this, "Puhelinnumeron vaihto onnistui", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Puhelinnumeron vaihto ei onnistunut", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void changedAddress(String address) { //Change address based on the code
        if (!address.equals("ERROR")) {
            user.setAddress(address);
            bank.getUserList().set(findUserId(), user);
            rawf.writeUsers();
            addressText.setText("Osoitteesi: " + user.getAddress());
            Toast.makeText(this, "Osoitteen vaihto onnistui", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Osoiteen vaihto ei onnistunut", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void changedPassword(String password)  { //Change password based on the code
        if (!password.equals("ERROR")) {
            byte[] salt = new byte[0];
            try {
                salt = ph.getSalt();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            String tempPassword = ph.getSecurePassword(password, salt);
            user.setPassword(tempPassword);
            user.setSalt(salt);
            bank.getUserList().set(findUserId(), user);
            rawf.writeUsers();
            Toast.makeText(this, "Salasanan vaihto onnistui", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Salasanan vaihto ei onnistunut", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void changedUsername(String username) { //Change username based on the code
        int found = 0;
        if (!username.equals("ERROR")) {
            for (int i = 0; i < bank.getUserList().size(); i++) { //Checking if the username is already taken
                if (username.equals(bank.getUserList().get(i).getUserName())) {
                    found = 1;
                }
            }
            if (found == 0) {
                user.setUserName(username);
                bank.getUserList().set(findUserId(), user);
                rawf.writeUsers();
                userNameText.setText("Käyttäjanimesi: " + user.getUserName());
                Toast.makeText(this, "Käyttäjänimen vaihto onnistui", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Käyttäjänimi on jo käytössä", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Käyttäjänimen pitää olla vähintään 3 merkkiä pitkä", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void confirmedCode(int code) {

    }

    @Override
    public void addedAmount(float amount) {

    }

    @Override
    public void changedPayLimit(int paylimit) {

    }

    @Override
    public void changedTakeLimit(int takelimit) {

    }

    @Override
    public void takenAmount(float amount, int region) {

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
        Intent intent = new Intent(UserSettingsActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

}
