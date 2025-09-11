package app;

import dao.UserDAO;
import model.User;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            UserDAO dao = new UserDAO();

            // INSERT test
            User newUser = new User(
                    5,
                    "Sandra",
                    "sandra221222@example.com",
                    "tajna123",
                    "Student RSi",
                    "Čačak",
                    "student",
                    null,
                    null
            );
            dao.insertUser(newUser);
            System.out.println("✅ Novi korisnik dodat!");

            // SELECT ALL test
            ArrayList<User> users = dao.getAllUsers();
            for (User u : users) {
                System.out.println(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
