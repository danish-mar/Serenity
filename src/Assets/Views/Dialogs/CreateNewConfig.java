package Assets.Views.Dialogs;

import Assets.Utils.DisplayUtils;
import javax.swing.*;
import java.awt.event.*;

public class CreateNewConfig extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField serverAddress;
    private String jsonConfig;

    public CreateNewConfig(String jsonConfig) {
        this.jsonConfig = jsonConfig;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setBounds(DisplayUtils.getCenterOfScreen(200, 200));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // Call onCancel() when the cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void handleOK() {
        String serverAdd = serverAddress.getText();
        if (!formatChecker(serverAdd)) {
            JOptionPane.showMessageDialog(buttonOK, "Please Enter a valid server address");
            return;
        }
        onOK(serverAdd);
    }

    private void onOK(String serverAddress) {
        jsonConfig = String.format("""
        {
            "server": "%s"
        }
        """, serverAddress);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public static boolean formatChecker(String address) {
        if (address == null || address.isEmpty()) {
            return false;  // Empty string is not a valid address
        }

        // Check for valid IP address format (IPv4)
        String ipPattern = "^([01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.([01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.([01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.([01]?\\d{1,2}|2[0-4]\\d|25[0-5])$";
        if (address.matches(ipPattern)) {
            return true;
        }

        // Check for URL format (with or without protocol)
        String urlPattern = "^(?!http://|https://)(?=.{1,255}$)(([a-zA-Z0-9][a-zA-Z0-9\\-]*)(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]*)+)?(?::\\d+)$";
        return address.matches(urlPattern);
    }


    public static void main(String[] args) {
        String sp = "";
        System.out.println("json config : " + sp);

        CreateNewConfig dialog = new CreateNewConfig(sp);
        dialog.pack();
        dialog.setVisible(true);
        System.out.println("json config : " + sp);

    }
}
