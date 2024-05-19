package Assets.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    private static final String DB_URL = "jdbc:sqlite:products.db";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                String createTableSQL = "CREATE TABLE IF NOT EXISTS products ("
                        + "id INTEGER PRIMARY KEY,"
                        + "name TEXT NOT NULL,"
                        + "brand TEXT NOT NULL,"
                        + "quantity INTEGER,"
                        + "price REAL"
                        + ");";
                Statement stmt = conn.createStatement();
                stmt.execute(createTableSQL);
                System.out.println("Table created successfully.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

