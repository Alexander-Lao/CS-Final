import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Notes {
    public static final int blockSize = 50;

    public Notes() {
        int mapNumber = 0;
        try {
            File myObj = new File("Notes.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                mapNumber++;
                int noteCount = myReader.nextInt();
                for (int i = 0; i < noteCount; i++) {
                    int xpos = myReader.nextInt(), ypos = myReader.nextInt();
                    GamePanel.notes[mapNumber][i][0] = xpos;
                    GamePanel.notes[mapNumber][i][1] = ypos;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}