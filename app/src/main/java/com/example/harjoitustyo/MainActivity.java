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
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getApplicationContext();

        infoText = findViewById(R.id.helloText);

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

        infoText.setText("Hyvää huomenta " + user.getName() + "!");
        navHeaderName.setText(user.getName());
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
                Toast.makeText(this, "Asetukset", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_accounts:
                drawer.closeDrawer(GravityCompat.START);
                Intent intentAccounts = new Intent(MainActivity.this, AccountsActivity.class);
                intentAccounts.putExtra("user", user);
                startActivity(intentAccounts);
                break;
            case R.id.nav_cardPayment:
                drawer.closeDrawer(GravityCompat.START);
                Toast.makeText(this, "Korttimaksu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_accountPayment:
                drawer.closeDrawer(GravityCompat.START);
                Toast.makeText(this, "Tilisiirto", Toast.LENGTH_SHORT).show();
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
                ReadAndWriteFiles rawf = new ReadAndWriteFiles(context);
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
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
