package com.emate.shop.h5.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.emate.shop.common.Log4jHelper;
import com.emate.shop.web.aop.AuthUtil;

@Controller
@RequestMapping
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
    
    private String dataJsonTwo(String message) {
        return "{\"message\":\"" + message + "\"}";
    }
	
	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadImg")
	@ResponseBody
	public String uploadImg(HttpServletRequest request,@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		String contextRealPath = request.getServletContext()
	                .getRealPath(File.separator);
		Log4jHelper.getLogger().info("h5端图片存储地址:"+contextRealPath);
	        String type = request.getParameter("type");
	        String imagePath = contextRealPath 
	        		+ File.separator 
	        		+ "resources"
	        		+ File.separator
	        		+ "imgs"
            		+ File.separator
            		+ "upload";
	        if("survey".equals(type)){
	        	imagePath = imagePath+ File.separator+"hjcf";
	        }else{
	        	imagePath = imagePath+ File.separator
	            		+getUserId(request,AuthUtil.CAR_H5_TOKEN);
	        }
            		
	        if("fixOrder".equals(type)){
	        	imagePath += File.separator
	        			+type;
	        }
	        if("survey".equals(type)){//Survey record
	        	imagePath += File.separator
	        			+type;
	        }
	        
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
		            	if("survey".equals(type)){
		            		if (this.isImageTypeTwo(mf.getContentType())) {
			                    String imageFilePath = this.buildImageRealPath(mf,
			                            imagePath);
			                    while (new File(imageFilePath).exists()) {//uuid有可能重复
			                        imageFilePath = this.buildImageRealPath(mf,
			                                imagePath);
			                    }
			                    String imageUrl = this.buildImageUrlPath(request,imageFilePath);
			                    mf.transferTo(new File(imageFilePath));
			                    return this.dataJson(imageUrl);
			                }else{
			                	String types = "图片格式只能是：jpg|png|jpeg";
			                	return this.dataJsonTwo(URLEncoder.encode(types,"utf-8"));
			                }
		            	}else{
		            		if (this.isImageType(mf.getContentType())) {
			                    String imageFilePath = this.buildImageRealPath(mf,
			                            imagePath);
			                    while (new File(imageFilePath).exists()) {//uuid有可能重复
			                        imageFilePath = this.buildImageRealPath(mf,
			                                imagePath);
			                    }
			                    String imageUrl = this.buildImageUrlPath(request,imageFilePath);
			                    mf.transferTo(new File(imageFilePath));
			                    return this.dataJson(imageUrl);
			                }else{
			                	String types = "图片格式只能是：jpg|png|jpeg";
			                	return this.dataJsonTwo(URLEncoder.encode(types,"utf-8"));
			                }
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
	
	private Boolean isImageTypeTwo(String fileType) {
        if (Objects.isNull(fileType)) {
            return Boolean.FALSE;
        }
        fileType = fileType.toLowerCase();
        String types = "jpg|png|jpeg";
        if (Objects.nonNull(types)) {
            return Arrays.asList(types.split("\\|"))
                    .contains(fileType.split("/")[1]);
        }
        return Boolean.FALSE;
    }
	

}
