import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Settings extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    private String back;
    private int backWidth;
    private int height;
    private int backX;
    private int backY;
    private boolean backHover;


    public Settings() {
        back = "Back";
        backHover = false;
    }

    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        g.drawImage(GameFrame.backgroundImage[3], 0, 0, this);
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