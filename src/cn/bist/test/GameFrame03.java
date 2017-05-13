package cn.bist.test;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



/**
 * 测试物体沿着水平和垂直移动
 * @author Administrator
 *
 */
public class GameFrame03 extends Frame { //GUI编程，AWT,Swing等
Image img =GameUtil.getImage("image/sun.");	
	/**
	 * 加载窗口
	 */
	  
	public void launchFrame(){
		setSize(500,500);
		setLocation(100,100);
		setVisible(true);
		
		new PaintThread().start(); //启动重画线程
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
			
		});
	}
	private double x=100,y=100;
	private boolean left;
	private boolean up;
	@Override
	public void paint(Graphics g) {
		g.drawImage(img,(int)x,(int)y,null);
		if(up){
			y-=10;
		}else{
			y+=10;
		}
		if(y>500-30){
			up = true;
		}
		if(y<30){
			up =false;
		}
	}
	
	/**
	 * 定义一个重画窗口的线程类，是一个内部类
	 */
	class PaintThread extends Thread {
		public void run(){
			while(true){
				repaint();
				try{
					Thread.sleep(40);
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
}
