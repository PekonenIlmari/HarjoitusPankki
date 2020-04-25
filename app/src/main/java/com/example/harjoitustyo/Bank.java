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
    public static Bank getInstance() { return bank;}
    private User user;
    Random r = new Random();

    public Bank() {
        addUser("Pekka Korvala", "Pekka", "123", "TIE", "20002");
        addUser("Masi Pallopää", "Masi", "123", "JOE", "22032");
    }

    private ArrayList<User> userList = new ArrayList<>();

    public void addUser(String name, String userName, String password, String address, String phone) {
        user = new User(name, userName, password, address, phone);
        userList.add(user);
    }

    public ArrayList<User> getUserList() {
        return userList;
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
        } else if (type.equals("Luotto")) {
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

    public String generateCardNumber(String type) {
        String tempCard_num = "";

        if (type.equals("Debit")) {
            tempCard_num = "4523";
        } else if (type.equals("Credit")) {
            tempCard_num = "2489";
        }

        for (int i = 0; i < 3; i++) {
            int temp_four_num = r.nextInt(8999) + 1000; //Gives number between 1000 and 9999
            tempCard_num = tempCard_num + " " + temp_four_num;
        }
        return tempCard_num;
    }
}
