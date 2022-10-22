package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class gamePanel extends JPanel implements ActionListener, KeyListener {
	
	    //1 - LEFT
		//2 - RIGHT
		//3 - TOP
		//4 - BOTTOM
	    private static final long serialVersionUID = 1L;
	    List<Point> snakePoints = new ArrayList<Point>();
	    final static int width=850, height = 580, boxSize=10;
	    static int direction  =1 ;

		Point food  = new Point();
		
		private Timer timer ;
		private boolean gameover = false ;
		private int score  = 0 ;
		
		gamePanel(){
			addKeyListener(this);
			setFocusable(true);
			setFocusTraversalKeysEnabled(true);
			timer = new Timer(50,this);
			timer.start();
			Point point = new Point((width/boxSize)/2, (height/boxSize)/2);
			snakePoints.add(point) ;
			getnewfood() ;
			
		}
		
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(Color.white);
		g.drawRect(19, 10, 851, 55);
		g.drawRect(19, 69, 851, 580);
		g.setColor(Color.black);
		g.fillRect(20, 70, 850, 580);
		Font font = new Font("Arial", Font.BOLD, 35);
		g.setColor(Color.white); 
		g.setFont(font);
		g.drawString("-----------SNAKEGAME-----------",200 ,60 );
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 15));
		g.drawString(" SCORE : "+ score, 780, 25);
		logic();
		if(gameover)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 50));
			g.drawString(" GAMEOVER ", 280, 300);
			
			g.drawString("SCORE : "+score, 320, 350);
		}
		else {
		drawSnake(g);
		drawFood(g);
		}
		
	}
	
	private void drawSnake(Graphics g) {
		g.setColor(Color.BLUE);
		for(Point pt: snakePoints) {
			g.fillRect(pt.x*boxSize, pt.y*boxSize, boxSize, boxSize);
		}
		
	}
 
	private void drawFood(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(food.x*boxSize, food.y*boxSize, boxSize, boxSize);
	}
	
	private void getnewfood() {
		Random rand = new Random();
		food.setLocation(rand.nextInt(6, width/boxSize-(boxSize)), rand.nextInt(16, height/boxSize-(boxSize)));
	}
	
	private void logic() {
		Point head = snakePoints.get(0);
    	if(food.x  == head.x && food.y == head.y) {
			getnewfood();
			Point lastPoint = snakePoints.get(snakePoints.size()-1);
			snakePoints.add(new Point(lastPoint.x, lastPoint.y));
			score++ ;
		}
		moveSnake();
	}

	private void moveSnake() {
		Point head = snakePoints.get(0);
		Point pt = new Point(head.x, head.y);
		int xMovement = 1;
		int yMovement = 0;
		switch(direction) {
			case 1: xMovement = -1; yMovement =  0;break;  //left
			case 2: xMovement =  1; yMovement =  0;break;  // right
			case 3: xMovement =  0; yMovement = -1;break;  // up
			case 4: xMovement =  0; yMovement =  1;break;  //down
			default:
				xMovement =yMovement = 0;break;
		}
		pt.setLocation(pt.x+xMovement, pt.y+yMovement);
		snakePoints.add(0, pt);
		snakePoints.remove(snakePoints.size()-1);
		
		handleCrossBorder();
		
	}
	
	private void handleCrossBorder() {
		
		Point head = snakePoints.get(0);
		double x = head.getX();
		double y = head.getY();
		
		if(x>width/boxSize && direction == 2) {
			x = 2;
		}
		if(x<2 && direction == 1) {
			x = width/boxSize;
		}
		if(y>height/boxSize &&  direction == 4) {
			y = 7;
		}
		if(y<7 &&  direction == 3) {
			y = height/boxSize;
		}
		head.setLocation(x, y);
	}
	
	private void collideswithbody() {
		Point head = snakePoints.get(0);
		for(Point pt : snakePoints)
		{
			if(pt != head && pt.x == head.x && pt.y == head.y)
			{
				timer.stop();
				gameover = true ;
			}
		}
	}
	
	 
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode() ;
		switch(key) {
			case KeyEvent.VK_UP :
				if(direction!=4) {
					direction=3;	
				}
				break;		
			case KeyEvent.VK_DOWN :
				if(direction!=3) {
					direction=4;	
				}; 
				break;
			case KeyEvent.VK_LEFT:
				direction=(direction!=2)?1:direction; break;
			case KeyEvent.VK_RIGHT:
				direction=(direction!=1)?2:direction; break;
			case KeyEvent.VK_P:
				direction=0; break;
				
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
 
	@Override
	public void keyReleased(KeyEvent e) {}
 
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		collideswithbody();
	}

}
