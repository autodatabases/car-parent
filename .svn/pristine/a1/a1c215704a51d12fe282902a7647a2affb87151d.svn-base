/**
 * 
 */
package com.emate.shop.business.remote;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @file EchoController.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月8日
 */
@Controller
@RequestMapping("echo")
public class EchoController {

    @ResponseBody
    @RequestMapping(value="hello.html",produces="text/html;charset=UTF-8")
    public String hello(HttpServletRequest request) {
    	String name = request.getParameter("name");
    	System.out.println(name);
        return "中文";
    }
}
