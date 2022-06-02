/*

*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements KeyListener {
    public Thread gameThread;
    public Image image;
    public Graphics graphics;
    public Player player;

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
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
        if (e.getKeyChar() == '1') {
            GamePanel.screen(2);
      }
    }
    
    public void keyTyped(KeyEvent e) {
        //
    }
}