package org.utils;

import java.io.Console;
import java.util.Scanner;

public class SecurePasswordReader {

    public static String readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            char[] password = console.readPassword(prompt);
            return new String(password);
        } else {
            System.out.print(prompt + " (visible - ejecuta desde terminal para ocultarla): ");
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        }
    }

    public static boolean isPasswordSecure(String password) {
        if (password == null || password.length() < 6) return false;

        int uppercase = 0, lowercase = 0, special = 0;
        String specialChars = "!@#$%^&*()_+-=[]{}|;':\",./<>?";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))          uppercase++;
            else if (Character.isLowerCase(c))     lowercase++;
            else if (specialChars.indexOf(c) >= 0) special++;
        }

        return uppercase >= 2 && lowercase >= 2 && special >= 2;
    }

    public static boolean isEmailValid(String email) {
        if (email == null || email.isBlank()) return false;
        return email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$");
    }
}