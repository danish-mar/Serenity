package Assets.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradientPanel extends JPanel implements ActionListener {
    private Timer timer;
    private float hue = 0f;

    public GradientPanel() {
        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Create gradient animation
        hue += 0.01f;
        if (hue > 1f) {
            hue = 0f;
        }
        Color color1 = Color.getHSBColor(hue, 0.6f, 1f);
        Color color2 = Color.getHSBColor(hue + 0.5f, 0.6f, 1f);

        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
        g2d.setPaint(gradientPaint);

        // Draw rounded rectangle
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
