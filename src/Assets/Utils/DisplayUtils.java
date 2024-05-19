package Assets.Utils;

import java.awt.*;

public class DisplayUtils {

    // Function to get the center of the display
        // Function to get a rectangle representing the center of the screen
        public static Rectangle getCenterOfScreen(int height, int width) {
            // Get the local graphics environment
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            // Get the default screen device
            GraphicsDevice gd = ge.getDefaultScreenDevice();

            // Get the bounds of the default screen device
            Rectangle bounds = gd.getDefaultConfiguration().getBounds();

            // Calculate the center point
            int centerX = bounds.x + bounds.width / 2;
            int centerY = bounds.y + bounds.height / 2;


            // Create a rectangle representing the center of the screen
            Rectangle centerRectangle = new Rectangle(centerX - width / 2, centerY - height / 2, width, height);

            return centerRectangle;
        }
}
