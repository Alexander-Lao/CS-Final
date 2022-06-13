//Screen -2

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HowToPlay extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    private String back;
    private int backWidth;
    private int height;
    private int backX;
    private int backY;
    private boolean backHover;

    private String title;


    public HowToPlay() {
        back = "Back";
        backHover = false;
        title = "How To Play";
    }

    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        g.drawImage(GameFrame.backgroundImage[3], 0, 0, this);

        g.setColor(java.awt.Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
        g.drawString(title, (GamePanel.GAME_WIDTH - g.getFontMetrics().stringWidth(title)) / 2, (GamePanel.GAME_HEIGHT) / 4);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        backWidth = g.getFontMetrics().stringWidth(back);
        height = (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent());

        backX = 50;
        backY = 50;

        if(backHover){
            g.setColor(java.awt.Color.red);
        }
        else{
            g.setColor(java.awt.Color.green);
        }
        g.drawString(back, backX , backY);
    }

    public void mousePressed(MouseEvent e){
        if(backHover){
            GamePanel.screen(0);
        }

    }

    public void mousePosition(int x, int y){
        if(x > backX && x < backX + backWidth && y > backY - height && y < backY){
            backHover = true;
        }
        else{
            backHover = false;
        }
    }
}