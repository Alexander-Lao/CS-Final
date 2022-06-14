//Screen -3, but also runs in all files as a "pause button" in top right corner

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Settings extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    private boolean hover;

    private String title;

    private int previousScreen;
    // private double previousTime;

    private int settingsX;
    private int settingsY;

    public Settings(){
        title = "Pause";
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
            if(hover){

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
        }
    }
    

    public void mousePressed(MouseEvent e){
        if(GamePanel.screen != -3){ //if not on pause screen, draw pause button in top right corner
            if(hover){ 
                previousScreen = GamePanel.screen;
                // previousTime = GamePanel.time;
                GamePanel.screen(-3);
            }
        }
        else{
            GamePanel.screen(previousScreen);
            // GamePanel.timeReset = previousTime;
        }

    }

    public void mousePosition(int x, int y){
        if(GamePanel.screen != -3){ //if not on pause screen, draw pause button in top right corner
            if(x > settingsX - Maps.blockSize && x < settingsX + Maps.blockSize && y > settingsY && y < settingsY + Maps.blockSize){
                hover = true;
            }
            else{
                hover = false;
            }
        }

    }
}
