


import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class GamePlay extends JPanel implements ActionListener,KeyListener {
 
    private boolean play = false;
    private int totalBrick = 28;
    private Timer timer;
    private int score = 0;
    private int delay=8;
    private int ballposX=20;
    private int ballposY=50;
    private int ballXdir=-1;
    private int ballYdir=-2; 
    private int playerX=350; 
    private MapGenerator map;


    public GamePlay()
    {

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay, this);
        timer.start();

        map = new MapGenerator(4,7);


    }
    public void paint(Graphics g)
    {
        
       

        //Canvas
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);
       
        //Border
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 692,10);
        g.fillRect(0, 3, 10,592);
        g.fillRect(676, 3, 10,592);

        //Paddel
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 540, 80, 8);

        //Brikcs

        map.draw((Graphics2D)g);

        //Ball
        g.setColor(Color.RED);
        g.fillOval(ballposX, ballposY, 20, 20);

        //score
        g.setColor(Color.GREEN);
        g.setFont(new Font("serif", Font.BOLD,20));
        g.drawString("Score :"+score, 550, 30);

        //Game over
        if(ballposY>=570)
        {
            play=false;
            ballXdir=0;
            ballYdir=0;

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 40));
            g.drawString("Game Over!!  & Score : "+score,130, 300);

            g.setColor(Color.YELLOW);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press ENTER To Restart",170, 530);
        }

        //Game Win

        
        if(totalBrick<=0)
        {

            play=false;
            ballXdir=0;
            ballYdir=0;

            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 40));
            g.drawString("YOU WON !!",160, 300);

            g.setColor(Color.YELLOW);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press ENTER To Restart",170, 530); 
        }
        

    }

    private void moveLeft()
    {
        play=true;
        playerX-=20;
    }
    private void moveRight()
    {
        play=true;
        playerX+=20;
    }
  
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
        {
            if(playerX<=10)
                playerX=10;
           else
                moveLeft();
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
        {
            if(playerX>=576)
                playerX=576;
                else
                 moveRight();
        }

        if(e.getKeyCode()==KeyEvent.VK_ENTER)
        {
            if(!play)
            {
                score=0;
                totalBrick=28;
                ballposX=120;
                ballposY=350;
                ballXdir=-1;
                ballYdir=-2;
                playerX=320;

                map=new MapGenerator(4,7);
            }
        }
        repaint();
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(play)
        {
            if(ballposX<=0)
            {
                ballXdir=-ballXdir;
            }
            if(ballposX>=670)
            {
                ballXdir=-ballXdir;
            }
            if(ballposY<=0)
            {
                ballYdir=-ballYdir;
            }

            Rectangle ballRectangle = new Rectangle(ballposX,ballposY,20,20);
            Rectangle paddRectangle = new Rectangle(playerX,550,100,8);

            if(ballRectangle.intersects(paddRectangle))
            {
                ballYdir=-ballYdir;
            }

            A:for(int i=0;i<map.map.length;i++)
            {
                for(int j=0;j<map.map[0].length;j++)
                {
                    if(map.map[i][j]>0)
                    {
                        int width=map.brickWidth;
                        int height=map.bricHight;
                        int brickXposc = 80+j*width;
                        int brickYposc = 50+i*height;

                        Rectangle brickRect = new Rectangle(brickXposc, brickYposc, width, height);
                        if(ballRectangle.intersects(brickRect))
                        {
                            map.setBrick(0, i,j);
                            totalBrick--;
                            score+=1;
                            if(ballposX+19<=brickXposc || ballposX +1>=brickXposc+width)
                            {
                                ballXdir=-ballXdir;
                            }
                            else{
                                ballYdir = -ballYdir;
                            }
                            break A;

                        }
                    }
                }
            }


            ballposX+=ballXdir;
            ballposY+=ballYdir;
        }
        repaint();        
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {  

    }   
}
