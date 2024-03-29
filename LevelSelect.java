//Screen 1

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LevelSelect extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    //Button details
    private boolean[] hover = new boolean[4];
    private int[][] pos = new int[4][3]; // x y width 
    private String[] buttonName = new String[4];

    private int height;
    public static int padding;

    //Initalization
    public LevelSelect() {
        buttonName[0] = "Level 1";
        buttonName[1] = "Level 2";
        buttonName[2] = "Level 3";
        buttonName[3] = "Custom Levels";
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
        //Background
        g.drawImage(GameFrame.backgroundImage[3], 0, 0, this);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));

        //Initalizing all button detials (height width x y etc...)
        for(int i = 0; i < pos.length; i++){
            pos[i][2] = g.getFontMetrics().stringWidth(buttonName[i]);
        }
        height = (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent());
        for (int i=0; i<pos.length; i++) {
            pos[i][0] = (GamePanel.GAME_WIDTH - pos[i][2])/2;
        }
        for (int i=0; i<pos.length; i++) {
            pos[i][1]= i*(GamePanel.GAME_HEIGHT - 300)/pos.length + 300;
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        for(int i = 0; i < pos.length; i++){   
            //hover condition and draw button
            if(hover[i]){
                g.setColor(java.awt.Color.darkGray);
            }
            else{
                g.setColor(java.awt.Color.gray);
            }
            
            g2.drawRoundRect(pos[i][0] - padding, pos[i][1] - height - padding, pos[i][2] + padding*2 , height + padding*2, 30, 30);
            g2.fillRoundRect(pos[i][0] - padding, pos[i][1] - height - padding, pos[i][2] + padding*2 , height + padding*2, 30, 30);
            
            g.setColor(java.awt.Color.white);
            g2.drawString(buttonName[i], pos[i][0], pos[i][1]);
        }
    }

    //if buttons are hovered, do on mouse pressed
    public void mousePressed(MouseEvent e){
        if(hover[0]){
            Maps.loadMap("level_1");
            GamePanel.setScreen = 2;
            GamePanel.currentLevel = 1;
        }
        else if(hover[1]){
            Maps.loadMap("level_2");
            GamePanel.setScreen = 2;
            GamePanel.currentLevel = 2;
        }
        else if(hover[2]){
            Maps.loadMap("level_3");
            GamePanel.setScreen = 2;
            GamePanel.currentLevel = 3;
        }
        else if (hover[3]) {
            new CustomLevelDropdown();
        }
    }

    //use mouse position to see if hovering over a button
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