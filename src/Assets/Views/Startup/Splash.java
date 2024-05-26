package Assets.Views.Startup;

import Assets.ProductManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class Splash extends JWindow {

    private float opacity = 0.0f;  // Initial opacity
    private Timer fadeInTimer;
    private Timer fadeOutTimer;
    private boolean mainAppLaunched = false; // Flag to ensure the main app is only launched once

    public Splash(ProductManager productManager) {
        // Set the size and position of the splash screen
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Make the window undecorated and set a rounded shape
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50));
        setBackground(new Color(0, 0, 0, 0)); // Transparent background to handle rounded corners properly

        // Custom panel with rounded borders
        RoundedPanel roundedPanel = new RoundedPanel();
        roundedPanel.setLayout(new BorderLayout());
        roundedPanel.setBackground(Color.DARK_GRAY);

        // Add a label with an image or text
        JLabel label = new JLabel("Welcome to Serenity", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setOpaque(false);
        label.setForeground(Color.WHITE);
        roundedPanel.add(label, BorderLayout.CENTER);

        setContentPane(roundedPanel);

        // Initialize the fade-in timer
        fadeInTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    fadeInTimer.stop();
                    // Start the fade-out timer after a delay
                    new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            fadeOutTimer.start();
                        }
                    }).start();
                }
                setOpacity(opacity);
            }
        });

        // Initialize the fade-out timer
        fadeOutTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                if (opacity <= 0.0f) {
                    opacity = 0.0f;
                    fadeOutTimer.stop();
                    dispose();  // Close the splash screen
                    // Launch the main application window here
                    launchMainApp(productManager);
                }
                setOpacity(opacity);
            }
        });
    }
    public Splash(Assets.Database.ProductManager productManager) {
        // Set the size and position of the splash screen
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Make the window undecorated and set a rounded shape
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50));
        setBackground(new Color(0, 0, 0, 0)); // Transparent background to handle rounded corners properly

        // Custom panel with rounded borders
        RoundedPanel roundedPanel = new RoundedPanel();
        roundedPanel.setLayout(new BorderLayout());
        roundedPanel.setBackground(Color.DARK_GRAY);

        // Add a label with an image or text
        JLabel label = new JLabel("Welcome to Serenity", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setOpaque(false);
        label.setForeground(Color.WHITE);
        roundedPanel.add(label, BorderLayout.CENTER);

        setContentPane(roundedPanel);

        // Initialize the fade-in timer
        fadeInTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    fadeInTimer.stop();
                    // Start the fade-out timer after a delay
                    new Timer(2000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            fadeOutTimer.start();
                        }
                    }).start();
                }
                setOpacity(opacity);
            }
        });

        // Initialize the fade-out timer
        fadeOutTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                if (opacity <= 0.0f) {
                    opacity = 0.0f;
                    fadeOutTimer.stop();
                    dispose();  // Close the splash screen
                    // Launch the main application window here
                    launchMainApp(productManager);
                }
                setOpacity(opacity);
            }
        });
    }

    public void showSplash() {
        setVisible(true);
        fadeInTimer.start();
    }

    private void launchMainApp(ProductManager productManager){
        if(!mainAppLaunched){
            mainAppLaunched = true;
            SwingUtilities.invokeLater(() -> {
                try {
                    new Startup(productManager);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void launchMainApp(Assets.Database.ProductManager productManager){
        if(!mainAppLaunched){
            mainAppLaunched = true;
            SwingUtilities.invokeLater(() -> {
                try {
                    new Startup(productManager);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    // Custom JPanel with rounded corners
    private class RoundedPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arcWidth = 50;
            int arcHeight = 50;
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

            g2d.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            super.paintBorder(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arcWidth = 50;
            int arcHeight = 50;
            g2d.setColor(Color.GRAY);
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);

            g2d.dispose();
        }
    }
}
