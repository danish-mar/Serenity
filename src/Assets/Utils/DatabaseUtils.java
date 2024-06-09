package Assets.Utils;
import Assets.Database.DatabaseConnection;

import javax.xml.crypto.Data;
import java.sql.*;

public class DatabaseUtils {
    public static void updateLastLogin(Connection connection, String username) {
        String query = "UPDATE User SET last_login = ? WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(2, username);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Last login timestamp updated successfully for user: " + username);
            } else {
                System.out.println("No user found with the username: " + username);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void updateInfo(Connection connection, String username) {
        String query = "SELECT first_name, last_name FROM User WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                DatabaseConnection.setUserNameFirstName(resultSet.getString("first_name"));
                DatabaseConnection.setUserNameLastName(resultSet.getString("last_name"));
                DatabaseConnection.setUserName(username);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static boolean isAdminAccount(Connection connection){
        String query = "SELECT isAdmin FROM User WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            String uname = DatabaseConnection.getUsername();
            statement.setString(1,uname);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                if(resultSet.getBoolean("isAdmin")){
                    return true;
                }
                return false;
            }return false;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean isAdminAccount(Connection connection, String username){
        String query = "SELECT isAdmin FROM User WHERE username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                if(resultSet.getBoolean("isAdmin")){
                    return true;
                }
                return false;
            }return false;
        }catch (Exception e){
            return false;
        }
    }

    public static void resetAutoIncrement(Connection connection, String tableName, int newStartValue) {
        String query = "ALTER TABLE " + tableName + " AUTO_INCREMENT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newStartValue);
            preparedStatement.executeUpdate();
            System.out.println("Auto-increment value reset successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error resetting auto-increment value.");
        }
    }

    public static int getHighestId(Connection connection, String tableName) {
        String query = "SELECT MAX(id) AS max_id FROM " + tableName;
        int maxId = -1;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                maxId = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving the highest ID.");
        }

        return maxId;
    }
}
