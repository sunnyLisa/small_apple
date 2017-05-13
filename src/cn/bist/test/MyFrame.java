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
		
		new PaintThread().start(); //�����ػ��߳�
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
			
		});
	}
	/**
	 * ����һ���ػ����ڵ��߳��࣬��һ���ڲ���
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
