import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Maps {
    public Maps(){
        int mapNumber = 0;

        try {
            File myObj = new File("Maps.txt");
            Scanner myReader = new Scanner(myObj);

            int width = Integer.parseInt(myReader.next());
            int height = Integer.parseInt(myReader.next());
            myReader.nextLine();
            
            int[][] tempMap = new int[height][width];

            while (myReader.hasNextLine()) {
                for(int i = 0; i < height; i++){
                    String data = myReader.nextLine();

                    for(int j = 0; j < width; j++){
                        tempMap[i][j] = Integer.parseInt(data.charAt(j) +"");
                    }
                }
                    GamePanel.grid[mapNumber] = tempMap;
                    mapNumber++;
                    myReader.nextLine();
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}
