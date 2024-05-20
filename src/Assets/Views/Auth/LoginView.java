package Assets.Views.Auth;

import Assets.ProductManager;
import Assets.Security.HashUtil;
import Assets.Utils.DisplayUtils;
import Assets.Views.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField username;
    private JPanel panel1;
    private JButton loginButton;
    private JPasswordField password;

    public LoginView(String passwordHash, String passedUsername, ProductManager productManager) {
        setTitle("Serenity - Login");
        setBackground(Color.black);
        setForeground(Color.white);
        setSize(300, 300);
        setBounds(DisplayUtils.getCenterOfScreen(300,300));
        Image icon = Toolkit.getDefaultToolkit().getImage("src/Assets/logo.png");
        setIconImage(icon);
        username.setCaretColor(Color.white);
        password.setCaretColor(Color.white);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        setVisible(true);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String enteredUsername = username.getText();
                String enteredPassword = new String(password.getPassword()); // Properly get the password from JPasswordField

                if (enteredUsername.equals(passedUsername)) {
                    if (HashUtil.checkHash(passwordHash, enteredPassword)) {
//                        JOptionPane.showMessageDialog(loginButton, "Login successful");
                        setVisible(false);
                        MainView mainView = new MainView(productManager);
                    } else {
                        JOptionPane.showMessageDialog(loginButton, "Error: The password is incorrect");
                        password.setForeground(Color.RED);
                    }
                } else {
                    JOptionPane.showMessageDialog(loginButton, "Error: The username is incorrect");

                    username.setForeground(Color.RED);
                }
            }
        });
    }
}
