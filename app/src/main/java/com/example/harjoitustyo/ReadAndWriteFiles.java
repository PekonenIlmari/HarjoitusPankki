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
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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

    public void readUsers() { //Method for reading user objects from file
        try {
            FileInputStream fis = new FileInputStream(new File(context.getFilesDir(), "users.ser"));
            ObjectInputStream ois = new ObjectInputStream(fis);

            users = (ArrayList) ois.readObject(); // Arraylsit full of User objects

            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (users.size() > 0) {
            bank.setUserList(users); //Replacing the userList in bank with new userList
        }

        if (bank.getUserList().size() > 0) {
            System.out.println("LÖYTYY KÄYTTÄJIÄ");
        } else {
            System.out.println("EI LÖYDY KÄYTTÄJIÄ");
        }

    }

    public void writeUsers() { //Method for saving user objects into file
        users = bank.getUserList();

        try {
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

    public void writeLoginLog(String user) { //MEthod for login Log
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date date = new Date();

        String tempLog = formatter.format(date) + "," + user + "\n";

        try {
            OutputStream os = new FileOutputStream(new File(context.getFilesDir(), "bankLoginLog.csv"), true);
            os.write(tempLog.getBytes(), 0,tempLog.length());
            os.close();
            System.out.println("LOKI KIRJOITETTU");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
