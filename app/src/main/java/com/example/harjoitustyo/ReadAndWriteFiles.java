package com.example.harjoitustyo;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;

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
    private static Context context;
    private Bank bank = Bank.getInstance();
    private ArrayList<User> users = new ArrayList<>();

    private static ReadAndWriteFiles rawf = new ReadAndWriteFiles();

    public static ReadAndWriteFiles getInstance(Context c) {
        context = c;
        return rawf;
    }

    public ReadAndWriteFiles() {
    }

    public void readUsers() {
        try {
            FileInputStream fis = new FileInputStream(new File(context.getFilesDir(), "users.ser"));
            ObjectInputStream ois = new ObjectInputStream(fis);

            users = (ArrayList) ois.readObject();

            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (users.size() > 0) {
            bank.setUserList(users);
        }

        if (bank.getUserList().size() > 0) {
            Toast.makeText(context, "LÖYTYY KÄYTTÄJIÄ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "EI LÖYDY KÄYTTÄJIÄ", Toast.LENGTH_SHORT).show();
        }

    }

    public void writeUsers() {
        users = bank.getUserList();

        try {
            context.deleteFile("users.ser");
            FileOutputStream fos = new FileOutputStream(new File(context.getFilesDir(), "users.ser"));
            ObjectOutput out = new ObjectOutputStream(fos);
            out.writeObject(users);
            fos.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("####Käyttäjät tallennettu####");

    }


}
