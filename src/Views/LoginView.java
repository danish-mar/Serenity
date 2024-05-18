package Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame{
    private JTextField username;
    private JPanel panel1;
    private JButton loginButton;
    private JPasswordField password;

    public LoginView(){
        setTitle("please login");
        setSize(300,300);
        setVisible(true);
        setContentPane(panel1);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(loginButton, "login successfull");
                loginButton.setVisible(false);
            }
        });
    }
}
