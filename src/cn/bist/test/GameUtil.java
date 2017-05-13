package cn.bist.test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 
 * ��Ϸ�����г��õĹ��ߣ����磺����ͼƬ�ȷ�����
 *
 */

public class GameUtil {
	private GameUtil(){}//������ͨ�������췽��˽����
	
       public static Image getImage(String path){
    	 URL u=GameUtil.class.getClassLoader().getResource(path);
    	BufferedImage img=null;
    	try{
    	img=ImageIO.read(u);
    	}catch (IOException e){
    		e.printStackTrace();
    	}
    	return img;
       }
	}

