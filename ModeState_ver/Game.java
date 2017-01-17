import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game extends JPanel implements Runnable, KeyListener{
    
    public static Thread mainThread = null;
    
    public static void  main(String[] args){
        
        JFrame frame = new JFrame();
        
        Game app = new Game();
        
        frame.getContentPane().add(app);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Game");
        frame.setVisible(true);
        
        mainThread = new Thread(app);
        
        app.init();
    }
    
    private GameManager _gmanager;
    
    private Image buffer;
    private Graphics bufferGraphics;
    
    public void init(){
        setBackground(Color.black);
        setForeground(Color.white);
        
        if(buffer == null){
            buffer = createImage(640,480);
            bufferGraphics = buffer.getGraphics();
        }
        
        addKeyListener(this);
        requestFocus();
        
        _gmanager = new GameManager(this);
        
        mainThread.start();
    }
    
    public void run(){
        while (true){
            try{
                Thread.sleep(20);
            }catch (InterruptedException e){
                break;
            }
        
            bufferGraphics.setBackground(Color.black);
            bufferGraphics.clearRect(0, 0, 480, 640);
            
            bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            bufferGraphics.setStroke(new BasicStroke(4.0f));
            
            _gmanager.GameMainUpdate();
            
            ShowObjects(bufferGraphics);
            
            repaint();
        }
    }
    
    public void ShowObjects(Graphics g)
    {
        _gmanager.State().Show(g);
    }
    
    public void paintComponent(Graphics g){
        g.setColor(Color.black);
        g.clearRect(0, 0, 480, 640);
        g.drawImage(buffer, 0, 0, this);
    }
    
    public void keyPressed(KeyEvent e){
        _gmanager.State().KeyPressed(e);
    }
    
    public void keyReleased(KeyEvent e){
        _gmanager.State().KeyReleased(e);
    }
    public void keyTyped(KeyEvent e)
    {
        _gmanager.State().KeyTyped(e);
    }
    
}

