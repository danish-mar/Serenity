package Assets.Views;

import Assets.Product;
import Assets.ProductManager;
import Assets.Utils.DisplayUtils;
import Assets.Utils.IconType;
import Assets.Utils.IconUtil;
import Assets.Utils.TableUtils;
import Assets.Views.Model.ProductTableModel;
import Assets.Views.Product.AddProduct;
import Assets.Views.Product.EditProduct;
import com.mysql.cj.xdevapi.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainView extends JFrame {
    private JPanel mainPanel;
    private JTable productsTable;
    private JLabel totalProductTextField;

    public MainView(ProductManager productManager) {
        setTitle("Serenity - Home");
        Image icon = Toolkit.getDefaultToolkit().getImage("src/Assets/logo.png");
        setIconImage(icon);
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(DisplayUtils.getCenterOfScreen(400,800));

        // setting purple theme
        productsTable.setBackground(Color.black);
        productsTable.setGridColor(Color.getHSBColor(122,55,155));
        productsTable.setForeground(Color.white);




        JMenu productMenu = new JMenu("Product \uD83D\uDCE6");
        JMenu billingMenu = new JMenu("Bill");
        JMenu sellerMenu = new JMenu("Seller");



        // Product menu items
        JMenuItem addProductMenuItem = new JMenuItem("➕ Add Product");
        JMenuItem deleteProductMenuItem = new JMenuItem("❌ Delete Product");
        JMenuItem editProductMenuItem = new JMenuItem("\uD83D\uDD04 Edit Product");


        addProductMenuItem.addActionListener(actionEvent -> {
            new AddProduct(productManager, productsTable,totalProductTextField).setVisible(true);
        });



        // billing menu items
        JMenuItem newBillMenuItem = new JMenuItem("New Bill");
        JMenuItem billingHistoryMenuItem = new JMenuItem("Billing History");

        // Seller Menu item
        JMenuItem addSellerMenuItem = new JMenuItem("Add Seller");
        JMenuItem viewSellersMenuItem = new JMenuItem("View Sellers");
        JMenuItem removeSellerMenuItem = new JMenuItem("Remove Seller");



        productMenu.add(addProductMenuItem);
        productMenu.add(deleteProductMenuItem);
        productMenu.add(editProductMenuItem);

        billingMenu.add(newBillMenuItem);
        billingMenu.add(billingHistoryMenuItem);

        sellerMenu.add(addSellerMenuItem);
        sellerMenu.add(viewSellersMenuItem);
        sellerMenu.add(removeSellerMenuItem);


        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.add(productMenu);
        mainMenuBar.add(billingMenu);
        mainMenuBar.add(sellerMenu);


        setJMenuBar(mainMenuBar);

        ProductTableModel productTableModel = new ProductTableModel(productManager.getInventory());
        totalProductTextField.setText("Total Product : " + productManager.getProductCount());

        productsTable = new JTable(productTableModel);
        TableUtils.setColumnWidth(productsTable,"ID",50);





        productsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productsTable.setColumnSelectionAllowed(false);
        productsTable.setFillsViewportHeight(true);


        productsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = productsTable.rowAtPoint(e.getPoint());
                    productsTable.setRowSelectionInterval(row, row);

                    JPopupMenu popupMenu = createPopupMenu(productManager, productTableModel, row, productsTable);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(productsTable);

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);


    }

    public MainView(Assets.Database.ProductManager productManager) {
        setTitle("Serenity - Home");
        Image icon = Toolkit.getDefaultToolkit().getImage("src/Assets/logo.png");
        setIconImage(icon);
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(DisplayUtils.getCenterOfScreen(400,800));

        // setting purple theme
        productsTable.setBackground(Color.black);
        productsTable.setGridColor(Color.getHSBColor(122,55,155));
        productsTable.setForeground(Color.white);



        JMenu productMenu = new JMenu("Product");
        productMenu.setIcon(IconUtil.getIcon(IconType.PACKAGE));
        JMenu billingMenu = new JMenu("Bill");
        JMenu sellerMenu = new JMenu("Seller");
        JMenu userMenu = new JMenu("User");
        userMenu.setIcon(IconUtil.getIcon(IconType.USER));


        // Product menu items
        JMenuItem addProductMenuItem = new JMenuItem("Add Product",IconUtil.getIcon(IconType.ADD));
        JMenuItem deleteProductMenuItem = new JMenuItem("Delete Product",IconUtil.getIcon(IconType.DELETE));
        JMenuItem editProductMenuItem = new JMenuItem("Edit Product",IconUtil.getIcon(IconType.EDIT));



        addProductMenuItem.addActionListener(actionEvent -> {
            new AddProduct(productManager, productsTable,totalProductTextField).setVisible(true);
        });

        // user menu
        JMenuItem logOutMenuItem = new JMenuItem("Logout",IconUtil.getIcon(IconType.UNLOCK));

        // billing menu items
        JMenuItem newBillMenuItem = new JMenuItem("New Bill");
        JMenuItem billingHistoryMenuItem = new JMenuItem("Billing History");

        // Seller Menu item
        JMenuItem addSellerMenuItem = new JMenuItem("Add Seller");
        JMenuItem viewSellersMenuItem = new JMenuItem("View Sellers");
        JMenuItem removeSellerMenuItem = new JMenuItem("Remove Seller");



        productMenu.add(addProductMenuItem);
        productMenu.add(deleteProductMenuItem);
        productMenu.add(editProductMenuItem);

        billingMenu.add(newBillMenuItem);
        billingMenu.add(billingHistoryMenuItem);

        sellerMenu.add(addSellerMenuItem);
        sellerMenu.add(viewSellersMenuItem);
        sellerMenu.add(removeSellerMenuItem);

        userMenu.add(logOutMenuItem);


        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.add(productMenu);
        mainMenuBar.add(billingMenu);
        mainMenuBar.add(sellerMenu);
        mainMenuBar.add(userMenu);


        setJMenuBar(mainMenuBar);

        ProductTableModel productTableModel = new ProductTableModel(productManager.getInventory());
        totalProductTextField.setText("Total Product : " + productManager.getProductCount());

        productsTable = new JTable(productTableModel);





        productsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productsTable.setColumnSelectionAllowed(false);
        productsTable.setFillsViewportHeight(true);


        productsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = productsTable.rowAtPoint(e.getPoint());
                    productsTable.setRowSelectionInterval(row, row);

                    JPopupMenu popupMenu = createPopupMenu(productManager, productTableModel, row, productsTable);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(productsTable);

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);


    }


    private JPopupMenu createPopupMenu(ProductManager productManager, ProductTableModel productTableModel, int row, JTable productTable) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete",IconUtil.getIcon(IconType.DELETE));
        JMenuItem editItem = new JMenuItem("Edit",IconUtil.getIcon(IconType.EDIT));

        deleteItem.addActionListener(e -> {
            if (row >= 0 && row < productTableModel.getRowCount()) {
                Product productToBeDeleted = productTableModel.getProductAt(row);  // Ensure this method exists in ProductTableModel
                int result = productManager.deleteProduct(productToBeDeleted);

                if (result == 200) {
                    productTableModel.removeRow(row);  // Ensure this method exists in ProductTableModel
                    totalProductTextField.setText("Total Product : " + productManager.getProductCount());

                    if (productTable.getModel() instanceof ProductTableModel) {
                        ((ProductTableModel) productTable.getModel()).fireTableDataChanged();
                    }
                    JOptionPane.showMessageDialog(null, "Product deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error deleting product.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid row selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editItem.addActionListener(e -> {
            if (row >= 0 && row < productTableModel.getRowCount()) {
                Product productToBeEdited = productTableModel.getProductAt(row);
                EditProduct editProductFrame = new EditProduct(productManager,productToBeEdited,productsTable,0);
                editProductFrame.setVisible(true);
            }
        });

        popupMenu.add(deleteItem);
        popupMenu.add(editItem);
        return popupMenu;
    }

    private JPopupMenu createPopupMenu(Assets.Database.ProductManager productManager, ProductTableModel productTableModel, int row, JTable productTable) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem deleteItem = new JMenuItem("Delete",IconUtil.getIcon(IconType.DELETE));
        JMenuItem editItem = new JMenuItem("Edit",IconUtil.getIcon(IconType.EDIT));

        deleteItem.addActionListener(e -> {
            if (row >= 0 && row < productTableModel.getRowCount()) {
                Product productToBeDeleted = productTableModel.getProductAt(row);  // Ensure this method exists in ProductTableModel
                int result = productManager.deleteProduct(productToBeDeleted);

                if (result == 200) {
                    productTableModel.removeRow(row);  // Ensure this method exists in ProductTableModel
                    totalProductTextField.setText("Total Product : " + productManager.getProductCount());

                    if (productTable.getModel() instanceof ProductTableModel) {
                        ((ProductTableModel) productTable.getModel()).fireTableDataChanged();
                    }
                    JOptionPane.showMessageDialog(null, "Product deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error deleting product.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid row selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editItem.addActionListener(e -> {
            if (row >= 0 && row < productTableModel.getRowCount()) {
                Product productToBeEdited = productTableModel.getProductAt(row);
                EditProduct editProductFrame = new EditProduct(productManager,productToBeEdited,productsTable,0);
                editProductFrame.setVisible(true);
            }
        });

        popupMenu.add(deleteItem);
        popupMenu.add(editItem);
        return popupMenu;
    }

}
