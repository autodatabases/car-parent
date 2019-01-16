package com.emate.tools.oufei;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.emate.shop.business.model.OilLog;
import com.emate.shop.business.vo.ofvo.OufeiCardInfo;
import com.emate.shop.business.vo.ofvo.OufeiCardPassword;
import com.emate.shop.business.vo.ofvo.OufeiFlowGood;
import com.emate.shop.business.vo.ofvo.OufeiOrder;
import com.emate.shop.business.vo.ofvo.OufeiPhoneGood;
import com.emate.shop.common.DocumentHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.common.RandomUtil;
import com.emate.shop.exception.BusinessException;
import com.emate.tools.gaoyang.KeyedDigestMD5;

/**
 * 欧飞对接util
 * @author dong
 *
 */
public class OufeiOilUtil {
	/**
	 * 欧飞查询卡号信息
	 * @param queryUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static OufeiCardInfo queryCardInfo(String queryUrl,Map<String,String> params) throws Exception{
		
		StringBuffer baoti = new StringBuffer();
		String md5_str="";//签名串
		baoti.append(params.get("userid"))
			.append(params.get("userpws"))
			.append(params.get("game_userid"))
			.append(params.get("keyStr"));
		md5_str = KeyedDigestMD5.getKeyedDigest(baoti.toString(),"").toUpperCase();
		Log4jHelper.getLogger().info("欧飞油卡卡号信息查询接口MD5字符串:"+md5_str);
		StringBuffer param = new StringBuffer();
		param.append("userid").append("=").append(params.get("userid")).append("&")
			.append("userpws").append("=").append(params.get("userpws")).append("&")
			.append("game_userid").append("=").append(params.get("game_userid")).append("&")
			.append("chargeType").append("=").append(params.get("chargeType")).append("&")
			.append("md5_str").append("=").append(md5_str).append("&")
			.append("version").append("=").append(params.get("version"));
		String url = queryUrl + param.toString();
		Log4jHelper.getLogger().info("欧飞油卡卡号信息查询请求url串:"+url);
		String postResponse= postRequest(url);
		OufeiCardInfo OufeiCardInfo = DocumentHelper.fromDocument(postResponse, OufeiCardInfo.class);
		Log4jHelper.getLogger().info("欧飞油卡卡号信息查询响应串:"+OufeiCardInfo.toString());
		return OufeiCardInfo;
	}
	
	/**
	 * 欧飞油卡充值接口
	 * @param chargeUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static OufeiOrder ChargeCardByOf(String chargeUrl,Map<String,String> params,OilLog oilLog) throws Exception{
		
		StringBuffer baoti = new StringBuffer();
		String md5_str="";//签名串
		baoti.append(params.get("userid"))
			.append(params.get("userpws"))
			.append(params.get("cardid"))
			.append(params.get("cardnum"))
			.append(params.get("sporder_id"))
			.append(params.get("sporder_time"))
			.append(params.get("game_userid"))
			.append(params.get("keyStr"));
		md5_str = KeyedDigestMD5.getKeyedDigest(baoti.toString(),"").toUpperCase();
		
		Log4jHelper.getLogger().info("欧飞油卡充值请求MD5字符串:"+md5_str);
		//System.out.println(md5_str);
		
		StringBuffer param = new StringBuffer();
		param.append("userid").append("=").append(params.get("userid")).append("&")
			.append("userpws").append("=").append(params.get("userpws")).append("&")
			.append("cardid").append("=").append(params.get("cardid")).append("&")
			.append("cardnum").append("=").append(params.get("cardnum")).append("&")
			.append("sporder_id").append("=").append(params.get("sporder_id")).append("&")
			.append("sporder_time").append("=").append(params.get("sporder_time")).append("&")
			.append("game_userid").append("=").append(params.get("game_userid")).append("&")
			.append("chargeType").append("=").append(params.get("chargeType")).append("&")
			.append("gasCardTel").append("=").append(params.get("gasCardTel")).append("&")
			//.append("gasCardName").append("=").append(params.get("gasCardName")).append("&")
			//.append("invoiceFlag").append("=").append(params.get("invoiceFlag")).append("&")
			.append("md5_str").append("=").append(md5_str).append("&")
			.append("ret_url").append("=").append(params.get("ret_url")).append("&")
			.append("version").append("=").append(params.get("version"));
		String url = chargeUrl + param.toString();
		Log4jHelper.getLogger().info("欧飞油卡充值请求串:"+url);
		oilLog.setRequestStr(url);
		String postResponse= postRequest(url);
		oilLog.setReturnResult(postResponse);;
		OufeiOrder oufeiOrder = DocumentHelper.fromDocument(postResponse, OufeiOrder.class);
		Log4jHelper.getLogger().info("欧飞油卡充值响应串:"+oufeiOrder.toString()+"订单号："+params.get("sporder_id"));
		return oufeiOrder;
	}
	
	/**
	 * 欧飞油卡订单查询接口
	 * @param queryOilUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String queryOilOrderByOf(String queryOilUrl,Map<String,String> params,OilLog oilLog) throws Exception{
		StringBuffer param = new StringBuffer();
		param.append("userid").append("=").append(params.get("userid")).append("&")
			.append("spbillid").append("=").append(params.get("spbillid"));
		String url = queryOilUrl + param.toString();
		if(Objects.nonNull(oilLog)){
			oilLog.setRequestStr(url);
		}
		Log4jHelper.getLogger().info("欧飞订单请求串:"+url);
		String postResponse= postRequest(url);
		if(Objects.nonNull(oilLog)){
			oilLog.setReturnResult(postResponse);
		}
		Log4jHelper.getLogger().info("欧飞订单响应串:"+postResponse);
		return postResponse;
	}
	
	private static String postRequest(String url) throws Exception{
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.addRequestHeader("User-Agent", "Mozilla/4.0");
		postMethod.setDoAuthentication(false);
        httpClient.executeMethod(postMethod);
        String responseStr = new String(postMethod.getResponseBody(),"GBK");
		return responseStr;
	}
	
	//==============================================================================
	//public static void main(String[] args) {
		//System.out.println(KeyedDigestMD5.getKeyedDigest("SHtaidou@123456",""));
		//查询油卡信息
/*		String queryUrl = "http://apitest.ofpay.com/sinopec/queryCardInfo.do?";
		Map<String, String> params = new HashMap<String,String>();
		params.put("userid", "A08566");//账户名称;可由SP自动注册生成,在登陆系统时得到以A开头的编号
		params.put("userpws", "4c625b7861a92c7971cd2029c2fd3c4a");//账户密码;SP接入密码(为账户密码的MD5值，如登陆密码为111111,此时这个值为md5(“111111”) (32位小写)
		params.put("game_userid", "1000111100006805204");//加油卡号（充值账号）;中石化：以100011开头共19位、中石油：以9开头共16位
		params.put("chargeType", "1");//加油卡类型 ;（1:中石化、2:中石油；默认为1，不参与MD5校验）
		params.put("version", "6.0");//固定值
		params.put("keyStr", "OFCARD");

		try {
			queryCardInfo(queryUrl,params);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		
/*		//油卡充值
		String chargeUrl = "http://apitest.ofpay.com/sinopec/onlineorder.do?";
		
		
		 * 中石化:
		 * 		64157001 :中石化1000元
		 * 		64157002 :中石化500元
		 * 		64157003 :中石化200元
		 * 		64157004 :中石化100元
		 * 		64157005 :中石化50元
		 * 中石油:
		 * 		64349101 :中石油1000元
		 * 		64349103 :中石油500元
		 * 		64349105 :中石油200元
		 * 		64349106 :中石油100元
		 
		Map<String, String> params2 = new HashMap<String,String>();
		params2.put("userid", "A08566");//账户名称;可由SP自动注册生成,在登陆系统时得到以A开头的编号
		params2.put("userpws", "4c625b7861a92c7971cd2029c2fd3c4a");//账户密码;SP接入密码(为账户密码的MD5值，如登陆密码为111111,此时这个值为md5(“111111”) (32位小写)
		params2.put("cardnum", "1");//1.任意充需要待充值面值（1的整数倍) 2.卡充充值这里表示数量
		params2.put("cardid", "64157005");//商品编号以产品部门提供的为准
		params2.put("sporder_id", "17071110105986543");//我们的订单编号,商家传给欧飞的唯一编号
		params2.put("sporder_time", "20070323140214");//订单时间 （yyyyMMddHHmmss 如：20070323140214）
		params2.put("game_userid", "1000111100003806529");//加油卡号（充值账号）中石化：以100011开头共19位、中石油：以90开头共16位
		params2.put("chargeType", "1");//加油卡类型 （1:中石化、2:中石油；默认为1）
		params2.put("gasCardTel", "13521322513");//持卡人手机号码
		params2.put("gasCardName", "东东");//持卡人姓名
		params2.put("invoiceFlag", "1");//是否需要发票1:是 、0:否(默认为0)
		params2.put("ret_url", "");//订单充值成功后返回的URL地址，可为空 ，具体说明参考接口说明1.3章节
		params2.put("version", "6.0");//固定值
		params2.put("keyStr", "OFCARD");
		
		try {
			ChargeCardByOf(chargeUrl,params2);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		
		//查询油卡订单结果
/*		String queryOrderUrl = "http://apitest.ofpay.com/api/query.do?";
		Map<String, String> params3 = new HashMap<String,String>();
		params3.put("userid", "A08566");
		params3.put("spbillid", "18051617395745367p");
		String queryOilOrderByOf= null;
		try {
			queryOilOrderByOf = queryOilOrderByOf(queryOrderUrl,params3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(queryOilOrderByOf);*/
		//话费充值信息
/*		String mobileUrl = "http://apitest.ofpay.com/onlineorder.do?";
		Map<String, String> params = new HashMap<String,String>();
	
		params.put("userid", "A08566");//SP编码如（A00001）
		params.put("userpws", "4c625b7861a92c7971cd2029c2fd3c4a");//SP接入密码
		params.put("cardid", "140101");//	快充140101，慢充170101
		params.put("cardnum", "30");//面值，快充可选面值（0.01、1、2、5、10、20、30、50、100、200、300、500） 慢充可选面值（30、50、100）
		//params.put("mctype", "");//如果是慢充商品必须传如48 表示48小时到账
		params.put("sporder_id", "18010918234793464");//Sp商家的订单号(商户传给欧飞的唯一编号)
		params.put("sporder_time", "20180817140214");//订单时间 （yyyyMMddHHmmss 如：20070323140214）
		params.put("game_userid", "13521322513");//手机号码
		params.put("keyStr", "OFCARD");//签名串
		params.put("ret_url", "");//订单充值有结果回调的URL地址，可为空（不参与MD5验算）
		params.put("version", "6.0");//	固定值
		//params.put("buyNum", "1");//1分钱商品使用，传入值为购买数量。cardnum*buynum
		OufeiOrder oufeiOrder = null;
		try {
			oufeiOrder = mobileByOf(mobileUrl,params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(oufeiOrder.toString());*/
	//}
	/**
	 * 欧飞油卡充值接口
	 * @param chargeUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static OufeiOrder mobileByOf(String mobileUrl,Map<String,String> params,OilLog oilLog) throws Exception{
		
		StringBuffer baoti = new StringBuffer();
		String md5_str="";//签名串
		baoti.append(params.get("userid"))
			.append(params.get("userpws"))
			.append(params.get("cardid"))
			.append(params.get("cardnum"))
			.append(params.get("sporder_id"))
			.append(params.get("sporder_time"))
			.append(params.get("game_userid"))
			.append(params.get("keyStr"));
		md5_str = KeyedDigestMD5.getKeyedDigest(baoti.toString(),"").toUpperCase();
		
		Log4jHelper.getLogger().info("欧飞话费充值请求MD5字符串:"+md5_str);
		
		StringBuffer param = new StringBuffer();
		param.append("userid").append("=").append(params.get("userid")).append("&")
			.append("userpws").append("=").append(params.get("userpws")).append("&")
			.append("cardid").append("=").append(params.get("cardid")).append("&")
			.append("cardnum").append("=").append(params.get("cardnum")).append("&")
			.append("sporder_id").append("=").append(params.get("sporder_id")).append("&")
			.append("sporder_time").append("=").append(params.get("sporder_time")).append("&")
			.append("game_userid").append("=").append(params.get("game_userid")).append("&")
			.append("md5_str").append("=").append(md5_str).append("&")
			.append("ret_url").append("=").append(params.get("ret_url")).append("&")
			.append("version").append("=").append(params.get("version"));
		String url = mobileUrl + param.toString();
		oilLog.setRequestStr(url);
		Log4jHelper.getLogger().info("欧飞话费充值请求串:"+url);
		String postResponse= postRequest(url);
		oilLog.setReturnResult(postResponse);
		Log4jHelper.getLogger().info(postResponse);
		OufeiOrder oufeiOrder = DocumentHelper.fromDocument(postResponse, OufeiOrder.class);
		if (Objects.isNull(oufeiOrder)) {
			Log4jHelper.getLogger().error("欧飞话费充值接口响应为空");
	        throw new BusinessException("话费充值接口响应为空");
	    }
		Log4jHelper.getLogger().info("欧飞话费充值响应串:"+oufeiOrder.toString()+"订单号："+params.get("sporder_id"));
		return oufeiOrder;
	}
	
    /**
     * 获取异常信息
     * @param code
     * @return
     */
    public static String getMsg(String code){
        if("9998".equals(code)){
            return code+"_"+"参数格式错误";
        }else if("1043".equals(code)){
            return code+"_"+"支付超时，订单处理失败";//(不能作失败处理，需要人工核实)
        }else if("334".equals(code)){
            return code+"_"+"订单生成超时";//(不能作为失败处理，需要人工核实)
        }else if("331".equals(code)){
            return code+"_"+"订单生成失败";
        }else if("305".equals(code)){
            return code+"_"+"充值失败未知错误";
        }else if("105".equals(code)){
            return code+"_"+"请求失败";//(不能作失败处理，需要人工核实)
        }else if("9999".equals(code)){
            return code+"_"+"未知错误";//(不能作失败处理，需要人工核实)
        }else if("1008".equals(code)){
            return code+"_"+"缺少必需参数";
        }else if("1006".equals(code)){
            return code+"_"+"充值金额超出系统限制";
        }else if("1005".equals(code)){
            return code+"_"+"购买的商品数量超出系统要求";
        }else if("1004".equals(code)){
            return code+"_"+"此商品暂不可用";
        }else if("1007".equals(code)){//账户余额不足
            return code+"_"+"该面额库存不足,正紧急调货,请稍后充值 ...";
        }else if("1003".equals(code)){
            return code+"_"+"MD5串验证错误";
        }else if("1002".equals(code)){
            return code+"_"+"商户IP验证错误";
        }else if("1001".equals(code)){
            return code+"_"+"商户名验证错误";
        }else if("1018".equals(code)){
            return code+"_"+"官方直接透传的信息";
        }else if("321".equals(code)){
            return code+"_"+"暂时不支持此类手机号的充值";
        }else if("319".equals(code)){
            return code+"_"+"充值的手机号不正确";
        }else if("11".equals(code)){
            return code+"_"+"运营商地区维护，暂不能充值";
        }else if("12".equals(code)){
            return code+"_"+"库存不足";
        }else{
            return code+"_"+"没有对于状态码...";
        }
    }
    
	/**
	 * 根据手机号查询手机号运营商
	 * @param phoneCarrierUrl
	 * @param mobilenum
	 * @return
	 * @throws Exception
	 */
	public static String getMobinfoUrl(String mobinfoUrl,String mobilenum) throws Exception{
		String url = mobinfoUrl + "mobilenum="+mobilenum;
		Log4jHelper.getLogger().info("欧飞油卡订单请求串:"+url);
		String postResponse= postRequest(url);
		Log4jHelper.getLogger().info("欧飞油卡订单响应串:"+postResponse);
		return postResponse;
	}
	public static void main(String[] args) {
		//查询手机供应商
	    String phoneCarrierUrl = "http://apitest.ofpay.com/mobinfo.do?";
		
		try {
			String str = getMobinfoUrl(phoneCarrierUrl, "15930490694");
		    System.out.println(str);
	    } catch (Exception e) {
		    e.printStackTrace();
	    }
		//System.out.println(KeyedDigestMD5.getKeyedDigest("SHtaidou@123456",""));
	}
	/**
	 * 欧飞获取卡密接口
	 * @param getUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static OufeiCardPassword getCardPassWordByOf(String getUrl,
			Map<String,String> params,OilLog oilLog) throws Exception{
		
		StringBuffer baoti = new StringBuffer();
		String md5_str="";//签名串
		baoti.append(params.get("userid"))
			.append(params.get("userpws"))
			.append(params.get("cardid"))
			.append(params.get("cardnum"))
			.append(params.get("sporder_id"))
			.append(params.get("sporder_time"))
			.append(params.get("keyStr"));
		md5_str = KeyedDigestMD5.getKeyedDigest(baoti.toString(),"").toUpperCase();
		Log4jHelper.getLogger().info("欧飞获取卡密请求MD5字符串:"+md5_str);
		StringBuffer param = new StringBuffer();
		param.append("userid").append("=").append(params.get("userid")).append("&")
			.append("userpws").append("=").append(params.get("userpws")).append("&")
			.append("cardid").append("=").append(params.get("cardid")).append("&")
			.append("cardnum").append("=").append(params.get("cardnum")).append("&")
			.append("sporder_id").append("=").append(params.get("sporder_id")).append("&")
			.append("sporder_time").append("=").append(params.get("sporder_time")).append("&")
			.append("phone").append("=").append(params.get("phone")).append("&")
			.append("md5_str").append("=").append(md5_str).append("&")
			.append("version").append("=").append(params.get("version"));
		String url = getUrl + param.toString();
		oilLog.setRequestStr(url);
		Log4jHelper.getLogger().info("欧飞获取卡密请求串:"+url);
		String postResponse= postRequest(url);
		oilLog.setReturnResult(postResponse);;
		OufeiCardPassword oufeiCardPassword = DocumentHelper.fromDocument(postResponse, OufeiCardPassword.class);
		if (Objects.isNull(oufeiCardPassword)) {
            Log4jHelper.getLogger().error("欧飞获取卡密接口响应为空");
            throw new BusinessException("获取卡密接口响应为空");
        } 
		Log4jHelper.getLogger().info("欧飞获取卡密响应串:"+oufeiCardPassword.toString()+"订单号："+params.get("sporder_id"));
		return oufeiCardPassword;
	}
	public static void main44(String[] args) {
		   //卡密获取接口
		   String getUrl = "http://apitest.ofpay.com/order.do?";
		   Map<String, String> params = new HashMap<String,String>();
		   String userid = "A08566";//欧飞账户编号
		   params.put("userid", userid);
		   String userpws = "4c625b7861a92c7971cd2029c2fd3c4a";//欧飞账户密钥
		   params.put("userpws", userpws);
		   String cardid = "1507005";//卡密商品编号
		   params.put("cardid", cardid);
		   String cardnum = "1";//购买商品数量
		   params.put("cardnum", cardnum);
		   String sporder_id = RandomUtil.getOrderSn();//订单号
		   params.put("sporder_id", sporder_id);
		   String sporder_time = "20180621112730";//下单时间
		   params.put("sporder_time", sporder_time);
		   String keyStr = "OFCARD";//加密串
		   params.put("keyStr", keyStr);
		   String phone = "13521322513";//接受卡密手机号（非必需）
		   params.put("phone", phone);
		   String version = "6.0";//固定值
		   params.put("version", version);
		   
		   try {
			   getCardPassWordByOf(getUrl,params,null);
		   } catch (Exception e) {
			   e.printStackTrace();
			   throw new BusinessException("获取卡密接口调用失败");
		   }
	}
	/**
	 * 欧飞流量充值
	 * @param chargeFlowUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static OufeiOrder chargeFlowByOf(String chargeFlowUrl,Map<String,String> params,OilLog oilLog) throws Exception{
		
		StringBuffer baoti = new StringBuffer();
		String md5Str="";//签名串
		baoti.append(params.get("userid"))
			.append(params.get("userpws"))
			.append(params.get("phoneno"))
			.append(params.get("perValue"))
			.append(params.get("flowValue"))
			.append(params.get("range"))
			.append(params.get("effectStartTime"))
			.append(params.get("effectTime"))
			//.append(params.get("netType"))
			.append(params.get("sporderId"))
			.append(params.get("keyStr"));
		md5Str = KeyedDigestMD5.getKeyedDigest(baoti.toString(),"").toUpperCase();
		Log4jHelper.getLogger().info("欧飞充值流量请求MD5字符串:"+md5Str);
		StringBuffer param = new StringBuffer();
		param.append("userid").append("=").append(params.get("userid")).append("&")
			.append("userpws").append("=").append(params.get("userpws")).append("&")
			.append("phoneno").append("=").append(params.get("phoneno")).append("&")
			.append("perValue").append("=").append(params.get("perValue")).append("&")
			.append("flowValue").append("=").append(params.get("flowValue")).append("&")
			.append("range").append("=").append(params.get("range")).append("&")
			.append("effectStartTime").append("=").append(params.get("effectStartTime")).append("&")
			.append("effectTime").append("=").append(params.get("effectTime")).append("&")
			//.append("netType").append("=").append(params.get("netType")).append("&")
			.append("sporderId").append("=").append(params.get("sporderId")).append("&")
			.append("md5Str").append("=").append(md5Str).append("&")
			.append("retUrl").append("=").append(params.get("retUrl")).append("&")
			.append("version").append("=").append(params.get("version"));
		String url = chargeFlowUrl + param.toString();
		oilLog.setRequestStr(url);
		Log4jHelper.getLogger().info("欧飞充值流量请求串:"+url);
		String postResponse= postRequest(url);
		oilLog.setReturnResult(postResponse);
		OufeiOrder oufeiOrder = DocumentHelper.fromDocument(postResponse, OufeiOrder.class);
		if (Objects.isNull(oufeiOrder)) {
            Log4jHelper.getLogger().error("欧飞流量充值接口响应为空");
            throw new BusinessException("流量充值接口响应为空");
        }
		Log4jHelper.getLogger().info("欧飞流量充值响应串:"+oufeiOrder.toString()+"订单号："+params.get("sporderId"));
		return oufeiOrder;
	}
	public static void main33(String[] args) {
		   //流量充值接口
		   String chargeFlowUrl = "http://apitest.ofpay.com/flowOrder.do?";
		   Map<String, String> params = new HashMap<String,String>();
		   String userid = "A08566";
		   params.put("userid", userid);
		   String userpws = "4c625b7861a92c7971cd2029c2fd3c4a";
		   params.put("userpws", userpws);
		   String phoneno = "13521322513";
		   params.put("phoneno", phoneno);
		   String perValue = "5";
		   params.put("perValue", perValue);
		   String flowValue = "30M";
		   params.put("flowValue", flowValue);
		   String range = "2";
		   params.put("range", range);
		   String effectStartTime = "1";
		   params.put("effectStartTime", effectStartTime);
		   String effectTime = "1";
		   params.put("effectTime", effectTime);
		   //String netType = "";
		   String sporderId = RandomUtil.getOrderSn();
		   params.put("sporderId", sporderId);
		   String keyStr = "OFCARD";
		   params.put("keyStr", keyStr);
		   String retUrl = "8888";
		   params.put("retUrl", retUrl);
		   String version = "6.0";
		   params.put("version", version);
		   try {
			   chargeFlowByOf(chargeFlowUrl,params,null);
		   } catch (Exception e) {
			   e.printStackTrace();
			   throw new BusinessException("充值流量接口调用失败");
		   }
	}
	/**
	 * 欧飞话费面额商品查询
	 * @param queryPhoneGoodUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static OufeiPhoneGood queryPhoneGoodByOf(String queryPhoneGoodUrl,
			Map<String,String> params) throws Exception{
		StringBuffer param = new StringBuffer();
		param.append("userid").append("=").append(params.get("userid")).append("&")
			.append("userpws").append("=").append(params.get("userpws")).append("&")
			.append("phoneno").append("=").append(params.get("phoneno")).append("&")
			.append("pervalue").append("=").append(params.get("pervalue")).append("&")
			.append("version").append("=").append(params.get("version"));
		String url = queryPhoneGoodUrl + param.toString();
		Log4jHelper.getLogger().info("欧飞话费充值面额商品请求串:"+url);
		String postResponse= postRequest(url);
		OufeiPhoneGood oufeiPhoneGood = DocumentHelper.fromDocument(postResponse, OufeiPhoneGood.class);
		Log4jHelper.getLogger().info("欧飞话费充值面额商品响应串:"+oufeiPhoneGood.toString());
		return oufeiPhoneGood;
	}
	public static void main22(String[] args) {
	    //欧飞话费充值面额商品查询
		String queryPhoneGoodUrl = "http://apitest.ofpay.com/telquery.do?";
			 
	    String userid = "A08566";
	    String userpws = "4c625b7861a92c7971cd2029c2fd3c4a";
	    String phoneno = "13521322513";
		String pervalue = "50";
		String version = "6.0";
			   
		Map<String, String> params = new HashMap<String,String>();
		params.put("userid", userid);
		params.put("userpws", userpws);
	    params.put("phoneno", phoneno);
		params.put("pervalue", pervalue);
	    params.put("version", version);
			   
	    try {
	    	 queryPhoneGoodByOf(queryPhoneGoodUrl, params);
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 欧飞手机流量商品查询
	 * @param queryPhoneGoodUrl
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static OufeiFlowGood queryFlowGoodByOf(String queryFlowGoodUrl,
			Map<String,String> params) throws Exception{
		StringBuffer param = new StringBuffer();
		param.append("userid").append("=").append(params.get("userid")).append("&")
			.append("userpws").append("=").append(params.get("userpws")).append("&")
			.append("phoneno").append("=").append(params.get("phoneno")).append("&")
			.append("perValue").append("=").append(params.get("perValue")).append("&")
			.append("flowValue").append("=").append(params.get("flowValue")).append("&")
			.append("range").append("=").append(params.get("range")).append("&")
			.append("effectStartTime").append("=").append(params.get("effectStartTime")).append("&")
			.append("effectTime").append("=").append(params.get("effectTime")).append("&")
			.append("version").append("=").append(params.get("version"));
		String url = queryFlowGoodUrl + param.toString();
		Log4jHelper.getLogger().info("欧飞手机流量商品请求串:"+url);
		System.out.println(url);
		String postResponse= postRequest(url);
		System.out.println(postResponse);
		OufeiFlowGood oufeiFlowGood = DocumentHelper.fromDocument(postResponse, OufeiFlowGood.class);
		Log4jHelper.getLogger().info("欧飞手机流量商品响应串:"+oufeiFlowGood.toString());
		System.out.println(oufeiFlowGood.toString());
		return oufeiFlowGood;
	}
   public static void main11(String[] args) {
	   //查询手机流量商品
	   String queryFlowGoodUrl = "http://apitest.ofpay.com/flowCheck.do?";
	   String userid = "A08566";
	   String userpws = "4c625b7861a92c7971cd2029c2fd3c4a";
	   String phoneno = "13521322513";
	   String perValue = "5";
	   String flowValue = "30M";
	   String range = "2";
	   String effectStartTime = "1";
	   String effectTime = "1";
	   //String netType = "";
	   String version = "6.0";
	   Map<String, String> params = new HashMap<String,String>();
	   params.put("userid", userid);
	   params.put("userpws", userpws);
	   params.put("phoneno", phoneno);
	   params.put("perValue", perValue);
	   params.put("flowValue", flowValue);
	   params.put("range", range);
	   params.put("effectStartTime", effectStartTime);
	   params.put("effectTime", effectTime);
	   params.put("version", version);
	   try {
		   queryFlowGoodByOf(queryFlowGoodUrl, params);
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
   }
   
   
}
