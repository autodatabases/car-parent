package com.emate.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//http://image.tuhu.cn/Images/Logo/biyadi.png

public class Url{
	public static File file = new File("D:\\car-workspace\\car-parent\\car-admin\\src\\main\\webapp\\resources\\imgs");
	public static String imgPath = "http://image.tuhu.cn/Images/Logo/*.png";
	public static void getImgFile(String pinyin) {  
	    try {  
	        //要想struts2的表单值天器自动填充就必须使用伪URL传参不管是使用get还是POST  
	        //?path=c:/test.xml&test=2012  
	        if(pinyin.equals("asidunmading")){
	        	pinyin = "asidun%c2%b7mading";
	        }
	        String spec = imgPath.replace("*", pinyin);
	  
	        URL url = new URL(spec);  
	        System.out.println(url);  
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	        conn.setRequestMethod("GET");  
	        conn.setDoInput(true);  
	        conn.setDoOutput(false);  
	        conn.setInstanceFollowRedirects(true);  
	        conn.setRequestProperty("content-type", "text/html");  
	  
	        conn.connect();// 握手  
//	        OutputStream os = conn.getOutputStream();// 拿到输出流  
//	        // os.write("?path=c:/test.xml&test=2012".getBytes("utf-8"));  
//	        PrintWriter out = new PrintWriter(os);  
//	        out.print("?path=c:/test.xml&test=2012");  
	  
//	        out.flush();  
//	        os.flush();  
//	        out.close();  
//	        os.close();  
	  
	        InputStream is = conn.getInputStream();//拿到输入流  
	        if(file.isDirectory()){
	        	File imgFile = new File(file, pinyin+".png");
	        	byte[] buffer = new byte[1024];
	        	int lenth = is.read(buffer);
	        	FileOutputStream fos = new FileOutputStream(imgFile);
	        	while(lenth != -1){
	        		fos.write(buffer, 0, lenth);
	        		lenth = is.read(buffer);
	        	}
	        	fos.flush();
	        	fos.close();
	        	is.close();
	        	 System.out.println("write file : "+imgFile.getCanonicalPath());  
	        }
//	        InputStreamReader isr = new InputStreamReader(is);  
//	        BufferedReader br = new BufferedReader(isr);  
//	        String s = br.readLine();  
//	        System.out.println(s);  
//	  
//	        br.close();  
//	        isr.close();  
	        is.close();  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	}  
}