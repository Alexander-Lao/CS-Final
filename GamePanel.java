import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener{

  //dimensions of window
  public static final int GAME_WIDTH = 1000;
  public static final int GAME_HEIGHT = 800;

  public Thread gameThread;
  public Image image;
  public Graphics graphics;
  // public Game game;
  public Maps maps;
  public Menu menu;
  public static int[] grid[][] = new int[2][100][500];

  public static int screen = 0; 
  public static double time = 0;


  public GamePanel(){

    maps = new Maps();
    menu = new Menu();

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
      menu.draw(g);
    }
    else if(screen > 0){
      displayMap(g);
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
      time += (now-lastTime)/ns;
      lastTime = now;

      if(delta >= 1){
        if(screen == 0){
          
        }
        else if(screen == 1){

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

    }
  }

  public void keyReleased(KeyEvent e){
    if(screen == 0){
      menu.keyReleased(e);
    }
    else if(screen == 1){
      if(e.getKeyChar() == '1'){
        screen(2);
      }
      
    }
  }

  public void keyTyped(KeyEvent e){

  }

  private void displayMap(Graphics g){

    g.drawImage(GameFrame.backgroundImage[1], 0, 0, this);

    for (int i = 0; i < grid[screen-1][0].length; i++){
      for(int j = 0; j < grid[screen-1].length; j++){

        int pos= grid[screen-1][j][i];

        if(pos == 1){
          g.setColor(java.awt.Color.yellow);

        }
        else if(pos == 2){
          g.setColor(java.awt.Color.red);
        }
        else if(pos == 3){
          g.setColor(java.awt.Color.blue);
        } 
        else if(pos == 4){
          g.setColor(java.awt.Color.black);
        }

        if(pos != 0){
          g.fillRect(i*50-(int)(time), j*50, 50, 50);
        }
      }
    }    
  }

  public static void screen(int s){
    screen = s;
    time = 0;
  }
}