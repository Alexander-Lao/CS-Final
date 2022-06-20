/*

*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors


public class Game extends JPanel implements KeyListener {
    public static final int blockSize = 50;
    public Thread gameThread;
    public Image image;
    public Graphics graphics;
    public Player player;
    boolean noteClicked = false, tap = false;
    public static int[] newTiming[] = new int[10000][2];
    public static int newNoteCount = 0;

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
    public void addNote(int x,int y) {
        newTiming[newNoteCount][0]=x; newTiming[newNoteCount][1]=y;
        newNoteCount++;
    }
    public void reset() {
        GamePanel.timeReset = 0;
        GamePanel.nextNote = 0;
        player.y = 600;
        player.gravity = false;
        player.touchingSurface = false;
    }
    public void checkCollision() {
        if (player.blockCollision()) reset();
        if (GamePanel.debug) {
            if (tap) {
                addNote(Player.xx,player.y);
                tap = false;
            }
            return;
        }
        if (GamePanel.nextNote != GamePanel.noteCount) {
            int i = GamePanel.nextNote;
            int xpos = GamePanel.notes[i][0], ypos = GamePanel.notes[i][1];
            // player: [player.x, player.x+blockSize] [player.y, player.x+blockSize]
            // note: [xpos,xpos+GamePanel.NOTE_SIZE] [ypos,ypos+GamePanel.NOTE_SIZE]
            if (tap) {
                tap = false;
                if (Player.xx+blockSize < xpos) { //clicked too early
                    reset();
                }
                else {
                    if (((player.y <= ypos) && (ypos <= player.y+blockSize)) || ((player.y <= ypos+GamePanel.NOTE_SIZE) && (ypos+GamePanel.NOTE_SIZE <= player.y+blockSize))) {
                        GamePanel.nextNote++;
                    }
                    else { //missed note vertically
                        reset();
                    }
                }
            }
            else {
                if (xpos+GamePanel.NOTE_SIZE < Player.xx) { //did not click note
                    reset();
                }
            }
        }
        if (player.y < 0){
            player.y = 0;
            player.touchingSurface = true;
        }
        if (player.y + player.height > GamePanel.GAME_HEIGHT){
            player.y = GamePanel.GAME_HEIGHT - player.height;
            player.touchingSurface = true;
        }
        //Temporary code to keep player on the map. Falling off the map should "kill" the player
    }
    public void saveGame() {
        try {
            FileWriter myWriter = new FileWriter("Notes.txt");
            myWriter.write(newNoteCount + "\n");
            for(int i = 0; i < newNoteCount; i++) {
                myWriter.write(newTiming[i][0] + " " + newTiming[i][1]);
                if (i != newNoteCount-1) myWriter.write("\n");
            }
            myWriter.close();
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyBinds.key[1] && noteClicked == false) {
            tap = true;
            noteClicked = true;
            return;
        }
        if (e.getKeyChar() == 'm' && GamePanel.debug) {
            saveGame();
        }
        player.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == KeyBinds.key[1]) {noteClicked = false; return;}
        player.keyReleased(e);
        
        if (e.getKeyChar() == KeyBinds.key[2]) {
            reset();
        }
    }
    
    public void keyTyped(KeyEvent e) {
        //
    }
}