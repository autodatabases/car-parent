/**
 * 
 */
package com.emate.tools.gaoyang;

import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.emate.shop.business.vo.gyvo.GaoYangilCardCommitVo;
import com.emate.shop.business.vo.gyvo.OilCardCallBack;
import com.emate.shop.business.vo.gyvo.OilCardGameResult;
import com.emate.shop.business.vo.gyvo.OilCardOrderResult;
import com.emate.shop.business.vo.gyvo.OilCardSearchProductResult;
import com.emate.shop.business.vo.gyvo.OilCardSearchResult;
import com.emate.shop.common.DocumentHelper;
import com.emate.shop.common.HttpClientHelper;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.exception.BusinessException;


/**
 * GAME60780中石油
 * GAME60560中石化
 * 100、200、500、1000中石油和中石化都是这四个面值的
 * 
 * @file GaoYangOilCardUtil.java
 * @author xieguijin
 * @mail guijin.xie@emateglobal.com
 * @since 2017年6月8日
 */
public class GaoYangOilCardUtil {
    //private  static HttpClient client = new HttpClient();
    /**
     * 可充值商品查询接口 
     * @param url
     * @param merchantid
     * @param key
     * @return
     */
    public static OilCardGameResult gamequery(String url,
           String merchantid , String key){
        String commandid  = "gamequery";
        String protocolid  = "gmcard";
        String version = "1";
        String mark = "";
        StringBuffer urlKeyValuePair = new StringBuffer();
        urlKeyValuePair.append("commandid=").append(commandid).append("&")
                        .append("mark=").append(mark).append("&")
                        .append("merchantid=").append(merchantid).append("&")
                        .append("protocolid=").append(protocolid).append("&")
                        .append("version=").append(version);
        String result = commonRequest(urlKeyValuePair, url, key, "高阳油卡：gamequery_可充值商品查询");
        //根据result解析
        return getResult(result, key, OilCardGameResult.class);
    }
    
    /**
     * 产品查询接口
     * @param url
     * @param merchantid
     * @param key
     * @param gameid  参照gamequery接口获取 
     * @return
     */
    public static OilCardSearchProductResult prodquery(String url,
            String merchantid , String key,String gameid){
        String commandid  = "prodquery";
        String protocolid  = "normal";
        String version = "1";
        String mark = "";
        StringBuffer urlKeyValuePair = new StringBuffer();
        urlKeyValuePair.append("commandid=").append(commandid).append("&")
                        .append("gameid=").append(gameid).append("&")
                        .append("mark=").append(mark).append("&")
                        .append("merchantid=").append(merchantid).append("&")
                        .append("protocolid=").append(protocolid).append("&")
                        .append("version=").append(version);
        String result = commonRequest(urlKeyValuePair, url, key, "高阳油卡：prodquery_产品查询");
        //根据result解析
        return getResult(result, key, OilCardSearchProductResult.class);
    }
    
    
    /**
     * 油卡直接充值
     * @param url
     * @param merchantid
     * @param key
     * @param gameid
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static OilCardOrderResult gasdirectfill(String url,
            String merchantid , String key,GaoYangilCardCommitVo commitVo) {
        String commandid  = "gasdirectfill";
        String protocolid  = "normal";
        String version = "1";
        String mark = "";
        int paymethod = 0;
        StringBuffer urlKeyValuePair = new StringBuffer();
        urlKeyValuePair.append("chargetype=").append(commitVo.getChargetype()).append("&")
                        .append("commandid=").append(commandid).append("&")
                        .append("fillnum=").append(commitVo.getFillnum()).append("&")
                        .append("gameid=").append(commitVo.getGameid()).append("&")
                        .append("gascardid=").append(commitVo.getGascardid()).append("&")
                        .append("gascardname=").append(commitVo.getGascardname()).append("&")
                        .append("gascardtel=").append(commitVo.getGascardtel()).append("&")
                        .append("mark=").append(mark).append("&")
                        .append("merchantid=").append(merchantid).append("&")
                        .append("orderid=").append(commitVo.getOrderid()).append("&")
                        .append("parvalue=").append(commitVo.getParvalue()).append("&")
                        .append("paymethod=").append(paymethod).append("&")
                        .append("protocolid=").append(protocolid).append("&")
                        .append("userip=").append(commitVo.getUserip()).append("&")
                        .append("version=").append(version);
        String result = commonRequest(urlKeyValuePair, url, key, "高阳油卡：gasdirectfill_油卡充值下单");
        return getResult(result, key, OilCardOrderResult.class);
    }
    
    /**
     * 订单接口查询
     * @param url
     * @param merchantid
     * @param key
     * @param gameid
     * @return
     */
    public static OilCardSearchResult orderquerynew(String url,
            String merchantid , String key,String orderid){
        String commandid  = "orderquerynew";
        String protocolid  = "normalnew";
        String version = "1";
        String mark = "";
        StringBuffer urlKeyValuePair = new StringBuffer();
        urlKeyValuePair.append("commandid=").append(commandid).append("&")
                        .append("mark=").append(mark).append("&")
                        .append("merchantid=").append(merchantid).append("&")
                        .append("orderid=").append(orderid).append("&")
                        .append("protocolid=").append(protocolid).append("&")
                        .append("version=").append(version);
        String result = commonRequest(urlKeyValuePair, url, key, "高阳油卡：orderquerynew_订单结果查询");
        return getResult(result, key, OilCardSearchResult.class);
    }
    
    /**
     * 公共请求
     * @param urlKeyValuePair
     * @param url
     * @param key
     * @param operate
     * @return
     */
    private static String commonRequest(StringBuffer urlKeyValuePair,String url,String key,String operate){
        StringBuffer urlParam = new StringBuffer(urlKeyValuePair);
        String sign = "";
        //计算签名
        {
            urlKeyValuePair.append("&").append("key=").append(key);
            String verifystring1 =  KeyedDigestMD5.getKeyedDigest(urlKeyValuePair.toString(), "");
            sign = KeyedDigestMD5.getKeyedDigest(key+verifystring1+key, "");
            urlParam.append("&sign=").append(sign);
        }
        //发送请求
        String resultString = "";
        try {
            url = url + encodParam(urlParam.toString());
            if((operate.indexOf("gamequery")!=-1)
                    ||(operate.indexOf("prodquery")!=-1)
                    ||(operate.indexOf("gasdirectfill")!=-1)
                    ||(operate.indexOf("orderquerynew")!=-1)){//产品查询时需要特殊转码
                resultString = gaoYangPost(url);
            }else{
              resultString = HttpClientHelper.httpPost(url, new HashMap<String,String>());
            }
            if (StringUtils.isEmpty(resultString)) 
                throw new BusinessException("[{}]返回为空...",operate);
            Log4jHelper.getLogger().info("[{}]结果：...\n{}",operate,resultString);
        } catch (Exception e) {
            e.printStackTrace();
            Log4jHelper.getLogger().info(e);
            throw new BusinessException("[{}]异常...",operate);
        }
        return resultString;
    }
    

    /**
     * 产品查询中文乱码，构造请求
     * @param client
     * @param url
     * @return
     * @throws Exception 
     */
    public static String gaoYangPost(String url) throws Exception {
    		Log4jHelper.getLogger().info("高阳请求："+url);
    		HttpClient client = new HttpClient();
    		//CloseableHttpClient client = PoolManager.getHttpClient();
            PostMethod httpPost = null;
            httpPost = new PostMethod(url);
            httpPost.addRequestHeader("User-Agent", "Mozilla/4.0");
            httpPost.setDoAuthentication(false);
            client.executeMethod(httpPost);
            String restionValue = "";
            byte respbytes[] = httpPost.getResponseBody();
            restionValue = new String(respbytes, "GBK");
            return restionValue;
    }
    
    
    /**
     * 参数中文转GBK编码传输...
     * @param param
     * @return
     * @throws Exception
     */
    public static String encodParam(String param) throws Exception{
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(param);
        Set<String> chineseWord = new HashSet<String>();
        while (m.find()) {
            chineseWord.add(m.group(0));
        }
        if (chineseWord.size() > 0) {
            for (String word : chineseWord) {
                param = param.replaceAll(word, URLEncoder.encode(word, "GBK"));
            }
        }
        return param;
    }
    
    /**
     * 构造返回...
     * @param result
     * @param key
     * @param clazz
     * @return
     */
    public static <T> T getResult(String result,String key, Class<T> clazz) {
        //确认sign
        String sign = result.substring(result.length()-32, result.length());
        result = result.substring(0,result.length()-32);
        StringBuffer resultBuffer = new StringBuffer();
        resultBuffer.append(result).append("&").append(key);
        String mySign =  KeyedDigestMD5.getKeyedDigest(resultBuffer.toString(),"");
        mySign = KeyedDigestMD5.getKeyedDigest(key+mySign+key, "");
        if(!sign.equals(mySign)){
            Log4jHelper.getLogger().info("响应数据sign校验失败，尽快排查...");
            return null;
        }
        return DocumentHelper.fromDocument(result, clazz);
    }
    
    
    
    public static void main11(String[] args) throws UnsupportedEncodingException {
    	
    	
    	
        /*测试环境中石化帐号：1000111100013840088 赵爽
                         测试环境中石油帐号：9030230003553452 赵*
                         此卡是在测试环境专用卡，请勿在生产环境发起充值，
         
         
         GAME60560中石化
         GAME60780中石油
         
         100、200、500、1000中石油和中石化都是这四个面值的 
        */
/*        String url = "http://114.247.40.65:18030/game/gameEsalesServlet.do?";
        String merchantid  = "guoshou";//agenttest2
        String key = "123456789";
       // String gameid = "GAME60560";
       // String gameid = "GAME60800";//中石化任意充  1~1000元任意正整数面值
        String gameid = "GAME60780";//中石油任意充  1~1000元任意正整数面值
        
        String orderId = "11213s1222d20";
        GaoYangilCardCommitVo commitVo = new GaoYangilCardCommitVo();
        commitVo.setChargetype(GaoYangilCardCommitVo.RECHARGE_TYPE_CNPC);//1 中石化
        commitVo.setFillnum(1);//购买数量
        commitVo.setGameid(gameid);*/
       // commitVo.setGascardid("1000111100013840088");//油卡账号
/*        commitVo.setGascardid("9030230003553452");//油卡账号
        commitVo.setGascardname("赵赵赵爽");
        commitVo.setGascardtel("17710310113");
        commitVo.setOrderid(orderId);//订单号
        commitVo.setParvalue(500+"");//面额  
        commitVo.setUserip("127.0.0.1");*/
        
        //OilCardGameResult r =  gamequery(url, merchantid, key);
       // OilCardSearchProductResult  cardSearchProductResult = prodquery(url, merchantid, key, gameid);
       // System.err.println(JacksonHelper.toJSON(cardSearchProductResult));
        //OilCardOrderResult cardOrderResult = gasdirectfill(url,merchantid,key,commitVo);
       // System.err.println(JacksonHelper.toJSON(cardOrderResult));
   //     OilCardSearchResult cardSearchResult = orderquerynew(url, merchantid, key, orderId);
  //      System.err.println(JacksonHelper.toJSON(cardSearchResult));
        
        
        //OilCardCallBack das = new OilCardCallBack();
        //das.setNotifyresult("0");
        //System.err.println(DocumentHelper.toDocument(das));
        //URLDecoder.decode("", "GBK") ;
    	
    }
    
    public static void main(String[] args) {
/*    	17122610405158191
    	17122610414491528
    	17122610422824064
    	17122610444631598
    	17122610455222776
    	17122610463781662
    	17122610473752874
    	17122610505606545*/
    	String url = "http://114.247.40.65:18030/game/gameEsalesServlet.do?";
        String merchantid = "guoshou2"; 
        String key = "123456789";
        String orderid = "17122610505606545";
        OilCardSearchResult orderquerynew = orderquerynew(url,merchantid,key,orderid);
        System.out.println(orderquerynew.toString());
	}
    /**
     * 获取异常信息
     * @param code
     * @return
     */
    public static String getMsg(String code){
        if("101".equals(code)){
            return code+"_"+"缺少参数...";
        }else if("102".equals(code)){
            return code+"_"+"代理商 ID 错误/代理商状态关闭 ...";
        }else if("103".equals(code)){
            return code+"_"+"缺少参数...";
        }else if("104".equals(code)){
            return code+"_"+"签名错误...";
        }else if("105".equals(code)){
            return code+"_"+"参数格式错误 ...";
        }else if("201".equals(code)){
            return code+"_"+"下单失败  ...";
        }else if("202".equals(code)){
            return code+"_"+"订单不存在  ...";
        }else if("203".equals(code)){
            return code+"_"+"产品已下架或不存在  ...";
        }else if("205".equals(code)){
            return code+"_"+"不存在可充值产品 ...";
        }else if("206".equals(code)){
            return code+"_"+"油卡卡信息不存在或没有获取到  ...";
        }else if("300".equals(code)){
            return code+"_"+" 支付失败 ...";
        }else if("301".equals(code)){//账户余额不足
            return code+"_"+"该面额库存不足,正紧急调货,请稍后充值 ...";
        }else if("401".equals(code)){
            return code+"_"+" 充值渠道异常   ...";
        }else if("402".equals(code)){
            return code+"_"+" 充值参数错误   ...";
        }else if("405".equals(code)){
            return code+"_"+" 卡密订单非成功状态，无法获取卡密信息    ...";
        }else if("406".equals(code)){
            return code+"_"+" 查询卡密失败   ...";
        }else if("999".equals(code)){
            return code+"_"+" 系统异常    ...";
        }else if("50059".equals(code)){
            return code+"_"+" 该卡已损坏    ...";
        }else if("50062".equals(code)){
            return code+"_"+" 该卡已挂失   ...";
        }else if("54037".equals(code)){
            return code+"_"+" 失败，其他原因    ...";
        }else if("81508".equals(code)){
            return code+"_"+" 失败，其他原因   ...";
        }else if("54015".equals(code)){
            return code+"_"+" 失败，其他原因   ...";
        }else if("54018".equals(code)){
            return code+"_"+"  账户余额不足     ...";
        }else if("22101".equals(code)){
            return code+"_"+" 失败，其他原因    ...";
        }else if("50028".equals(code)){
            return code+"_"+" 失败，其他原因    ...";
        }else if("63200".equals(code)){
            return code+"_"+" 失败，其他原因    ...";
        }else if("22204".equals(code)){
            return code+"_"+" 失败，其他原因    ...";
        }else if("50025".equals(code)){
            return code+"_"+" 失败，其他原因    ...";
        }else if("80012".equals(code)){
            return code+"_"+" 失败，其他原因    ...";
        }else if("80012".equals(code)){
            return code+"_"+" 失败，其他原因    ...";
        }else if("50046".equals(code)){
            return code+"_"+" 不能给副卡充值     ...";
        }else if("90002".equals(code)){
            return code+"_"+" 失败，其他原因    ...";
        }else if("3008".equals(code)){
            return code+"_"+" 该功能您获取校验码的次数超限，获取校验码功能已被锁定，请 24 小时以后再获取或直接输入校验码！     ...";
        }else if("4001".equals(code)){
            return code+"_"+" 钱包账户已被绑定     ...";
        }else if("1012".equals(code)){
            return code+"_"+" 该订单非法,请重新下单    ...";
        }else if("1003".equals(code)){
            return code+"_"+" 无法获取订单信息     ...";
        }else if("1111".equals(code)){
            return code+"_"+" 其它异常   ...";
        }else if("1004".equals(code)){
            return code+"_"+" 状态非法     ...";
        }else if("1013".equals(code)){
            return code+"_"+" 代理商已存在     ...";
        }else if("1007".equals(code)){
            return code+"_"+" 用户已暂停     ...";
        }else if("1008".equals(code)){
            return code+"_"+" 用户状态非法      ...";
        }else if("3001".equals(code)){
            return code+"_"+" 该功能暂停使用     ...";
        }else if("3002".equals(code)){
            return code+"_"+" 该功能必须经过安全验证      ...";
        }else if("3003".equals(code)){
            return code+"_"+"  功能安全验证方式不存在     ...";
        }else if("3004".equals(code)){
            return code+"_"+" 您已连续 5 次校验码验证失败，校验码验证功能已锁定，请 10 分钟以后再试！    ...";
        }else if("1001".equals(code)){
            return code+"_"+" 传入参数有误      ...";
        }else if("1002".equals(code)){
            return code+"_"+"  无法获取产品信息     ...";
        }else if("3013".equals(code)){
            return code+"_"+" 验证信息不正确      ...";
        }else if("1005".equals(code)){
            return code+"_"+"  代理商不存在   ...";
        }else if("1011".equals(code)){
            return code+"_"+"  待绑定手机已存在      ...";
        }else if("1003".equals(code)){
            return code+"_"+"  无法获取订单信息   ...";
        }else if("3005".equals(code)){
            return code+"_"+"  该功能不存在    ...";
        }else if("1009".equals(code)){
            return code+"_"+"  用户未激活      ...";
        }else if("1010".equals(code)){
            return code+"_"+"   用户已锁定     ...";
        }else if("3006".equals(code)){
            return code+"_"+"  验证发送次数超过最大限制    ...";
        }else if("1006".equals(code)){
            return code+"_"+" 订单信息已存在  ...";
        }else if("3007".equals(code)){
            return code+"_"+"  校验码与手机号不一致     ...";
        }else if("3011".equals(code)){
            return code+"_"+"  用户输入的验证串已失效     ...";
        }else if("70002".equals(code)){
            return code+"_"+"  失败，其他原因     ...";
        }else {
            return code+"_"+"没有对于状态码...";
        }
    }
}
