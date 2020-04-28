package com.example.harjoitustyo;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class Bank { // class where all the users are stored
    private static Bank bank = new Bank();

    public static Bank getInstance() {
        return bank;
    }

    private User user;
    Random r = new Random();

    public Bank() {
        //addUser("Pankinjohtaja", "admin", "password", "Pankkikuja 1, 00100 Helsinki", "0100100");
    }

    private ArrayList<User> userList = new ArrayList<>();

    public void addUser(String name, String userName, String password, String address, String phone, byte[] salt) {
        user = new User(name, userName, password, address, phone, salt);
        userList.add(user);
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public String generateRandomLogInCode() {
        int tempLoginCode = r.nextInt(899999) + 100000;
        String loginCode = Integer.toString(tempLoginCode);
        return loginCode;
    }

    public String generateAccountNumber(String type) {
        String tempAcc_num = "";

        if (type.equals("Normaali")) {
            tempAcc_num = "RP01";
        } else if (type.equals("Säästö")) {
            tempAcc_num = "RP27";
        }
        for (int i = 0; i < 3; i++) {
            int temp_four_num = r.nextInt(8999) + 1000; //Gives number between 1000 and 9999
            tempAcc_num = tempAcc_num + " " + temp_four_num;
        }
        int temp_two_num = r.nextInt(89) + 10; //Gives number between 10 and 99
        tempAcc_num = tempAcc_num + " " + temp_two_num;

        return tempAcc_num;
    }

    public String generateCardNumber() {
        String tempCard_num = "4523";

        for (int i = 0; i < 3; i++) {
            int temp_four_num = r.nextInt(8999) + 1000; //Gives number between 1000 and 9999
            tempCard_num = tempCard_num + " " + temp_four_num;
        }
        return tempCard_num;
    }
}
