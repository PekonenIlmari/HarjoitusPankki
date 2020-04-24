package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UserSettingsActivity extends AppCompatActivity implements PasswordChangeDialog.VerificationCodeDialogListener {
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
    public void confirmedCode(int code) {
        if (code == 1) {
            Toast.makeText(this, "Salasanan vaihto onnistui", Toast.LENGTH_SHORT).show();
        } else if (code == 0) {
            Toast.makeText(this, "Salasanan vaihto ei onnistunut",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserSettingsActivity.this, MainActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

}
