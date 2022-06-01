import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JPanel implements KeyListener{

  public Thread gameThread;
  public Image image;
  public Graphics graphics;

  public Menu(){

  }

  
  public void paint(Graphics g){ 
    image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT); 
    graphics = image.getGraphics();
    draw(graphics);
    g.drawImage(image, 0, 0, this); 
  }

  public void draw(Graphics g){
    g.drawImage(GameFrame.backgroundImage[0], 0, 0, this);

    String title = "Put Cringy Title Here";
    g.setColor(java.awt.Color.green);
    g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
    int width = g.getFontMetrics().stringWidth(title);

    g.drawString(title, (GamePanel.GAME_WIDTH-width)/2, GamePanel.GAME_HEIGHT/2);
  }


  public void keyPressed(KeyEvent e){

  }

  public void keyReleased(KeyEvent e){
    GamePanel.screen(1);
  }

  public void keyTyped(KeyEvent e){

  }
}