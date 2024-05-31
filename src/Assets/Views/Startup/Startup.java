package Assets.Views.Startup;

import Assets.Database.DatabaseConnection;
import Assets.ProductManager;
import Assets.Utils.BlurUtil;
import Assets.Utils.DisplayUtils;
import Assets.Utils.FrameBlurUtil;
import Assets.Views.Auth.LoginView;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Startup extends JFrame {
    private JButton connectButton;
    private JPanel recentsPanel;
    private JPanel splashMainPanel;
    private JComboBox serverAddress;
    private JProgressBar progressBar1;
    private JLabel operationStatus;

    public Startup(ProductManager productManager) throws InterruptedException {

        setContentPane(splashMainPanel);
        LoginView lnr = new LoginView(productManager, progressBar1,serverAddress.getModel().getSelectedItem().toString(), this);



        setBounds(DisplayUtils.getCenterOfScreen(305,500));

        recentsPanel = new JPanel(new GridBagLayout());

        JLabel option1 = new JLabel("server.vps.com");
        option1.setForeground(Color.blue);

        recentsPanel.add(option1);
        operationStatus.setText("");


        setVisible(true);
//
//        for (int i = 0; i<=100;i++){
//            progressBar1.setValue(i);
//            Thread.sleep(50);
//        }

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DatabaseConnection.setMySqlAddress(serverAddress.getModel().getSelectedItem().toString(),"sora");

                progressBar1.setValue(progressBar1.getValue()+25);
                lnr.setTitle("Authenticate " + serverAddress.getModel().getSelectedItem().toString());
                lnr.setVisible(true);

            }
        });



    }
    public Startup(Assets.Database.ProductManager productManager) throws InterruptedException {

        setContentPane(splashMainPanel);
        LoginView lnr = new LoginView(productManager, progressBar1,serverAddress.getModel().getSelectedItem().toString(), this);



        setBounds(DisplayUtils.getCenterOfScreen(305,500));

        recentsPanel = new JPanel(new GridBagLayout());

        JLabel option1 = new JLabel("server.vps.com");
        option1.setForeground(Color.blue);

        recentsPanel.add(option1);
        operationStatus.setText("");


        setVisible(true);
//
//        for (int i = 0; i<=100;i++){
//            progressBar1.setValue(i);
//            Thread.sleep(50);
//        }

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DatabaseConnection.setMySqlAddress(serverAddress.getModel().getSelectedItem().toString(),"sora");
                progressBar1.setValue(progressBar1.getValue()+25);
                lnr.setTitle("Authenticate " + serverAddress.getModel().getSelectedItem().toString());
                FrameBlurUtil.applyBlur(Startup.this);
                lnr.setVisible(true);

                BlurUtil.blur(Startup.this);

            }
        });



    }


    public void progress(){
        operationStatus.setText("Connecting...");
        for (int i = 0;i<=100;i++){
            try {
                Thread.sleep(500);
                progressBar1.setValue(i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    public static void main(String[] args) throws InterruptedException {
//        FlatDarkPurpleIJTheme.setup();
//
//        Startup sn = new Startup();
//
//    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
