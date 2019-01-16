package com.emate.shop.common;


import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * 
 * 生成二维码的类
 * @author likk
 *
 */
public class QRCodeUtil { 
	private static final Logger logger = Logger.getLogger(QRCodeUtil.class);
	/*
	 * 生成二维码图片信息流
	 * @param text:文本信息  width:二维码宽度  height:二维码高度  format：生成二维码格式 filepath:文件路径  (文件名的格式一定要和format一致)
	 * @throws Exception
	 *
	 */
	public static void  createQRCodeImage (String text,int width,int height,String format,OutputStream output) throws IOException{
		BufferedImage image=null;
		try{
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();    
        //内容所使用编码    
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        //边框的大小 1：最小 2、3、4依次增大
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
                BarcodeFormat.QR_CODE, width, height, hints);    
        //生成二维码    
		  image=MatrixToImageWriter.toBufferedImage(bitMatrix);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		ImageIO.write(image, format, output);
	}
	/*
	 * 解析二维码信息
	 * @param filePath:二维码的路径信息
	 * @return 二维码的信息
	 */
	@SuppressWarnings("unchecked")
	public static String decodeQRCode(String filePath){
		 Result result=null;
		 try {
             MultiFormatReader formatReader = new MultiFormatReader();
			 File file = new File(filePath);
			 BufferedImage image = ImageIO.read(file);;
			 LuminanceSource source = new BufferedImageLuminanceSource(image);
			 Binarizer  binarizer = new HybridBinarizer(source);
			 BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			 @SuppressWarnings("rawtypes")
			Map hints = new HashMap();
			 hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			  result = formatReader.decode(binaryBitmap,hints);
			 System.out.println("result = "+ result.toString());
			// System.out.println("resultFormat = "+ result.getBarcodeFormat());
			// System.out.println("resultText = "+ result.getText());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		return result.toString();
	}
	
	
	/**
	 * 给二维码添加背景
	 */
	public static void addBackground(String url,OutputStream os){
	      try
	        {
	    	 
				BufferedImage image = ImageIO.read(new URL(url));
				
				 BufferedImage newImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_RGB);
				 for(int x=0;x<image.getWidth();x++){
					 for(int y=0;y<image.getHeight();y++){
						 newImage.setRGB(x, y, image.getRGB(x, y));
					 }
				 }
	            ImageIO.write(newImage, "png", os);
	        }
	        catch (Exception e)
	        {
	            logger.error(e.getMessage(), e);
	        }
	}
	
	/**
	* 给二维码图片添加Logo
	* 
	* @param qrPic 二维码图片路径
	* @param logoPic 需要添加logo的路径 
	* @param destFilePath 目标文件地址("D:/newPic.jpg")
	* 
	*/
	public static void  addLogo_QRCode(String qrPict, String logoPict, LogoConfig logoConfig,String destFilePath)
	{
		try
		{
			File qrPic=new File(qrPict);
			File logoPic=new File(logoPict);
			if (!qrPic.isFile() || !logoPic.isFile())
			{
				System.out.print("file not find !");
				System.exit(0);
			}
			/**
			* 读取二维码图片，并构建绘图对象
			*/
			BufferedImage image = ImageIO.read(qrPic);
			Graphics2D g = image.createGraphics();
			/**
			* 读取Logo图片
			*/
			BufferedImage logo = ImageIO.read(logoPic);
			int widthLogo = logo.getWidth(), heightLogo = logo.getHeight();
			// 计算图片放置位置
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - logo.getHeight()) / 2;
			//开始绘制图片
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
			g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
			g.setStroke(new BasicStroke(logoConfig.getBorder()));
			g.setColor(logoConfig.getBorderColor());
			g.drawRect(x, y, widthLogo, heightLogo);
			g.dispose();
			ImageIO.write(image, "jpeg", new File(destFilePath));
		}catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	} 
	/**
	* 给二维码图片添加Logo
	* 
	* @param qrPic 二维码图片路径
	* @param logoPic 需要添加logo的路径 
	* @param destFilePath 目标文件地址("D:/newPic.jpg")
	* 
	*/
	public static BufferedImage  addLogo_QRCodeBuffImage(BufferedImage image, String logoPict, LogoConfig logoConfig){
		try
		{
			File logoPic=new File(logoPict);
			/**
			* 读取二维码图片，并构建绘图对象
			*/
			Graphics2D g = image.createGraphics();
			/**
			* 读取Logo图片
			*/
			BufferedImage logo = ImageIO.read(logoPic);
			int widthLogo = logo.getWidth(), heightLogo = logo.getHeight();
			// 计算图片放置位置
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - logo.getHeight()) / 2;
			//开始绘制图片
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
			g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
			g.setStroke(new BasicStroke(logoConfig.getBorder()));
			g.setColor(logoConfig.getBorderColor());
			g.drawRect(x, y, widthLogo, heightLogo);
			g.dispose();
			
		}catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	   return image;
	} 
	
    /**
    * 给二维码图片添加Logo
    * 
    * @param image 
    * @param inStream 
    * @param logoConfig
    * @return BufferedImage 
    * 
    */
    public static BufferedImage addLogoForQRCodeBuff(BufferedImage image, InputStream inStream, LogoConfig logoConfig)
    {
        try
        {
            /**
            * 读取二维码图片，并构建绘图对象
            */
            Graphics2D g = image.createGraphics();
            /**
            * 读取Logo图片
            */
            BufferedImage logo = ImageIO.read(inStream);
            int widthLogo = logo.getWidth(), heightLogo = logo.getHeight();
            // 计算图片放置位置
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - logo.getHeight()) / 2;
            //开始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            g.setStroke(new BasicStroke(logoConfig.getBorder()));
            g.setColor(logoConfig.getBorderColor());
            g.drawRect(x, y, widthLogo, heightLogo);
            g.dispose();
            
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
        return image;
    }

    public static void main(String[] args) throws FileNotFoundException {  
//    	try{
//    	 String text = "http://www.baidu.com";    
//         int width = 200;    
//         int height = 200;    
//         //二维码的图片格式    
//         String format = "gif";    
//         Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();    
//         //内容所使用编码    
//         hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    
//         BitMatrix bitMatrix = new MultiFormatWriter().encode(text,    
//                 BarcodeFormat.QR_CODE, width, height, hints);    
//         //生成二维码    
//         File outputFile = new File("d://test/lqmnew.gif");    
//         MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);    
//    	}
//    	catch(Exception e){
//    		System.out.println(e.toString());
//    	}
    	//CreateQRCode("http://s.click.taobao.com/t?e=m%3D2%26s%3DzilLnIquMVYcQipKwQzePOeEDrYVVa64pRe%2F8jaAHci5VBFTL4hn2Z7GlF%2FqY3JMrbXOwzJUOFBxDSfhuIxKdGcO096EQR5d8xeGKRnEF0oBWE7OuxqqSuyXVc50zku1MEau7tMMowY1%2BXa1AJLmOsYOae24fhW0&unid=kingkey01099000000324",512,512,"jpg","d://test/tmalltest.jpg");
    	//LogoConfig lf=new LogoConfig();
    	//lf.DEFAULT_BORDERCOLOR=Color.blue;
    	//lf.DEFAULT_BORDERCOLOR=red
    	//addLogo_QRCode("d://test/yejuan.jpg","d://test/cha.jpg",lf,"d://test/yejuanchaye.jpg");
    //	System.out.println(decodeQRCode("d://test/displayQRCode.png"));
    //	String string=decodeQRCode("d://test/555.jpg");
    	//CreateQRCode(string,512,512,"jpg","d://test/webchart.jpg");
    	//CreateQRCode("801115115",);
    }   
}