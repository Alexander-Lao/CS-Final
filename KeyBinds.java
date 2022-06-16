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
    private String[] keyNames = {"Gravity", "Note Click", "Reset", "Save", "Load", "Shift Left", "Shift Right"};
    private String[] combinedName = new String[key.length];
    public static int currentlyBinding;

    private boolean[] hover = new boolean[7];
    private int[][] pos = new int[7][3]; // x y width 
    private int height;

    public String title;
    public String title2;

    public KeyBinds(){
        title = "Game";
        title2 = "Editor";
        currentlyBinding = -1;
        for(int i = 0; i < pos.length; i++){
            combinedName[i] = key[i] + " - " + keyNames[i];
        }
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
            key[currentlyBinding] = t.getText().charAt(0);
        });

        f.setLocationRelativeTo(null);
    }

    public void paint(Graphics g) {
        image = createImage(GamePanel.GAME_WIDTH, GamePanel.GAME_HEIGHT);
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        g.drawImage(GameFrame.backgroundImage[3], 0, 0, this);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        for(int i = 0; i < pos.length; i++){
            pos[i][2] = g.getFontMetrics().stringWidth(combinedName[i]);
        }
        height = (g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent());

        //Left Row for play
        pos[0][0] = (GamePanel.GAME_WIDTH - pos[0][2])/4;
        pos[0][1] = (GamePanel.GAME_HEIGHT - height)/2;
        pos[1][0] = (GamePanel.GAME_WIDTH - pos[0][2])/4;
        pos[1][1] = (GamePanel.GAME_HEIGHT - height)*5/8;
        pos[2][0] = (GamePanel.GAME_WIDTH - pos[0][2])/4;
        pos[2][1] = (GamePanel.GAME_HEIGHT - height)*6/8;

        //Right Row for editor
        pos[3][0] = (GamePanel.GAME_WIDTH - pos[0][2])*3/4;
        pos[3][1] = (GamePanel.GAME_HEIGHT - height)/2;
        pos[4][0] = (GamePanel.GAME_WIDTH - pos[0][2])*3/4;
        pos[4][1] = (GamePanel.GAME_HEIGHT - height)*5/8;
        pos[5][0] = (GamePanel.GAME_WIDTH - pos[0][2])*3/4;
        pos[5][1] = (GamePanel.GAME_HEIGHT - height)*6/8;
        pos[6][0] = (GamePanel.GAME_WIDTH - pos[0][2])*3/4;
        pos[6][1] = (GamePanel.GAME_HEIGHT - height)*7/8;

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        for(int i = 0; i < pos.length; i++){      
            if(hover[i]){
                g.setColor(java.awt.Color.darkGray);
            }
            else{
                g.setColor(java.awt.Color.gray);
            }
            
            g2.drawRoundRect(pos[i][0] - Menu.padding, pos[i][1] - height - Menu.padding, pos[i][2] + Menu.padding*2 , height + Menu.padding*2, 30, 30);
            g2.fillRoundRect(pos[i][0] - Menu.padding, pos[i][1] - height - Menu.padding, pos[i][2] + Menu.padding*2 , height + Menu.padding*2, 30, 30);
            
            g.setColor(java.awt.Color.white);
            g2.drawString(combinedName[i], pos[i][0], pos[i][1]);
        }

        //Set titles here becuase I need the x values from above
        g.setColor(java.awt.Color.black);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
        g.drawString(title, (GamePanel.GAME_WIDTH - pos[0][2])/4, (GamePanel.GAME_HEIGHT) / 4);
        g.drawString(title2, (GamePanel.GAME_WIDTH - pos[0][2])*3/4, (GamePanel.GAME_HEIGHT) / 4);
    }

    public void mousePressed(MouseEvent e){
        for(int i = 0; i < pos.length; i++){
            if(hover[i]){ 
                currentlyBinding = i;
                t.setText(key[currentlyBinding]+"");
            }
        }
        
        f.setVisible(true);
        l.setText("Lmao nerd");
        t.setText("2");
    }

    public void mousePosition(int x, int y){
        for(int i = 0; i < pos.length; i++){
            if(x > pos[i][0] - Menu.padding && x < pos[i][0] - Menu.padding + pos[i][2] + Menu.padding*2 && y > pos[i][1] - height - Menu.padding && y < pos[i][1] + Menu.padding*2 - 13){
                hover[i] = true;
            }
            else{
                hover[i] = false;
            }
        }
    }
}
