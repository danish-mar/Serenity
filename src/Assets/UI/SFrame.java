package Assets.UI;

import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;

public class SFrame extends JFrame {
    public SFrame(){
        setUndecorated(true);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        setContentPane(new RoundedPanel());


    }



    public static void main(String[] args) {

        SFrame sf = new SFrame();
        sf.setVisible(true);

    }
}
