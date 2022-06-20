import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Notes {
    public static final int blockSize = 50; //The size of every block in pixels

    public Notes() {
        //Read the notes file and store values of each note
        try {
            File myObj = new File("Notes.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                int noteCount = myReader.nextInt();
                GamePanel.noteCount = noteCount;
                for (int i = 0; i < noteCount; i++) {
                    int xpos = myReader.nextInt(), ypos = myReader.nextInt();
                    GamePanel.notes[i][0] = xpos;
                    GamePanel.notes[i][1] = ypos;
                }
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void loadNotes(String mapName) {
        ///Load the notes when in that map
        try {
            File myObj = new File("levels/"+mapName+"/Notes.txt");
            Scanner myReader = new Scanner(myObj);
            int nc = Integer.parseInt(myReader.next());
            GamePanel.noteCount = nc;
            myReader.nextLine();
            for (int i = 0; i < nc; i++) {
                int xpos = Integer.parseInt(myReader.next());
                int ypos = Integer.parseInt(myReader.next());
                GamePanel.notes[i][0] = xpos; GamePanel.notes[i][1] = ypos;
                myReader.nextLine();
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Map not found");
            e.printStackTrace();
        }
    }
}