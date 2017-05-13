package cn.bist.test;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyFrame extends Frame{
	public static final int GAME_width=500;
	public static final int GAME_HIGHT=500;
	public void launchFrame(){
		setSize(GAME_width,GAME_HIGHT);
		setLocation(100,100);
		setVisible(true);
		
		new PaintThread().start(); //启动重画线程
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
			
		});
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
