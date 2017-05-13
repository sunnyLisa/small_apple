package cn.bist.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 游戏窗口类
 * @author Administrator
 *
 */

public class GameFrame extends Frame {  //GUI编程：AWT,swing等
	Image img=GameUtil.getImage("images/sun.jpg");
	
	
	
	
	/**
	 * 加载窗口
	 */
	
	public void launchFrame(){
		setSize(500,500);
		setLocation(100,100);
		setVisible(true);
		
		new PaintThread().start();//启动重画线程
		addWindowListener(new WindowAdapter(){ //匿名内部类

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
			
		});
		
	}
	
	double x=10;double y=10;
	
@Override
	public void paint(Graphics g) {
	    //System.out.println("paint!!!");
		g.drawLine(100,100,200,200);
		g.drawRect(100,100,200,200);
		g.drawOval(100,100,200,200);
		g.drawRoundRect(150, 150, 100, 100, 50, 50);
		
		Font f=new Font("宋体",Font.BOLD,40);
		g.setFont(f);
		g.drawString("我是喵星人",100,100);
		g.fillRect(100,100,20,20);
		Color c=g.getColor();
		g.setColor(c);
		
		g.setColor(Color.red);
		
		g.fillOval(300,300,20,20);
		g.drawImage(img,(int)x,(int)y,null);
		x +=3;
		y +=3;
		
		
		
	}
	/**定义一个重画窗口的线程类，是一个内部类
	 * 
	 * @author Administrator
	 *
	 */

	class PaintThread extends Thread{
		public void run(){
			while(true){
				repaint();
				try {
					Thread.sleep(40);//1s=1000ms
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				
			}
		}
	}

public static void main(String[] args) {
	GameFrame gf=new GameFrame();
	gf.launchFrame();
	}
}
