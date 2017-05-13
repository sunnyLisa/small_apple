package cn.bist.test;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



/**
 * ������������ˮƽ�ʹ�ֱ�ƶ�
 * @author Administrator
 *
 */
public class GameFrame03 extends Frame { //GUI��̣�AWT,Swing��
Image img =GameUtil.getImage("image/sun.");	
	/**
	 * ���ش���
	 */
	  
	public void launchFrame(){
		setSize(500,500);
		setLocation(100,100);
		setVisible(true);
		
		new PaintThread().start(); //�����ػ��߳�
		
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
