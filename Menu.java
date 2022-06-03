import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    private String title;
    private String play;
    private String howToPlay;
    private String settings;

    private int playWidth;
    private int howToPlayWidth;
    private int settingsWidth;
    private int height;

    private int playX;
    private int playY;
    private int howToPlayX;
    private int howToPlayY;
    private int settingsX;
    private int settingsY;

    private boolean playHover;
    private boolean howToPlayHover;
    private boolean settingsHover;


    public Menu() {
        title = "Put Cringy Title Here";
        play = "Play";
        howToPlay = "How To Play";
        settings = "Settings";

        playHover = false;
        howToPlayHover = false;
        settingsHover = false;
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
        playWidth = g.getFontMetrics().stringWidth(play);
        howToPlayWidth = g.getFontMetrics().stringWidth(howToPlay);
        settingsWidth = g.getFontMetrics().stringWidth(settings);
        height = (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent());

        playX = (GamePanel.GAME_WIDTH - playWidth)/2;
        playY = (GamePanel.GAME_HEIGHT - height)/2;
        howToPlayX = (GamePanel.GAME_WIDTH - howToPlayWidth)/2;
        howToPlayY = (GamePanel.GAME_HEIGHT - height)*11/16;
        settingsX = (GamePanel.GAME_WIDTH - settingsWidth) / 2;
        settingsY = (GamePanel.GAME_HEIGHT - height)*7/8;

        if(playHover){
            g.setColor(java.awt.Color.red);
        }
        else{
            g.setColor(java.awt.Color.green);
        }
        g.drawString(play, playX , playY);

        if(howToPlayHover){
            g.setColor(java.awt.Color.red);
        }
        else{
            g.setColor(java.awt.Color.green);
        }
        g.drawString(howToPlay, howToPlayX , howToPlayY);

        if(settingsHover){
            g.setColor(java.awt.Color.red);
        }
        else{
            g.setColor(java.awt.Color.green);
        }
        g.drawString(settings, settingsX, settingsY);
    }

    public void mousePressed(MouseEvent e){
        if(playHover){
            GamePanel.screen(1);
        }
        else if(howToPlayHover){
            GamePanel.screen(-1);
        }
        else if(settingsHover){
            GamePanel.screen(-2);
        }
    }

    public void mousePosition(int x, int y){
        if(x > playX && x < playX +playWidth && y > playY - height && y < playY){
            playHover = true;
        }
        else{
            playHover = false;
        }

        if(x > howToPlayX && x < howToPlayX +howToPlayWidth && y > howToPlayY - height && y < howToPlayY){
            howToPlayHover = true;
        }
        else{
            howToPlayHover = false;
        }

        if(x > settingsX && x < settingsX +settingsWidth && y > settingsY - height && y < settingsY){
            settingsHover = true;
        }
        else{
            settingsHover = false;
        }
    }
}