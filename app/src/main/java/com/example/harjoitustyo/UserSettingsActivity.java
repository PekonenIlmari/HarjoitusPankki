package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UserSettingsActivity extends AppCompatActivity {
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserSettingsActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

}
