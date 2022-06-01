import java.awt.*;
import java.awt.event.*;

public class Player extends Rectangle{
    
    private int velocity;
    public boolean blockAbove;
    public boolean blockBelow;

    private boolean keyIsPressed;

    public Player(){
        super(100, 100, 50, 50);
        velocity = 10;
        blockAbove = false;
        blockBelow = false;
        keyIsPressed = false;
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyChar() == 'q' && !keyIsPressed){
            keyIsPressed = true;
            if(blockAbove){
                velocity = 10;
                blockAbove = false;
            }
            else if(blockBelow){
                velocity = -10;
                blockBelow = false;
            }
        }
    }

    public void keyReleased(KeyEvent e){
        if(e.getKeyChar() == 'q'){
            keyIsPressed = false;
        }

    }

    public void blockCollision(){
        //Check block above
        if(GamePanel.grid[GamePanel.screen-1][(int)(y-1)/Maps.blockHeight][(int)(x + (int)GamePanel.time)/Maps.blockWidth] > 0
        || GamePanel.grid[GamePanel.screen-1][(int)(y-1)/Maps.blockHeight][(int)(x + (int)GamePanel.time)/Maps.blockWidth + 1] > 0){
            //Set player to proper Y position
            y = (y+49) - (y+49)%50;
            blockAbove = true;
        }
        else{
            blockAbove = false;
        }
    }

    public void move(){
        if(!blockAbove && !blockBelow){
            y+=velocity;
        }
    }

    public void draw(Graphics g){
        g.setColor(Color.black);
        g.fillRect(x, y, width, height);
    }
}