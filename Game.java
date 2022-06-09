/*

*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements KeyListener {
    public static final int blockSize = 50;
    public Thread gameThread;
    public Image image;
    public Graphics graphics;
    public Player player;
    boolean noteClicked = false;

    public Game() {
        player = new Player();
    }

    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        player.draw(g);
    }

    public void move() {
        player.move();
    }

    public void checkCollision() {
        player.blockCollision();
        if (GamePanel.nextNote != GamePanel.notes[GamePanel.screen].length) {
            int i = GamePanel.nextNote;
            int xpos = GamePanel.notes[GamePanel.screen][i][0], ypos = GamePanel.notes[GamePanel.screen][i][1];
            // player: [player.x, player.x+blockSize] [player.y, player.x+blockSize]
            // note: [xpos,xpos+GamePanel.NOTE_SIZE] [ypos,ypos+GamePanel.NOTE_SIZE]
            if (xpos+GamePanel.NOTE_SIZE <= player.xx) {
                GamePanel.screen = 0;
                //TODO
            }
            else if (((player.xx <= xpos) && (xpos <= player.xx+blockSize)) || ((player.xx <= xpos+GamePanel.NOTE_SIZE) && (xpos+GamePanel.NOTE_SIZE <= player.xx+blockSize))) {
                if (((player.y <= ypos) && (ypos <= player.y+blockSize)) || ((player.y <= ypos+GamePanel.NOTE_SIZE) && (ypos+GamePanel.NOTE_SIZE <= player.y+blockSize))) {
                    if (noteClicked) GamePanel.nextNote++;
                }
            }
        }
        //Temporary code to keep player on the map. Falling off the map should "kill" the player
        //removed for now
        /*if(player.y < 0){
            player.y = 0;
            player.blockAbove = true;
        }
        if(player.y + player.height > GamePanel.GAME_HEIGHT){
            player.y = GamePanel.GAME_HEIGHT - player.height;
            player.blockBelow = true;
        }*/
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'p') {noteClicked = true; return;}
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'p') {noteClicked = false; return;}
        player.keyReleased(e);
        if (e.getKeyChar() == '1') {
            GamePanel.screen(2);
        }
        if (e.getKeyChar() == 'r') {
            GamePanel.screen(GamePanel.screen);
        }
    }
    
    public void keyTyped(KeyEvent e) {
        //
    }
}