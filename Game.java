/*

*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements KeyListener {
    public Thread gameThread;
    public Image image;
    public Graphics graphics;
    public PlayerBall ball;

    public Game() {
        ball = new PlayerBall(GamePanel.GAME_WIDTH / 2, GamePanel.GAME_HEIGHT / 2);
    }

    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        ball.draw(g);
    }

    public void move() {
        ball.move();
    }

    public void checkCollision() {
        if (ball.y <= 0) {
            ball.y = 0;
        }
        if (ball.y >= GamePanel.GAME_HEIGHT - PlayerBall.BALL_DIAMETER) {
            ball.y = GamePanel.GAME_HEIGHT - PlayerBall.BALL_DIAMETER;
        }
        if (ball.x <= 0) {
            ball.x = 0;
        }
        if (ball.x + PlayerBall.BALL_DIAMETER >= GamePanel.GAME_WIDTH) {
            ball.x = GamePanel.GAME_WIDTH - PlayerBall.BALL_DIAMETER;
        }
    }

    public void keyPressed(KeyEvent e) {
        ball.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        ball.keyReleased(e);
    }
    
    public void keyTyped(KeyEvent e) {
        //
    }
}