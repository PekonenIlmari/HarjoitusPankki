package com.example.harjoitustyo;

import java.io.Serializable;

public class Card implements Serializable {
    private String card_holder, acc_num, card_num, type;
    private int credit_limit;
    private int payment_limit;

    public Card(String card_holder, String acc_num, String card_num, String type) {
        this.card_holder = card_holder;
        this.acc_num = acc_num;
        this.card_num = card_num;
        this.type = type;
        if (type.equals("Debit")) {
            this.credit_limit = 0;
        } else if (type.equals("Credit")) {
            this.credit_limit = 500;
        }
        this.payment_limit = 500;
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
}
