package com.example.harjoitustyo;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {//stores data of each user
    private Account account;
    private String name, userName, address, phone, password;
    private byte[] salt;
    private String latestAction = "Ei vielä tilitapahtumia";

    public User(String name, String userName, String password, String address, String phone, byte[] salt) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.salt = salt;
    }

    ArrayList<Account> accounts = new ArrayList<>();

    public void addAccount(String name, String acc_number, String type, int canPay) {
        account = new Account(name, acc_number, type, canPay);
        accounts.add(account);
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getLatestAction() {
        return latestAction;
    }

    public void setLatestAction(String latestAction) {
        this.latestAction = latestAction;
    }

    @NonNull
    @Override
    public String toString() { //Setup how the user is displayed in spinner
        return name;
    }
}
