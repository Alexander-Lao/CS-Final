//Screen 0

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NextLevel extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    private boolean hover = false;
    private String buttonName;

    private String title;
    private int[] pos = new int[3];
    private int height;
    public static int padding;


    public NextLevel() {
        if (GamePanel.nextScreen > GamePanel.lastMap) {
            title = "Congrats you beat the game!";
            buttonName = "Home";
        }
        else {
            title = "YAY!";
            buttonName = "Next level";
        }
        padding = 15;
    }

    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        g.drawImage(GameFrame.backgroundImage[0], 0, 0, this);

        g.setColor(java.awt.Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
        g.drawString(title, (GamePanel.GAME_WIDTH - g.getFontMetrics().stringWidth(title)) / 2, (GamePanel.GAME_HEIGHT) / 4);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));

        g.getFontMetrics().stringWidth(buttonName);
        height = (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent());
        pos[2] = g.getFontMetrics().stringWidth(buttonName);

        pos[0] = (GamePanel.GAME_WIDTH - pos[2])/2;
        pos[1]= (GamePanel.GAME_HEIGHT - height)/2;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        if(hover){
            g.setColor(java.awt.Color.darkGray);
        }
        else{
            g.setColor(java.awt.Color.gray);
        }
        g2.drawRoundRect(pos[0] - padding, pos[1] - height - padding, pos[2] + padding*2 , height + padding*2, 30, 30);
        g2.fillRoundRect(pos[0] - padding, pos[1] - height - padding, pos[2] + padding*2 , height + padding*2, 30, 30);
        g.setColor(java.awt.Color.white);
        g2.drawString(buttonName, pos[0], pos[1]);
    }

    public void mousePressed(MouseEvent e){
        if(hover) {
            if (GamePanel.nextScreen > GamePanel.lastMap) {
                GamePanel.setScreen = 0;
            }
            else {
                GamePanel.setScreen = GamePanel.nextScreen;
            }
        }
    }

    public void mousePosition(int x, int y){
        if(x > pos[0] - padding && x < pos[0] - padding + pos[2] + padding*2 && y > pos[1] - height - padding && y < pos[1] + padding*2 - 13){
            hover = true;
        }
        else{
            hover = false;
        }
    }
}