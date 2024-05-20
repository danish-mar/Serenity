package Assets.Database;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {


    private static String mySqlAddress = "jdbc:mysql://10.10.11.7:3306/sora";
    private static String username = "null";

    private static String password = "null";

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


}
