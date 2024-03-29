import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NextLevel extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    //The buttons for this screen
    private boolean hover = false;
    private static String buttonName;
    private static String title;
    private int[] pos = new int[3];
    private int height;
    public static int padding = 15;

    public static boolean inEditor; //if we were previously in the editor

    //Initalization
    public NextLevel() {
        inEditor = false;
    }
    
    public static void checkNext() {
        //Setting the button titles
        if (GamePanel.isCustomLevel) {
            title = "YAY!";
            if(inEditor){
                buttonName = "Back to editor";
            }
            else{
                buttonName = "Home";
            }
            
        }
        else {
            if (GamePanel.currentLevel == GamePanel.lastMap) {
                title = "Congrats you beat the game!";
                buttonName = "Home";
            }
            else {
                title = "YAY!";
                buttonName = "Next level";
            }
        }
    }

    //Double buffer all drawn items
    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        //Draw background
        g.drawImage(GameFrame.backgroundImage[5], 0, 0, this);

        //Draw title
        g.setColor(java.awt.Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
        g.drawString(title, (GamePanel.GAME_WIDTH - g.getFontMetrics().stringWidth(title)) / 2, (GamePanel.GAME_HEIGHT) / 4);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        
        //button details
        g.getFontMetrics().stringWidth(buttonName);
        height = (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent());
        pos[2] = g.getFontMetrics().stringWidth(buttonName);

        pos[0] = (GamePanel.GAME_WIDTH - pos[2])/2;
        pos[1]= (GamePanel.GAME_HEIGHT - height)/2;
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        //hover condition and draw button
        if (hover) {
            g.setColor(java.awt.Color.darkGray);
        }
        else {
            g.setColor(java.awt.Color.gray);
        }
        g2.drawRoundRect(pos[0] - padding, pos[1] - height - padding, pos[2] + padding*2 , height + padding*2, 30, 30);
        g2.fillRoundRect(pos[0] - padding, pos[1] - height - padding, pos[2] + padding*2 , height + padding*2, 30, 30);
        g.setColor(java.awt.Color.white);
        g2.drawString(buttonName, pos[0], pos[1]);
    }

    public void mousePressed(MouseEvent e){
        //mouse hovering over button condition
        if(hover) {
            if (GamePanel.isCustomLevel || GamePanel.currentLevel == GamePanel.lastMap) {
                //if in editor, go back to editor, otherwise, back to menu screen
                if(inEditor){
                    GamePanel.setScreen = -1;
                }
                else{
                    GamePanel.setScreen = 0;
                }
            }
            else { //Go the next available level
                GamePanel.currentLevel++;
                Maps.loadMap("level_"+GamePanel.currentLevel);
                GamePanel.setScreen = 2;
            }
        }
    }

    //use mouse position to see if button is hovered or not
    public void mousePosition(int x, int y){
        if(x > pos[0] - padding && x < pos[0] - padding + pos[2] + padding*2 && y > pos[1] - height - padding && y < pos[1] + padding*2 - 13){
            hover = true;
        }
        else{
            hover = false;
        }
    }
}