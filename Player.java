import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Player {
    
    //キャラの大きさ
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    //スピード
    private static final int SPEED = 1;
    //ジャンプ力
    private static final int JUMP_SPEED = 15;
    
    //位置
    private double x;
    private double y;
    
    //速度
    private double vx;
    private double vy;
    
    //着地しているか
    private boolean onGround;
    
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
    
    //プレイヤーを描画
    public void draw(Graphics g, int offsetX, int offsetY) {
        g.setColor(Color.RED);
        g.fillOval((int)x + offsetX, (int)y + offsetY, WIDTH, HEIGHT);
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
}
