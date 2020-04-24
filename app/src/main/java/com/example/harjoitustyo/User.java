package com.example.harjoitustyo;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {//stores data of each user
    private Account account;
    private String name, address, phone, password;

    public User(String name, String password, String address, String phone) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    ArrayList<Account> accounts = new ArrayList<>();

    public void addAccount(String name, String acc_number, String type, int canPay) {
        account = new Account(name, acc_number, type, canPay);
        accounts.add(account);
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
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
}
