import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Arrays;
public class CustomLevelSelect extends JFrame implements ItemListener,ActionListener {
    static JFrame f;
    static JLabel txt;
    static JComboBox<String> cb;
    public static int ll = 0;
    JButton b;
    public CustomLevelSelect() {
        // create a new frame
        f = new JFrame("Select a Level");
        f.setSize(1000, 250);
        f.setLocationRelativeTo(null);
        f.setLayout(new FlowLayout());
        cb = new JComboBox<String>(Arrays.copyOfRange(GamePanel.customMapNames,1,GamePanel.customMapCount+1));
        cb.addItemListener(this);
        cb.setFont(new Font("Serif",Font.BOLD,30));
        txt = new JLabel("Select the level:   ");
        txt.setFont(new Font("SansSerif",Font.BOLD,30));
        txt.setForeground(Color.black);
        JPanel p = new JPanel();
        p.add(txt);
        p.add(cb);
        f.add(p);
        f.setVisible(true);
        b = new JButton("Load Level");
        b.setPreferredSize(new Dimension(700,100));
        b.setFont(new Font("SansSerif",Font.BOLD,30));
        b.addActionListener(this);
        f.add(b);
        if (GamePanel.customMapCount == 0) ll = 0;
        else ll = 1;
    }
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cb) {
            ll = cb.getSelectedIndex() + 1;
        }
    }
    public void actionPerformed(ActionEvent e) {
        //close window and load map
        if (ll != 0) {
            LevelMaker.loadedLevel = ll;
            GamePanel.loadSelectedMap = true;
            f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
        }
    }
}