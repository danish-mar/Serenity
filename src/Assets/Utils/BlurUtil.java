package Assets.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.AffineTransformOp;

public class BlurUtil {

    private static BufferedImage blurredImage;

    public static void blur(JFrame frame) {
        // Create a translucent JPanel that covers the entire frame
        BlurPanel blurPanel = new BlurPanel(frame);
        frame.add(blurPanel);
        frame.setGlassPane(blurPanel);
        blurPanel.setVisible(true);
        frame.getGlassPane().setVisible(true);
    }

    public static void unblur(JFrame frame) {
        // Remove the translucent panel
        frame.getGlassPane().setVisible(false);
        frame.remove((BlurPanel) frame.getGlassPane());
        frame.repaint();
    }

    private static class BlurPanel extends JPanel {
        private BlurPanel(JFrame previousFrame) {
            setOpaque(false);
            setLayout(null); // Allows absolute positioning of components

            // Create a translucent background
            setBackground(new Color(0, 0, 0, 100)); // Adjust transparency as needed

            // Blur the content of the previous frame
            blurPreviousFrame(previousFrame);

            // Set the size of the panel to cover the entire previous frame
            setSize(previousFrame.getSize());
        }

        private void blurPreviousFrame(JFrame previousFrame) {
            // Get the content pane of the previous frame
            Container contentPane = previousFrame.getContentPane();

            // Create a BufferedImage of the content pane
            BufferedImage image = new BufferedImage(contentPane.getWidth(), contentPane.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            contentPane.paint(g2d);
            g2d.dispose();

            // Apply a blur effect to the image
            float blurRadius = 10.0f; // Adjust the blur radius as needed
            blurredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = blurredImage.createGraphics();
            g2.drawImage(image, 0, 0, null);
            g2.dispose();

            // Apply a blur filter
            BufferedImageOp op = new AffineTransformOp(AffineTransform.getScaleInstance(0.5, 0.5), AffineTransformOp.TYPE_BILINEAR);
            blurredImage = op.filter(blurredImage, null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the blurred image on the panel
            g.drawImage(blurredImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
