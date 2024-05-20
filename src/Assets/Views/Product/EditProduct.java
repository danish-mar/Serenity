package Assets.Views.Product;

import Assets.Product;
import Assets.ProductManager;
import Assets.Utils.DisplayUtils;
import Assets.Views.Model.ProductTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProduct extends JFrame {
    private JPanel mainPanel;
    private JTextField productName;
    private JTextField productBrand;
    private JSpinner productQuantity;
    private JTextField productPrice;
    private JButton saveButton;
    private JButton cancelButton;

    public EditProduct(ProductManager productManager, Product oldProduct, JTable productsTable, int result) {
        mainPanel.setName("Edit - " + oldProduct.getName());
        setContentPane(mainPanel);
        setTitle("Edit Product - " + oldProduct.getName());
        setLocationRelativeTo(null);
        setBounds(DisplayUtils.getCenterOfScreen(400,300));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Set old values
        productName.setText(oldProduct.getName());
        productBrand.setText(oldProduct.getBrand());
        productPrice.setText(Float.toString(oldProduct.getPrice()));
        productQuantity.setValue(oldProduct.getQuantity());

        // Save button action
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = productName.getText();
                String brand = productBrand.getText();
                String priceText = productPrice.getText();
                Object quantityValue = productQuantity.getValue();

                // Validate input
                if (name == null || name.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(EditProduct.this, "Error: Invalid Product name");
                    return;
                }

                if (brand == null || brand.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(EditProduct.this, "Error: Invalid Product brand");
                    return;
                }

                float price;
                try {
                    price = Float.parseFloat(priceText);
                    if (price <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(EditProduct.this, "Error: Invalid product price");
                    return;
                }

                int quantity;
                try {
                    quantity = Integer.parseInt(quantityValue.toString());
                    if (quantity <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(EditProduct.this, "Error: Invalid product quantity");
                    return;
                }

//                // Update the old product
//                oldProduct.setName(name);
//                oldProduct.setBrand(brand);
//                oldProduct.setPrice(price);
//                oldProduct.setQuantity(quantity);

                // create a nwe product:
                Product newProduct = new Product(oldProduct.getId(),name,brand,quantity,price);

                // Notify the product manager
                int result = productManager.updateProduct(oldProduct.getId(),newProduct,oldProduct);
                if(result == 200){
                    JOptionPane.showMessageDialog(null, "Product updated successfully.");
                }else{
                    JOptionPane.showMessageDialog(null, "Internal Error Occoured.");
                }

                // Close the window
                dispose();
                if (productsTable.getModel() instanceof ProductTableModel) {
                    ((ProductTableModel) productsTable.getModel()).fireTableDataChanged();
                }
            }
        });

        // Cancel button action
        cancelButton.addActionListener(e -> dispose());
    }
}
