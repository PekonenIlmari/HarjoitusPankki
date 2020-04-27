package com.example.harjoitustyo;

import java.io.Serializable;

public class Card implements Serializable {
    private String card_holder, acc_num, card_num;
    private int region; //1 = Kotimaa, 2 = Koko maailma
    private int payment_limit;
    private int dead; //0 = card is usable, 1 = card has been closed

    public Card(String card_holder, String acc_num, String card_num, int region) {
        this.card_holder = card_holder;
        this.acc_num = acc_num;
        this.card_num = card_num;
        this.payment_limit = 400; //Payment limit 400 on default
        this.region = region;
        this.dead = 0;
    }

    public String getAcc_num() {
        return acc_num;
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

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public int getDead() {
        return dead;
    }
}
