package Assets.UI;

import javax.swing.*;
import java.awt.*;

class RoundedPanel extends JPanel {
    public RoundedPanel() {
        setOpaque(false); // Make the panel non-opaque to support transparency
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Enable anti-aliasing for smooth corners
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color with transparency
        g2d.setColor(new Color(255, 255, 255, 200));

        // Draw a rounded rectangle
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);

        g2d.dispose();
    }
}

//public class CustomFrame extends JFrame {
//    public CustomFrame() {
//        setUndecorated(true);
//        setBackground(new Color(0, 0, 0, 0));
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(400, 300);
//        setLocationRelativeTo(null);
//
//        // Set the custom panel as the content pane
//        setContentPane(new RoundedPanel());
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            CustomFrame frame = new CustomFrame();
//            frame.setVisible(true);
//        });
//    }
//}