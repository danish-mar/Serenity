package Assets.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

public class FrameBlurUtil {

    private static class BlurGlass extends JComponent {
        private JFrame frame;

        public BlurGlass(JFrame frame) {
            this.frame = frame;
            setOpaque(false);
            setFocusable(false);
            addMouseListener(new MouseAdapter() {});
            addMouseMotionListener(new MouseMotionAdapter() {});
        }

        @Override
        protected void paintComponent(Graphics g) {
            int w = frame.getWidth();
            int h = frame.getHeight();
            setLocation(0, 0);
            setSize(w, h);
            g.setColor(new Color(0, 0, 0, 0.5f)); // Semi-transparent overlay
            g.fillRect(0, 0, w, h);
        }
    }

    public static void applyBlur(JFrame frame) {
        BlurGlass blurGlass = new BlurGlass(frame);
        frame.setGlassPane(blurGlass);
        frame.getGlassPane().setVisible(true);
        frame.setFocusable(false);
    }

    public static void removeBlur(JFrame frame) {
        frame.getGlassPane().setVisible(false);
        frame.setFocusable(true);
    }
}
