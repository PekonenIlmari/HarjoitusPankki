package com.example.harjoitustyo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements AllChangeDialog.NewAllChangeDialogListener {
    EditText nameLogin, passwordLogin, nameRegister, passwordregister, passwordCheckRegister;
    private int confirmationCodeCheck;

    PasswordChecker pc = PasswordChecker.getInstance();
    Bank bank = Bank.getInstance();
    User user;
    ArrayList<User> users = new ArrayList<>();
    //ReadAndWriteFiles rawf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //rawf = ReadAndWriteFiles.getInstance(getApplicationContext());

        //rawf.readUsers();


        users = bank.getUserList();

        nameLogin = findViewById(R.id.userLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        nameRegister = findViewById(R.id.userRegister);
        passwordregister = findViewById(R.id.passwordRegister);
        passwordCheckRegister = findViewById(R.id.passwordCheckRegister);
    }

    public void login(View v) {
        String loginName = nameLogin.getText().toString();
        String loginPassword = passwordLogin.getText().toString();
        int found = 0;

        System.out.println(users.size());

        for (int i = 0; i < users.size(); i++) {
            user = (User) users.get(i);
            if (loginName.equals(user.getUserName()) && loginPassword.equals(user.getPassword())) {
                found = 1;
                openDialog();
                break;
            }
        }
        if (found == 0) {
            Toast.makeText(this, "Virheellinen käyttäjätunnus tai salasana", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDialog() {
        AllChangeDialog acd = AllChangeDialog.newInstance(4);
        acd.show(getSupportFragmentManager(), "Varmistuskoodi ikkuna");
    }

    @Override
    public void confirmedCode(int code) {
        confirmationCodeCheck = code;
        if (confirmationCodeCheck == 1) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            Toast.makeText(this, "Tervetuloa " + user.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Virheellinen vahvistuskoodi", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void register(View v) {
        String registerName = nameRegister.getText().toString();
        String registerPassword = passwordregister.getText().toString();

        if (registerName.length() > 0 && registerPassword.length() > 0 && passwordCheckRegister.getText().toString().length() > 0) {
            int passwordValid = passWordChecker(registerPassword);
            System.out.println("OKOKOKO");

            for (int i = 0; i < users.size(); i++) {
                user = (User) users.get(i);
                if (!registerName.equals(user.getUserName()) || users.size() == 0) {
                    if (passwordValid == 1) {
                        Bundle extras = new Bundle();
                        Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
                        extras.putString("NAME", registerName);
                        extras.putString("PASSWORD", registerPassword);
                        intent.putExtras(extras);
                        startActivity(intent);
                    } else if (passwordValid == 0) {
                        Toast.makeText(this, "Virheellinen salasana: salasanan  tulee sisältää vähintään yhden " +
                                "numeron, erikoismerkin, ison ja pienen kirjaimen ja olla vähintään 12 merkkiä pitkä", Toast.LENGTH_LONG).show();
                    } else if (passwordValid == 2) {
                        Toast.makeText(this, "Salasanat eivät täsmää", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "Käyttäjätunnus varattu", Toast.LENGTH_SHORT).show();
                    nameRegister.setText("");
                    passwordregister.setText("");
                    passwordCheckRegister.setText("");
                    break;
                }
            }
        } else {
            Toast.makeText(this, "Täytä kaikki kentät", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void register(View v) {
        String registerName = nameRegister.getText().toString();
        String registerPassword = passwordregister.getText().toString();
        String registerPasswordCheck = passwordCheckRegister.getText().toString();

        if (registerName.length() > 0 && registerPassword.length() > 0 && passwordCheckRegister.getText().toString().length() > 0) {
            int passwordValid = pc.checkPassword(registerPassword, registerPasswordCheck);
            //int passwordValid = passWordChecker(registerPassword);
            //int passwordValid = 1;
            System.out.println("OKOKOKO");

            if (passwordValid == 1) {
                Bundle extras = new Bundle();
                Intent intent = new Intent(LoginActivity.this, NewUserActivity.class);
                extras.putString("USERNAME", registerName);
                extras.putString("PASSWORD", registerPassword);
                intent.putExtras(extras);
                startActivity(intent);
            } else if (passwordValid == 0) {
                Toast.makeText(this, "Virheellinen salasana: salasanan  tulee sisältää vähintään yhden " +
                        "numeron, erikoismerkin, ison ja pienen kirjaimen ja olla vähintään 12 merkkiä pitkä", Toast.LENGTH_LONG).show();
            } else if (passwordValid == 2) {
                Toast.makeText(this, "Salasanat eivät täsmää", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Täytä kaikki kentät", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void changedPhoneNumber(String phoneNum) {

    }

    @Override
    public void changedPassword(String password) {

    }

    @Override
    public void changedAddress(String address) {

    }

    @Override
    public void changedUsername(String username) {

    }

    @Override
    public void addedAmount(float amount) {

    }
}
