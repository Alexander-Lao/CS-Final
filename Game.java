/*

*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.*;

public class Game extends JPanel implements KeyListener {
    public static final int blockSize = 50;
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    public Player player; //Creating object of player class
    boolean noteClicked = false, tap = false;
    public static int[] newTiming[] = new int[10000][2];
    public static int newNoteCount = 0;

    //Initalization
    public Game() {
        player = new Player();
    }

    //Double buffer
    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    //Send draw over to player class
    public void draw(Graphics g) {
        player.draw(g);
    }

    //Send move over to player class
    public void move() {
        player.move();
    }
    
    //Adding notes the the game
    public void addNote(int x,int y) {
        newTiming[newNoteCount][0]=x; newTiming[newNoteCount][1]=y;
        newNoteCount++;
    }

    //Resets the game
    public void reset() {
        GamePanel.timeReset = 0;
        GamePanel.nextNote = 0;
        player.y = 600;
        player.gravity = false;
        player.touchingSurface = false;
    }

    //Checks player collision with surfaces, head on contact, and notes
    public void checkCollision() {
        if (player.blockCollision()) {reset(); return;}
        if (GamePanel.editNotes) {
            if (tap) {
                addNote(Player.xx,player.y);
                tap = false;
            }
        }
        else {
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

    //Saves the game by writing to the file
    public void saveGame() {
        try {
            FileWriter myWriter = new FileWriter("levels/"+GamePanel.customMapNames[LevelMaker.savedLevel]+"/Notes.txt");
            myWriter.write(newNoteCount + "\n");
            Arrays.sort(newTiming, 0, newNoteCount, (a, b) -> Double.compare(a[0], b[0]));
            //TODO maybe sort the notes before writing?
            for(int i = 0; i < newNoteCount; i++) {
                myWriter.write(newTiming[i][0] + " " + newTiming[i][1]);
                if (i != newNoteCount-1) myWriter.write("\n");
            }
            myWriter.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            System.out.println(e);
        }
    }

    //Using key to add notes and save notes
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyBinds.key[1] && noteClicked == false) {
            tap = true;
            noteClicked = true;
            return;
        }
        if (e.getKeyChar() == 'm' && GamePanel.editNotes) {
            saveGame();
        }
        player.keyPressed(e);
    }

    //Ensuring key release before next note can be pressed
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == KeyBinds.key[1]) {noteClicked = false; return;}
        player.keyReleased(e);
        
        if (e.getKeyChar() == KeyBinds.key[2]) {
            reset();
        }
    }
    
    //Override
    public void keyTyped(KeyEvent e) {

    }
}