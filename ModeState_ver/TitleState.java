import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class TitleState implements GameMode{
    
    private final static int START	= 0;
    private final static int END		= 1;
    private int _cursorPos = START;
    
    // メインタイトルの位置
    private final static int TITLEPOSX	= 50;
    private final static int TITLEPOSY	= 150;
    
    // メインメニューの表示位置，表示感覚，カーソル位置（x座標のみ）
    private final static int MENUPOSX		= 200;
    private final static int MENUPOSY		= 280;
    private final static int MENUINTVL	= 50;
    private final static int CURSOR		= 150;
    
    // キーフラグ
    private boolean m_bKeyUp;
    public void KeyUp(boolean on){m_bKeyUp = on;}
    private boolean m_bKeyDown;
    public void KeyDown(boolean on){m_bKeyDown = on;}
    private boolean m_bKeyZ;
    public void KeyZ(boolean on){m_bKeyZ = on;}
    
    public TitleState()
    {
        init();
    }
    
    @Override
    public void init() {
        // TODO Auto-generated method stub
    }
    
    // キー移動．決定など
    public void run(GameManager gm)
    {
        if(m_bKeyUp)
        {
            if(_cursorPos != START)
                _cursorPos--;
        }
        else if(m_bKeyDown)
        {
            if(_cursorPos != END)
                _cursorPos++;
        }
        
        // Zを押した時
        if(m_bKeyZ)
        {
            // カーソル位置で分岐
            switch(_cursorPos)
            {
                case START:
                    gm.ChangeMode(new MainMode());
                    break;
//                case END:
//                    gm.ChangeMode(new ExitState());
//                    break;
            }
        }
    }
    
    @Override
    public void Show(Graphics2D g2) {
        g2.setFont(new Font("Arial", Font.BOLD, 28));
        
        // タイトル
        g2.setPaint(Color.yellow);
        g2.drawString("Templete Shooting",TITLEPOSX,TITLEPOSY);
        
        // スタート
        if(_cursorPos == START)
            g2.setPaint(Color.green);
        else
            g2.setPaint(Color.yellow);
        
        g2.drawString("Game Start",MENUPOSX,MENUPOSY);
        
        // おわり
        if(_cursorPos == END)
            g2.setPaint(Color.green);
        else
            g2.setPaint(Color.yellow);
        
        g2.drawString("Quit",MENUPOSX,MENUPOSY + MENUINTVL);
        
        // カーソル
        g2.setPaint(Color.green);
        switch(_cursorPos)
        {
            case START:
                g2.drawString("@",CURSOR,MENUPOSY);
                break;
            case END:
                g2.drawString("@",CURSOR,MENUPOSY + MENUINTVL);
                break;
        }
        
        // 操作表示
        g2.setPaint(Color.yellow);
        g2.setFont(new Font("MS ゴシック", Font.BOLD, 20));
        g2.drawString("↑↓キーでカーソル移動", 50,600);
    }
    
    @Override
    public void KeyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        switch(arg0.getKeyCode())
        {
            case KeyEvent.VK_Z:
                KeyZ(true);
                break;
            case KeyEvent.VK_UP:
                KeyUp(true);
                break;
            case KeyEvent.VK_DOWN:
                KeyDown(true);
                break;
        }
    }
    
    @Override
    public void KeyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
        switch(arg0.getKeyCode())
        {
            case KeyEvent.VK_Z:
                KeyZ(false);
                break;
            case KeyEvent.VK_UP:
                KeyUp(false);
                break;
            case KeyEvent.VK_DOWN:
                KeyDown(false);
                break;
        }
    }
    
    @Override
    public void KeyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
}
