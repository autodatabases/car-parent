package com.emate.shop.h5.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.emate.shop.common.Log4jHelper;
import com.emate.shop.web.aop.AuthUtil;
import com.emate.tools.ImageUtils;

@Controller
@RequestMapping("imgget")
public class ImageGetController implements AuthUtil{

    private void prepareResponseHeaders(HttpServletRequest request,
            HttpServletResponse response) {
        String uri = request.getParameter("uri");
        if (uri.endsWith(".png")) {
            response.setContentType("image/png");
        } else if (uri.endsWith(".gif")) {
            response.setContentType("image/gif");
        } else {
            response.setContentType("image/jpeg");
        }
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    private int[] gotRequestSize(HttpServletRequest request) {
        String requestSize = request.getParameterMap().keySet().stream()
                .filter(name -> name.matches("^\\d+[xX]\\d+$")).findFirst()
                .orElse(null);
        if (Objects.isNull(requestSize)) {
            return null;
        }
        int[] size = new int[2];
        String[] split = requestSize.split("[xX]");
        size[0] = Integer.parseInt(split[0]);
        size[1] = Integer.parseInt(split[1]);
        return size;
    }
    
    private String gotRequestRealPath(HttpServletRequest request) {
    	String contextRealPath = request.getServletContext()
                .getRealPath(File.separator);
        return contextRealPath+File.separator+".."+request.getParameter("uri");
    }
    
    private String gotFileType(String pathOrName) {
        return pathOrName.substring(pathOrName.lastIndexOf('.')).replace(".",
                "");
    }
    
    @RequestMapping("getimg")
    public void getImg(HttpServletRequest request, HttpServletResponse response) throws IOException{
        this.prepareResponseHeaders(request, response);
        String realPath = this.gotRequestRealPath(request);
        File imageFile = new File(realPath);
        if (!imageFile.exists()) {
        	Log4jHelper.getLogger().error("文件不存在！realPath = "+realPath);
        	response.sendError(HttpServletResponse.SC_NOT_FOUND, "找不到文件");
            return;
        }
        int[] requestSize = this.gotRequestSize(request);
        File imageOutput = imageFile;
        if (Objects.nonNull(requestSize)) {
            String requestSizePath = this.reducePath(realPath,
                    String.valueOf(requestSize[0]),
                    String.valueOf(requestSize[1]));
            File requestSizeImageFile = new File(requestSizePath);
            if (!requestSizeImageFile.exists()) {
                OutputStream imageFileReduceOutputStream = new FileOutputStream(
                        requestSizeImageFile);
                ImageUtils.reduceImg(imageFile.getCanonicalPath(), new FileOutputStream(requestSizeImageFile),
                        requestSize[0], requestSize[1]);
                imageFileReduceOutputStream.close();
            }
            imageOutput = requestSizeImageFile;
        }
        String type = this.gotFileType(imageOutput.getName());
        ImageIO.write(ImageUtils.read(imageOutput), type,
                response.getOutputStream());
        response.getOutputStream().flush();
    }
    private String reducePath(String imagePath, String width, String height) {
        return imagePath.substring(0, imagePath.lastIndexOf('.')) + "_" + width
                + "x" + height + "." + this.gotFileType(imagePath);
    }
}
