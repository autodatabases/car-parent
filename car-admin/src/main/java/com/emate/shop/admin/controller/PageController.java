/**
 * 
 */
package com.emate.shop.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转到指定页面
 * @author likk
 *
 */
@Controller
@RequestMapping("page")
public class PageController {
    /**
     * 去登陆页面
     * @param request
     * @return
     */
        @RequestMapping("404")
        public String notfound(HttpServletRequest request) {
            return "404/404";
        }
        
        @RequestMapping("500")
        public String servererror(HttpServletRequest request) {
            return "404/500";
        }

   
}
