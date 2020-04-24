package com.example.harjoitustyo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ReadAndWriteFiles {
    Context context;
    private Bank bank = Bank.getInstance();
    private ArrayList<User> users;

    public ReadAndWriteFiles(Context context) {
        this.context = context;
    }

    /*public void readUsers() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.ser"));

            users = (ArrayList) ois.readObject();

            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Add users to bank
        for (User user : users) {
            System.out.println(user.getName());
        }
    }*/

    public void readUsers() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(context.getFilesDir(), "users.ser")));

            users = (ArrayList) ois.readObject();

            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Add users to bank
        for (User user : users) {
            System.out.println(user.getName());
        }
    }

    /*public void writeUsers() {
        users = bank.getUserList();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.ser"));

            oos.writeObject(users);

            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void writeUsers() {
        users = bank.getUserList();

        try {
            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(new File(context.getFilesDir(), "users.ser")));
            out.writeObject(users);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("####Käyttäjät tallennettu####");

    }


}
