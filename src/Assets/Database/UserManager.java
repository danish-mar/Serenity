package Assets.Database;

import Assets.Security.HashUtil;
import com.mysql.cj.x.protobuf.MysqlxPrepare;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class UserManager {

    private ArrayList<User> users;
    private int userCount;
    Connection connection;

    public UserManager(){

        // sets the connection
        connection = DatabaseConnection.getConnection();
        //gets the usercount from the database
        userCount =getUserCount();

        users = new ArrayList<>();

        updateLocalDatabaseFromDatabase();


    }

    // add user ?

    public void addUser(User user){


        // inserts into the main database

        String query = "CREATE USER ?@'%' IDENTIFIED BY ?";

        try {
            PreparedStatement preparedStatement = null;

            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2,HashUtil.hashString(user.getPassword()));

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }


        // inserts into the application's database
        String newquery = "INSERT INTO User(username, password, email, first_name, last_name, created_at, last_login, isAdmin) VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(newquery)){

            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.setString(4,user.getFirstName());
            preparedStatement.setString(5,user.getLastName());
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setBoolean(8, user.isAdmin());

            preparedStatement.executeUpdate();

            System.out.println("[Serenity]->(UserManager)->(Database) : User created successfully!");

            grantUserSelectionPermission(user.getUsername());

            // Give necessary permissions
            if (user.isCanUpdateProdcuts() || user.isAdmin()) {
                String grantUpdateString = "GRANT UPDATE ON Products TO '" + user.getUsername() + "'@'%'";
                try (Statement grantStatement = connection.createStatement()) {
                    grantStatement.executeUpdate(grantUpdateString);
                    System.out.println("[Serenity]->(UserManager)->(Database)->[User] : Granted update on Products to " + user.getUsername());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (user.isCanCreateProducts() || user.isAdmin()) {
                String grantInsertString = "GRANT INSERT ON Products TO '" + user.getUsername() + "'@'%'";
                try (Statement insertPermission = connection.createStatement()) {
                    insertPermission.executeUpdate(grantInsertString);
                    System.out.println("[Serenity]->(UserManager)->(Database)->[User] : Granted creation of Products to " + user.getUsername());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (user.isCanViewProdcuts() || user.isAdmin()) {
                String viewProductQuery = "GRANT SELECT ON Products TO '" + user.getUsername() + "'@'%'";
                try (Statement viewStatement = connection.createStatement()) {
                    viewStatement.executeUpdate(viewProductQuery);
                    System.out.println("[Serenity]->(UserManager)->(Database)->[User] : Granted viewing of Products to " + user.getUsername());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (user.isCanDeleteProducts() || user.isAdmin()) {
                String deleteProductPermission = "GRANT DELETE ON Products TO '" + user.getUsername() + "'@'%'";
                try (Statement deleteStatement = connection.createStatement()) {
                    deleteStatement.executeUpdate(deleteProductPermission);
                    System.out.println("[Serenity]->(UserManager)->(Database)->[User] : Granted deletion of Products to " + user.getUsername());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (user.isAdmin()) {
                String grantAllPermissionsQuery = "GRANT ALL PRIVILEGES ON *.* TO '" + user.getUsername() + "'@'%'" + " WITH GRANT OPTION";
                try (Statement grantAllStatement = connection.createStatement()) {
                    grantAllStatement.executeUpdate(grantAllPermissionsQuery);
                    System.out.println("[Serenity]->(UserManager)->(Database)->[User] : Made " + user.getUsername() + " Admin Account \uD83D\uDC51");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            updateLocalDatabaseFromDatabase();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void addUser(String username, String password, String email, String firstName, String lastName, boolean isAdmin, boolean canCreateProducts, boolean canViewProducts, boolean canUpdateProducts, boolean canDeleteProducts) {

        User user = new User(0,username,password,email,firstName,lastName, null,null,isAdmin,canCreateProducts,canDeleteProducts,canUpdateProducts,canViewProducts);

        addUser(user);

    }

    public void deleteUser(int userId) {
        String query = "DELETE FROM User WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Serenity]->(UserManager)->(Database) : User with ID " + userId + " deleted successfully!");
            } else {
                System.out.println("[Serenity]->(UserManager)->(Database) : User with ID " + userId + " not found!");
            }
            updateLocalDatabaseFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User newUser, User updatedUser) {
        String query = "UPDATE User SET username = ?,  email = ?, first_name = ?, last_name = ?, isAdmin = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, updatedUser.getUsername());
            preparedStatement.setString(2, updatedUser.getEmail());
            preparedStatement.setString(3, updatedUser.getFirstName());
            preparedStatement.setString(4, updatedUser.getLastName());
            preparedStatement.setBoolean(5, updatedUser.isAdmin());
            preparedStatement.setInt(6, newUser.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Serenity]->(UserManager)->(Database) : User with ID " + newUser.getId() + " updated successfully!");
                updateLocalDatabaseFromDatabase();
            } else {
                System.out.println("[Serenity]->(UserManager)->(Database) : User with ID " + newUser.getId() + " not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adminChangeUserPassword(int id, String updatedPassword) {

        String hashedUpdatedPassword = HashUtil.hashString(updatedPassword);

        // Update the main database user in the authentication database
        String authQuery = "ALTER USER '" + getUsernameById(id) + "' IDENTIFIED BY ?";

        try (PreparedStatement authStatement = connection.prepareStatement(authQuery)) {
            authStatement.setString(1, hashedUpdatedPassword);
            authStatement.executeUpdate();

            System.out.println("User password updated in the authentication database.");
            updateLocalDatabaseFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Update the user's password in your application's User table (assuming it's named sora.User)
        String appQuery = "UPDATE User SET password = ? WHERE id = ?";

        try (PreparedStatement appStatement = connection.prepareStatement(appQuery)) {
            appStatement.setString(1, hashedUpdatedPassword);
            appStatement.setInt(2, id);
            appStatement.executeUpdate();

            System.out.println("User password updated in the application's User table.");
            updateLocalDatabaseFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeUserPassword(int id, String updatedPassword) throws SQLException {

        String hashedUpdatedPassword = HashUtil.hashString(updatedPassword);

        if(Objects.equals(getUserById(id).getPassword(), hashedUpdatedPassword)){

        // Update the main database user in the authentication database
        String authQuery = "ALTER USER '" + getUsernameById(id) + "' IDENTIFIED BY ?";

        try (PreparedStatement authStatement = connection.prepareStatement(authQuery)) {
            authStatement.setString(1, hashedUpdatedPassword);
            authStatement.executeUpdate();

            System.out.println("User password updated in the authentication database.");
            updateLocalDatabaseFromDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Update the user's password in your application's User table (assuming it's named sora.User)
        String appQuery = "UPDATE User SET password = ? WHERE id = ?";

        try (PreparedStatement appStatement = connection.prepareStatement(appQuery)) {
            appStatement.setString(1, hashedUpdatedPassword);
            appStatement.setInt(2, id);
            appStatement.executeUpdate();

            System.out.println("User password updated in the application's User table.");
            updateLocalDatabaseFromDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        }else{
            throw new SQLException("Passwords do not match");
        }

    }








    private String getUsernameById(int id) {
        String query = "SELECT username FROM User WHERE id = ?";
        String username = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set parameter for the prepared statement
            preparedStatement.setInt(1, id);

            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    username = resultSet.getString("username");
                } else {
                    System.out.println("User with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return username;
    }


    public void updateUser(int userId, String username , String email, String firstName, String lastName, boolean isAdmin) {
        String query = "UPDATE User SET username = ?, email = ?, first_name = ?, last_name = ?, isAdmin = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setBoolean(5, isAdmin);
            preparedStatement.setInt(6, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("[Serenity]->(UserManager)->(Database) : User with ID " + userId + " updated successfully!");
                updateLocalDatabaseFromDatabase();

            } else {
                System.out.println("[Serenity]->(UserManager)->(Database) : User with ID " + userId + " not found!");
                updateLocalDatabaseFromDatabase();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User getUserById(int id) {
        String query = "SELECT * FROM User WHERE id = ?";
        User user = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("email"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getTimestamp("created_at"),
                            resultSet.getTimestamp("last_login"),
                            resultSet.getBoolean("isAdmin")
                    );

                    fetchUserPrivileges(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }







    private int getUserCount(){
        String sql = "SELECT COUNT(*) AS user_count FROM User";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return resultSet.getInt("user_count");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    private void grantUserSelectionPermission(String username){
        Connection connection = DatabaseConnection.getConnection();
        String prepStatement = "GRANT SELECT ON User TO '" + username + "'@'%'";
        try (Statement viewStatement = connection.createStatement()) {
            viewStatement.executeUpdate(prepStatement);
            System.out.println("[Serenity]->(UserManager)->(Database)->[User] : Granted viewing of users to " + username);
            updateLocalDatabaseFromDatabase();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLocalDatabaseFromDatabase() {
        try {
            // Clear the existing list of users
            users.clear();

            // Query the database to fetch all users
            String sql = "SELECT * FROM User";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    Date createdAt = resultSet.getDate("created_at");
                    Date lastLogin = resultSet.getDate("last_login");
                    boolean isAdmin = resultSet.getBoolean("isAdmin");

                    User newUser = new User(id,username,password,email,firstName,lastName,createdAt,lastLogin,isAdmin);

                    fetchUserPrivileges(newUser);


                    // Fetch user privileges from the database (if needed)
                    boolean canCreateProducts = newUser.isCanCreateProducts();
                    boolean canViewProducts = newUser.isCanViewProdcuts();
                    boolean canUpdateProducts = newUser.isCanUpdateProdcuts();
                    boolean canDeleteProducts = newUser.isCanDeleteProducts();

                    // Create a new User object and add it to the local list of users
                    User user = new User(id, username, password, email, firstName, lastName, createdAt, lastLogin, isAdmin, canCreateProducts, canViewProducts, canUpdateProducts, canDeleteProducts);
                    users.add(user);
                }
                System.out.println("[Serenity]->(UserManager)->(Database) : Local database updated from the main database");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateCreatePermissions(int id, boolean value) {
        String perm = value ? "GRANT" : "REVOKE";
        String grantInsertString = perm + " INSERT ON Products TO '" + getUserById(id).getUsername() + "'@'%'";
        executePermissionQuery(grantInsertString, "creation of Products", id);
    }

    public void updateViewPermissions(int id, boolean value) {
        String perm = value ? "GRANT" : "REVOKE";
        String viewProductQuery = perm + " SELECT ON Products TO '" + getUserById(id).getUsername() + "'@'%'";
        executePermissionQuery(viewProductQuery, "viewing of Products", id);
    }

    public void updateDeletePermissions(int id, boolean value) {
        String perm = value ? "GRANT" : "REVOKE";
        String deleteProductPermission = perm + " DELETE ON Products TO '" + getUserById(id).getUsername() + "'@'%'";
        executePermissionQuery(deleteProductPermission, "deletion of Products", id);
    }

    public void updateAdminPermissions(int id, boolean value) {
        if (value) {
            String grantAllPermissionsQuery = "GRANT ALL PRIVILEGES ON *.* TO '" + getUserById(id).getUsername() + "'@'%'" + " WITH GRANT OPTION";
            executePermissionQuery(grantAllPermissionsQuery, "Admin Account", id);
        } else {
            System.out.println("Cannot revoke admin privileges directly.");
        }
    }

    private void executePermissionQuery(String query, String permissionDescription, int id) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("[Serenity]->(UserManager)->(Database)->[User] : Granted " + permissionDescription + " to " + getUserById(id).getUsername());
            updateLocalDatabaseFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void fetchUserPrivileges(User user) {
        // Query to fetch user privileges from the database
        String query = "SHOW GRANTS FOR '" + user.getUsername() + "'@'%'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                // Parse the result set to extract user privileges
                String privilege = resultSet.getString(1);
                // Check if the privilege is related to the Products table
                if (privilege.contains("Products")) {
                    // Set the corresponding privilege for the user
                    if (privilege.contains("SELECT")) {
                        user.setCanViewProdcuts(true);
                    }
                    if (privilege.contains("INSERT")) {
                        user.setCanCreateProducts(true);
                    }
                    if (privilege.contains("UPDATE")) {
                        user.setCanUpdateProdcuts(true);
                    }
                    if (privilege.contains("DELETE")) {
                        user.setCanDeleteProducts(true);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
