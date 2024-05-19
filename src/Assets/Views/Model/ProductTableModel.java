package Assets.Views.Model;


import javax.swing.table.AbstractTableModel;
import Assets.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {

    private final List<Product> products;
    private final String[] columnNames = {"ID", "Name", "Brand", "Quantity", "Price"};

    public ProductTableModel(List<Product> products) {
        this.products = products;
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> product.getId();
            case 1 -> product.getName();
            case 2 -> product.getBrand();
            case 3 -> product.getQuantity();
            case 4 -> product.getPrice();
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
            case 3 -> Integer.class;
            case 4 -> Float.class;
            default -> String.class;
        };
    }
}
