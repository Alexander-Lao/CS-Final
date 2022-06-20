import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class CustomLevelSave extends JFrame implements ItemListener,ActionListener {
    static JFrame f;
    static JLabel txt,txt2;
    static JComboBox<String> cb;
    static JTextField fileNameIn = new JTextField(20);
    static int cl = 0;
    JButton b;
    public CustomLevelSave() {
        // create a new frame
        f = new JFrame("Select a Level or Create a New One");
        f.setSize(1200, 300);
        f.setLocationRelativeTo(null);
        f.setLayout(new FlowLayout());
        cb = new JComboBox<String>(Arrays.copyOfRange(GamePanel.customMapNames,1,GamePanel.customMapCount+1));
        cb.addItemListener(this);
        cb.setFont(new Font("Serif",Font.BOLD,30));
        txt = new JLabel("Select the level to save to:   ");
        txt.setFont(new Font("SansSerif",Font.BOLD,30));
        txt.setForeground(Color.black);
        txt2 = new JLabel("Or create a new level with the name:   ");
        txt2.setFont(new Font("SansSerif",Font.BOLD,30));
        txt2.setForeground(Color.black);
        JPanel p = new JPanel();
        p.add(txt);
        p.add(cb);
        f.add(p);
        f.setVisible(true);
        b = new JButton("Save Level");
        b.setPreferredSize(new Dimension(700,100));
        b.setFont(new Font("SansSerif",Font.BOLD,30));
        b.addActionListener(this);
        JPanel p2 = new JPanel();
        p2.add(txt2);
        fileNameIn.setText("");
        fileNameIn.setFont(new Font("SansSerif",Font.BOLD,30));
        p2.add(fileNameIn);
        f.add(p2);
        f.add(b);
        if (GamePanel.customMapCount == 0) cl = 0;
        else cl = 1;
    }
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cb) {
            cl = cb.getSelectedIndex() + 1;
        }
    }
    public void actionPerformed(ActionEvent e) {
        //close window and load map
        if (fileNameIn.getText().equals("")) {
            if (cl == 0) return;
            LevelMaker.savedLevel = cl;
            GamePanel.saveSelectedMap = true;
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            return;
        }
        int ret = checkValidName();
        if (ret == 0) {
            JLabel tempLabel = new JLabel("what even is that name lmao (only alphanumeric and underscores allowed)");
            tempLabel.setFont(new Font("SansSerif",Font.BOLD,20));
            JOptionPane.showMessageDialog(null, tempLabel,"Error",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        else if (ret == -1) {
            JLabel tempLabel = new JLabel("lmao that already exists");
            tempLabel.setFont(new Font("SansSerif",Font.BOLD,20));
            JOptionPane.showMessageDialog(null, tempLabel,"Error",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //make a new folder and do stuff
        new File("levels/"+fileNameIn.getText()).mkdirs();
        File myObj = new File("levels/"+fileNameIn.getText()+"/Map.txt");
        try {
            myObj.createNewFile();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        File myObj2 = new File("levels/"+fileNameIn.getText()+"/Notes.txt");
        try {
            myObj2.createNewFile();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        GamePanel.customMapCount++;
        GamePanel.customMapNames[GamePanel.customMapCount] = fileNameIn.getText();
        LevelMaker.savedLevel = GamePanel.customMapCount;
        //modify levelList
        try {
            File myFile = new File("levels/levelList.txt");
            Scanner myReader = new Scanner(myFile);
            int tmp1 = Integer.parseInt(myReader.next());
            myReader.nextLine();
            int tmp2 = Integer.parseInt(myReader.next());
            myReader.nextLine();
            String[] tmparr = new String[tmp2+1];
            for (int i = 1; i <= tmp2; i++) {
                tmparr[i] = myReader.nextLine();
            }
            myReader.close();
            FileWriter myWriter = new FileWriter("levels/levelList.txt");
            myWriter.write(tmp1+"\n"+(tmp2+1)+"\n");
            for (int i = 1; i <= tmp2; i++) {
                myWriter.write(tmparr[i]+"\n");
            }
            myWriter.write(fileNameIn.getText());
            myWriter.close();
        }
        catch (IOException e1) {
            System.out.println("cant read+write uhoh");
        }
        GamePanel.saveSelectedMap = true;
        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
    }
    public int checkValidName() {
        String fileName = fileNameIn.getText();
        for (int i = 0; i < fileName.length(); i++) {
            if (!Character.isLetterOrDigit(fileName.charAt(i)) && fileName.charAt(i)!='_') {
                return 0;
            }
        }
        for (int i = 1; i <= GamePanel.customMapCount; i++) {
            if (GamePanel.customMapNames[i].equals(fileName)) {
                return -1;
            }
        }
        return 1;
    }
}