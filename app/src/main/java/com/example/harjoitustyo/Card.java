package com.example.harjoitustyo;

import java.io.Serializable;

public class Card implements Serializable {
    private String card_holder, acc_num, card_num, type;
    private int payment_limit;

    public Card(String card_holder, String acc_num, String card_num, String type) {
        this.card_holder = card_holder;
        this.acc_num = acc_num;
        this.card_num = card_num;
        this.type = type;
        this.payment_limit = 400; //Payment limit 400 on default
    }

    public String getAcc_num() {
        return acc_num;
    }

    public String getType() {
        return type;
    }

    public String getCard_num() {
        return card_num;
    }

    public String getCard_holder() {
        return card_holder;
    }

    public int getPayment_limit() {
        return payment_limit;
    }
}
