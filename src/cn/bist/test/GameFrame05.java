package cn.bist.test;

import java.awt.Graphics;
import java.awt.Image;

public class GameFrame05 extends MyFrame {
	Image img =GameUtil.getImage("images/sun.jpg");	
	
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
	
	 public static void main(String[] args) {
		  GameFrame05 gf= new GameFrame05();
		  gf.launchFrame();
		}
}
