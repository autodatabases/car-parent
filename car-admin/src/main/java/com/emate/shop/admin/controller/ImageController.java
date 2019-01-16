package com.emate.shop.admin.controller;

//import java.awt.image.BufferedImage;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
//import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.emate.shop.web.aop.AuthUtil;

@Controller
@RequestMapping("img")
public class ImageController implements AuthUtil{
	
    private String buildImageRealPath(MultipartFile fileItem, String imagePath) {
        String name = fileItem.getOriginalFilename();
        if (name.lastIndexOf('.') > 0) {
            name = name.substring(name.lastIndexOf('.'), name.length());
        } else {
            name = ".jpg";
        }
        return imagePath + File.separator
                + UUID.randomUUID().toString().replace("-", "") + name;
    }
    
    private String buildImageUrlPath(HttpServletRequest request,String realPath) {
        return realPath
                .replace(request.getServletContext().getRealPath(File.separator),
                		request.getContextPath())
                .replace(File.separator, "/");
    }
    
    private String dataJson(String url) {
        return "{\"url\":\"" + url + "\"}";
    }
	
	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadImg")
	@ResponseBody
	public String uploadImg(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "imgtype", required = true) String imgtype) throws Exception {
		//response.setContentType("text/html;charset=utf-8");
		String contextRealPath = request.getServletContext()
	                .getRealPath(File.separator);
	        
	        String imagePath = contextRealPath 
	        		+ File.separator 
	        		+ "resources"
	        		+ File.separator
	        		+ "imgs"
            		+ File.separator
            		+ "upload"
            		+ File.separator
            		+ imgtype;
	        
	        String tempPath = contextRealPath + File.separator + "temp";
	        File imagePathFile = new File(imagePath);
	        if (!imagePathFile.exists()) {
	            imagePathFile.mkdirs();
	        }
	        File tempPathFile = new File(tempPath);
	        if (!tempPathFile.exists()) {
	            tempPathFile.mkdirs();
	        }
	        
	        try{
	            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
		        List<MultipartFile> fileList = multipartRequest.getFiles("file_upload");  
		        for (MultipartFile mf : fileList) {  
		            if(!mf.isEmpty()){ 
		            	 if (this.isImageType(mf.getContentType())) {
			                    String imageFilePath = this.buildImageRealPath(mf,
			                            imagePath);
			                    while (new File(imageFilePath).exists()) {//uuid有可能重复
			                        imageFilePath = this.buildImageRealPath(mf,
			                                imagePath);
			                    }
			                    String imageUrl = this.buildImageUrlPath(request,imageFilePath);
/*			            		 if("counterman".equals(imgtype)){
			            			 BufferedImage img = ImageIO.read(mf.getInputStream());
			            			 if(img.getHeight()>220||img.getHeight()<180||img.getWidth()>220||img.getWidth()<180){
			            				  return 
			            					new String("{\"message\":\"图片长宽必须在180px和220px之间\"}".getBytes(),"ISO-8859-1");
			            				// throw new BusinessException("");
			            			 };
			            		 }*/
			            		 mf.transferTo(new File(imageFilePath));    
			            		 return this.dataJson(imageUrl);
			                }
		            }  
		        }  
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	}
	
	private Boolean isImageType(String fileType) {
        if (Objects.isNull(fileType)) {
            return Boolean.FALSE;
        }
        fileType = fileType.toLowerCase();
        String types = "jpg|gif|bmp|png|jpeg|ico|webp";
        if (Objects.nonNull(types)) {
            return Arrays.asList(types.split("\\|"))
                    .contains(fileType.split("/")[1]);
        }
        return Boolean.FALSE;
    }	
}
