package UI;

import model.Skill;
import model.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

    private final String[] cols = {
            "ID", "Name", "Email", "Location", "Skills", "Goal", "Style", "Avail (h/w)"
    };

    private List<User> data = new ArrayList<>();

    public void setData(List<User> users) {
        this.data = users != null ? users : new ArrayList<>();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User u = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> u.getId();
            case 1 -> u.getName();
            case 2 -> u.getEmail();
            case 3 -> u.getLocation();
            case 4 -> String.join(", ",
                    u.getSkills().stream().map(Skill::getName).toList());
            default -> "";
        };
    }
}
