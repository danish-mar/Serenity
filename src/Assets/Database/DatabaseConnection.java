package Assets.Database;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {


    private static String mySqlAddress = "jdbc:mysql://10.10.11.7:3306/sora";
    private static String username = "null";

    private static String password = "null";

    private static String name = "null";

    private static Connection connection= null;

    private static String userNameFirstName = null;
    private static String userNameLastName = null;


    public static String[] getConnectionProperties(){
        return new String[]{mySqlAddress, username, password};
    }

    public DatabaseConnection(String username, String password){
        DatabaseConnection.username = username;
        DatabaseConnection.password = password;
    }

    public DatabaseConnection(String serverAddress, String username, String password){
        DatabaseConnection.username = username;
        DatabaseConnection.mySqlAddress = serverAddress;
        DatabaseConnection.password = password;
    }

    public static void setUserName(String uname){
        username = uname;
    }

    public static void setPassword(String passwd){
        password = passwd;
    }


    public static void load(String uname, String passwd){
        username = uname;
        password = passwd;
    }

    public static String getMySqlAddress() {
        return mySqlAddress;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUsername() {
        return username;
    }

    public static void setConnection(Connection connection) {
        DatabaseConnection.connection = connection;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setMySqlAddress(String mySqlAddress,String databaseName) {
        DatabaseConnection.name = mySqlAddress;
        DatabaseConnection.mySqlAddress = "jdbc:mysql://" + mySqlAddress + ":3306/" + databaseName;
    }

    public static String getName() {
        return name;
    }

    public static String getUserNameFirstName() {
        return userNameFirstName;
    }

    public static String getUserNameLastName() {
        return userNameLastName;
    }

    public static void setUserNameFirstName(String userNameFirstName) {
        DatabaseConnection.userNameFirstName = userNameFirstName;
    }

    public static void setUserNameLastName(String userNameLastName) {
        DatabaseConnection.userNameLastName = userNameLastName;
    }

    public static void deinitialize() throws SQLException {
        setPassword("");
        setUserName("");
        userNameFirstName = null;
        userNameLastName = null;
        connection.close();
    }


}
