
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    public static final int GAME_WIDTH = 1200;
    public static final int GAME_HEIGHT = 700;
    public static final int NOTE_SIZE = 50;
    public Thread gameThread;
    public Image image;
    public Graphics graphics;
    public Maps maps;
    public Notes temporary;
    public Menu menu;
    public Game game;
    public HowToPlay howToPlay;
    public Settings settings;
    public LevelMaker levelMaker;
    public KeyBinds keyBinds;
    public NextLevel nextLevel;
    public static int nextNote = 0;
    public static int lastMap = 0;
    public static int[] grid[][] = new int[3][100][500]; //Change y to GAME_HEIGHT/Maps.blockSize
    public static int[] notes[][] = new int[3][1000][2]; //first dimension: map, second dimension: note number, third dimension: pair x,y position
    public static int[] noteCount = new int[3];
    public static int screen = 0, setScreen = -100;
    public static double time = 0;
    public static double timeReset = -100;
    public static boolean debug = false; //set true to retime maps
    public static int nextScreen = 0;

    public static int parallaxRatio = 10;

    public GamePanel() {
        maps = new Maps();
        temporary = new Notes();
        menu = new Menu();
        game = new Game();
        howToPlay = new HowToPlay();
        settings = new Settings();
        levelMaker = new LevelMaker();
        keyBinds = new KeyBinds();
        nextLevel = new NextLevel();
        this.setFocusable(true);
        this.addKeyListener(this);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(screen == 0){
                    menu.mousePressed(e);
                }
                else if(screen == -1){
                    levelMaker.mousePressed(e);
                }
                // else if(screen == -2){
                //     howToPlay.mousePressed(e);
                // }
                //currentlybinding == -1 means nothing is being binded, basicly does not allow user input until binding is finished
                else if(screen == -4 && KeyBinds.currentlyBinding == -1){
                    keyBinds.mousePressed(e);
                }
                else if(screen == -5) {
                    nextLevel.mousePressed(e);
                }
                if(KeyBinds.currentlyBinding == -1){
                    settings.mousePressed(e);
                }
            }

            public void mouseReleased(MouseEvent e){
                if(screen == -1){
                    levelMaker.mouseReleased(e);
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
            displayNotes(g);
            game.draw(g);
        }
        else if (screen == -1) {
            levelMaker.draw(g);
        }
        else if (screen == -2) {
            howToPlay.draw(g);
        }
        else if (screen == -4) {
            keyBinds.draw(g);
        }
        else if (screen == -5) {
            nextLevel.draw(g);
        }
        settings.draw(g);
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 700;
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
            if (timeReset > -50) {
                time = timeReset;
                timeReset = -100;
            }
            if (setScreen!=-100) {
                screen = setScreen;
                setScreen = -100;
            } 
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
                    else if(screen == -1){ 
                        levelMaker.mousePosition(mouseX, mouseY);
                        levelMaker.drag();
                    }
                    // else if(screen == -2){ 
                    //     howToPlay.mousePosition(mouseX, mouseY);
                    // }
                    else if(screen == -4 && KeyBinds.currentlyBinding == -1){
                        keyBinds.mousePosition(mouseX, mouseY);
                    }
                    else if (screen == -5) {
                        nextLevel.mousePosition(mouseX, mouseY);
                    }
                    if(KeyBinds.currentlyBinding == -1){
                        settings.mousePosition(mouseX, mouseY);
                    }
                }
                catch (Exception e) {
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
        else if (screen == -1) {
            levelMaker.keyPressed(e);
        }
        settings.keyPressed(e);
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
        g.drawImage(GameFrame.backgroundImage[1], (int)(-time/parallaxRatio % GAME_WIDTH), 0, GAME_WIDTH, GAME_HEIGHT, null);
        g.drawImage(GameFrame.backgroundImage[1], (int)(-time/parallaxRatio % GAME_WIDTH) + GAME_WIDTH - 1, 0, GAME_WIDTH, GAME_HEIGHT, null);

        for (int i = 0; i < grid[screen].length; i++) {
            for (int j = 0; j < grid[screen][0].length; j++) {
                int pos = grid[screen][i][j];                    

                //This only loads the blocks on the screen but its laggier for some reason
                // if (pos != 0 && j < GamePanel.GAME_WIDTH/Maps.blockSize + 10 + time/Maps.blockSize
                // && j > time/Maps.blockSize - 10) {
                if(pos != 0){
                    g.drawImage(GameFrame.blocks[pos-1], j * Maps.blockSize - (int) (time), i * Maps.blockSize, null);
                }
            }
        }
    }
    
    private void displayNotes(Graphics g) {
        if (debug) {
            for (int i=0; i<Game.newNoteCount; i++) {
                int xpos = Game.newTiming[i][0], ypos = Game.newTiming[i][1];
                g.fillRect(xpos - (int)(time), ypos, NOTE_SIZE, NOTE_SIZE);
            }
        }
        for (int i = nextNote; i < noteCount[screen]; i++) {
            int xpos = notes[screen][i][0], ypos = notes[screen][i][1];
            g.fillRect(xpos - (int)(time), ypos, NOTE_SIZE, NOTE_SIZE);
        }
    }

    public static void screen(int s) {
        setScreen = s;
        timeReset = 0;
    }
}