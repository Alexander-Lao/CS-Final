//Screen -2

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import javax.swing.*;

public class HowToPlay extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    public HowToPlay() {
        // JFrame f = new JFrame();
        // JPanel p = new JPanel();
        // JLabel l = new JLabel();
        // ImageIcon icon = new ImageIcon(GameFrame.icon);
        // // f.setSize(1200, 700);
        // l.setIcon(icon);
        // p.add(l);
        // f.getContentPane().add(p);
        // f.setLocationRelativeTo(null);
        // f.setResizable(false);
        // f.pack();
        // f.setVisible(true);
        // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        g.drawImage(GameFrame.icon, 0, 0, this);
        // g.drawString("iterator", 50, 50);
        //1000 -> 4 sec
        // if(GamePanel.time < 1000){
            // g.drawString("iterator", x, y);
        // }
    }
}