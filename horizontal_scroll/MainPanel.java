import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;


public class MainPanel extends JPanel implements Runnable, KeyListener {

    //パネル
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    //マップ
    private Map map;
    //プレイヤー
    private Player player;
    //キーの入力状態
    private boolean spacePressed;
    //ゲームループ
    private Thread gameLoop;
    
    public MainPanel() {
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //キー入力受付
        setFocusable(true);
        
        //マップ作成
        map = new Map();
        
        //キャラ作成
        player = new Player(192, 32, map);
        
        addKeyListener(this);
        
        //ゲーム開始
        gameLoop = new Thread(this);
        gameLoop.start();
    }
    
    public void run(){
        
        while (true) {
            
            //自動で右へ移動
            player.accelerateRight();
            if (spacePressed) {
                player.jump();
                spacePressed = false;
            }
            //状態更新
            player.update();
            //再描画
            repaint();
            //休止
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void paintComponent(Graphics g) {    //描画
        
        super.paintComponent(g);
        int offsetX = MainPanel.WIDTH/2 - (int)player.getX();
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, MainPanel.WIDTH - Map.WIDTH);
        int offsetY = MainPanel.HEIGHT/2 - (int)player.getY();
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, MainPanel.HEIGHT - Map.HEIGHT);
        //背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        //マップ
        map.draw(g, offsetX, offsetY);
        //プレイヤー
        player.draw(g, offsetX, offsetY);
    }
    
    public void keyPressed(KeyEvent e) {    //キーを押した時
        
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
    }
    
    public void keyReleased(KeyEvent e) {    //キーを離した時
        
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
    }
    
    public void keyTyped(KeyEvent e) {}
    
}
