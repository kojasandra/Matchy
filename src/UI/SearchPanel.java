package UI;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SearchPanel extends JPanel {

    private final JComboBox<String> cbSkill = new JComboBox<>(new String[]{
            "", "Java", "Python", "UI/Design", "JavaScript", "SQL"
    });

    private final UserTableModel tableModel = new UserTableModel();
    private final JTable table = new JTable(tableModel);

    public SearchPanel(MainFrame frame) {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Search Users", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        JPanel filters = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filters.add(new JLabel("Skill:"));
        filters.add(cbSkill);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> refresh());

        JButton btnAll = new JButton("Show All");
        btnAll.addActionListener(e -> loadAll());

        JButton btnHome = new JButton("Home");
        btnHome.addActionListener(e -> frame.showCard(MainFrame.CARD_REG));

        filters.add(btnSearch);
        filters.add(btnAll);
        filters.add(btnHome);

        add(filters, BorderLayout.PAGE_START);

        table.setFillsViewportHeight(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadAll();
    }

    private void loadAll() {
        try {
            List<User> users = new UserDAO().getAllUsers();
            tableModel.setData(users);
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void refresh() {
        String skill = (String) cbSkill.getSelectedItem();

        try {
            List<User> users = new UserDAO().search(skill == null ? "" : skill);
            tableModel.setData(users);
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void showError(Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
                this,
                "DB error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
