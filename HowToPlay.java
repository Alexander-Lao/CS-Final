//Screen -2

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class HowToPlay extends JPanel {
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    private int drawScreen;

    public HowToPlay() {
        drawScreen = 0;
    }

    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        // Draws the Game Animations onto the HowToPlay page

        if(drawScreen == 0){
            g.drawImage(GameFrame.gifs[0], 0, 0, 600, 350, null); // Top Left
            g.drawImage(GameFrame.gifs[1], 0, 350, 600, 350, null); // Bottom Left
            g.drawImage(GameFrame.gifs[5], 600, 0, 600, 700, null); 
        }
        else if(drawScreen == 1){
            g.drawImage(GameFrame.gifs[2], 0, 350, 600, 350, null); // Bottom Left
            g.drawImage(GameFrame.gifs[3], 0, 0, 600, 350, null); // Top Left
            g.drawImage(GameFrame.gifs[6], 600, 0, 600, 700, null); 
        }
        else if(drawScreen == 2){
            g.drawImage(GameFrame.gifs[4], 0, 0, 1200, 700, null);
        }
        else if(drawScreen == 3){
            g.drawImage(GameFrame.gifs[7], 0, 0, 1200, 700, null);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.drawString("In the note adding editor, press [p] to place a note in the desired location", 100, 80);
            g.drawString("Press [m] to save", 100, 150);
        }
        else{
            GamePanel.setScreen = 0;
            drawScreen = 0;
        }
    }

    public void keyPressed(KeyEvent e){
        drawScreen++;
    }
}