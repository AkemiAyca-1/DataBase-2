package org.utils;

import java.io.Console;
import java.io.FileInputStream;
import java.util.Scanner;

public class SecurePasswordReader {

    public static String readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            char[] password = console.readPassword(prompt + " ");
            return password != null ? new String(password) : "";
        }

        try {
            Process sttyOff = Runtime.getRuntime()
                    .exec(new String[]{"sh", "-c", "stty -echo < /dev/tty"});
            sttyOff.waitFor();

            System.out.print(prompt + " ");
            Scanner tty = new Scanner(new FileInputStream("/dev/tty"));
            String pwd = tty.nextLine();

            Process sttyOn = Runtime.getRuntime()
                    .exec(new String[]{"sh", "-c", "stty echo < /dev/tty"});
            sttyOn.waitFor();
            System.out.println();

            return pwd;
        } catch (Exception e) {
            System.out.print(prompt + " (visible - ejecuta desde terminal para ocultarla): ");
            return new Scanner(System.in).nextLine();
        }
    }

    public static boolean isPasswordSecure(String password) {
        if (password == null || password.length() < 6) return false;

        int uppercase = 0, lowercase = 0, special = 0, numbers = 0;
        String specialChars = "!@#$%^&*()_+-=[]{}|;':\",./<>?";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))          uppercase++;
            else if (Character.isLowerCase(c))     lowercase++;
            else if (specialChars.indexOf(c) >= 0) special++;
            else if (Character.isDigit(c)) numbers++;
        }

        return uppercase >= 2 && lowercase >= 2 && special >= 2 && numbers >= 2;
    }

    public static boolean isEmailValid(String email) {
        if (email == null || email.isBlank()) return false;
        return email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$");
    }
}