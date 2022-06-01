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