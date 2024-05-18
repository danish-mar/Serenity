package Views;

import Assets.ProductManager;

import javax.swing.*;

public class AddProduct extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton addButton;
    private JPanel addnewProduct;

    public AddProduct(ProductManager productManager){
        setSize(400,400);
        setTitle("Add New Product");
        setContentPane(addnewProduct);
    }

}
