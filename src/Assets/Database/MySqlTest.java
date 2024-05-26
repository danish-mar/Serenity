package Assets.Database;

import java.sql.*;

public class MySqlTest {

    public static void main(String[] args) throws SQLException {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://10.10.11.7/sora", "root", "nahida@dendro123");

            // Create a statement object
            stmt = conn.createStatement();

            // Execute the query to select all data from Products table
            rs = stmt.executeQuery("SELECT * FROM Products");

            // Check if there are any results
            if (rs.next()) {
                // Loop through the results and print product ID and name
                while (rs.next()) {
                    System.out.println(rs.getInt(1) + " " + rs.getString(2) + " "+ rs.getString(3) + " " + rs.getInt(4) + " " + rs.getFloat(5));
                }
            } else {
                System.out.println("Products table is empty.");
            }

        } catch (Exception e) {
            e.printStackTrace();  // Handle or log the exception
        } finally {
            // Close resources regardless of exceptions
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();  // Handle or log the closing exceptions
            }
        }
    }
}
