package Assets.Database;

import java.sql.Connection;

public class DatabaseConnection {


    private static String mySqlAddress = "jdbc:mysql://10.10.11.7:3306/sora";
    private static String username = "null";

    private static String password = "null";


    private static Connection connection= null;

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
        DatabaseConnection.mySqlAddress = "jdbc:mysql://" + mySqlAddress + ":3306/" + databaseName;
    }


}
