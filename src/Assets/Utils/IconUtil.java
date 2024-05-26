package Assets.Utils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IconUtil {

    private static final String ICON_DIR = "Assets/Drawable/Icons/";

    // Method to load and resize an icon
    private static Icon loadIcon(String filename, int width, int height) {
        URL iconURL = IconUtil.class.getClassLoader().getResource(ICON_DIR + filename);
        if (iconURL != null) {
            ImageIcon originalIcon = new ImageIcon(iconURL);
            Image resizedImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } else {
            System.err.println("Icon not found: " + filename);
            return null;
        }
    }

    // Method to get an icon based on the type
    public static Icon getIcon(IconType iconType, int width, int height) {
        return loadIcon(iconType.getFilename(), width, height);
    }

    // Overloaded method to get an icon with default size
    public static Icon getIcon(IconType iconType) {
        return getIcon(iconType, 16, 16); // Default size 16x16
    }
}
