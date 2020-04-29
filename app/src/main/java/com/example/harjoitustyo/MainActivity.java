package com.example.harjoitustyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    NavigationView navigationView;
    TextView infoText, navHeaderName;
    User user;
    ReadAndWriteFiles rawf;
    TextView latestActivity, accountCount, cardCount, overallMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rawf = ReadAndWriteFiles.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        infoText = findViewById(R.id.helloText);

        latestActivity = findViewById(R.id.latestActivityTextView);
        accountCount = findViewById(R.id.accountCount);
        cardCount = findViewById(R.id.cardCount);
        overallMoney = findViewById(R.id.overallMoney);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        navHeaderName = headerView.findViewById(R.id.nav_header_name);

        navigationView.setCheckedItem(R.id.nav_frontpage);


        user = (User) getIntent().getSerializableExtra("user");

        setTextViews();
    }

    private void setTextViews() {
        infoText.setText("Hyvää huomenta " + user.getName() + "!");
        navHeaderName.setText(user.getName());

        latestActivity.setText("Viimeisin tilitapahtuma: " + user.getLatestAction());

        accountCount.setText("Tiliesi lukumäärä: " + user.getAccounts().size());

        int overallCardCount = 0;
        float overallUserMoney = 0;

        for (int a = 0; a < user.getAccounts().size(); a++) {
            for (int c = 0; c < user.getAccounts().get(a).getCards().size(); c++) {
                overallCardCount++;
            }
        }
        cardCount.setText("Korttiesi lukumäärä: " + overallCardCount);

        for (int a = 0; a < user.getAccounts().size(); a++) {
            overallUserMoney += user.getAccounts().get(a).getAmount();
        }

        overallMoney.setText("Rahaa tileillä yhteensä: " + String.format("%.2f", overallUserMoney) + "€");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_frontpage:
                navigationView.setCheckedItem(R.id.nav_frontpage);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_settings:
                drawer.closeDrawer(GravityCompat.START);
                Intent intentUserSettigs = new Intent(MainActivity.this, UserSettingsActivity.class);
                intentUserSettigs.putExtra("user", user);
                startActivity(intentUserSettigs);
                break;
            case R.id.nav_accounts:
                drawer.closeDrawer(GravityCompat.START);
                Intent intentAccounts = new Intent(MainActivity.this, AccountsActivity.class);
                intentAccounts.putExtra("user", user);
                startActivity(intentAccounts);
                break;
            case R.id.nav_cardPayment:
                drawer.closeDrawer(GravityCompat.START);
                Intent intentCardPayment = new Intent(MainActivity.this, CardPaymentActivity.class);
                intentCardPayment.putExtra("user", user);
                startActivity(intentCardPayment);
                break;
            case R.id.nav_accountPayment:
                drawer.closeDrawer(GravityCompat.START);
                Intent intentWireTransfer = new Intent(MainActivity.this, WireTransferActivity.class);
                intentWireTransfer.putExtra("user", user);
                startActivity(intentWireTransfer);
                break;
            case R.id.nav_transferToOwnAccount:
                drawer.closeDrawer(GravityCompat.START);
                Intent intentToOwn = new Intent(MainActivity.this, OwnTransferActivity.class);
                intentToOwn.putExtra("user", user);
                startActivity(intentToOwn);
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Kirjauduttu ulos", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                rawf.writeUsers();
                Intent intentLogout = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogout);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            rawf.writeUsers();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) { //For dismissing the keyboard when clicking somewhere else
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
