
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
    
    //Adding all the java files
    public Maps maps;
    public Notes temporary;
    public Menu menu;
    public Game game;
    public LevelSelect levelSelect;
    public HowToPlay howToPlay;
    public Settings settings;
    public LevelMaker levelMaker;
    public KeyBinds keyBinds;
    public NextLevel nextLevel;

    //Game details
    public static int nextNote = 0;
    public static int gridLength = 0;
    public static int[] grid[] = new int[100][20000]; //Change y to GAME_HEIGHT/Maps.blockSize
    public static int[] notes[] = new int[1000][2]; //first dimension: map, second dimension: note number, third dimension: pair x,y position
    public static int noteCount;
    
    //in-game maps numbered from 1 to lastMap
    //custom maps numbered from 1 to customMapCount
    public static int lastMap = 0;
    public static int customMapCount;
    public static String[] customMapNames = new String[1000];
    
    //TO DO! TOO LAZY TO EXPLAIN RN
    public static int screen = 0, setScreen = -100; //The major screen that switches between the objects of classes
    public static int currentLevel = 0;
    public static boolean isCustomLevel = false;
    public static double time = 0;
    public static double timeReset = -100;
    public static boolean editNotes = false; //set true to retime maps
    public static int parallaxRatio = 10;
    public static boolean debug;
    public static boolean loadSelectedMap = false, saveSelectedMap = false;

    //Initalization
    public GamePanel() {
        maps = new Maps();
        System.out.println(customMapCount);
        menu = new Menu();
        game = new Game();
        howToPlay = new HowToPlay();
        settings = new Settings();
        levelMaker = new LevelMaker();
        keyBinds = new KeyBinds();
        nextLevel = new NextLevel();
        levelSelect = new LevelSelect();
        this.setFocusable(true);
        this.addKeyListener(this);
        
        //On mouse pressed, send value to the "currently running" class
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(screen == 0){
                    menu.mousePressed(e);
                }
                else if (screen == 1) {
                    levelSelect.mousePressed(e);
                }
                else if(screen == -1){
                    levelMaker.mousePressed(e);
                }
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

            //Just for the drag function in levelMaker
            public void mouseReleased(MouseEvent e){
                if(screen == -1){
                    levelMaker.mouseReleased(e);
                }
            }
        });
        this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT)); //Dimensions

        //Running thread
        gameThread = new Thread(this); 
        gameThread.start();
    }

    //Double buffer
    public void paint(Graphics g) {
        image = createImage(GAME_WIDTH, GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    //Run draw function of "currently running" class
    public void draw(Graphics g) {
        if (screen == 0) {
            menu.draw(g);
        }
        else if (screen == 2) {
            displayMap(g);
            displayNotes(g);
            game.draw(g);
        }
        else if (screen == 1) {
            levelSelect.draw(g);
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
        settings.draw(g); //Always drawn for the settings logo
    }

    //The infinite loop function!
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 700;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long now;

        //Mouse position
        int mouseX;
        int mouseY;

        //I DUNNO WHAT THIS STUFF DOES AARON PLZ WRITE COMMENTS FOR THIS FUNCTION!!!!
        //
        //
        //!!!!!!!!
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
                if (screen!=-3) time = 0;
                screen = setScreen;
                setScreen = -100;
            }
            if (loadSelectedMap) {
                levelMaker.loadMap();
                loadSelectedMap = false;
            }
            if (saveSelectedMap) {
                levelMaker.saveMap();
                saveSelectedMap = false;
            }
            if (delta >= 1) {
                try {
                    //Get values for the mouse position
                    mouseX = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
                    mouseY = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;

                    //Run the "currently running" object. (Mainly just mouse position checking for button press)
                    if (screen > 1) { //level
                        game.move();
                        game.checkCollision();
                    }
                    else if(screen == 0){ // menu
                        menu.mousePosition(mouseX, mouseY);             
                    }
                    else if(screen == 1) {
                        levelSelect.mousePosition(mouseX, mouseY);
                    }
                    else if(screen == -1){ 
                        levelMaker.mousePosition(mouseX, mouseY);
                        levelMaker.drag();
                    }
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

    //Send key pressed to "currently running" object
    public void keyPressed(KeyEvent e) {
        if (screen == 0) {
            //nothing
        }
        else if (screen == 1) {
            //nothing
        }
        else if (screen == 2) {
            game.keyPressed(e);
        }
        else if (screen == -1) {
            levelMaker.keyPressed(e);
        }
        else if (screen == -2){
            howToPlay.keyPressed(e);
        }
        settings.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        if (screen == 0) {
            //
        }
        //For the gravity switching code
        else if (screen == 2) {
            game.keyReleased(e);
        }
    }

    //here to be overriden
    public void keyTyped(KeyEvent e) {
        //
    }

    private void displayMap(Graphics g) {
        //Parallax (moving background)
        g.drawImage(GameFrame.backgroundImage[1], (int)(-time/parallaxRatio % GAME_WIDTH), 0, GAME_WIDTH, GAME_HEIGHT, null);
        g.drawImage(GameFrame.backgroundImage[1], (int)(-time/parallaxRatio % GAME_WIDTH) + GAME_WIDTH - 1, 0, GAME_WIDTH, GAME_HEIGHT, null);

        //Draw the blocks that are stored in the current file
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < gridLength; j++) {
                int blockType = grid[i][j];
                if(blockType != 0){
                    g.drawImage(GameFrame.blocks[blockType-1], j * Maps.blockSize - (int) (time), i * Maps.blockSize, null);
                }
                //This should only load the blocks on the screen but its laggier for some reason????
                // if (pos != 0 && j < GamePanel.GAME_WIDTH/Maps.blockSize + 10 + time/Maps.blockSize
                // && j > time/Maps.blockSize - 10) {
            }
        }
    }
    
    //Displays the notes
    private void displayNotes(Graphics g) {
        if (editNotes) {
            for (int i=0; i<Game.newNoteCount; i++) {
                int xpos = Game.newTiming[i][0], ypos = Game.newTiming[i][1];
                // g.fillRect(xpos - (int)(time), ypos, NOTE_SIZE, NOTE_SIZE);
                g.drawImage(GameFrame.blocks[16], xpos - (int)(time), ypos, null);
            }
        }
        for (int i = nextNote; i < noteCount; i++) {
            int xpos = notes[i][0], ypos = notes[i][1];
            // g.fillRect(xpos - (int)(time), ypos, NOTE_SIZE, NOTE_SIZE);
            g.drawImage(GameFrame.blocks[16], xpos - (int)(time), ypos, null);
        }
    }

    //IS THIS STILL BEING USED????
    //
    //
    //???????????
    public static void screen(int s) {
        setScreen = s;
        timeReset = 0;
    }
}