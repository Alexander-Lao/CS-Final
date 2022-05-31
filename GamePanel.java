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
  public Game game;
  public Maps maps;
  public static int[] grid[][] = new int[2][][];

  private int screen = 0; 
  private double time = 0;


  public GamePanel(){

    maps();
    maps = new Maps();

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
    displayMap(g);

    if(screen == 0){
      //g.fillRect(0, 0, 100, 100);
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
      time += (now-lastTime)/ns;
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

  private void displayMap(Graphics g){
    if (screen >= 0){
      for (int i = 0; i < grid[screen].length; i++){
        for(int j = 0; j < grid[screen][0].length; j++){

          int pos= grid[screen][j][i];

          switch (pos){
            
              case 0:
                g.setColor(java.awt.Color.black);
                g.fillRect(i*50-(int)(time), j*50, 50, 50);
                break;
              case 1:
                g.setColor(java.awt.Color.yellow);
                g.fillRect(i*50-(int)(time), j*50, 50, 50);
                break;
              case 2:  
                g.setColor(java.awt.Color.red);
                g.fillRect(i*50-(int)(time), j*50, 50, 50);
                break;
              case 3:  
                g.setColor(java.awt.Color.blue);
                g.fillRect(i*50-(int)(time), j*50, 50, 50);
                break;  
              case 4:
                //White
                  break;
          }
        }
      }
    }
  }

  private void maps(){

    int[][] grid0 ={{1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,3,1,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,0,0,0,0,0,1},
                    {1,0,0,0,0,1,0,0,0,1},
                    {1,0,0,0,0,1,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,1},
                    {1,4,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1}};


    int[][] grid1 ={{1,1,1,1,1,1,1,1,1,1},
                    {1,0,2,0,0,0,0,1,0,1},
                    {1,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,3,0,2,0,0,1},
                    {1,1,1,0,0,0,1,0,1,1},
                    {1,0,0,0,0,0,1,0,0,1},
                    {1,0,0,0,0,0,1,0,0,1},
                    {1,0,0,0,0,1,1,0,0,1},
                    {1,4,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1}};

    grid[0] = grid0;
    grid[1] = grid1;
  }

}