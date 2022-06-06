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

    public int[][] map = new int[100][500];

    public LevelMaker() {
        currentBlockNumber = 0;
        mouseBlockX = 0;
        mouseBlockY = 0;
        mouseBlockXWithShifts = 0;
        screenShifts = 0;

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
        for(int i = 0; i < GamePanel.GAME_WIDTH/Maps.blockSize; i++){
            g2.drawLine(i * Maps.blockSize, 0, i * Maps.blockSize, GamePanel.GAME_HEIGHT);
        }
        for(int i = 0; i < GamePanel.GAME_HEIGHT/Maps.blockSize; i++){
            g2.drawLine(0, i * Maps.blockSize, GamePanel.GAME_WIDTH, i * Maps.blockSize);
        }

        //Shown editor blocks on the side
        for(int i = 0; i < pos.length; i++){
            g.drawImage(GameFrame.blocks[i], pos[i][0], pos[i][1], null);
        }

    }

    public void mousePressed(MouseEvent e){
        if(mouseBlockX >= GamePanel.GAME_WIDTH/Maps.blockSize - 6){
            for(int i = 0; i < pos.length; i++){
                if(hover[i]){
                    currentBlockNumber = i + 1;
                }
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

    public void mousePosition(int x, int y){
        int xMoves = x + screenShifts;
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
        g.drawImage(GameFrame.backgroundImage[1], 0, 0, this);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int pos = map[i][j];      

                if (pos != 0) {
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