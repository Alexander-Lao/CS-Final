import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Maps {
    public static final int blockSize = 50;

    public Maps() {
        int mapNumber = 0;
        try {
            File myObj = new File("Maps.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                mapNumber++;
                int rows = Integer.parseInt(myReader.next());
                int cols = Integer.parseInt(myReader.next());
                myReader.nextLine();
                System.out.println(mapNumber);
                for (int i = 0; i < rows; i++) {
                    String data = myReader.nextLine();
                    for (int j = 0; j < cols; j++) {
                        try {
                            //For numbers 0 - 9
                            GamePanel.grid[mapNumber][i][j] = Integer.parseInt(data.charAt(j) + "");
                        } catch (Exception e) {
                            //for converting letters to numbers, starting with a = 10
                            GamePanel.grid[mapNumber][i][j] = data.charAt(j) - 'a' + 10;
                        }
                        System.out.print(GamePanel.grid[mapNumber][i][j]);
                    }
                    System.out.println();
                }
            }
            myReader.close();
            GamePanel.lastMap = mapNumber;
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
/*
17
952 50
1499 600
1594 600
2170 358
2695 550
3195 600
3595 50
3666 50
4434 600
4504 600
4577 600
4647 600
5114 50
5191 50
5721 50
6147 50
6695 50
*/