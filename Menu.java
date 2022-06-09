import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    private boolean[] hover = new boolean[3];
    private int[][] pos = new int[3][3]; // x y width 
    private String[] names = new String[3]; //play, howToPlay, settings

    private String title;
    private int height;


    public Menu() {
        title = "Put Cringy Title Here";
        names[0] = "Play";
        names[1] = "How To Play";
        names[2] = "Settings";
    }

    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        g.drawImage(GameFrame.backgroundImage[0], 0, 0, this);

        g.setColor(java.awt.Color.green);
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
        pos[2][0] = (GamePanel.GAME_WIDTH - pos[2][2]) / 2;
        pos[2][1] = (GamePanel.GAME_HEIGHT - height)*7/8;

        for(int i = 0; i < pos.length; i++){
            if(hover[i]){
                g.setColor(java.awt.Color.red);
            }
            else{
                g.setColor(java.awt.Color.green);
            }
            g.drawString(names[i], pos[i][0], pos[i][1]);
        }
    }

    public void mousePressed(MouseEvent e){
        if(hover[0]){ //play
            GamePanel.screen(1);
            // GamePanel.screen(-3);
        }
        else if(hover[1]){ // howToPlay
            GamePanel.screen(-1);
        }
        else if(hover[2]){ // settings
            GamePanel.screen(-2);
        }
    }

    public void mousePosition(int x, int y){
        for(int i = 0; i < pos.length; i++){
            if(x > pos[i][0] && x < pos[i][0] + pos[i][2] && y > pos[i][1] - height && y < pos[i][1]){
                hover[i] = true;
            }
            else{
                hover[i] = false;
            }
        }
    }
}