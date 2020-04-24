package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserSettingsActivity extends AppCompatActivity implements PasswordChangeDialog.NewPasswordDialogListener {
    TextView nameText, addressText, phoneText;
    Bank bank = Bank.getInstance();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        user = (User) getIntent().getSerializableExtra("user");

        nameText = findViewById(R.id.userName);
        addressText = findViewById(R.id.address);
        phoneText = findViewById(R.id.phoneNumber);

        nameText.setText("Nimesi: " + user.getName());
        addressText.setText("Osoitteesi: " + user.getAddress());
        phoneText.setText("Puhelinnumerosi: " + user.getPhone());
    }

    public void openDialog(View v) {
        PasswordChangeDialog pcd = new PasswordChangeDialog();
        pcd.show(getSupportFragmentManager(), "Salasanan vaihto ikkuna");
    }

    @Override
    public void changedPassword(String password) {
        if (!password.equals("ERROR")) {
            user.setPassword(password);
            bank.getUserList().set(findUserId(), user);
            Toast.makeText(this, "Salasanan vaihto onnistui",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Salasanan vaihto ei onnistunut",Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        Intent intent = new Intent(UserSettingsActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

}
