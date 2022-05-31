import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Menu extends JPanel implements KeyListener{

  public Thread gameThread;
  public Image image;
  public Graphics graphics;
  private BufferedImage backgroundImage = null;

  public Menu(){

    try {
        backgroundImage = GameFrame.resize(ImageIO.read(new File("Menu picture.png")),GamePanel.GAME_WIDTH,GamePanel.GAME_HEIGHT);
        
    } catch (IOException e) {

    }
  }

  
  public void paint(Graphics g){ 
    image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT); 
    graphics = image.getGraphics();
    draw(graphics);
    g.drawImage(image, 0, 0, this); 
  }

  public void draw(Graphics g){
    g.drawImage(backgroundImage, 0, 0, this);

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