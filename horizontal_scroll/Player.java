import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

public class Player {
    
    //キャラの大きさ
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    //スピード
    private static final int SPEED = 1;
    //ジャンプ力
    private static final int JUMP_SPEED = 15;
    
    //方向
    private static final int RIGHT = 0;
    
    //位置
    private double x;
    private double y;
    
    //速度
    private double vx;
    private double vy;
    
    //着地しているか
    private boolean onGround;
    
    //向いている方向
    private int dir = RIGHT;
    
    //アニメーション用カウンタ
    private int count;
    
    //プレイヤー画像
    private Image image;
    
    //マップへの参照
    private Map map;
    
    //天井についたかどうか
    private boolean onCeiling;
    
    public Player(double x, double y, Map map) {
        this.x = x;
        this.y = y;
        this.map = map;
        vx = 0;
        vy = 0;
        onGround = false;
        onCeiling = false;
        count = 0;
        
        loadImage();
        
        //アニメーション用
        AnimationThread thread = new AnimationThread();
        thread.start();
    }
    
    //停止
    public void stop() {
        vx = 0;
    }
    
    //右に加速
    public void accelerateRight() {
        vx = SPEED;
    }
    
    //ジャンプする
    public void jump() {
        //if (onGround) {
            //上へ速度を加える
            vy = -JUMP_SPEED;
          //  onGround = false;
        //}
    }
    
    //プレイヤー状態更新
    public void update() {
        //重力がかかる
        vy += Map.GRAVITY;
        
        /*x方向の当たり判定*/
        //移動先
        double newX = x + vx;
        //移動先のタイルの有無
        Point tile = map.getTileCollision(this, newX, y);
        if (tile==null) {   //タイルなし
            x = newX;
        } else {    //タイルあり
            if (vx > 0) {
                x = Map.tilesToPixels(tile.x) - WIDTH;  //位置調整
            }
            vx = 0;
        }
        
        /*y方向の当たり判定*/
        //移動先
        double newY = y + vy;
        //移動先のタイルの有無
        tile = map. getTileCollision(this, x, newY);
        if (tile == null){  //タイルなし
            y = newY;
            onGround = false;
            onCeiling = false;
        } else {    //タイルあり
            if(vy>0) {  //下に移動中の時，下のブロックと衝突
                y = Map.tilesToPixels(tile.y) - HEIGHT;
                vy = 0;
                onGround = true;
            } else if (vy<0) {
                y = Map.tilesToPixels(tile.y + 1);
                vy = 0;
                onCeiling = true;
            }
        }
        /*
        //速度をもとに位置更新
        x += vx;
        y += vy;*/
        
        //天井についたかどうか
        if (y< 0) {
            y = 0;
            onCeiling = true;
        }
        
    }
    
    private void loadImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource("image/player.gif"));
        image = icon.getImage();
    }
    
    //プレイヤーを描画
    public void draw(Graphics g, int offsetX, int offsetY) {
        g.drawImage(image,
                    (int)x + offsetX, (int)y + offsetY,
                    (int)x + offsetX + WIDTH, (int)y + offsetY + HEIGHT,
                    count * WIDTH, dir * HEIGHT,
                    count * WIDTH + WIDTH, dir * HEIGHT + HEIGHT,
                    null);
    }
    
    //アニメーション処理
    private class AnimationThread extends Thread {
        public void run() {
            while (true) {
                if(count == 0){
                    count=1;
                } else {
                    count=0;
                }
                
                //絵の切り替え
                try{
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
}
