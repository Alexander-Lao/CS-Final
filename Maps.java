import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Maps {
    public static final int blockSize = 50; //Block size
    
    //Read map from files and store into an array where it will be read
    public Maps() {
        //Read all maps
        try {
            File myObj = new File("levels/levelList.txt");
            Scanner myReader = new Scanner(myObj);
            GamePanel.lastMap = Integer.parseInt(myReader.next());
            myReader.nextLine();
            GamePanel.customMapCount = Integer.parseInt(myReader.next());
            myReader.nextLine();
            for (int i = 1; i <= GamePanel.customMapCount; i++) {
                String name = myReader.nextLine();
                GamePanel.customMapNames[i] = name;
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("level list not found");
            e.printStackTrace();
        }
    }

    //Load specific map onto grid
    public static void loadMap(String mapName) {
        try {
            File myObj = new File("levels/"+mapName+"/Map.txt");
            Scanner myReader = new Scanner(myObj);
            int rows = Integer.parseInt(myReader.next());
            int cols = Integer.parseInt(myReader.next());
            for (int i = 0; i < GamePanel.grid.length; i++) {
                for (int j = 0; j < GamePanel.gridLength; j++) {
                    GamePanel.grid[i][j]=0;
                }
            }
            GamePanel.gridLength = cols;
            myReader.nextLine();
            for (int i = 0; i < rows; i++) {
                String data = myReader.nextLine();
                for (int j = 0; j < cols; j++) {
                    try {
                        //For numbers 0 - 9
                        GamePanel.grid[i][j] = Integer.parseInt(data.charAt(j) + "");
                    }
                    catch (Exception e) {
                        //for converting letters to numbers, starting with a = 10
                        GamePanel.grid[i][j] = data.charAt(j) - 'a' + 10;
                    }
                }
            }
            
            //Add finish line at the end
            GamePanel.gridLength++;
            for(int i = 0; i < rows; i++){
                GamePanel.grid[i][cols] = 18;
            }
            for (int i=0; i<rows; i++) {
                for (int j=0; j<=cols; j++) System.out.print(GamePanel.grid[i][j]+" ");
                System.out.println();
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Map not found");
            e.printStackTrace();
        }
    }
}