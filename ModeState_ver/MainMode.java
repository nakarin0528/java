import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MainMode implements GameMode{

    //マップ
    private Map map;
    //プレイヤー
    private Player player;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

public MainMode(){
	init();
}

public void init(){
	//マップ生成
	map = new Map();
	//キャラクター生成
	player = new Player(192, 32, map);

}

public void show(Graphics2D g2){
        //offsetを計算，マップ端ではスクロールしない
        int offsetX = MainMode.WIDTH/2 - (int)player.getX();
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, MainPanel.WIDTH - Map.WIDTH);
        int offsetY = MainMode.HEIGHT/2 - (int)player.getY();
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, MainPanel.HEIGHT - Map.HEIGHT);
        //背景
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        //マップ
        map.show(g2, offsetX, offsetY);
        //プレイヤー
        player.show(g2, offsetX, offsetY);
}

public void run(){
	player.Move();
}


public void KeyPressed(KeyEvent arg0){
	player.KeyPressedAnalyze(arg0);
}
public void KeyReleased(KeyEvent arg0){
	player.KeyReleasedAnalyze(arg0);
}
public void KeyTyped(KeyEvent arg0){
	player.KeyTypedAnalyze(arg0);
}


}
