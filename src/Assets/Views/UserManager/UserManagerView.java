package Assets.Views.UserManager;

import Assets.Database.User;
import Assets.Database.UserManager;
import Assets.Utils.DisplayUtils;
import Assets.Utils.FrameBlurUtil;
import Assets.Utils.IconType;
import Assets.Utils.IconUtil;
import Assets.Views.Model.UserTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserManagerView extends JFrame {
    private JPanel mainPanel;
    private JTable usersTable;
    private UserManager userManager;
    private UserTableModel userTableModel;

    public UserManagerView(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Serenity - Users");
        Image icon = Toolkit.getDefaultToolkit().getImage("src/Assets/logo.png");
        setIconImage(icon);
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(DisplayUtils.getCenterOfScreen(400, 800));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                // Window lost focus, apply blur
                FrameBlurUtil.applyBlur(UserManagerView.this);
            }

            @Override
            public void windowActivated(WindowEvent e) {
                // Window gained focus, remove blur
                FrameBlurUtil.removeBlur(UserManagerView.this);
            }
        });

        userTableModel = new UserTableModel(userManager.getUsers());
        usersTable = new JTable(userTableModel);

        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersTable.setColumnSelectionAllowed(false);
        usersTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(usersTable);

        // Add necessary menu items
        JMenuBar mainMenuBar = new JMenuBar();

        usersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = usersTable.rowAtPoint(e.getPoint());
                    usersTable.setRowSelectionInterval(row, row);

                    JPopupMenu popupMenu = createPopupMenu();
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete User", IconUtil.getIcon(IconType.DELETE));

        deleteItem.addActionListener(e -> {
            int selectedRow = usersTable.getSelectedRow();
            if (selectedRow >= 0) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    User userToBeDeleted = userTableModel.getUserAt(selectedRow);
                    boolean success = userManager.deleteUser(userToBeDeleted.getId());

                    if (success) {
                        userTableModel.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(null, "User deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error deleting user.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No user selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        popupMenu.add(deleteItem);
        return popupMenu;
    }
}
