package Assets.Views.Dialogs;

import Assets.Database.DatabaseConnection;
import Assets.Security.HashUtil;
import Assets.Utils.DisplayUtils;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AddNewUser extends JFrame {
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JCheckBox createProductsCheckBox;
    private JCheckBox viewProductsCheckBox;
    private JCheckBox updateProductsCheckBox;
    private JCheckBox deleteProductsCheckBox;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JCheckBox adminAccountCheckBox;
    private JButton cancelButton;
    private JButton addButton;
    private JPanel mainPanel;
    private JTextField emailTextField;

    public AddNewUser() {
        setContentPane(mainPanel);
        setVisible(true);
        setBounds(DisplayUtils.getCenterOfScreen(450, 400));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Add action listener to adminAccountCheckBox
        adminAccountCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (adminAccountCheckBox.isSelected()) {
                    // If admin account is checked, disable other checkboxes and set them to selected
                    createProductsCheckBox.setSelected(true);
                    viewProductsCheckBox.setSelected(true);
                    updateProductsCheckBox.setSelected(true);
                    deleteProductsCheckBox.setSelected(true);
                    createProductsCheckBox.setEnabled(false);
                    viewProductsCheckBox.setEnabled(false);
                    updateProductsCheckBox.setEnabled(false);
                    deleteProductsCheckBox.setEnabled(false);
                } else {
                    // If admin account is unchecked, enable other checkboxes
                    createProductsCheckBox.setEnabled(true);
                    viewProductsCheckBox.setEnabled(true);
                    updateProductsCheckBox.setEnabled(true);
                    deleteProductsCheckBox.setEnabled(true);

                    createProductsCheckBox.setSelected(false);
                    viewProductsCheckBox.setSelected(false);
                    updateProductsCheckBox.setSelected(false);
                    deleteProductsCheckBox.setSelected(false);
                }
            }
        });

        // Add action listener to cancelButton
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the dialog
                SwingUtilities.getWindowAncestor((JButton) e.getSource()).dispose();
            }
        });

        // Add action listener to addButton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Connection connection = null;
                PreparedStatement preparedStatement = null;

                String username = usernameTextField.getText();
                char[] passwordChars = passwordTextField.getPassword();
                String password = new String(passwordChars);  // Convert char[] to String
                String query = "CREATE USER ?@'%' IDENTIFIED BY ?";

                try {
                    connection = DatabaseConnection.getConnection();
                    preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, HashUtil.hashString(password));

                    preparedStatement.executeUpdate();



                    // add user into the user table

                    String createUserQuery = "INSERT INTO User (username, password, email, first_name, last_name, created_at, last_login, isAdmin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                    PreparedStatement createUserPreparedStatement = connection.prepareStatement(createUserQuery);


                    createUserPreparedStatement.setString(1, username);
                    createUserPreparedStatement.setString(2, HashUtil.hashString(new String(passwordTextField.getPassword())));
                    createUserPreparedStatement.setString(3,emailTextField.getText());
                    createUserPreparedStatement.setString(4,firstNameTextField.getText());
                    createUserPreparedStatement.setString(5,lastNameTextField.getText());
                    createUserPreparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                    createUserPreparedStatement.setTimestamp(7,new Timestamp(System.currentTimeMillis()));
                    createUserPreparedStatement.setBoolean(8,adminAccountCheckBox.isSelected());

                    createUserPreparedStatement.executeUpdate();

                    System.out.println("[Serenity]->(Core)->(Database) : User created successfully!");

                    grantUserSelectionPermission();

                    // Give necessary permissions
                    if (updateProductsCheckBox.isSelected() || adminAccountCheckBox.isSelected()) {
                        String grantUpdateString = "GRANT UPDATE ON Products TO '" + usernameTextField.getText() + "'@'%'";
                        try (Statement grantStatement = connection.createStatement()) {
                            grantStatement.executeUpdate(grantUpdateString);
                            System.out.println("[Serenity]->(Core)->(Database)->[User] : Granted update on Products to " + usernameTextField.getText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (createProductsCheckBox.isSelected() || adminAccountCheckBox.isSelected()) {
                        String grantInsertString = "GRANT INSERT ON Products TO '" + usernameTextField.getText() + "'@'%'";
                        try (Statement insertPermission = connection.createStatement()) {
                            insertPermission.executeUpdate(grantInsertString);
                            System.out.println("[Serenity]->(Core)->(Database)->[User] : Granted creation of Products to " + usernameTextField.getText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (viewProductsCheckBox.isSelected() || adminAccountCheckBox.isSelected()) {
                        String viewProductQuery = "GRANT SELECT ON Products TO '" + usernameTextField.getText() + "'@'%'";
                        try (Statement viewStatement = connection.createStatement()) {
                            viewStatement.executeUpdate(viewProductQuery);
                            System.out.println("[Serenity]->(Core)->(Database)->[User] : Granted viewing of Products to " + usernameTextField.getText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (deleteProductsCheckBox.isSelected() || adminAccountCheckBox.isSelected()) {
                        String deleteProductPermission = "GRANT DELETE ON Products TO '" + usernameTextField.getText() + "'@'%'";
                        try (Statement deleteStatement = connection.createStatement()) {
                            deleteStatement.executeUpdate(deleteProductPermission);
                            System.out.println("[Serenity]->(Core)->(Database)->[User] : Granted deletion of Products to " + usernameTextField.getText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (adminAccountCheckBox.isSelected()) {
                        String grantAllPermissionsQuery = "GRANT ALL PRIVILEGES ON *.* TO '" + usernameTextField.getText() + "'@'%'" + " WITH GRANT OPTION";
                        try (Statement grantAllStatement = connection.createStatement()) {
                            grantAllStatement.executeUpdate(grantAllPermissionsQuery);
                            System.out.println("[Serenity]->(Core)->(Database)->[User] : Made " + usernameTextField.getText() + " Admin Account \uD83D\uDC51");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                SwingUtilities.getWindowAncestor((JButton) actionEvent.getSource()).dispose();
            }
        });

        setVisible(true);
    }

    private void grantUserSelectionPermission(){
        Connection connection = DatabaseConnection.getConnection();
        String prepStatement = "GRANT SELECT ON User TO '" + usernameTextField.getText() + "'@'%'";
        try (Statement viewStatement = connection.createStatement()) {
            viewStatement.executeUpdate(prepStatement);
            System.out.println("[Serenity]->(Core)->(Database)->[User] : Granted viewing of users to " + usernameTextField.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
