package UI;

import dao.UserDAO;
import model.User;
import util.Session;
import util.Validator;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    private final JTextField tfEmail = new JTextField(20);
    private final JPasswordField tfPassword = new JPasswordField(20);

    public LoginFrame() {
        setTitle("Matchy - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        JLabel lblTitle = new JLabel("Login", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 20f));
        add(lblTitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.anchor = GridBagConstraints.WEST;

        gc.gridx = 0; gc.gridy = 0;
        form.add(new JLabel("Email:"), gc);
        gc.gridx = 1;
        form.add(tfEmail, gc);

        gc.gridx = 0; gc.gridy = 1;
        form.add(new JLabel("Password:"), gc);
        gc.gridx = 1;
        form.add(tfPassword, gc);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> doLogin());

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(e -> {
            dispose(); // zatvori login
            MainFrame frame = new MainFrame();
            frame.showCard(MainFrame.CARD_REG);
            frame.setVisible(true);
        });

        JPanel buttons = new JPanel();
        buttons.add(btnLogin);
        buttons.add(btnRegister);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void doLogin() {
        String email = tfEmail.getText().trim();
        String password = new String(tfPassword.getPassword());

        if (!Validator.isEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return;
        }

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password cannot be empty.");
            return;
        }

        try {
            User user = new UserDAO().login(email, password);
            if (user != null) {
                Session.setCurrentUser(user);
                JOptionPane.showMessageDialog(this, "Welcome " + user.getName() + "!");
                dispose();
                new MainFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Wrong email or password.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB error: " + ex.getMessage());
        }
    }
}
