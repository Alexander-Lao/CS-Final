import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener{

  //dimensions of window
  public static final int GAME_WIDTH = 250;
  public static final int GAME_HEIGHT = 250;

  public Thread gameThread;
  public Image image;
  public Graphics graphics;
  public Game game;

  private int screen = 0; 


  public GamePanel(){
    this.setFocusable(true); 
    this.addKeyListener(this); 
    
    addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {

			}
		});
    this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

    gameThread = new Thread(this);
    gameThread.start();
  }

 
  public void paint(Graphics g){
    image = createImage(GAME_WIDTH, GAME_HEIGHT); 
    graphics = image.getGraphics();
    draw(graphics);
    g.drawImage(image, 0, 0, this); 

  }

  public void draw(Graphics g){
    if(screen == 0){
      g.fillRect(0, 0, 100, 100);
    }
    else if(screen == 1){
      game.draw(g);
    }
    
  }
  

  public void run(){
    
    long lastTime = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000/amountOfTicks;
    double delta = 0;
    long now;
    
    

    while(true){
      now = System.nanoTime();
      delta = delta + (now-lastTime)/ns;
      lastTime = now;

      if(delta >= 1){
        if(screen == 0){
          
        }
        else if(screen == 1){
          game.move();
          game.checkCollision();
        }

        repaint();
        delta--;
      }
    }
  }

  public void keyPressed(KeyEvent e){
    if(screen == 0){
      
    }
    else if(screen == 1){
      game.keyPressed(e);
    }
  }

  public void keyReleased(KeyEvent e){
    if(screen == 0){
      game = new Game();
      screen = 1;
    }
    else if(screen == 1){
      game.keyReleased(e);
    }
  }

  public void keyTyped(KeyEvent e){

  }
}