package Assets.Views.Auth;

import Assets.Database.DatabaseConnection;
import Assets.ProductManager;
import Assets.Security.HashUtil;
import Assets.Utils.DisplayUtils;
import Assets.Views.MainView;
import Assets.Views.Startup.Startup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginView extends JFrame {
    private JTextField username;
    private JPanel panel1;
    private JButton loginButton;
    private JPasswordField password;

    public LoginView(ProductManager productManager, JProgressBar progressBar, String serverName, Startup startup) {
        setTitle("Login - " + serverName);
        setSize(300, 300);
        setBounds(DisplayUtils.getCenterOfScreen(300, 300));
        Image icon = Toolkit.getDefaultToolkit().getImage("src/Assets/logo.png");
        setIconImage(icon);
        username.setCaretColor(Color.white);
        password.setCaretColor(Color.white);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setVisible(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String enteredUsername = username.getText();
                String enteredPassword = new String(password.getPassword());

                // Reset progress bar at the beginning of the login process
                progressBar.setValue(0);

                SwingWorker<Void, Integer> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            // Load the MySQL JDBC driver
                            publish(10);
                            Class.forName("com.mysql.cj.jdbc.Driver");

                            // Establish a connection
                            Connection connection = DriverManager.getConnection(
                                    DatabaseConnection.getMySqlAddress(),
                                    enteredUsername,
                                    HashUtil.hashString(enteredPassword)
                            );
                            publish(30);

                            // Prepare the SQL query
                            String query = "SELECT password FROM User WHERE username = ?";
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setString(1, enteredUsername);
                            publish(50);

                            ResultSet resultSet = preparedStatement.executeQuery();
                            publish(70);

                            if (resultSet.next()) {
                                String storedPasswordHash = resultSet.getString("password");
                                publish(80);

                                // Check if the entered password matches the stored hash
                                if (HashUtil.hashString(enteredPassword).equals(storedPasswordHash)) {
                                    // Successful login
                                    publish(100);
                                    DatabaseConnection.setConnection(connection);
                                    dispose();
                                    startup.dispose();

                                    new MainView(productManager); // Assuming MainView is your main application window
                                } else {
                                    // Incorrect password
                                    JOptionPane.showMessageDialog(loginButton, "Invalid username or password.");
                                }
                            } else {
                                // Username not found
                                JOptionPane.showMessageDialog(loginButton, "Invalid username or password.");
                            }

                            // Clean up
                            resultSet.close();
                            preparedStatement.close();
                        } catch (Exception sqlException) {
                            sqlException.printStackTrace();
                            String sqlState = ((SQLException)sqlException).getSQLState();
                            System.out.println("sql state : " + ((SQLException)sqlException).getSQLState());
                            if(sqlState.equalsIgnoreCase("28000") || sqlState.equalsIgnoreCase("HY000")){
                                JOptionPane.showMessageDialog(loginButton, "Invalid username or password.");
                                return null;
                            }
                            JOptionPane.showMessageDialog(loginButton, "An error occurred while trying to log in. Please try again.");
                        }

                        return null;
                    }

                    @Override
                    protected void process(java.util.List<Integer> chunks) {
                        for (int value : chunks) {
                            progressBar.setValue(value);
                        }
                    }
                };

                worker.execute();
            }
        });
    }

    public LoginView(Assets.Database.ProductManager productManager, JProgressBar progressBar, String serverName, Startup startup) {
        setTitle("Login - " + serverName);
        setSize(300, 300);
        setBounds(DisplayUtils.getCenterOfScreen(300, 300));
        Image icon = Toolkit.getDefaultToolkit().getImage("src/Assets/logo.png");
        setIconImage(icon);
        username.setCaretColor(Color.white);
        password.setCaretColor(Color.white);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setVisible(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String enteredUsername = username.getText();
                String enteredPassword = new String(password.getPassword());

                // Reset progress bar at the beginning of the login process
                progressBar.setValue(0);

                SwingWorker<Void, Integer> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            // Load the MySQL JDBC driver
                            publish(10);
                            Class.forName("com.mysql.cj.jdbc.Driver");

                            // Establish a connection
                            Connection connection = DriverManager.getConnection(
                                    DatabaseConnection.getMySqlAddress(),
                                    enteredUsername,
                                    HashUtil.hashString(enteredPassword)
                            );
                            publish(30);

                            // Prepare the SQL query
                            String query = "SELECT password FROM User WHERE username = ?";
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setString(1, enteredUsername);
                            publish(50);

                            ResultSet resultSet = preparedStatement.executeQuery();
                            publish(70);

                            if (resultSet.next()) {
                                String storedPasswordHash = resultSet.getString("password");
                                publish(80);

                                // Check if the entered password matches the stored hash
                                if (HashUtil.hashString(enteredPassword).equals(storedPasswordHash)) {
                                    // Successful login
                                    publish(100);
                                    DatabaseConnection.setConnection(connection);
                                    productManager.init(DatabaseConnection.getConnection());
                                    dispose();
                                    startup.dispose();

                                    new MainView(productManager); // Assuming MainView is your main application window
                                } else {
                                    // Incorrect password
                                    JOptionPane.showMessageDialog(loginButton, "Invalid username or password.");
                                }
                            } else {
                                // Username not found
                                JOptionPane.showMessageDialog(loginButton, "Invalid username or password.");
                            }

                            // Clean up
                            resultSet.close();
                            preparedStatement.close();
                        } catch (Exception sqlException) {
                            sqlException.printStackTrace();
                            String sqlState = ((SQLException)sqlException).getSQLState();
                            System.out.println("sql state : " + ((SQLException)sqlException).getSQLState());
                            if(sqlState.equalsIgnoreCase("28000") || sqlState.equalsIgnoreCase("HY000")){
                                JOptionPane.showMessageDialog(loginButton, "Invalid username or password.");
                                return null;
                            }
                            JOptionPane.showMessageDialog(loginButton, "An error occurred while trying to log in. Please try again.");
                        }

                        return null;
                    }

                    @Override
                    protected void process(java.util.List<Integer> chunks) {
                        for (int value : chunks) {
                            progressBar.setValue(value);
                        }
                    }
                };

                worker.execute();
            }
        });
    }



//    public static void main(String[] args) {
//        // Initialize the progress bar
//        JProgressBar progressBar = new JProgressBar(0, 100);
//        progressBar.setStringPainted(true);
//
//        // Create the product manager (assuming you have this class)
//        ProductManager productManager = new ProductManager();
//
//        // For testing purposes
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Login");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(400, 400);
//
//            // Add the progress bar to the frame
//            frame.setLayout(new BorderLayout());
//            frame.add(new LoginView(productManager, progressBar), BorderLayout.CENTER);
//            frame.add(progressBar, BorderLayout.SOUTH);
//
//            frame.setVisible(true);
//        });
//    }
}
