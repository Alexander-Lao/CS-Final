import java.awt.*;
import java.awt.event.*;

public class Player extends Rectangle{    
    public final int velocity = 1; //Speed of player
    //These variables are explained in the name
    public boolean touchingSurface = false;
    public boolean gravity = false;
    private boolean keyIsPressed = false;
    
    public static int xx; //The x position with time included

    public Player(){
        super(100, 600, 50, 50);
    }

    public void keyPressed(KeyEvent e){
        //Gravity switch code
        if (e.getKeyChar() == KeyBinds.key[0] && !keyIsPressed && touchingSurface) {
            keyIsPressed = true;
            if (GamePanel.screen > 0) gravity = !gravity;
        }
    }

    public void keyReleased(KeyEvent e){
        if (e.getKeyChar() == KeyBinds.key[0]) {
            keyIsPressed = false;
        }
    }

    public boolean blockCollision(){
        //Check block above
        //left corner (x,y)
        //bottom right (x+blocksize)
        int xcol,yrow;
        xx = (x + (int)GamePanel.time);
        if (!gravity) { //gravity going down so check down
            xcol = (xx)/Maps.blockSize;
            yrow = y/Maps.blockSize + 1;
            if (y % Maps.blockSize == 0) yrow--;
            if (GamePanel.grid[yrow][xcol] > 0) {
                //collision
                if (Math.abs(y - (Maps.blockSize*(yrow - 1))) < 3) {
                    y = Maps.blockSize*(yrow - 1);
                    touchingSurface = true;
                }
            }
            if ((xx % Maps.blockSize != 0) && (GamePanel.grid[yrow][xcol+1] > 0)) {
                //collision
                if (Math.abs(y - (Maps.blockSize*(yrow - 1))) < 3) {
                    y = Maps.blockSize*(yrow - 1);
                    touchingSurface = true;
                }
            }
        }
        else { //gravity going up so check up
            xcol = xx/Maps.blockSize;
            yrow = y/Maps.blockSize;
            if (GamePanel.grid[yrow][xcol] > 0) {
                //collision
                if (Math.abs(y - (Maps.blockSize*(yrow + 1))) < 3) {
                    y = Maps.blockSize*(yrow + 1);
                    touchingSurface = true;
                }
            }
            if ((xx % Maps.blockSize != 0) && (GamePanel.grid[yrow][xcol+1] > 0)) {
                //collision
                if (Math.abs(y - (Maps.blockSize*(yrow + 1))) < 3) {
                    y = Maps.blockSize*(yrow + 1);
                    touchingSurface = true;
                }
            }
        }
        //check right collision
        xcol = xx/Maps.blockSize + 1;
        yrow = y/Maps.blockSize;
        if (xx % Maps.blockSize == 0) xcol--;
        if (GamePanel.grid[yrow][xcol] != 0) {
            //collision
            if (GamePanel.grid[yrow][xcol]==18) {
                GamePanel.setScreen = -5;
                NextLevel.checkNext();
            }
            return true;
        }
        if ((y % Maps.blockSize != 0) && (GamePanel.grid[yrow+1][xcol] != 0)) {
            //collision
            if (GamePanel.grid[yrow+1][xcol]==18) {
                GamePanel.setScreen = -5;
                NextLevel.checkNext();
            }
            return true;
        }
        return false;
    }

    //Moves the player according to the speed
    public void move(){
        if (gravity) {
            y-=velocity;
        }
        if (!gravity) {
            y+=velocity;
        }
        touchingSurface = false;
    }

    //Draw the sprite facing up or upside down
    public void draw(Graphics g){
        if(!gravity){
            g.drawImage(GameFrame.resize(GameFrame.sprites[0], Maps.blockSize, Maps.blockSize), x, y, null);
        }
        else{
            g.drawImage(GameFrame.resize(GameFrame.sprites[1], Maps.blockSize, Maps.blockSize), x, y, null);
        }
        
    }
}