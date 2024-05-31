package Assets.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

class BlurGlass extends JComponent {
    private JFrame f;

    public BlurGlass(JFrame f) {
        this.f = f;
        setOpaque(false);
        setFocusable(false);
        addMouseListener(new MouseAdapter() {});
        addMouseMotionListener(new MouseMotionAdapter() {});
    }

    @Override
    protected void paintComponent(Graphics g) {
        int w = f.getWidth();
        int h = f.getHeight();
        setLocation(0, 0);
        setSize(w, h);
        g.setColor(new Color(0, 0, 0, 0.5f));
        g.fillRect(0, 0, w, h);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Blur Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new BorderLayout());

            // Add some content to the frame
            JLabel label = new JLabel("Content behind blur", SwingConstants.CENTER);
            frame.add(label, BorderLayout.CENTER);

            // Create and set the blur glass pane
            BlurGlass blurGlass = new BlurGlass(frame);
            frame.setGlassPane(blurGlass);

            // Display the frame
            frame.setVisible(true);

            // Simulate showing a blur effect after a few seconds
            Timer timer = new Timer(2000, e -> {
                blurGlass.setVisible(true);  // Show the blur effect
                frame.setFocusable(false);  // Optionally, make the frame non-focusable
            });
            timer.setRepeats(false);  // Only execute once
            timer.start();

            // Simulate hiding the blur effect after a few more seconds
            Timer timer2 = new Timer(5000, e -> {
                blurGlass.setVisible(false);  // Hide the blur effect
                frame.setFocusable(true);  // Optionally, make the frame focusable again
            });
            timer2.setRepeats(false);  // Only execute once
            timer2.start();
        });
    }
}



