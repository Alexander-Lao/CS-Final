//Screen 0

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    //Button details
    private boolean[] hover = new boolean[3];
    private int[][] pos = new int[3][3]; // x y width 
    private String[] names = new String[3]; //play, howToPlay, settings

    private String title;
    private int height;
    public static int padding;

    //Initialization
    public Menu() {
        title = "Pellets Lmao";
        names[0] = "Play";
        names[1] = "Level Editor";
        names[2] = "How to Play";
        padding = 15;
    }

    //Double buffer
    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        //Draw background
        g.drawImage(GameFrame.backgroundImage[0], 0, 0, this);

        //Draw title
        g.setColor(java.awt.Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
        g.drawString(title, (GamePanel.GAME_WIDTH - g.getFontMetrics().stringWidth(title)) / 2, (GamePanel.GAME_HEIGHT) / 4);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));

        //create values for the position and details related to the button
        for(int i = 0; i < pos.length; i++){
            pos[i][2] = g.getFontMetrics().stringWidth(names[i]);
        }
        height = (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent());

        pos[0][0] = (GamePanel.GAME_WIDTH - pos[0][2])/2;
        pos[0][1]= (GamePanel.GAME_HEIGHT - height)/2;
        pos[1][0] = (GamePanel.GAME_WIDTH - pos[1][2])/2;
        pos[1][1] = (GamePanel.GAME_HEIGHT - height)*11/16;
        pos[2][0] = (GamePanel.GAME_WIDTH - pos[2][2]) / 2;
        pos[2][1] = (GamePanel.GAME_HEIGHT - height)*7/8;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        //hover condition and draw actual buttons
        for(int i = 0; i < pos.length; i++){      
            if(hover[i]){
                g.setColor(java.awt.Color.darkGray);
            }
            else{
                g.setColor(java.awt.Color.gray);
            }
            
            g2.drawRoundRect(pos[i][0] - padding, pos[i][1] - height - padding, pos[i][2] + padding*2 , height + padding*2, 30, 30);
            g2.fillRoundRect(pos[i][0] - padding, pos[i][1] - height - padding, pos[i][2] + padding*2 , height + padding*2, 30, 30);
            
            g.setColor(java.awt.Color.white);
            g2.drawString(names[i], pos[i][0], pos[i][1]);
        }
    }

    //find what button is pressed
    public void mousePressed(MouseEvent e){
        if(hover[0]){ //play
            GamePanel.setScreen = 1;
        }
        else if(hover[1]){ // level editor
            GamePanel.setScreen = -1;
        }
        else if(hover[2]){ // How to play
            GamePanel.setScreen = -2;
        }
    }

    //mouse location to find whether mouse is hovering over a button
    public void mousePosition(int x, int y){
        for(int i = 0; i < pos.length; i++){
            if(x > pos[i][0] - padding && x < pos[i][0] - padding + pos[i][2] + padding*2 && y > pos[i][1] - height - padding && y < pos[i][1] + padding*2 - 13){
                hover[i] = true;
            }
            else{
                hover[i] = false;
            }
        }
    }
}