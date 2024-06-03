package Assets.Views.UserManager;

import Assets.Database.Product;
import Assets.Database.UserManager;
import Assets.ProductManager;
import Assets.Utils.DisplayUtils;
import Assets.Utils.FrameBlurUtil;
import Assets.Utils.IconType;
import Assets.Utils.IconUtil;
import Assets.Views.MainView;
import Assets.Views.Model.ProductTableModel;
import Assets.Views.Model.UserTableModel;
import Assets.Views.Product.EditProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import Assets.ProductManager;


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

        //add necessary menu items
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
        JMenuItem deleteItem = new JMenuItem("Delete",IconUtil.getIcon(IconType.DELETE));
        JMenuItem editItem = new JMenuItem("Edit",IconUtil.getIcon(IconType.EDIT));


        popupMenu.add(deleteItem);
        popupMenu.add(editItem);
        return popupMenu;


    }
}
