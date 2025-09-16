package UI;

import dao.SkillDAO;
import dao.UserDAO;
import model.Skill;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfilePanel extends JPanel {

    private final JTextField tfName = new JTextField(20);
    private final JTextField tfLocation = new JTextField(20);
    private final JTextArea taBio = new JTextArea(4, 20);
    private final List<JCheckBox> skillChecks = new ArrayList<>();

    public ProfilePanel(MainFrame frame, User workingUser) {
        setLayout(new BorderLayout(10,10));

        JLabel title = new JLabel("Profile", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        int r=0;

        gc.gridx=0; gc.gridy=r; form.add(new JLabel("Name:"), gc);
        tfName.setText(workingUser.getName() != null ? workingUser.getName() : "");
        gc.gridx=1; form.add(tfName, gc);

        r++; gc.gridx=0; gc.gridy=r; form.add(new JLabel("Location:"), gc);
        gc.gridx=1; form.add(tfLocation, gc);

        r++; gc.gridx=0; gc.gridy=r; form.add(new JLabel("Bio:"), gc);
        gc.gridx=1;
        taBio.setLineWrap(true); taBio.setWrapStyleWord(true);
        form.add(new JScrollPane(taBio), gc);

        r++; gc.gridx=0; gc.gridy=r; form.add(new JLabel("Skills:"), gc);
        JPanel skillsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        try {
            List<Skill> allSkills = new SkillDAO().getAllSkills();
            for (Skill s : allSkills) {
                JCheckBox cb = new JCheckBox(s.getName());
                cb.setActionCommand(String.valueOf(s.getId()));
                skillChecks.add(cb);
                skillsPanel.add(cb);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            skillsPanel.add(new JLabel("DB error loading skills"));
        }

        gc.gridx=1; form.add(skillsPanel, gc);
        add(form, BorderLayout.CENTER);

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBack = new JButton("← Back");
        btnBack.addActionListener(e -> frame.showCard(MainFrame.CARD_REG));

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> {
            workingUser.setName(tfName.getText().trim());
            workingUser.setLocation(tfLocation.getText().trim());
            workingUser.setBio(taBio.getText().trim());

            List<Skill> selectedSkills = new ArrayList<>();
            for (JCheckBox cb : skillChecks) {
                if (cb.isSelected()) {
                    int skillId = Integer.parseInt(cb.getActionCommand());
                    selectedSkills.add(new Skill(skillId, cb.getText(), ""));
                }
            }
            workingUser.setSkills(selectedSkills);

            try {
                new UserDAO().insertUser(workingUser);
                JOptionPane.showMessageDialog(this, "Saved to database.", "Matchy", JOptionPane.INFORMATION_MESSAGE);
                frame.showCard(MainFrame.CARD_SEARCH);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "DB error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnGoSearch = new JButton("Skip → Search");
        btnGoSearch.addActionListener(e -> frame.showCard(MainFrame.CARD_SEARCH));

        nav.add(btnBack);
        nav.add(btnGoSearch);
        nav.add(btnSave);
        add(nav, BorderLayout.SOUTH);
    }
}
