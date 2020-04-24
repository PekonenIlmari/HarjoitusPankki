package com.example.harjoitustyo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements VerificationCodeDialog.VerificationCodeDialogListener {
    EditText nameLogin, passwordLogin, nameRegister, passwordregister, passwordCheckRegister;
    private int confirmationCodeCheck;

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
            if (loginName.equals(user.getName()) && loginPassword.equals(user.getPassword())) {
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
        VerificationCodeDialog vcd = new VerificationCodeDialog();
        vcd.show(getSupportFragmentManager(), "Varmistuskoodi ikkuna");
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
                if (!registerName.equals(user.getName()) || users.size() == 0) {
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

        if (registerName.length() > 0 && registerPassword.length() > 0 && passwordCheckRegister.getText().toString().length() > 0) {
            //int passwordValid = passWordChecker(registerPassword);
            int passwordValid = 1;
            System.out.println("OKOKOKO");

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
            Toast.makeText(this, "Täytä kaikki kentät", Toast.LENGTH_SHORT).show();
        }
    }

    private int passWordChecker(String pass) { //This checks if the password matches the criteria
        char[] passArr = pass.toCharArray();
        int match = 0, len = 0, num = 0, special = 0, upper = 0, lower = 0;

        //check if the passwords match
        if (pass.equals(passwordCheckRegister.getText().toString())) {
            match = 1;
        }

        // check the password lenght is at least 12 characters long
        if (pass.length() >= 12) {
            len = 1;
        }

        // check if has number
        // check digits from 0 to 9
        for (char c : passArr) {
            if (Character.isDigit(c)) {
                num = 1;
            }
        }

        // for special characters
        if (pass.contains("@") || pass.contains("#")
                || pass.contains("!") || pass.contains("~")
                || pass.contains("$") || pass.contains("%")
                || pass.contains("^") || pass.contains("&")
                || pass.contains("*") || pass.contains("(")
                || pass.contains(")") || pass.contains("-")
                || pass.contains("+") || pass.contains("/")
                || pass.contains(":") || pass.contains(".")
                || pass.contains(", ") || pass.contains("<")
                || pass.contains(">") || pass.contains("?")
                || pass.contains("|")) {
            special = 1;
        }


        // checking capital letters
        for (int i = 65; i <= 90; i++) {

            // type casting
            char c = (char) i;

            String str1 = Character.toString(c);
            if (pass.contains(str1)) {
                upper = 1;
            }
        }

        // checking small letters
        for (int i = 90; i <= 122; i++) {

            // type casting
            char c = (char) i;
            String str1 = Character.toString(c);

            if (pass.contains(str1)) {
                lower = 1;
            }
        }
        if (match == 1 && len == 1 && num == 1 && special == 1 && upper == 1 && lower == 1) {
            return 1;
        } else if (match == 0) {
            return 2;
        } else {
            return 0;
        }
    }
}
