package Assets.Views.UserManager;

import Assets.Database.UserManager;
import Assets.Utils.DisplayUtils;
import Assets.Utils.FrameBlurUtil;
import Assets.Views.MainView;
import Assets.Views.Model.UserTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserManagerView extends JFrame{
    private JPanel mainPanel;
    private JTable usersTable;

    public UserManagerView(UserManager userManager){

        setTitle("Serenity - Users");
        Image icon = Toolkit.getDefaultToolkit().getImage("src/Assets/logo.png");
        setIconImage(icon);
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(DisplayUtils.getCenterOfScreen(400,800));

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

        UserTableModel userTableModel = new UserTableModel(userManager.getUsers());

        usersTable = new JTable(userTableModel);

        usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersTable.setColumnSelectionAllowed(false);
        usersTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(usersTable);

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);

    }
}
