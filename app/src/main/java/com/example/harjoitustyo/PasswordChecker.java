package com.example.harjoitustyo;

public class PasswordChecker {
    private static PasswordChecker pc = new PasswordChecker();

    public static PasswordChecker getInstance() {
        return pc;
    }


    public PasswordChecker() {
    }

    public int checkPassword(String pass, String passCheck) { //This checks if the password matches the criteria
        char[] passArr = pass.toCharArray();
        int match = 0, len = 0, num = 0, special = 0, upper = 0, lower = 0;

        //check if the passwords match
        if (pass.equals(passCheck)) {
            match = 1;
        }

        // check the password lenght is at least 12 characters long
        if (pass.length() >= 12) {
            len = 1;
        }

        // check if has number
        // check digits from 0 to 9
        for (char c : passArr) {
            if (Character.isDigit(c)) {
                num = 1;
            }
        }

        // for special characters
        if (pass.contains("@") || pass.contains("#")
                || pass.contains("!") || pass.contains("~")
                || pass.contains("$") || pass.contains("%")
                || pass.contains("^") || pass.contains("&")
                || pass.contains("*") || pass.contains("(")
                || pass.contains(")") || pass.contains("-")
                || pass.contains("+") || pass.contains("/")
                || pass.contains(":") || pass.contains(".")
                || pass.contains(", ") || pass.contains("<")
                || pass.contains(">") || pass.contains("?")
                || pass.contains("|")) {
            special = 1;
        }


        // checking capital letters
        for (int i = 65; i <= 90; i++) {

            // type casting
            char c = (char) i;

            String str1 = Character.toString(c);
            if (pass.contains(str1)) {
                upper = 1;
            }
        }

        // checking small letters
        for (int i = 90; i <= 122; i++) {

            // type casting
            char c = (char) i;
            String str1 = Character.toString(c);

            if (pass.contains(str1)) {
                lower = 1;
            }
        }
        if (match == 1 && len == 1 && num == 1 && special == 1 && upper == 1 && lower == 1) {
            return 1;
        } else if (match == 0) {
            return 2;
        } else {
            return 0;
        }
    }
}

