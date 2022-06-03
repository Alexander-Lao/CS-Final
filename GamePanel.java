import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 800;
    public Thread gameThread;
    public Image image;
    public Graphics graphics;
    public Maps maps;
    public Menu menu;
    public Game game;
    public HowToPlay howToPlay;
    public Settings settings;
    public static int[] grid[][] = new int[3][100][500];
    public static int screen = 0;
    public static double time = 0;

    public GamePanel() {
        maps = new Maps();
        menu = new Menu();
        game = new Game();
        howToPlay = new HowToPlay();
        settings = new Settings();
        this.setFocusable(true);
        this.addKeyListener(this);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(screen == 0){
                    menu.mousePressed(e);
                }
                else if(screen == -1){
                    howToPlay.mousePressed(e);
                }
                else if(screen == -2){
                    settings.mousePressed(e);
                }
            }
        });
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void paint(Graphics g) {
        image = createImage(GAME_WIDTH, GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        if (screen == 0) {
            menu.draw(g);
        }
        else if (screen > 0) {
            displayMap(g);
            game.draw(g);
        }
        else if(screen == -1){
            howToPlay.draw(g);
        }
        else if(screen == -2){
            settings.draw(g);
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 1000;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long now;

        int mouseX;
        int mouseY;

        while (true) {
            now = System.nanoTime();
            delta = delta + (now - lastTime) / ns;
            time += ((now - lastTime) / ns) / 2;
            lastTime = now;

            if (delta >= 1) {
                try {
                    mouseX = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
                    mouseY = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;
                    if (screen > 0) { //level
                        game.move();
                        game.checkCollision();
                    }
                    else if(screen == 0){ // menu
                        menu.mousePosition(mouseX, mouseY);             
                    }
                    else if(screen == -1){ // how to play
                        howToPlay.mousePosition(mouseX, mouseY);
                    }
                    else if(screen == -2){ // settings
                        settings.mousePosition(mouseX, mouseY);
                    }
                        
                } catch (Exception e) {

                }
                repaint();
                delta--;
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (screen == 0) {
            
        }
        else if (screen > 0) {
            game.keyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        if (screen == 0) {

        } else if (screen > 0) {
            game.keyReleased(e);
        }
    }

    //here to be overriden
    public void keyTyped(KeyEvent e) {
        //
    }

    private void displayMap(Graphics g) {
        g.drawImage(GameFrame.backgroundImage[1], 0, 0, this);

        for (int i = 0; i < grid[screen].length; i++) {
            for (int j = 0; j < grid[screen][0].length; j++) {
                int pos = grid[screen][i][j];                    

                if (pos != 0) {
                    g.drawImage(GameFrame.blocks[pos-1], j * Maps.blockSize - (int) (time), i * Maps.blockSize, null);
                }
            }
        }
    }

    public static void screen(int s) {
        time = 0;
        screen = s;
        System.out.println(time);
    }
}