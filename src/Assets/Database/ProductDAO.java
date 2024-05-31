package Assets.Database;


import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ProductDAO {


    private static final String DB_URL = DatabaseConnection.getConnectionProperties()[0];

    // Insert a new product
    public void insertProduct(Product product) {
        String sql = "INSERT INTO products(id, name, brand, quantity, price) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getBrand());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setFloat(5, product.getPrice());
            pstmt.executeUpdate();
            System.out.println("Product inserted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Update an existing product
    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, brand = ?, quantity = ?, price = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getBrand());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setFloat(4, product.getPrice());
            pstmt.setInt(5, product.getId());
            pstmt.executeUpdate();
            System.out.println("Product updated successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete a product by id
    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Product deleted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Retrieve a product by id
    public Product getProductById(int id) {
        String sql = "SELECT id, name, brand, quantity, price FROM products WHERE id = ?";
        Product product = null;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getInt("quantity"),
                        rs.getFloat("price")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return product;
    }

    // Retrieve all products
    public List<Product> getAllProducts() {
        String sql = "SELECT id, name, brand, quantity, price FROM products";
        List<Product> productList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getInt("quantity"),
                        rs.getFloat("price")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return productList;
    }

    public static void main(String[] args) {
        DatabaseConnection dm = new DatabaseConnection("root","nahida@dendro123");

        ProductDAO productDAO = new ProductDAO();

        Product testProduct = new Product(1,"electro k7","electro",5,150.7f);

        productDAO.insertProduct(testProduct);

    }
}
