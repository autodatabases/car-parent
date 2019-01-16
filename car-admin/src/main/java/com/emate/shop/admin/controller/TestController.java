/**
 * 
 */
package com.emate.shop.admin.controller;

//import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @file SystemAuthController.java
 * @author kevin
 * @mail kevin@emateglobal.com
 * @since 2016年7月4日
 */
@Controller
@RequestMapping("test")
public class TestController {
//    @SuppressWarnings("rawtypes")
//	@Autowired
//    private  RedisTemplate redisTemplate;
//    
//    public void setRedisTemplate(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {  
//        this.redisTemplate = redisTemplate;  
//    } 
//    
//    @SuppressWarnings("unchecked")
//	protected RedisSerializer<String> getRedisSerializer() {  
//        return redisTemplate.getStringSerializer();  
//    }  
//
//    /**
//     * 添加类目
//     * @param request
//     * @param category
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping("test")
//    public String test(HttpServletRequest request,String inputName) {
//    	
//       @SuppressWarnings("unchecked")
//	Object o = redisTemplate.execute(new RedisCallback<Boolean>() {  
//            public Boolean doInRedis(RedisConnection connection)  
//                    throws DataAccessException {  
//                RedisSerializer<String> serializer = getRedisSerializer();  
//                byte[] key  = serializer.serialize("name");  
//                byte[] name = serializer.serialize(inputName);  
//                return connection.setNX(key, name);  
//            }  
//        });  
//    	return "hello" + o;
//    }
//    

    /**
     * 去登陆页面
     * @param request
     * @return
     */
/*        @RequestMapping("testftl")
        public String testftl(HttpServletRequest request) {
            return "test";
        }
        
        @RequestMapping("testjsp")
        public String testjsp(HttpServletRequest request) {
            return "testjsp";
        }*/

   
}
