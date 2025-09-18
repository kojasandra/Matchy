package util;

public class Validator {

    public static boolean isEmail(String email) {
        if (email == null) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isStrongPassword(String password) {
        if (password == null) return false;
        return password.matches("^(?=.*[A-Z])(?=.*\\d).{6,}$");
    }
}
