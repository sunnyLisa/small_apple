package cn.bist.test;
/**
 * �����������Ÿ��ֹ켣�˶�
 * ���ʵ��һ��С��̨����Ϸ
 * @author Administrator
 *
 */

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


	

	public class GameFrame02 extends Frame {  //GUI��̣�AWT,swing��
		Image img=GameUtil.getImage("images/sun.jpg");
		
		
		
		
		/**
		 * ���ش���
		 */
		
		public void launchFrame(){
			setSize(500,500);
			setLocation(100,100);
			setVisible(true);
			
			new PaintThread().start();//�����ػ��߳�
			addWindowListener(new WindowAdapter(){ //�����ڲ���

				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
				
				
			});
			
		}
		
		private double x=100, y=100;
		private double degree=3.14/3; //[0,2pi]
		private double speed=20;
		
	@Override
		public void paint(Graphics g) {
			g.drawImage(img,(int)x,(int)y,null);
			if(speed>0){
            speed =speed -0.05;
			}else{
			speed = 0;
			}
			x +=speed*Math.cos(degree);
			y +=speed*Math.cos(degree);
			
			if(y>500-30||y<0){
				degree = -degree;
				}
			if(x<0||x>500-30){
				degree = Math.PI-degree;
				}
		}
	  
		
		/**����һ���ػ����ڵ��߳��࣬��һ���ڲ���
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
		  GameFrame02 gf= new GameFrame02();
		  gf.launchFrame();
		}
	}

	


