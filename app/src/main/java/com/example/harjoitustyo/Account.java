package com.example.harjoitustyo;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable { //Stores data of each account
    private Card card;
    private String acc_owner, acc_number, type; //Type can be normal or credit
    private float amount; //Amount and Credit limit are 0 on default
    private int credit_limit, canPay; //Decides if you can use account for payments; 0 = No, 1 = Yes

    public Account(String acc_owner,String acc_number, String type, int canPay) {
        this.acc_owner = acc_owner;
        this.acc_number = acc_number;
        this.type = type;
        this.amount = 0;
        this.credit_limit = 0;
        this.canPay = canPay;
    }

    ArrayList<Card> cards = new ArrayList<>();

    public void addCard(String acc_num, String card_number, String type) {
        card = new Card(acc_num, card_number, type);
        cards.add(card);
    }

    public String getAcc_number() {
        return acc_number;
    }

    public String getType() {
        return type;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getAcc_owner() {
        return acc_owner;
    }

    public int getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(int credit_limit) {
        this.credit_limit = credit_limit;
    }

    public int getCanPay() {
        return canPay;
    }

    public void setCanPay(int canPay) {
        this.canPay = canPay;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    @NonNull
    @Override
    public String toString() {
        return acc_number + ", Saldo: " + String.format("%.2f", amount) + "€";
    }
}
