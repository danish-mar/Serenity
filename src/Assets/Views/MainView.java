package Assets.Views;

import Assets.ProductManager;
import Assets.Utils.DisplayUtils;
import Assets.Views.Model.ProductTableModel;

import javax.swing.*;
import java.awt.*;


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

        // Create a menu item for the product menu
        JMenuItem addProductMenuItem = new JMenuItem("Add Product");

        // Add action listener to the menu item
        addProductMenuItem.addActionListener(actionEvent -> {
            // Open the AddProduct frame when the menu item is clicked
            new AddProduct(productManager, productsTable,totalProductTextField).setVisible(true);
        });

        // Create a menu for the product operations
        JMenu productMenu = new JMenu("Product");
        productMenu.add(addProductMenuItem);

        // Add the product menu to the menu bar
        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.add(productMenu);

        // Set the menu bar for the frame
        setJMenuBar(mainMenuBar);

        // Create a ProductTableModel with the inventory from the ProductManager
        ProductTableModel productTableModel = new ProductTableModel(productManager.getInventory());
        totalProductTextField.setText("Total Product : " + productManager.getProductCount());

        // Create a JTable with the ProductTableModel
        productsTable = new JTable(productTableModel);

        // Set up the table appearance and behavior
        productsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        productsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productsTable.setColumnSelectionAllowed(false);
        productsTable.setFillsViewportHeight(true);

        // Add the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(productsTable);

        // Add the JScrollPane to the mainPanel (or directly to the frame)
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
