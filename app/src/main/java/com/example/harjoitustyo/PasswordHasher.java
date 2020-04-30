package com.example.harjoitustyo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHasher { //Class is used for hashing the password using sha-256 algorithm

    private static PasswordHasher ph = new PasswordHasher();

    public static PasswordHasher getInstance() {
        return ph;
    }

    public PasswordHasher() {

    }

    public String getSecurePassword(String password, byte[] salt) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt); //Adding salt to input
            byte[] bytes = md.digest(password.getBytes()); //Generate hashed password
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) { //Converting byte into a string
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; //Generate random salt
        random.nextBytes(salt);
        return salt;
    }
}
