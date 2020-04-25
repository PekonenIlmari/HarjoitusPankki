package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends AppCompatActivity {
    Bank bank = Bank.getInstance();
    private String name, userName, password, address, phone;
    EditText nameBox, addressBox, phoneBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        nameBox = findViewById(R.id.nameBox);
        addressBox = findViewById(R.id.addressBox);
        phoneBox = findViewById(R.id.phoneBox);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        userName = extras.getString("USERNAME");
        password = extras.getString("PASSWORD");
    }

    public void finalizeRegistration(View v) {
        address = addressBox.getText().toString();
        phone = phoneBox.getText().toString();
        name = nameBox.getText().toString();
        if (address.length() > 0 && phone.length() > 0) {
            bank.addUser(name, userName, password, address, phone);
            Intent intent = new Intent(NewUserActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Rekisteröity", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Täytä vaadittavat kentät", Toast.LENGTH_SHORT).show();
        }
    }

}
