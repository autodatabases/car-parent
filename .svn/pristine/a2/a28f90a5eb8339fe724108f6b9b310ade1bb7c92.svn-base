package com.emate.tools.gaoyang;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.emate.shop.business.vo.gyvo.Accsegment;
import com.emate.shop.business.vo.gyvo.Allproducts;
import com.emate.shop.business.vo.gyvo.Fill;
import com.emate.shop.common.HttpClientHelper;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.Log4jHelper;
import com.emate.shop.exception.BusinessException;
import com.emate.tools.DocumentHelper;

public class GaoYangUtil {
	  public static boolean updateFlag = false;
	  public static Map<String, Object> cacheMap = new ConcurrentHashMap<String,Object>();// 缓存容器  

	    /**
	     * @Description: 号段查询
	     * @param: @param url
	     * @param: @param agentid
	     * @param: @param merchantKey
	     * @param: @param source
	     * @param: @param mobile
	     * @param: @return      
	     * @return: List<Map<String,String>>
	     */
	    public static Map<String, String> accsegment(String url,
	            String agentId,String merchantKey,String source,String mobilenum){
	        StringBuffer urlKeyValuePair = new StringBuffer();
	        urlKeyValuePair.append("agentid=").append(agentId).append("&")
	        .append("source=").append(source).append("&")
	        .append("mobilenum=").append(mobilenum).append("&")
	        .append("merchantKey=").append(merchantKey);
	        // 2. MD5签名
	        String verifystring = DigestUtils.md5Hex(urlKeyValuePair.toString());
	        // 3. 拼接请求url
	        String requestUrl = url + 
	        urlKeyValuePair.toString() + "&verifystring=" + verifystring;
	        Log4jHelper.getLogger().info("号段查询请求串:"+requestUrl);
	        // 4. 发送网络请求
	            String resultString = HttpClientHelper.httpPost(requestUrl, new HashMap<String,String>());
	            if (StringUtils.isEmpty(resultString)) {
	            	Log4jHelper.getLogger().info("号段查询请求异常:返回参数为空");
	                throw new BusinessException("号段查询请求响应异常...");
	            }
	            try {
					resultString = URLDecoder.decode(resultString, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					Log4jHelper.getLogger().info("号段查询异常:响应结果编码失败!");
					throw new BusinessException("号段查询异常...");
				}
	            if(resultString.length()<10){
	            	 if("0999".equals(resultString)){
	            		Log4jHelper.getLogger().info("号段查询请求异常:"+resultString+"_"+"商户未开通直冲功能");
	 	            	throw new BusinessException("商户未开通直冲功能");
	     	        }else if("1000".equals(resultString)){
	     	        	Log4jHelper.getLogger().info("号段查询请求异常:"+resultString+"_"+"没有对应的号段");
	 	            	throw new BusinessException("没有对应的号段");
	     	        }else if("1001".equals(resultString)){
	     	        	Log4jHelper.getLogger().info("号段查询请求异常:"+resultString+"_"+"传入参数不完整");
	 	            	throw new BusinessException("传入参数不完整");
	     	        }else if("1002".equals(resultString)){
	     	        	Log4jHelper.getLogger().info("号段查询请求异常:"+resultString+"_"+"验证摘要串验证失败");
	 	            	throw new BusinessException("验证摘要串验证失败");
	     	        }else if("1022".equals(resultString)){
	     	        	Log4jHelper.getLogger().info("号段查询请求异常:"+resultString+"_"+"充值号码格式错误");
	 	            	throw new BusinessException("充值号码格式错误");
	     	        }else {
	     	        	Log4jHelper.getLogger().info("号段查询请求异常:"+resultString+"_"+"没有对于状态码...");
	 	            	throw new BusinessException("没有对于状态码...");
	     	        }
	            }
	            Log4jHelper.getLogger().info("[{}]号段查询...\n{}", mobilenum,resultString);
	            // 5. 处理返回参数，解析XML文档
	            Accsegment acc =DocumentHelper.fromDocument(resultString, Accsegment.class);
	            Map<String,String> map = new HashMap<String,String>();
	            acc.getAcc().getMobiles().forEach(mo->{
	                map.put(mo.getName(), mo.getValue());
	            });
	            return map;
	    }
	    
	    @SuppressWarnings("unchecked")
	    public synchronized static List<Map<String,String>> getProducts(String url,
	            String agentId,String merchantKey,String source){
	        if(GaoYangUtil.updateFlag){//如果正在清除缓存，正常查询
	        	Log4jHelper.getLogger().info("正在清除缓存，请求接口获取高阳产品列表...");
	            return GaoYangUtil.directProduct(url, agentId, merchantKey, source);
	        }
	        if(Objects.isNull(GaoYangUtil.cacheMap.get("products"))){
	        	Log4jHelper.getLogger().info("请求接口获取高阳产品列表...创建定时器...");
	            GaoYangUtil.cacheMap.put("products",GaoYangUtil.directProduct(url, agentId, merchantKey, source));
	            //创建定时器对象
	            Timer t=new Timer();
	            //在1天后执行MyTask类中的run方法，清空缓存 1000*60*60*24
	            //注意的类
	            t.schedule(new MyTask(),1000*60*60*24);
	            return (List<Map<String,String>>)GaoYangUtil.cacheMap.get("products");
	        }
	        Log4jHelper.getLogger().info("缓存中获取高阳产品列表...");
	        return (List<Map<String,String>>)GaoYangUtil.cacheMap.get("products");
	    }
	    
	    /**
	     * @Description: 查询产品列表
	     * @param: @param url
	     * @param: @param agentId
	     * @param: @param merchantKey
	     * @param: @param source
	     * @param: @return      
	     * @return: List<Map<String,String>>
	     */
	    public static List<Map<String,String>> directProduct(String url,
	            String agentId,String merchantKey,String source){
	        // 2. 拼接请求字符串
	        StringBuffer requestString = new StringBuffer();
	        requestString.append("agentid=").append(agentId).append("&")
	        .append("source=").append(source).append("&")
	        .append("merchantKey=").append(merchantKey);

	        // 3. 请求字符串进行MD5签名并拼接到请求串之后
	        String verifyString = DigestUtils.md5Hex(requestString.toString()); // 摘要串
	        requestString.append("&").append("verifystring=").append(verifyString);
	        url = url + requestString.toString();
	        Log4jHelper.getLogger().info("高阳商品查询请求串:"+url);
	        // 4. 发送网络请求
	       
	       // try {
	            String resultString = HttpClientHelper.httpPost(url, new HashMap<String,String>());
	            if (StringUtils.isEmpty(resultString)){
	            	Log4jHelper.getLogger().info("商品查询请求异常:接口返回参数为空");
	                throw new BusinessException("商品查询请求响应异常...");
	            }
	            try {
					resultString = URLDecoder.decode(resultString, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
		            throw new BusinessException("查询产品列表异常:编码响应结果失败");
				}
	            if(resultString.length()<10){
	            	 if("0999".equals(resultString)){
	            		Log4jHelper.getLogger().info("商品查询请求异常:"+resultString+"_"+"商户未开通直冲功能");
	 	            	throw new BusinessException("商户未开通直冲功能");
	     	        }else if("1000".equals(resultString)){
	     	        	Log4jHelper.getLogger().info("商品查询请求异常:"+resultString+"_"+"系统异常");
	 	            	throw new BusinessException("系统异常");
	     	        }else if("1001".equals(resultString)){
	     	        	Log4jHelper.getLogger().info("商品查询请求异常:"+resultString+"_"+"传入参数不完整");
	 	            	throw new BusinessException("传入参数不完整");
	     	        }else if("1002".equals(resultString)){
	     	        	Log4jHelper.getLogger().info("商品查询请求异常:"+resultString+"_"+"验证摘要串验证失败");
	 	            	throw new BusinessException("验证摘要串验证失败");
	     	        }else if("1005".equals(resultString)){
	     	        	Log4jHelper.getLogger().info("商品查询请求异常:"+resultString+"_"+"查询数据库无产品返回");
	 	            	throw new BusinessException("查询数据库无产品返回");
	     	        }else {
	     	        	Log4jHelper.getLogger().info("商品查询请求异常:"+resultString+"_"+"没有对于状态码...");
	 	            	throw new BusinessException("没有对于状态码...");
	     	        }
	            }
	            // 5. 输出xml文档并解析
	            //Log4jHelper.getLogger().info("话费充值产品查询如下...\n{}",resultString);
	            Allproducts allProducts = DocumentHelper.fromDocument(resultString, Allproducts.class);
	            List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
	            allProducts.getProducts().forEach(product->{
	                Map<String,String> map = new HashMap<String,String>();
	                product.getProduct().forEach(p->{
	                    map.put(p.getName(), p.getValue());
	                });
	                mapList.add(map);
	            });
	            return mapList;
	    }
	    
	    /**
	     * @Description: 话费直冲
	     * @param: @param url
	     * @param: @param agentid
	     * @param: @param merchantKey
	     * @param: @param mobilenum
	     * @param: @param prodid
	     * @param: @param orderNo
	     * @param: @param returntype 返回类型 [1]表示post返回到backurl[2]表示返回XML信息
	     * @param: @param backurl  直冲请求响应url
	     * @param: @param source  留字段，代理商系统返回时原样带回
	     * @param: @param mark
	     * @param: @return      
	     * @return: String
	     */
	    public static Map<String,String> directFill(String url,String agentid,
	            String merchantKey,String mobilenum,String prodid,String orderNo,
	            String returntype,String backurl,String source,String mark){
	        StringBuffer urlKeyValuePair = new StringBuffer();
	        urlKeyValuePair.append("prodid=").append(prodid).append("&")
	                        .append("agentid=").append(agentid).append("&")
	                        .append("backurl=").append(backurl).append("&")
	                        .append("returntype=").append(returntype).append("&")
	                        .append("orderid=").append(orderNo).append("&")
	                        .append("mobilenum=").append(mobilenum).append("&")
	                        .append("source=").append(source).append("&")
	                        .append("mark=").append(mark).append("&")
	                        .append("merchantKey=").append(merchantKey);
	        // 2. MD5签名
	        String verifystring = DigestUtils.md5Hex(urlKeyValuePair.toString());
	        // 3. 拼接请求url
	        String requestUrl = url + 
	                            urlKeyValuePair.toString() + "&verifystring=" + verifystring;
	        // 4. 发送网络请求
	        Log4jHelper.getLogger().info("高阳话费充值请求串:"+requestUrl);
	            String resultString = HttpClientHelper.httpPost(requestUrl, new HashMap<String,String>());
	            if (StringUtils.isEmpty(resultString)){
	            	Log4jHelper.getLogger().info("话费充值请求异常:接口返回参数为空");
	                throw new BusinessException("话费充值请求响应异常");
	            }
	            try {
	            	resultString = URLDecoder.decode(resultString, "utf-8");
	            } catch (Exception e) {
	            	e.printStackTrace();
	            	throw new BusinessException("话费充值异常::编码响应结果失败");
	            } 
	            
	            Log4jHelper.getLogger().info("话费充值订单[{}]下单结果如下...\n{}",orderNo,resultString);
	 	        // 5. 处理返回参数,先验证返回的XML文档是否合法;
	 	        Fill fill = DocumentHelper.fromDocument(resultString, Fill.class);
	 	        Map<String, String> paramMap = new HashMap<String, String>();
	 	        fill.getItems().getItem().forEach(it->{
	 	        	   paramMap.put(it.getName(), it.getValue());
	 	        });
	 	        // 签名源串
		        String md5Source = "prodid=" + paramMap.get("prodid") + "&orderid=" + paramMap.get("orderid")
		                       + "&tranid=" + paramMap.get("tranid") + "&resultno=" + paramMap.get("resultno")
		                       + "&mark=" + paramMap.get("mark") + "&merchantKey=" + merchantKey;
		            
		        String md5Sign = DigestUtils.md5Hex(md5Source);
	          
	            if (md5Sign.equals(paramMap.get("verifystring"))){
	                return paramMap;        // 转存返回码
	            }else{
	                return paramMap;//签名验证失败  
	            }
	    }
	    
	    
	    /**
	     * @Description: 手机重接结果查询
	     * @param: @param url
	     * @param: @param agentid
	     * @param: @param merchantKey
	     * @param: @param orderNo 
	     * @param: @param returntype 返回类型 [1]表示post返回到backurl[2]表示返回XML信息
	     * @param: @param backurl 直冲请求返回url
	     * @param: @param source
	     * @param: @return      
	     * @return: String
	     */
	    public static Map<String, String> orderDirectSearch(String url,String agentid,
	            String merchantKey,String orderNo,
	            String returntype,String backurl,String source){
	        StringBuffer urlKeyValuePair = new StringBuffer();
	        urlKeyValuePair.append("agentid=").append(agentid).append("&").append(
	                "backurl=").append(backurl).append("&").append("returntype=")
	                .append(returntype).append("&").append("orderid=").append(
	                        orderNo).append("&").append("source=").append(source)
	                .append("&").append("merchantKey=").append(merchantKey);
	        // 2. MD5签名
	        String verifystring = DigestUtils.md5Hex(urlKeyValuePair.toString());
	        // 3. 拼接请求url
	        String requestUrl = url + urlKeyValuePair.toString()
	                + "&verifystring=" + verifystring;
	        // 4. 发送网络请求
	        Log4jHelper.getLogger().info("手机重接结果请求串:"+requestUrl);
	        try {
	            String resultString = HttpClientHelper.httpPost(requestUrl, new HashMap<String,String>());
	            if (StringUtils.isEmpty(resultString)) 
	                throw new BusinessException("订单查询请求响应异常...");
	            resultString = URLDecoder.decode(resultString, "utf-8");
	            // 5. 处理返回的XML文档并验证返回的XML文档是否合法;
	            //Log4jHelper.info("话费充值订单[{}]查询结果如下...\n{}",orderNo,resultString);
	            Log4jHelper.getLogger().info("话费充值订单[{}]查询结果如下...\n{}",orderNo,resultString);
	            
	            Fill fill = DocumentHelper.fromDocument(resultString, Fill.class);
	            Map<String, String> paramMap = new HashMap<String, String>();
	            fill.getItems().getItem().forEach(it->{
	                paramMap.put(it.getName(), it.getValue());
	            });
	            System.err.println(JacksonHelper.toJSON(paramMap));
	            // 签名源串
	            String md5Source = "orderid=" + paramMap.get("orderid") + "&resultno="
	                    + paramMap.get("resultno") + "&finishmoney="
	                    + paramMap.get("finishmoney") + "&merchantKey=" + merchantKey;
	            String md5Sign = DigestUtils.md5Hex(md5Source);
	            if (md5Sign.equals(paramMap.get("verifystring"))) {
	                return paramMap; // 转存返回码
	            }else{
	                return new HashMap<String,String>();//签名验证失败
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new BusinessException("充值结果查询异常...");
	        }
	    }
	    
	    /**
	     * 清除高阳产品缓存
	     * @return
	     */
	    public static Boolean clearData(){
	    	Log4jHelper.getLogger().info("清除高阳产品列表缓存...");
	    	
	        GaoYangUtil.updateFlag = true;
	        GaoYangUtil.cacheMap.clear();
	        GaoYangUtil.updateFlag = false;
	 
	        Log4jHelper.getLogger().info("清除高阳产品列表缓存——完成...");
	        return Boolean.TRUE;
	    }
	    
	    public static void main(String[] args) {
	    	//账户名称
	    	String agentId ="hjcf";
	    	//秘钥
	    	String merchantKey="w9ls16s28ap0p5n6aqix0bewuesrm5ax6cfinpk342uqinyj7rga25co68ir6ns59cuta3nyorz61vhrdi49ux5jil0otp8iqn45pidq396c5jc1q6y7hwopwco84qun";
	    	//来源(固定值)
	    	String source="esales";
	    	
	    	//号段查询
	    	String numberSerchUrl="http://hfjk.19ego.com/esales2/accegment/accsegment.do?" ;
	    	
	    	Map<String, String> accsegment = accsegment(numberSerchUrl, agentId, merchantKey, source, "13521322513");
	    	search(accsegment);
	    	//手机充值
	    	//String orderSendUrl="http://219.143.36.227:101/dealer/directfill/directFill.do?" ;
	    	//directFill(orderSendUrl, agentId, merchantKey, "13521322513", "50083805", "12345678533", "1", backurl, source, "mark");*/
	    	//高阳产品查询
	    	/*String productSearchUrl="http://hfjk.19ego.com/esales2/prodquery/directProduct.do?" ;
	    	List<Map<String, String>> products = getProducts(productSearchUrl, agentId, merchantKey, source);
	    	
	    	for(Map<String, String> map:products){
	    		search(map);
	    	}*/
	    	//订单查询
	    	//String orderSarchUrl="http://219.143.36.227:101/dealer/orderquery/directSearch.do?"; 

	    	//Map<String, String> orderDirectSearch = orderDirectSearch(orderSarchUrl, agentId, merchantKey, "17071316333826424", "2", "", source);
	    	//search(orderDirectSearch);
	    }
	    
	    private static void search(Map<String, String> map){
	    	Set<Entry<String, String>> entrySet = map.entrySet();
	    	for(Entry<String, String> entry:entrySet){
	    		System .out.print(entry.getKey()+":"+entry.getValue()+";");
	    	}
	    	System.out.println();
	    }
	    
	    /**
	     * 获取异常信息
	     * @param code
	     * @return
	     */
	    public static String getMsg(String code){
	        if("0999".equals(code)){
	            return "商户未开通直冲功能";
	        }else if("1000".equals(code)){
	            return "没有对应的号段";
	        }else if("1001".equals(code)){
	            return "传入参数不完整";
	        }else if("1002".equals(code)){
	            return "验证摘要串验证失败";
	        }else if("1022".equals(code)){
	            return "充值号码格式错误";
	        }else {
	            return "没有对于状态码...";
	        }
	    }
	    public static void getMessage(String code){
			if("0001".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:0001_高阳支付异常");
				throw new BusinessException("系统异常,0001_支付异常");
			}else if("0999".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:0999_未开通直冲功能");
				throw new BusinessException("下单失败,0999_未开通直冲功能");
			}else if("1000".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1000_未扣款，请稍后重试");
				throw new BusinessException("下单失败,1000_未扣款，请稍后重试");
			}else if("1001".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1001_传入参数不完整");
				throw new BusinessException("下单失败,1001_传入参数不完整");
			}else if("1002".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1002_验证摘要串验证失败");
				throw new BusinessException("下单失败,1002_验证摘要串验证失败");
			}else if("1005".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1005_没有对应充值产品");
				throw new BusinessException("下单失败,1005_该面额产品已下架");
			}else if("1007".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1007_账户余额不足");
				throw new BusinessException("下单失败,1007_该面额库存不足,请稍后充值");
			}else if("1008".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1008_此商品超出当天限额");
				throw new BusinessException("下单失败,1008_此商品超出当天限额");
			}else if("1010".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1010_产品和手机号不匹配");
				throw new BusinessException("下单失败,1010_产品和手机号不匹配");
			}else if("1011".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1011_订单号重复,修改订单号");
				throw new BusinessException("下单失败,1011_订单号重复");
			}else if("1013".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1013_暂时不能充值");
				throw new BusinessException("系统维护,1013_暂时不能充值,请稍后重试");
			}else if("1015".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1015_无法查到对应号段");
				throw new BusinessException("下单失败,1015_无法查到对应号段");
			}else if("1017".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1013_电信手机10秒内不能重复充值");
				throw new BusinessException("下单失败,1013_电信手机10秒内不能重复充值");
			}else if("1020".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1020_号码不支持流量充值卡");
				throw new BusinessException("下单失败,1020_号码不支持流量充值卡");
			}else if("1022".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1022_充值号码格式错误");
				throw new BusinessException("下单失败,1022_充值号码格式错误");
			}else if("1028".equals(code)){
				Log4jHelper.getLogger().info("高阳下单接口失败:1028_下单接口请求次数超限");
				throw new BusinessException("下单失败,1028_下单接口请求次数超限");
			}else{
				Log4jHelper.getLogger().info("高阳下单接口失败:没有状态码返回");
				throw new BusinessException("下单失败,没有状态码返回");
			}
		}
}
	    

class MyTask extends TimerTask{
	    @Override
	    public void run() {
	        Log4jHelper.getLogger().info("清除高阳产品列表缓存...");
	        GaoYangUtil.updateFlag = true;
	        GaoYangUtil.cacheMap.clear();
	        GaoYangUtil.updateFlag = false;
	        Log4jHelper.getLogger().info("清除高阳产品列表缓存——完成...");
	    }
}
