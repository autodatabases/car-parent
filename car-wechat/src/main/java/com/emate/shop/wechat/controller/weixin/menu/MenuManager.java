package com.emate.shop.wechat.controller.weixin.menu;

import com.emate.wechat.AccessTokenVo;
import com.emate.wechat.Menu;
import com.emate.wechat.WeixinUtil;
import com.emate.wechat.menu.Button;
import com.emate.wechat.menu.CommonButton;
import com.emate.wechat.menu.ComplexButton;
import com.emate.wechat.menu.ViewButton;

/** 
 * 菜单管理器类 
 *  
 *@author likk
 *@date 2014.10.09
 */
public class MenuManager {  
	
	long long1 = 2147483648l;
    public static void main(String[] args) { 
  
        // 调用接口获取access_token  
       // AccessToken at = WeixinUtil.getAccessToken(ParamesAPI.corpId, ParamesAPI.secret);  
        AccessTokenVo at = new AccessTokenVo();
        at.setToken("O67XVnvYjrBwjS0xZafFyipLzb599_58gN9-uMvxGFPmvoGc0VHAe9Px4S-V0YSLSftt96-oVAQBK9agPrK04JyZVfWa32Pye6njeLIdNeqAZJl6I0jiYoBEYVolRawwETWjAEAAPS");
        if (null != at) {  
        	WeixinUtil.deleteMenu(at.getToken());
            // 调用接口创建菜单  
            int result = WeixinUtil.createMenu(getMenu(), at.getToken());
  
            // 判断菜单创建结果  
            if (0 == result){  
            	System.out.println("菜单创建成功！");
            }else  
            	System.out.println("菜单创建失败！");
        }  
    }  
  
   //private static String domin = "http://k.189.cn";
    //http://smart.emateglobal.com/car-wechat/
    /** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    private static Menu getMenu() { 
    	
    	ViewButton btn11 = new ViewButton();  
    	btn11.setName("商家首页");  
    	btn11.setType("view");
    	//btn11.setUrl("http://car.emateglobal.com/wechat/");car.emates.cn
    	btn11.setUrl("http://car.emates.cn/wechat/");
    	
    	//第一个大菜单的子菜单
    	ViewButton btn12 = new ViewButton();  
    	btn12.setName("商家订单");  
    	btn12.setType("view");
    	//btn12.setUrl("http://car.emateglobal.com/wechat/order/order.html?orderStatus=1");
    	btn12.setUrl("http://car.emates.cn/wechat/order/order.html?orderStatus=1");
      	
        ViewButton btn13 = new ViewButton();
        btn13.setName("商家结算");  
        btn13.setType("view");
       //btn13.setUrl("http://car.emateglobal.com/wechat/setlment/setlment.html");
        btn13.setUrl("http://car.emates.cn/wechat/setlment/setlment.html");
        
  
        //第二个大菜单的子菜单
        ViewButton btn21 = new ViewButton();  
        btn21.setName("精选商品");  
        btn21.setType("view");  
        btn21.setUrl("http://mypot.cn/m/index/goodsList?commodityConfigType=92"); 
  
        /*ViewButton btn22 = new ViewButton();  
        btn22.setName("近期新品");  
        btn22.setType("view");  
        btn22.setUrl("http://mypot.cn/m/index/goodsList?orderColumn=saleTime");  */
        
        ViewButton btn23 = new ViewButton();  
        btn23.setName("畅销商品");  
        btn23.setType("view");  
        btn23.setUrl("http://mypot.cn/m/index/goodsList?orderColumn=commoditySaleCount");
        
        /*ViewButton btn24 = new ViewButton();  
        btn24.setName("主题商品");  
        btn24.setType("view");  
        btn24.setUrl("http://mypot.cn/m/index/goodsList?commodityConfigType=90");*/
        
        ViewButton btn25 = new ViewButton();  
        btn25.setName("全部商品");  
        btn25.setType("view");  
        btn25.setUrl("http://mypot.cn/m/index/goodsList");
        
        
       //第3个大菜单的子菜单
        CommonButton btn31 = new CommonButton();  
        btn31.setName("账户绑定");  
        btn31.setType("click");
        btn31.setKey("click_key_3001");
        
        ViewButton btn32 = new ViewButton();  
        btn32.setName("个人中心");  
        btn32.setType("view");  
        btn32.setUrl("http://mypot.cn/m/index/infocenter");
        
        ViewButton btn33 = new ViewButton();  
        btn33.setName("我的订单");  
        btn33.setType("view");  
        btn33.setUrl("http://mypot.cn/m/userOrder/orderList");
        
        /*CommonButton btn34 = new CommonButton();  
        btn34.setName("我的荣誉");  
        btn34.setType("click");  
        btn34.setKey("click_key_3004"); */
        
        ViewButton btn35 = new ViewButton();  
        btn35.setName("找回密码");  
        btn35.setType("view");  
        btn35.setUrl("http://mypot.cn/m/index/findpassword"); 
        
        
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("好玩");  
        mainBtn1.setSub_button(new Button[] {btn11,btn12,btn13});  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("好礼");  
        mainBtn2.setSub_button(new Button[] {btn21,btn23,btn25});  
 
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("账户");  
        mainBtn3.setSub_button(new Button[] {btn35,btn33,btn32,btn31});  
  
        /** 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        //menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });
        menu.setButton(new Button[] { btn11,btn12,btn13});
  
        return menu;  
    }  
}  
