//Screen -1

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.Scanner;

import javax.swing.*;

public class LevelMaker extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    private boolean[] hover = new boolean[15];
    private int[][] pos = new int[15][2];
    private int currentBlockNumber;
    private int mouseBlockX;
    private int mouseBlockY;
    private int mouseBlockXWithShifts;
    private int screenShifts;
    private boolean ifNone;
    private int xMoves;

    public int ticksMouseHeld;
    private boolean mouseHeld;

    public int[][] map = new int[100][500];

    //Initalize all variables
    public LevelMaker() {
        currentBlockNumber = 0;
        mouseBlockX = 0;
        mouseBlockY = 0;
        mouseBlockXWithShifts = 0;
        screenShifts = 0;

        ticksMouseHeld = 0;
        mouseHeld = false;

        //Row 1
        pos[8][0] = GamePanel.GAME_WIDTH - 6*Maps.blockSize;
        pos[8][1] = 2*Maps.blockSize;

        pos[1][0] = GamePanel.GAME_WIDTH - 4*Maps.blockSize;
        pos[1][1] = 2*Maps.blockSize;

        pos[5][0] = GamePanel.GAME_WIDTH - 2*Maps.blockSize;
        pos[5][1] = 2*Maps.blockSize;

        //Row 2
        pos[4][0] = GamePanel.GAME_WIDTH - 6*Maps.blockSize;
        pos[4][1] = 4*Maps.blockSize;

        pos[0][0] = GamePanel.GAME_WIDTH - 4*Maps.blockSize;
        pos[0][1] = 4*Maps.blockSize;

        pos[2][0] = GamePanel.GAME_WIDTH - 2*Maps.blockSize;
        pos[2][1] = 4*Maps.blockSize;

        //Row 3
        pos[7][0] = GamePanel.GAME_WIDTH - 6*Maps.blockSize;
        pos[7][1] = 6*Maps.blockSize;

        pos[3][0] = GamePanel.GAME_WIDTH - 4*Maps.blockSize;
        pos[3][1] = 6*Maps.blockSize;

        pos[6][0] = GamePanel.GAME_WIDTH - 2*Maps.blockSize;
        pos[6][1] = 6*Maps.blockSize;

        //Row 4
        pos[10][0] = GamePanel.GAME_WIDTH - 6*Maps.blockSize;
        pos[10][1] = 8*Maps.blockSize;

        pos[11][0] = GamePanel.GAME_WIDTH - 4*Maps.blockSize;
        pos[11][1] = 8*Maps.blockSize;

        //Row 5
        pos[9][0] = GamePanel.GAME_WIDTH - 6*Maps.blockSize;
        pos[9][1] = 10*Maps.blockSize;

        pos[12][0] = GamePanel.GAME_WIDTH - 4*Maps.blockSize;
        pos[12][1] = 10*Maps.blockSize;

        //Eraser
        pos[13][0] = GamePanel.GAME_WIDTH - 2*Maps.blockSize;
        pos[13][1] = 8*Maps.blockSize;

        //Trash
        pos[14][0] = GamePanel.GAME_WIDTH - 2*Maps.blockSize;
        pos[14][1] = 10*Maps.blockSize;
    }

    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        g.drawImage(GameFrame.backgroundImage[0], 0, 0, this);
        displayMap(g);
        //Set width (Currently every other vertical line is thicker IDK why. But it looks nice /shrug)
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        //Grid Lines
        for(int i = 0; i < GamePanel.GAME_WIDTH/Maps.blockSize - 6; i++){
            g2.drawLine(i * Maps.blockSize, 0, i * Maps.blockSize, GamePanel.GAME_HEIGHT);
        }
        for(int i = 0; i < GamePanel.GAME_HEIGHT/Maps.blockSize; i++){
            g2.drawLine(0, i * Maps.blockSize, GamePanel.GAME_WIDTH - 7*50, i * Maps.blockSize);
        }

        //Show editor blocks on the right side
        for(int i = 0; i < pos.length; i++){
            g.drawImage(GameFrame.blocks[i], pos[i][0], pos[i][1], null);
        }

        //Draw current block display
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("Current:", GamePanel.GAME_WIDTH - (6)*Maps.blockSize, 13*Maps.blockSize - 10);
        //Draw eraser for no block selected
        if(currentBlockNumber == 0){
            g.drawImage(GameFrame.blocks[13], GamePanel.GAME_WIDTH - (2)*Maps.blockSize, 12*Maps.blockSize, null);
        }
        //Draw image of current block otherwise
        else{
            g.drawImage(GameFrame.blocks[currentBlockNumber-1], GamePanel.GAME_WIDTH - (2)*Maps.blockSize, 12*Maps.blockSize, null);
        }
    }

    //Does a lot of damn stuff. Lots of stuff to click haha
    public void mousePressed(MouseEvent e){
        mouseHeld = true;
        ifNone = true; //This boolean for checking if none of the blocks were clicked. It will set the block to eraser. Can be removed
        
        //If mouse is on the right side where the block selector is
        if(mouseBlockX >= GamePanel.GAME_WIDTH/Maps.blockSize - 7){
            for(int i = 0; i < pos.length; i++){
                if(hover[i]){
                    currentBlockNumber = i + 1;

                    //Erase all with warning message
                    if(currentBlockNumber == 15){
                        mouseHeld = false;
                        int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete EVERYTHING?", 
                        "WARNING", JOptionPane.YES_NO_OPTION);

                        if(result == JOptionPane.YES_OPTION){
                            clearGrid();
                        } 
                    }
                    else{
                       ifNone = false; 
                    }
                    
                }
            }
            if(ifNone){
                currentBlockNumber = 0;
            }
        }
        //If mouse is in the grid
        else{
            try {
                //Remove block if the block is the same
                if(map[mouseBlockY][mouseBlockXWithShifts] == currentBlockNumber || currentBlockNumber >= 14){
                    map[mouseBlockY][mouseBlockXWithShifts] = 0;
                }
                //Add block otherwise
                else{
                    map[mouseBlockY][mouseBlockXWithShifts] = currentBlockNumber;
                }
            } 
            catch (Exception ex) {
                //TODO: handle exception
            } 
        }        
    }

    //Currently only used for detecting release to stop the "drag" effect
    public void mouseReleased(MouseEvent e){
        mouseHeld = false;
        ticksMouseHeld = 0;
    }

    //This gets called in the run() function which gets constantly called every tick
    public void drag(){
        if(mouseHeld){
            //100 tick requirement so blocks can be removed without the "hold" bringing it back
            if(ticksMouseHeld > 100 && mouseBlockX < GamePanel.GAME_WIDTH/Maps.blockSize - 7){
                if(currentBlockNumber >= 14){
                    map[mouseBlockY][mouseBlockXWithShifts] = 0;
                }
                else{
                    map[mouseBlockY][mouseBlockXWithShifts] = currentBlockNumber; 
                }
            }
            ticksMouseHeld++;
        }
    }

    //Uses the mouse X and Y position for game elements
    public void mousePosition(int x, int y){
        xMoves = x + screenShifts;
        for(int i = 0; i < pos.length; i++){
            if(x > pos[i][0] && x < pos[i][0] + Maps.blockSize && y > pos[i][1] && y < pos[i][1] + Maps.blockSize){
                hover[i] = true;
            }
            else{
                hover[i] = false;
            }
        }
        //Changes mouse position to block position (first block is 0, second is 1 etc...)
        mouseBlockX = (int)x/Maps.blockSize;
        mouseBlockY = (int)y/Maps.blockSize;
        mouseBlockXWithShifts = (int)xMoves/Maps.blockSize;
    }

    //Displays current map by constantly updating the maps matrix
    private void displayMap(Graphics g) {
        //parallax (the background moves)
        g.drawImage(GameFrame.backgroundImage[1], (int)(-screenShifts/GamePanel.parallaxRatio % GamePanel.GAME_WIDTH), 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);
        g.drawImage(GameFrame.backgroundImage[1], (int)(-screenShifts/GamePanel.parallaxRatio % GamePanel.GAME_WIDTH) + GamePanel.GAME_WIDTH - 1, 0, GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT, null);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int pos = map[i][j];      
                
                //These conditions are to not draw anything that isint on the board
                if (pos != 0 && j < GamePanel.GAME_WIDTH/Maps.blockSize - 7 + screenShifts/Maps.blockSize
                && j > screenShifts/Maps.blockSize - 1) {
                    g.drawImage(GameFrame.blocks[pos-1], j * Maps.blockSize - (int) (screenShifts), i * Maps.blockSize, null);
                }
            }
        }
    }

    //This shifts the editor left and right
    //Also currently used for save and load cuz I'm lazy, NEED TO ADD SOME MENU/PAUSE BUTTON TO DO THIS.
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            if(screenShifts > 0){
                screenShifts -= Maps.blockSize;
            }  
        }
        if (e.getKeyChar() == 'd') {
            screenShifts += Maps.blockSize;
        }
        if (e.getKeyChar() == 's') {
            //Alert dialague
            int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to save?", 
            "WARNING", JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION){
                saveGame();
            }
        }
        if (e.getKeyChar() == 'l') {
            loadGame();
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    //here to be overriden
    public void keyTyped(KeyEvent e) {
        //
    }

    //Copid from maps.java
    public void loadGame(){
        clearGrid();
        try {
            File myObj = new File("customLevel.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                int rows = Integer.parseInt(myReader.next());
                int cols = Integer.parseInt(myReader.next());
                myReader.nextLine();
                for (int i = 0; i < rows; i++) {
                    String data = myReader.nextLine();
                    for (int j = 0; j < cols; j++) {
                        try {
                            //For numbers 0 - 9
                            map[i][j] = Integer.parseInt(data.charAt(j) + "");
                        } catch (Exception e) {
                            //for converting letters to numbers, starting with a = 10
                            map[i][j] = data.charAt(j) - 'a' + 10;
                        }
                        
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //Loads current state of game into customLevel.txt file
    public void saveGame(){     
        int maxX = 0;
        int maxY = 0;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if(map[i][j] != 0){
                    if(i > maxX){
                        maxX = i;
                    }
                    if(j > maxY){
                        maxY = j;
                    }
                }
            }
        }
        maxX++;
        maxY++;
        try {
            FileWriter myWriter = new FileWriter("customLevel.txt");
            myWriter.write(maxX + " " + maxY + "\n");
            for(int i = 0; i < maxX; i++){
                for(int j = 0; j < maxY; j++){
                    if(map[i][j] >= 10){
                        myWriter.write(getCharForNumber(map[i][j]));
                    }
                    else{
                        myWriter.write(map[i][j] + "");
                    }
                }
                myWriter.write("\n");
            }

            myWriter.close();
            } 
            catch (IOException e) {
            System.out.println("An error occurred.");
            }
    }

    //Simple turns 10 -> a, 11 -> b etc... Used for the numbers >= 10
    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 'a' - 10)) : null;

    }

    //Sets the entire grid to zero
    private void clearGrid(){
        for(int j = 0; j < map.length; j++){
            for(int k = 0; k < map[0].length; k++){
                map[j][k] = 0;
            }
        }
    }
}