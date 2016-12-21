import java.awt.Container;
import javax.swing.JFrame;

public class Game extends JFrame {

    public Game() {
        
        setTitle("マップスクロール");
        //サイズ変更不可
        setResizable(false);
        
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);
        
        pack();
    }
    
    public static void main(String[] args) {
        Game frame = new Game();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
