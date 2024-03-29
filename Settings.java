//Screen -3, but also runs in all files as a "pause button" in top right corner

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Settings extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;
    private boolean settingsHover;
    private int previousScreen;

    //Position of settings title
    private int settingsX;
    private int settingsY;
    public double previousTime;

    //array holding the buttons with their position, heights, and whether they are hovered or not
    private int height;
    private boolean[] hover = new boolean[3];
    private int[][] pos = new int[3][3]; // x y width 
    private String[] names = new String[3]; //Menu, Keybinds back

    private int loopTimes;

    //Initalization
    public Settings(){
        names[0] = "Menu";
        names[1] = "Key Binds";
        names[2] = "Back";
    }

    //Double buffer all drawn items
    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        if(GamePanel.screen != -3){ //if not on pause screen, draw pause button 

            //Location of settings screen button
            if(GamePanel.screen == 0){
                settingsX = GamePanel.GAME_WIDTH - Maps.blockSize;
                settingsY = GamePanel.GAME_HEIGHT - Maps.blockSize;
            }
            else{
                settingsX = GamePanel.GAME_WIDTH - Maps.blockSize;
                settingsY = 0;
            }
            //Draw settings button
            g.drawImage(GameFrame.blocks[15], settingsX, settingsY, null);
        }
        else{ //When in the settings panel
            //Draw background
            g.drawImage(GameFrame.backgroundImage[4], 0, 0, this);

            g.setFont(new Font("TimesRoman", Font.PLAIN, 40));

            //set the width
            for(int i = 0; i < pos.length; i++){
                pos[i][2] = g.getFontMetrics().stringWidth(names[i]);
            }
            //all text same font and size have same height
            height = (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent());
    
            //Setting the x and y position of each button
            pos[0][0] = (GamePanel.GAME_WIDTH - pos[0][2])/2;
            pos[0][1]= (GamePanel.GAME_HEIGHT - height)/2;
            pos[1][0] = (GamePanel.GAME_WIDTH - pos[1][2])/2;
            pos[1][1] = (GamePanel.GAME_HEIGHT - height)*11/16;
            pos[2][0] = 50;
            pos[2][1] = 60;

            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));

            //If statement whether to draw the back button or not (loop one less time if we dont want to draw back button)
            if(previousScreen > 0 || previousScreen == -1 || previousScreen == -2){
                loopTimes = pos.length;
            }
            else{
                loopTimes = pos.length-1;
            }

            //Actual drawing button function and its hover condition
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
    
    //
    public void mousePressed(MouseEvent e){
        if(GamePanel.screen != -3){ //if not on settings screen already, find if setting button is pressed 
            if(settingsHover){ 
                previousScreen = GamePanel.screen;
                previousTime = GamePanel.time;
                GamePanel.setScreen = -3;
            }
        }
        else{//Button mouse input in settings
            if(hover[0]){ //Menu
                GamePanel.setScreen = 0;
                NextLevel.inEditor = false;
                HowToPlay.drawScreen = 0;
            }
            else if(hover[1]){ //Key Binds
                GamePanel.setScreen = -4;
            }
            else if(hover[2]){ //Back Button
                GamePanel.setScreen = previousScreen;
                GamePanel.timeReset = previousTime;
            }
        }

    }

    //If esc is pressed, open settings
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && GamePanel.screen!=-3) {
            previousScreen = GamePanel.screen;
            previousTime = GamePanel.time;
            GamePanel.setScreen = -3;
        }
    }

    //Try commenting these two out I dont think we need them overridden for subclasses?
    public void keyReleased(KeyEvent e) {

    }
    
    public void keyTyped(KeyEvent e) {
        //
    }

    //This gives constant updates to the mouse position for location when mouse is ontop of a button or not
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
