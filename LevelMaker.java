import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LevelMaker extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    private boolean[] hover = new boolean[13];
    private int[][] pos = new int[13][2];
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

    public LevelMaker() {
        currentBlockNumber = 0;
        mouseBlockX = 0;
        mouseBlockY = 0;
        mouseBlockXWithShifts = 0;
        screenShifts = 0;

        ticksMouseHeld = 0;
        mouseHeld = false;


        /*These current positions are temporary, I did it like this because I didnt want to type each one out
        I plan to keep the spacing the same but change the order of how the blocks are displayed
        Currently:
        1 2 3
        4 5 6
        7 8 9
        a b c
        d
        
        Plan:
        9 2 3
        8 1 4
        7 6 5
        b c
        a d
        */

        //Row 1
        for(int i = 0; i < 3; i++){
            pos[i][0] = GamePanel.GAME_WIDTH - (6-i*2)*Maps.blockSize;
            pos[i][1] = 2*Maps.blockSize;
        }

        //Row 2
        for(int i = 0; i < 3; i++){
            pos[i+3][0] = GamePanel.GAME_WIDTH - (6-i*2)*Maps.blockSize;
            pos[i+3][1] = 4*Maps.blockSize;
        }

        //Row 3
        for(int i = 0; i < 3; i++){
            pos[i+6][0] = GamePanel.GAME_WIDTH - (6-i*2)*Maps.blockSize;
            pos[i+6][1] = 6*Maps.blockSize;
        }

        //Row 4
        for(int i = 0; i < 3; i++){
            pos[i+9][0] = GamePanel.GAME_WIDTH - (6-i*2)*Maps.blockSize;
            pos[i+9][1] = 8*Maps.blockSize;
        }

        //Row 5
        pos[12][0] = GamePanel.GAME_WIDTH - 6*Maps.blockSize;
        pos[12][1] = 10*Maps.blockSize;
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
        //Set width (Currently every other vertical line is thicker IDK why)
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

        //Shown editor blocks on the side
        for(int i = 0; i < pos.length; i++){
            g.drawImage(GameFrame.blocks[i], pos[i][0], pos[i][1], null);
        }

        //Draw current block display
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("Current:", GamePanel.GAME_WIDTH - (6)*Maps.blockSize, 13*Maps.blockSize - 10);
        //Draw empty rectange for no block selected
        if(currentBlockNumber == 0){
            g.drawRect(GamePanel.GAME_WIDTH - (2)*Maps.blockSize, 12*Maps.blockSize, Maps.blockSize, Maps.blockSize);
        }
        //Draw image of current block otherwise
        else{
            g.drawImage(GameFrame.blocks[currentBlockNumber-1], GamePanel.GAME_WIDTH - (2)*Maps.blockSize, 12*Maps.blockSize, null);
        }
 
    }

    public void mousePressed(MouseEvent e){
        mouseHeld = true;
        ifNone = true;
        if(mouseBlockX >= GamePanel.GAME_WIDTH/Maps.blockSize - 7){
            for(int i = 0; i < pos.length; i++){
                if(hover[i]){
                    currentBlockNumber = i + 1;
                    ifNone = false;
                }
            }
            if(ifNone){
                currentBlockNumber = 0;
            }
        }
        else{
            try {
                //Remove block if the block is the same
                if(map[mouseBlockY][mouseBlockXWithShifts] == currentBlockNumber){
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

    public void mouseReleased(MouseEvent e){
        mouseHeld = false;
        ticksMouseHeld = 0;
    }

    public void drag(){
        if(mouseHeld){
            if(ticksMouseHeld > 100 && mouseBlockX < GamePanel.GAME_WIDTH/Maps.blockSize - 7){
                map[mouseBlockY][mouseBlockXWithShifts] = currentBlockNumber; 
            }
            ticksMouseHeld++;
        }
    }

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
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            if(screenShifts > 0){
                screenShifts -= Maps.blockSize;
            }  
        }
        if (e.getKeyChar() == 'd') {
            screenShifts += Maps.blockSize;
            // System.out.println("pressed");
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    //here to be overriden
    public void keyTyped(KeyEvent e) {
        //
    }
}