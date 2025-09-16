package UI;

import model.User;

import javax.swing.*;
import java.awt.*;

public class RegistrationPanel extends JPanel {
    private final JTextField tfName = new JTextField(20);
    private final JTextField tfEmail = new JTextField(20);
    private final JPasswordField pfPassword = new JPasswordField(20);

    public RegistrationPanel(MainFrame frame, User workingUser) {
        setLayout(new BorderLayout(10,10));

        JLabel title = new JLabel("Registration", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.anchor = GridBagConstraints.LINE_START;

        int r=0;
        gc.gridx=0; gc.gridy=r; form.add(new JLabel("Name:"), gc);
        gc.gridx=1; form.add(tfName, gc);

        r++; gc.gridx=0; gc.gridy=r; form.add(new JLabel("Email:"), gc);
        gc.gridx=1; form.add(tfEmail, gc);

        r++; gc.gridx=0; gc.gridy=r; form.add(new JLabel("Password:"), gc);
        gc.gridx=1; form.add(pfPassword, gc);

        add(form, BorderLayout.CENTER);

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnNext = new JButton("Next →");
        btnNext.addActionListener(e -> {
            // store partial data
            workingUser.setName(tfName.getText().trim());
            workingUser.setEmail(tfEmail.getText().trim());
            workingUser.setPassword(new String(pfPassword.getPassword()));

            frame.showCard(MainFrame.CARD_PROFILE);
        });
        nav.add(btnNext);
        add(nav, BorderLayout.SOUTH);
    }
}
