//Screen -3, but also runs in all files as a "pause button" in top right corner

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Settings extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;
    private boolean settingsHover;
    private String title;
    private String volumeSlider;
    private int previousScreen;
    // private double previousTime;

    private int settingsX;
    private int settingsY;
    public double previousTime;

    private int height;
    private boolean[] hover = new boolean[3];
    private int[][] pos = new int[3][3]; // x y width 
    private String[] names = new String[3]; //Menu, Keybinds back

    private int loopTimes;

    public Settings(){
        title = "Settings";
        volumeSlider = "Volume Slider:";

        names[0] = "Menu";
        names[1] = "Key Binds";
        names[2] = "Back";
    }

    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        if(GamePanel.screen != -3){ //if not on pause screen, draw pause button 

            if(GamePanel.screen == 0){
                settingsX = GamePanel.GAME_WIDTH - Maps.blockSize;
                settingsY = GamePanel.GAME_HEIGHT - Maps.blockSize;
            }
            else{
                settingsX = GamePanel.GAME_WIDTH - Maps.blockSize;
                settingsY = 0;
            }

            g.drawImage(GameFrame.blocks[15], settingsX, settingsY, null);

            //Draw hover over settings effect
            if(settingsHover){

            }
            else{
                
            }
        }
        else{
            g.drawImage(GameFrame.backgroundImage[0], 0, 0, this);

            g.setColor(java.awt.Color.black);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
            g.drawString(title, (GamePanel.GAME_WIDTH - g.getFontMetrics().stringWidth(title)) / 2, (GamePanel.GAME_HEIGHT) / 4);

            g.setFont(new Font("TimesRoman", Font.PLAIN, 40));

            for(int i = 0; i < pos.length; i++){
                pos[i][2] = g.getFontMetrics().stringWidth(names[i]);
            }
            height = (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent());
    
            pos[0][0] = (GamePanel.GAME_WIDTH - pos[0][2])/2;
            pos[0][1]= (GamePanel.GAME_HEIGHT - height)/2;
            pos[1][0] = (GamePanel.GAME_WIDTH - pos[1][2])/2;
            pos[1][1] = (GamePanel.GAME_HEIGHT - height)*11/16;
            pos[2][0] = 50;
            pos[2][1] = 60;

            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));

            if(previousScreen > 0 || previousScreen == -1 || previousScreen == -2){
                loopTimes = pos.length;
            }
            else{
                loopTimes = pos.length-1;
            }

            for(int i = 0; i < loopTimes; i++){      
                if(hover[i]){
                    g.setColor(java.awt.Color.darkGray);
                }
                else{
                    g.setColor(java.awt.Color.gray);
                }
                
                g2.drawRoundRect(pos[i][0] - Menu.padding, pos[i][1] - height - Menu.padding, pos[i][2] + Menu.padding*2 , height + Menu.padding*2, 30, 30);
                g2.fillRoundRect(pos[i][0] - Menu.padding, pos[i][1] - height - Menu.padding, pos[i][2] + Menu.padding*2 , height + Menu.padding*2, 30, 30);
                
                g.setColor(java.awt.Color.white);
                g2.drawString(names[i], pos[i][0], pos[i][1]);
            }
        }
    }
    

    public void mousePressed(MouseEvent e){
        if(GamePanel.screen != -3){ //if not on pause screen, draw pause button in top right corner
            if(settingsHover){ 
                previousScreen = GamePanel.screen;
                previousTime = GamePanel.time;
                GamePanel.screen(-3);
            }
        }
        else{
            if(hover[0]){ //Menu
                GamePanel.screen(0);
            }
            else if(hover[1]){ //Key Binds
                GamePanel.screen(-4);
            }
            else if(hover[2]){ //Back Button
                GamePanel.screen(previousScreen);
            }
        }

    }

    public void mousePosition(int x, int y){
        if(GamePanel.screen != -3){ //if not on pause screen, draw pause button in top right corner
            if(x > settingsX - Maps.blockSize && x < settingsX + Maps.blockSize && y > settingsY && y < settingsY + Maps.blockSize){
                settingsHover = true;
            }
            else{
                settingsHover = false;
            }
        }
        else{
            for(int i = 0; i < loopTimes; i++){
                if(x > pos[i][0] - Menu.padding && x < pos[i][0] - Menu.padding + pos[i][2] + Menu.padding*2 && y > pos[i][1] - height - Menu.padding && y < pos[i][1] + Menu.padding*2 - 13){
                    hover[i] = true;
                }
                else{
                    hover[i] = false;
                }
            }
        }

    }
}
