package Assets.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static BufferedImage recolorImage(BufferedImage originalImage, Color newColor) {
        BufferedImage newImage = new BufferedImage(
                originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(originalImage, 0, 0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(newColor);
        g.fillRect(0, 0, originalImage.getWidth(), originalImage.getHeight());
        g.dispose();
        return newImage;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
        Image tmp = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public static void main(String[] args) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("path/to/your/image.png"));
            BufferedImage recoloredImage = recolorImage(originalImage, Color.WHITE);
            BufferedImage resizedImage = resizeImage(recoloredImage, 24, 24); // Adjust size as needed

            JLabel poweredByLabel = new JLabel("Powered By ");
            poweredByLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            poweredByLabel.setForeground(Color.WHITE);

            JLabel iconLabel = new JLabel(new ImageIcon(resizedImage));
            poweredByLabel.add(iconLabel);

            JFrame frame = new JFrame();
            frame.setLayout(new FlowLayout());
            frame.add(poweredByLabel);
            frame.setSize(300, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
