import java.awt.*;
import java.awt.event.*;

public class Player extends Rectangle{    
    public final int velocity = 1;
    public boolean touchingSurface = false;
    public boolean gravity = false;
    private boolean keyIsPressed = false;

    public Player(){
        super(100, 700, 50, 50);
    }

    public void keyPressed(KeyEvent e){
        if (e.getKeyChar() == 'q' && !keyIsPressed && touchingSurface) {
            keyIsPressed = true;
            if (GamePanel.screen > 0) gravity = !gravity;
        }
    }

    public void keyReleased(KeyEvent e){
        if (e.getKeyChar() == 'q') {
            keyIsPressed = false;
        }
    }

    public void blockCollision(){
        //Check block above
        //left corner (x,y)
        //bottom right (x+blocksize)
        int xcol,yrow;
        int xx = (x + (int)GamePanel.time);
        if (!gravity) { //gravity going down so check down
            xcol = (xx)/Maps.blockSize;
            yrow = y/Maps.blockSize + 1;
            if (y % Maps.blockSize == 0) yrow--;
            if (GamePanel.grid[GamePanel.screen][yrow][xcol] > 0) {
                //collision
                if (Math.abs(y - (Maps.blockSize*(yrow - 1))) < 3) {
                    y = Maps.blockSize*(yrow - 1);
                    touchingSurface = true;
                }
            }
            if ((xx % Maps.blockSize != 0) && (GamePanel.grid[GamePanel.screen][yrow][xcol+1] > 0)) {
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
            if (GamePanel.grid[GamePanel.screen][yrow][xcol] > 0) {
                //collision
                if (Math.abs(y - (Maps.blockSize*(yrow + 1))) < 3) {
                    y = Maps.blockSize*(yrow + 1);
                    touchingSurface = true;
                }
            }
            if ((xx % Maps.blockSize != 0) && (GamePanel.grid[GamePanel.screen][yrow][xcol+1] > 0)) {
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
        if (GamePanel.grid[GamePanel.screen][yrow][xcol] != 0) {
            //collision
            GamePanel.time = 0;
        }
        if ((y % Maps.blockSize != 0) && (GamePanel.grid[GamePanel.screen][yrow+1][xcol] != 0)) {
            //collision
            GamePanel.time = 0;
        }
        /*
        if(GamePanel.grid[GamePanel.screen][(int)(y-1)/Maps.blockSize][(int)(x + (int)GamePanel.time)/Maps.blockSize] > 0
        || GamePanel.grid[GamePanel.screen][(int)(y-1)/Maps.blockSize][(int)(x + (int)GamePanel.time)/Maps.blockSize + 1] > 0){
            //Set player to proper Y position
            y = (y+49) - (y+49)%Maps.blockSize;
            blockAbove = true;
        }
        else{
            blockAbove = false;
        }

        //Check block below
        if(GamePanel.grid[GamePanel.screen][(int)(y + Maps.blockSize)/Maps.blockSize][(int)(x + (int)GamePanel.time)/Maps.blockSize] > 0
        || GamePanel.grid[GamePanel.screen][(int)(y + Maps.blockSize)/Maps.blockSize][(int)(x + (int)GamePanel.time)/Maps.blockSize + 1] > 0){
            //Set player to proper Y position
            y = (y+49) - (y+49)%Maps.blockSize;
            blockBelow = true;
        }
        else{
            blockBelow = false;
        }

        //Check block infront
        if(GamePanel.grid[GamePanel.screen][(int)(y)/Maps.blockSize][(int)(x + Maps.blockSize + (int)GamePanel.time)/Maps.blockSize] > 0
        || GamePanel.grid[GamePanel.screen][(int)(y - 1 + Maps.blockSize)/Maps.blockSize][(int)(x + Maps.blockSize + (int)GamePanel.time)/Maps.blockSize] > 0){
            // GamePanel.screen(0);
            GamePanel.time = 0;
        }*/
    }

    public void move(){
        if (gravity) {
            y-=velocity;
        }
        if (!gravity) {
            y+=velocity;
        }
        touchingSurface = false;
    }

    public void draw(Graphics g){
        // g.setColor(Color.black);
        // g.fillRect(x, y, width, height);
        if(!gravity){
            g.drawImage(GameFrame.resize(GameFrame.sprites[0], Maps.blockSize, Maps.blockSize), x, y, null);
        }
        else{
            g.drawImage(GameFrame.resize(GameFrame.sprites[1], Maps.blockSize, Maps.blockSize), x, y, null);
        }
        
    }
}