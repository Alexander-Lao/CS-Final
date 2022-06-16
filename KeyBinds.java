//Screen -4
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KeyBinds extends JPanel{
    public Thread gameThread;
    public Image image;
    public Graphics graphics;

    static JTextField t;
    static JFrame f;
    static JButton b;
    static JLabel l;

    public static char[] key = {'q','p','r','s','l','a','d'};
    private String[] keyName = {"Gravity", "Note Click", "Reset", "Save", "Load", "Shift Left", "Shift Right"};

    public KeyBinds(){
        f = new JFrame();
        t = new JTextField("a", 1);
        l = new JLabel("desc:");
        b = new JButton("Confirm");

        JPanel p = new JPanel();
        p.add(l);
        p.add(t);
        p.add(b);
        f.add(p);

        f.setSize(200, 80);
        f.setVisible(false);
        f.setResizable(false);
        t.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) { 
                if (t.getText().length() >= 1 ) // limit textfield to 1 character
                    e.consume(); 
            }  
        });

        b.addActionListener(e -> {
            f.setVisible(false);
        });

        f.setLocationRelativeTo(null);

        System.out.println(t.getText());
        t.setText("h");
    }

    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        g.drawImage(GameFrame.backgroundImage[3], 0, 0, this);
    }

    public void mousePressed(MouseEvent e){
        f.setVisible(true);
        l.setText("Lmao nerd");
        t.setText("2");
    }

    public void mousePosition(int x, int y){
        // if(x > backX - Menu.padding && x < backX - Menu.padding + backWidth + Menu.padding*2 && y > backY - height - Menu.padding && y < backY + Menu.padding*2 - 13){
        //     backHover = true;
        // }
        // else{
        //     backHover = false;
        // }
    }

}
