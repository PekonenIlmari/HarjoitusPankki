package com.example.harjoitustyo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AccountActivitiesActivity extends AppCompatActivity {
    User user;

    private int account_id; //Passed for getting the right account from the User object

    private TextView activitiesTypeTextView, activitiesReceiverTextView, activitiesAmountTextView;
    private ArrayList<String> activities = new ArrayList<>();
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activities);

        user = (User) getIntent().getSerializableExtra("user");
        String temp_account_id = getIntent().getStringExtra("ACCOUNT_ID");
        account_id = Integer.parseInt(temp_account_id);

        activitiesTypeTextView = findViewById(R.id.accountActivitiesTypeTextView);
        activitiesReceiverTextView = findViewById(R.id.accountActivitiesReceiverTextView);
        activitiesAmountTextView = findViewById(R.id.accountActivitiesAmountTextView);

        account = user.getAccounts().get(account_id);

        activities = account.getAccountActivities();

        showInfo();
    }

    private void showInfo() { //Method for showing all the activities of the current account
        String[] partion;
        String typeColumn = "";
        String receiverColumn = "";
        String amountColumn = "";

        if (activities.size() > 1) {
            for (int i = 1; i < activities.size(); i++) {
                partion = activities.get(i).split(",");
                typeColumn = typeColumn + partion[0] + "\n";
                receiverColumn = receiverColumn + partion[1] + "\n";
                ;
                float tempF = Float.parseFloat(partion[2]);
                String tempAmount = String.format("%.2f", tempF);
                if (tempF >= 0) {
                    tempAmount = "+" + tempAmount;
                }
                amountColumn = amountColumn + tempAmount + "\n";
            }
        }
        activitiesTypeTextView.setText(typeColumn);
        activitiesReceiverTextView.setText(receiverColumn);
        activitiesAmountTextView.setText(amountColumn);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AccountActivitiesActivity.this, AccountInfoActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("ACC_NUM", account.getAcc_number());
        startActivity(intent);
        finish();
    }
}
