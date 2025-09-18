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

    private final JComboBox<String> cbGoal = new JComboBox<>(new String[]{"project", "internship", "networking"});
    private final JRadioButton rbTeam = new JRadioButton("Team-oriented");
    private final JRadioButton rbInd = new JRadioButton("Independent");
    private final JRadioButton rbFlex = new JRadioButton("Flexible");

    private final JSlider sAvail = new JSlider(0, 40, 8);
    private final JLabel lblAvail = new JLabel("8 h/week");

    private final JList<Skill> skillsList;

    public ProfilePanel(MainFrame frame, User workingUser) {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Profile", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        int r = 0;

        gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Name:"), gc);
        tfName.setText(workingUser.getName() != null ? workingUser.getName() : "");
        gc.gridx = 1; form.add(tfName, gc);

        r++; gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Location:"), gc);
        tfLocation.setText(workingUser.getLocation() != null ? workingUser.getLocation() : "");
        gc.gridx = 1; form.add(tfLocation, gc);

        r++; gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Bio:"), gc);
        gc.gridx = 1;
        taBio.setLineWrap(true); taBio.setWrapStyleWord(true);
        taBio.setText(workingUser.getBio() != null ? workingUser.getBio() : "");
        form.add(new JScrollPane(taBio), gc);

        r++; gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Skills:"), gc);
        DefaultListModel<Skill> model = new DefaultListModel<>();
        try {
            List<Skill> allSkills = new SkillDAO().getAllSkills();
            for (Skill s : allSkills) {
                model.addElement(s);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        skillsList = new JList<>(model);
        skillsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        skillsList.setVisibleRowCount(5);
        if (workingUser.getSkills() != null) {
            List<Integer> ids = new ArrayList<>();
            for (int i = 0; i < model.size(); i++) {
                for (Skill us : workingUser.getSkills()) {
                    if (model.get(i).getId() == us.getId()) ids.add(i);
                }
            }
            int[] sel = ids.stream().mapToInt(i -> i).toArray();
            skillsList.setSelectedIndices(sel);
        }
        gc.gridx = 1; form.add(new JScrollPane(skillsList), gc);

        r++; gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Goal:"), gc);
        cbGoal.setSelectedItem(workingUser.getGoal() != null ? workingUser.getGoal() : "project");
        gc.gridx = 1; form.add(cbGoal, gc);

        r++; gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Working Style:"), gc);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbTeam); bg.add(rbInd); bg.add(rbFlex);
        if ("team-oriented".equals(workingUser.getWorkingStyle())) rbTeam.setSelected(true);
        else if ("independent".equals(workingUser.getWorkingStyle())) rbInd.setSelected(true);
        else rbFlex.setSelected(true);
        JPanel stylePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        stylePanel.add(rbTeam); stylePanel.add(rbInd); stylePanel.add(rbFlex);
        gc.gridx = 1; form.add(stylePanel, gc);

        r++; gc.gridx = 0; gc.gridy = r; form.add(new JLabel("Availability (h/week):"), gc);
        Integer avail = workingUser.getAvailability();
        sAvail.setMajorTickSpacing(10);
        sAvail.setMinorTickSpacing(2);
        sAvail.setPaintTicks(true);
        sAvail.setValue(avail != null && avail > 0 ? avail : 8);
        lblAvail.setText(sAvail.getValue() + " h/week");
        sAvail.addChangeListener(e -> lblAvail.setText(sAvail.getValue() + " h/week"));
        JPanel availPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        availPanel.add(sAvail);
        availPanel.add(lblAvail);
        gc.gridx = 1; form.add(availPanel, gc);

        add(form, BorderLayout.CENTER);

        JPanel nav = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnBack = new JButton("← Back");
        btnBack.addActionListener(e -> frame.showCard(MainFrame.CARD_REG));

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> {
            workingUser.setName(tfName.getText().trim());
            workingUser.setLocation(tfLocation.getText().trim());
            workingUser.setBio(taBio.getText().trim());
            workingUser.setGoal((String) cbGoal.getSelectedItem());
            if (rbTeam.isSelected()) workingUser.setWorkingStyle("team-oriented");
            else if (rbInd.isSelected()) workingUser.setWorkingStyle("independent");
            else workingUser.setWorkingStyle("flexible");
            workingUser.setAvailability(sAvail.getValue());
            List<Skill> selectedSkills = skillsList.getSelectedValuesList();
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
