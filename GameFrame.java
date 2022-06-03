/* GameFrame class establishes the frame (window) for the game
It is a child of JFrame because JFrame manages frames
Runs the constructor in GamePanel class

*/ 
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameFrame extends JFrame{

  GamePanel panel;

  //Background Images
  private static String[] images = {"blueLand.png", "blueDesert.png", "blueShroom.png", "greenLand.png", "greenGrass.png", "coloredDesert.png", "coloredShroom.png"};
  public static BufferedImage[] backgroundImage = new BufferedImage[images.length];

  //Sprites
  private static String[] sprite = {"robotDrive1.png", "robotDrive1Flipped.png"};
  public static BufferedImage[] sprites = new BufferedImage[sprite.length];

  //Terrain
  private static String[] terrain = {"none.png", "T.png", "R.png", "B.png", "L.png", "TR.png", "BR.png", "BL.png", "TL.png", "TRC.png", "BRC.png", "BLC.png", "TLC.png" };
  public static BufferedImage[] blocks = new BufferedImage[terrain.length];


  public GameFrame(){
    panel = new GamePanel(); //run GamePanel constructor
    this.add(panel);
    this.setTitle("GUI is cool!"); //set title for frame
    this.setResizable(false); //frame can't change size
    this.setBackground(Color.white);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
    this.pack();//makes components fit in window - don't need to set JFrame size, as it will adjust accordingly
    this.setVisible(true); //makes window visible to user
    this.setLocationRelativeTo(null);//set window in middle of screen

    for(int i = 0; i < images.length; i++){
      try {
        backgroundImage[i] = resize(ImageIO.read(new File("assets/"+images[i])),GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);   
      } catch (IOException e) {

      }
    }

    for(int i = 0; i < sprite.length; i++){
      try {
        sprites[i] = ImageIO.read(new File("assets/"+sprite[i]));    
      } catch (IOException e) {

      }
    }

    for(int i = 0; i < terrain.length; i++){
      try {
        blocks[i] = resize(ImageIO.read(new File("assets/"+terrain[i])), Maps.blockSize, Maps.blockSize);    

      } catch (IOException e) {

      }
    }
  }

  public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();

    return dimg;
  }  
}