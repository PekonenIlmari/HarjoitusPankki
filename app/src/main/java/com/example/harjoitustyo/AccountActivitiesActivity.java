package com.example.harjoitustyo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.ArrayList;

public class AccountActivitiesActivity extends AppCompatActivity {
    User user;

    private int account_id;

    private TextView activitiesTextView;
    private ArrayList<String> activities = new ArrayList<>();
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activities);

        user = (User) getIntent().getSerializableExtra("user");
        String temp_account_id = getIntent().getStringExtra("ACCOUNT_ID");
        account_id = Integer.parseInt(temp_account_id);

        activitiesTextView = findViewById(R.id.accountActivitiesTextView);

        activitiesTextView.setMovementMethod(new ScrollingMovementMethod());

        account = user.getAccounts().get(account_id);

        activities = account.getAccountActivities();

        showInfo();
    }

    private void showInfo() {
        String info = "";

        for (String s : activities) {
            info = info + s;
        }

        activitiesTextView.setText(info);
    }
}
