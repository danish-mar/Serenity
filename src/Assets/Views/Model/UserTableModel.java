package Assets.Views.Model;

import javax.swing.table.AbstractTableModel;
import Assets.Database.User;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

    private final List<User> users;
    private final String[] columnNames = {"ID", "Username", "Email", "First Name", "Last Name", "Created At", "Last Login", "Admin", "Create", "View", "Update", "Delete"};

    public UserTableModel(List<User> users) {
        this.users = users;
    }

    public void sortUsersById() {
        users.sort(Comparator.comparingInt(User::getId));
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> user.getId();
            case 1 -> user.getUsername();
            case 2 -> user.getEmail();
            case 3 -> user.getFirstName();
            case 4 -> user.getLastName();
            case 5 -> user.getCreatedAt();
            case 6 -> user.getLastLogin();
            case 7 -> user.isAdmin();
            case 8 -> user.isCanCreateProducts();
            case 9 -> user.isCanViewProdcuts();
            case 10 -> user.isCanUpdateProdcuts();
            case 11 -> user.isCanDeleteProducts();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Integer.class;
            case 5, 6 -> java.util.Date.class;
            case 7, 8, 9, 10, 11 -> Boolean.class;
            default -> String.class;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 7 && columnIndex <= 11; // Permissions columns are editable
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex >= 7 && columnIndex <= 11) {
            User user = users.get(rowIndex);
            boolean value = (boolean) aValue;
            switch (columnIndex) {
                case 7 -> user.setAdmin(value);
                case 8 -> user.setCanCreateProducts(value);
                case 9 -> user.setCanViewProdcuts(value);
                case 10 -> user.setCanUpdateProdcuts(value);
                case 11 -> user.setCanDeleteProducts(value);
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    public User getUserAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < users.size()) {
            return users.get(rowIndex);
        } else {
            return null;  // or throw an exception
        }
    }

    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < users.size()) {
            users.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        fireTableDataChanged();
    }
}
