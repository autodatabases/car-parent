package com.emate.tools;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

public class ImageUtils {

    /**
     * 加水印
     * 
     * @param pressImg
     *            水印图片
     * @param targetImg
     *            目标图片
     * @param x
     *            水印位置坐标x
     * @param y
     *            水印位置坐标y
     * @param alpha
     *            透明属性
     */
    public static final void pressImage(String pressImg, String targetImg,
            int x, int y, float alpha) {
        try {
            File img = new File(targetImg);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);

            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(10, alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2 + x,
                    (height - height_biao) / 2 + y, wideth_biao, height_biao,
                    null);

            g.dispose();
            ImageIO.write(image, "jpg", img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出指定大小的图片
     * 
     * @param src
     *            图片文件
     * @param height
     *            目标高度
     * @param width
     *            目标宽度
     * @param bb
     * @param outputStream
     *            输出流
     */
    public static void resize(File src, int width, int height, boolean bb,
            OutputStream outputStream) {
        try {
            double ratio = 0.0D;
            BufferedImage bi = ImageIO.read(src);
            Image itemp = bi.getScaledInstance(width, height, 4);
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth())
                    ratio = new Integer(height).doubleValue() / bi.getHeight();
                else {
                    ratio = new Integer(width).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(
                        AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reduceImg(String src, OutputStream outputStream,
            int width, int height) {
        try {
            File srcfile = new File(src);
            if (!srcfile.exists()) {
                return;
            }
            String type = src.substring(src.lastIndexOf('.') + 1, src.length());
            Image srcImage = javax.imageio.ImageIO.read(srcfile);
            if (height == 0) {
                srcImage = square(src, srcImage.getWidth(null),
                        srcImage.getHeight(null));
                height = width;
            }
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().setColor(Color.WHITE);
            tag.getGraphics().fillRect(0, 0, width, height);
            tag.getGraphics().drawImage(srcImage.getScaledInstance(width,
                    height, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIO.write(tag, type, outputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static BufferedImage cutImage(String src, int x, int y, int w,
            int h) throws IOException {
        String type = src.substring(src.lastIndexOf('.') + 1, src.length());
        Iterator<ImageReader> iterator = ImageIO
                .getImageReadersByFormatName(type);
        ImageReader reader = iterator.next();
        InputStream in = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(x, y, w, h);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        return bi;
    }

    private static BufferedImage square(String src, int srcWidth, int srcHeight)
            throws IOException {
        int length = Math.min(srcWidth, srcHeight);
        return cutImage(src, Math.abs(srcWidth - length) / 2,
                Math.abs(srcHeight - length) / 2, length, length);
    }
    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // This code ensures that all the pixels in the image are loaded  
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null),
                    image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen  
        }
        if (bimage == null) {
            // Create a buffered image using the default color model  
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null),
                    image.getHeight(null), type);
        }
        // Copy image to buffered image  
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image  
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }
    
    public static BufferedImage read(File imgFile) throws IOException {
        Image image = Toolkit.getDefaultToolkit()
                .getImage(imgFile.getAbsolutePath());
        return toBufferedImage(image);
    }
    
    public static void main1(String[] args) throws FileNotFoundException, IOException {
		String frder = "C:\\Users\\likk\\Desktop\\auto\\peijian_image\\jiyou";
		String frdersmall = frder+ "\\small";
		File forder = new File(frder);
		for(File f : forder.listFiles()){
			File fout = new File(frdersmall+"\\"+f.getName());
			if(!fout.exists()){
				fout.createNewFile();
			}
			reduceImg(f.getCanonicalPath(),new FileOutputStream(fout),400,400);
		}
	}
    
    public static void main(String[] args) throws IOException {
    	reduceImg("d:\\1.jpg", new FileOutputStream("d:\\test.jpg"),400,400);
	}
}