import java.awt.*;
import java.awt.event.*;

public class Player extends Rectangle{
    
    private int velocity;
    public boolean blockAbove;
    public boolean blockBelow;

    private boolean keyIsPressed;

    public Player(){
        super(100, 610, 50, 50);
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
        if(GamePanel.grid[GamePanel.screen][(int)(y-1)/Maps.blockHeight][(int)(x + (int)GamePanel.time)/Maps.blockWidth] > 0
        || GamePanel.grid[GamePanel.screen][(int)(y-1)/Maps.blockHeight][(int)(x + (int)GamePanel.time)/Maps.blockWidth + 1] > 0){
            //Set player to proper Y position
            y = (y+49) - (y+49)%Maps.blockHeight;
            blockAbove = true;
        }
        else{
            blockAbove = false;
        }

        //Check block below
        if(GamePanel.grid[GamePanel.screen][(int)(y + Maps.blockHeight)/Maps.blockHeight][(int)(x + (int)GamePanel.time)/Maps.blockWidth] > 0
        || GamePanel.grid[GamePanel.screen][(int)(y + Maps.blockHeight)/Maps.blockHeight][(int)(x + (int)GamePanel.time)/Maps.blockWidth + 1] > 0){
            //Set player to proper Y position
            y = (y+49) - (y+49)%Maps.blockHeight;
            blockBelow = true;
        }
        else{
            blockBelow = false;
        }

        //Check block infront
        if(GamePanel.grid[GamePanel.screen][(int)(y)/Maps.blockHeight][(int)(x + Maps.blockWidth + (int)GamePanel.time)/Maps.blockWidth] > 0
        || GamePanel.grid[GamePanel.screen][(int)(y - 1 + Maps.blockHeight)/Maps.blockHeight][(int)(x + Maps.blockWidth + (int)GamePanel.time)/Maps.blockWidth] > 0){
            // GamePanel.screen(0);
            GamePanel.time = 0;
        }
    }

    public void move(){
        if(!blockAbove && !blockBelow){
            y+=velocity;
        }
    }

    public void draw(Graphics g){
        // g.setColor(Color.black);
        // g.fillRect(x, y, width, height);
        g.drawImage(GameFrame.resize(GameFrame.sprites[0], Maps.blockWidth, Maps.blockHeight), x, y, null);
        
    }
}