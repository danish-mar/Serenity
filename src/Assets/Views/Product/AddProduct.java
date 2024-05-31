package Assets.Views.Product;

import Assets.ProductManager;
import Assets.Utils.DisplayUtils;
import Assets.Views.Model.ProductTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProduct extends JFrame {
    private JTextField productName;
    private JTextField productBrand;
    private JTextField productPrice;
    private JButton addButton;
    private JPanel addnewProduct;
    private JSpinner productQuantity;

    public AddProduct(ProductManager productManager, JTable productTable, JLabel totalProductTextField) {
        setBounds(DisplayUtils.getCenterOfScreen(250,400));
        setTitle("Add New Product");
        setContentPane(addnewProduct);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = productName.getText().trim();
                String brand = productBrand.getText().trim();
                int quantity = (int) productQuantity.getValue();
                float price = 0;
                try {
                    price = Float.parseFloat(productPrice.getText().trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(addButton, "Error: Invalid product price");
                }

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(addButton, "Error: The product name cannot be empty");
                    return;
                }
                if (brand.isEmpty()) {
                    JOptionPane.showMessageDialog(addButton, "Error: The product brand cannot be empty");
                    return;
                }
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(addButton, "Error: Invalid product quantity");
                    return;
                }
                if (price <= 0) {
                    JOptionPane.showMessageDialog(addButton, "Error: Invalid product price");
                    return;
                }

                int returnCode = productManager.addNewProduct(name,brand,price,quantity);


                // Refresh the product table
                if (productTable.getModel() instanceof ProductTableModel) {
                    ((ProductTableModel) productTable.getModel()).fireTableDataChanged();
                }
                if(returnCode == 200){
                    JOptionPane.showMessageDialog(addButton, "Product added successfully");
                    totalProductTextField.setText("Total Product : " + String.valueOf(productManager.getProductCount()));
                    dispose();

                } else if (returnCode == 202) {
                    JOptionPane.showMessageDialog(addButton, "Product Already Exists");
                }
            }
        });
    }

    public AddProduct(Assets.Database.ProductManager productManager, JTable productTable, JLabel totalProductTextField) {
        setBounds(DisplayUtils.getCenterOfScreen(250,400));
        setTitle("Add New Product");
        setContentPane(addnewProduct);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = productName.getText().trim();
                String brand = productBrand.getText().trim();
                int quantity = (int) productQuantity.getValue();
                float price = 0;
                try {
                    price = Float.parseFloat(productPrice.getText().trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(addButton, "Error: Invalid product price");
                }

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(addButton, "Error: The product name cannot be empty");
                    return;
                }
                if (brand.isEmpty()) {
                    JOptionPane.showMessageDialog(addButton, "Error: The product brand cannot be empty");
                    return;
                }
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(addButton, "Error: Invalid product quantity");
                    return;
                }
                if (price <= 0) {
                    JOptionPane.showMessageDialog(addButton, "Error: Invalid product price");
                    return;
                }

                int returnCode = productManager.addNewProduct(name,brand,price,quantity);


                // Refresh the product table
                if (productTable.getModel() instanceof ProductTableModel) {
                    ((ProductTableModel) productTable.getModel()).fireTableDataChanged();
                }
                if(returnCode == 200){
                    JOptionPane.showMessageDialog(addButton, "Product added successfully");
                    totalProductTextField.setText("Total Product : " + String.valueOf(productManager.getProductCount()));
                    dispose();

                } else if (returnCode == 202) {
                    JOptionPane.showMessageDialog(addButton, "Product Already Exists");
                }
            }
        });
    }
}
