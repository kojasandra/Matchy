package UI;

import model.Skill;
import model.User;

import javax.swing.*;
import java.awt.*;

public class ProfileDetailsDialog extends JDialog {

    public ProfileDetailsDialog(JFrame parent, User user) {
        super(parent, "User Profile", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10,10));

        JTextArea details = new JTextArea();
        details.setEditable(false);

        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(user.getName()).append("\n");
        sb.append("Email: ").append(user.getEmail()).append("\n");
        sb.append("Location: ").append(user.getLocation()).append("\n");
        sb.append("Bio: ").append(user.getBio()).append("\n");
        sb.append("\nSkills:\n");
        for (Skill s : user.getSkills()) {
            sb.append(" - ").append(s.getName()).append("\n");
        }

        details.setText(sb.toString());
        add(new JScrollPane(details), BorderLayout.CENTER);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> dispose());
        add(btnClose, BorderLayout.SOUTH);
    }
}
