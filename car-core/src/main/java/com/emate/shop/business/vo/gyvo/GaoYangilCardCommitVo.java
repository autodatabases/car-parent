/**
 * 
 */
package com.emate.shop.business.vo.gyvo;

import java.lang.reflect.InvocationTargetException;

/**
 * @file GaoYangilCardCommitVo.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月9日
 */
public class GaoYangilCardCommitVo {

    /**
     * 中石化
     */
    public final static String RECHARGE_TYPE_SNPC = "1";

    /**
     * 中石油
     */
    public final static String RECHARGE_TYPE_CNPC    = "2";

    private String             gameid;                     //有卡商品编号，通过高阳查询获得

    private String             parvalue;                   //面值

    private String             orderid;                    //订单编号

    private String             gascardid;                  //加油卡号

    private String             chargetype;                 //加油卡类型 （1:中石化、2:中石油；默认为1）

    private int                fillnum;                    //购买数量，正整数，最大值为100（建议不 超过10） 

    private String             gascardtel;                 //持卡人手机号

    private String             gascardname;                //持卡人姓名

    private String             userip;                     //实际用户充值IP，并非代理商服务器IP 
    

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public String getParvalue() {
        return parvalue;
    }

    public void setParvalue(String parvalue) {
        this.parvalue = parvalue;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getGascardid() {
        return gascardid;
    }

    public void setGascardid(String gascardid) {
        this.gascardid = gascardid;
    }

    public String getChargetype() {
        return chargetype;
    }

    public void setChargetype(String chargetype) {
        this.chargetype = chargetype;
    }

    public int getFillnum() {
        return fillnum;
    }

    public void setFillnum(int fillnum) {
        this.fillnum = fillnum;
    }

    public String getGascardtel() {
        return gascardtel;
    }

    public void setGascardtel(String gascardtel) {
        this.gascardtel = gascardtel;
    }

    public String getGascardname() {
        return gascardname;
    }

    public void setGascardname(String gascardname) {
        this.gascardname = gascardname;
    }

    public String getUserip() {
        return userip;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }
    
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
//    	GaoYangilCardCommitVo commitVo = new GaoYangilCardCommitVo();
//		commitVo.setChargetype(GaoYangilCardCommitVo.RECHARGE_TYPE_SNPC);// 1
//																			// 中石化
//		commitVo.setFillnum(1);// 购买数量
//		// commitVo.setGascardid("1000111100013840088");//油卡账号
//		commitVo.setGascardid("fuck");// 油卡账号
//		//commitVo.setGascardname(mobile);
//		GyOilLog log = new GyOilLog();
//		BeanUtils.copyProperties(log, commitVo);
//		//System.out.println(log.getFillnum());
//		System.out.println(log.getChargetype());
    	
//    	System.out.println(new BigDecimal(-100).add(new BigDecimal(-200)).intValue());
    	System.out.println(new Byte("2"));
	}

}
