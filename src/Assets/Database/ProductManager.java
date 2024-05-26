package Assets.Database;

import Assets.Product;
import Assets.Security.HashUtil;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.util.ArrayList;

public class ProductManager {

    private ArrayList<Product> inventory;
    private int productCount;
    Connection connection;

    public ProductManager(Connection connection){
        inventory = new ArrayList<>();
        this.connection = connection;
        productCount = 0;
        productCount = getProductCountFromDatabase();
    }

    public ProductManager(){

    }

    public void init(Connection connection){
        inventory = new ArrayList<>();
        this.connection = connection;
        productCount = 0;
        productCount = getProductCountFromDatabase();
    }

    /*
    * @todo :
    *   make the function suitable for the database
    * */

    public int addNewProduct(String name, String brand, float price, int availableQuality){
        String sql = "INSERT INTO Products(id, name, brand, quantity, price) VALUES(?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,productCount);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3,brand);
            preparedStatement.setFloat(4, price);
            preparedStatement.setInt(5, availableQuality);
            preparedStatement.executeUpdate();
            System.out.println("[Serenity]->(Database) : Product " + name + " was added into the inventory");
            inventory.add(new Product(productCount++, name, brand, availableQuality, price));
            updateLocalDatabaseFromDatabase();

            return 200;
        }catch (SQLException e){
            e.printStackTrace();
            return 201;
        }
    }

    public int addNewProduct(Product product){

        String sql = "INSERT INTO Products(id, name, brand, quantity, price) VALUES(?,?,?,?,?)";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,productCount);
            product.setId(productCount);
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3,product.getBrand());
            preparedStatement.setInt(4, product.getQuantity());
            preparedStatement.setFloat(5, product.getPrice());
            preparedStatement.executeUpdate();
            System.out.println("[Serenity]->(Database) : Product " + product.getName() + " was added into the inventory");
            inventory.add(product);
            updateLocalDatabaseFromDatabase();
            return 200;
        }catch (SQLException e){
            e.printStackTrace();
            return 201;
        }

    }

    public int deleteProduct(int id) {
        String sql = "DELETE FROM Products WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully.");
                updateLocalDatabaseFromDatabase();

            } else {
                System.out.println("No product found with the specified id.");
            }
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteProduct(Product productToBeDeleted) {
        String checkSql = "SELECT COUNT(*) FROM Products WHERE id = ?";
        String deleteSql = "DELETE FROM Products WHERE id = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql);
             PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {

            // Check if the product exists
            checkStmt.setInt(1, productToBeDeleted.getId());
            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    // Product exists, proceed to delete
                    deleteStmt.setInt(1, productToBeDeleted.getId());
                    deleteStmt.executeUpdate();
                    updateLocalDatabaseFromDatabase();
                    System.out.println("[Serenity]->(Core)->(Database) : the product " + productToBeDeleted.getName() + " is removed from the inventory");
                    return 200;
                } else {
                    // Product does not exist
                    System.err.println("[Serenity]->(Core)->(Database) : the product " + productToBeDeleted.getName() + " was not found");
                    return 201;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 500; // Internal server error
        }
    }


    public int updateProduct(int id, Product updatedProduct, Product oldProduct) {
        String checkSql = "SELECT COUNT(*) FROM Products WHERE id = ?";
        String updateSql = "UPDATE Products SET name = ?, price = ?, quantity = ? WHERE id = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql);
             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {

            // Check if the old product exists
            checkStmt.setInt(1, oldProduct.getId());
            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    // Old product exists, proceed to update
                    updateStmt.setString(1, updatedProduct.getName());
                    updateStmt.setDouble(2, updatedProduct.getPrice());
                    updateStmt.setInt(3, updatedProduct.getQuantity());
                    updateStmt.setInt(4, oldProduct.getId());
                    updateStmt.executeUpdate();
                    updateLocalDatabaseFromDatabase();

                    System.out.println("[Serenity]->(Core)->(Database) : the product " + oldProduct.getName() + " is updated");
                    return 200;
                } else {
                    // Old product does not exist
                    System.err.println("[Serenity]->(Core)->(Database) : the product " + oldProduct.getName() + " was not found");
                    return 201;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 500; // Internal server error
        }
    }


    public int updateStock(int id, int newStock) {
        String checkSql = "SELECT COUNT(*) FROM Products WHERE id = ?";
        String updateSql = "UPDATE Products SET quantity = ? WHERE id = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql);
             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {

            // Check if the product exists
            checkStmt.setInt(1, id);
            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    // Product exists, proceed to update the stock
                    updateStmt.setInt(1, newStock);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();

                    System.out.println("[Serenity]->(Core)->(Database)- : the stocks have been updated for product ID " + id + " to " + newStock);
                    updateLocalDatabaseFromDatabase();
                    
                    return 200;
                } else {
                    // Product does not exist
                    System.err.println("[Serenity]->(Core)->(Database) : invalid product ID " + id);
                    return 201;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 500; // Internal server error
        }
    }

    public int updateProductById(int id, float price) {
        String checkSql = "SELECT COUNT(*) FROM Products WHERE id = ?";
        String updateSql = "UPDATE Products SET price = ? WHERE id = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql);
             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {

            // Check if the product exists
            checkStmt.setInt(1, id);
            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    // Product exists, proceed to update the price
                    updateStmt.setFloat(1, price);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();

                    System.out.println("[Serenity]->(Core)->(Database)->(Database) : the price has been updated for product ID " + id + " to " + price);
                    updateLocalDatabaseFromDatabase();
                    
                    return 200;
                } else {
                    // Product does not exist
                    System.err.println("[Serenity]->(Core)->(Database)->(Database) : invalid product ID " + id);
                    return 201;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 500; // Internal server error
        }
    }


    public int updateProductById(int id, String name) {
        String checkSql = "SELECT COUNT(*) FROM Products WHERE id = ?";
        String updateSql = "UPDATE Products SET name = ? WHERE id = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql);
             PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {

            // Check if the product exists
            checkStmt.setInt(1, id);
            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    // Product exists, proceed to update the name
                    updateStmt.setString(1, name);
                    updateStmt.setInt(2, id);
                    updateStmt.executeUpdate();

                    System.out.println("[Serenity]->(Core)->(Database)->(Database) : the name has been updated for product ID " + id + " to " + name);
                    updateLocalDatabaseFromDatabase();
                    return 200;
                } else {
                    // Product does not exist
                    System.err.println("[Serenity]->(Core)->(Database)->(Database) : invalid product ID " + id);
                    return 201;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 500; // Internal server error
        }
    }



    public int getProductCountFromDatabase() {
        String sql = "SELECT COUNT(*) AS product_count FROM Products";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("product_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void updateLocalDatabaseFromDatabase() {
        try {
            // Clear the existing inventory
            inventory.clear();

            // Query the database to fetch all products
            String sql = "SELECT * FROM Products";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String brand = resultSet.getString("brand");
                    int quantity = resultSet.getInt("quantity");
                    float price = resultSet.getFloat("price");

                    // Create a new Product object and add it to the local inventory
                    Product product = new Product(id, name, brand, quantity, price);
                    inventory.add(product);
                }
                System.out.println("[Serenity]->(Core)->(Database) : Local database updated from the main database");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");


        // Establish a connection
        Connection connection = DriverManager.getConnection(
                DatabaseConnection.getMySqlAddress(),
                "root",
                "nahida@dendro123"
        );
        DatabaseConnection.setConnection(connection);

        ProductManager productManager = new ProductManager(DatabaseConnection.getConnection());
        productManager.addNewProduct("S22 ultra","samsung",129f,4);
        Product electroPhone = new Product(productManager.getProductCountFromDatabase(),"Hutao","one-plus",100,2999f);
        productManager.addNewProduct(electroPhone);
        System.out.println(productManager.getProductCountFromDatabase());
    }

    public int getProductCount() {
        productCount = getProductCountFromDatabase();
        return productCount;
    }

    public ArrayList<Product> getInventory() {
        updateLocalDatabaseFromDatabase();
        return inventory;
    }
}
