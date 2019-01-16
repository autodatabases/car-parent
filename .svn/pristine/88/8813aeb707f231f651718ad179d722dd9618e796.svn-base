package com.emate.shop.business.service;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emate.shop.business.api.SmsService;
import com.emate.shop.business.model.AuthCode;
import com.emate.shop.business.model.AuthCodeExample;
import com.emate.shop.business.model.UserExample;
import com.emate.shop.common.HttpClientHelper;
import com.emate.shop.common.JacksonHelper;
import com.emate.shop.common.RandomUtil;
import com.emate.shop.datasource.Write;
import com.emate.shop.exception.BusinessException;
import com.emate.shop.exception.ParameterException;
import com.emate.shop.mapper.AuthCodeMapper;
import com.emate.shop.rpc.dto.DatasetBuilder;
import com.emate.shop.rpc.dto.DatasetSimple;
import com.emate.tools.ExcelOper;

import net.sf.json.JSONArray;

@Service
@PropertySource("classpath:properties/neteasesms.properties")
public class SmsServiceImpl implements SmsService{
	private static Map<Integer, String> MESSAGE_MAPPING = new HashMap<Integer, String>();
    static {
        MESSAGE_MAPPING.put(200, "操作成功");
        MESSAGE_MAPPING.put(201, "客户端版本不对，需升级sdk");
        MESSAGE_MAPPING.put(301, "被封禁");
        MESSAGE_MAPPING.put(302, "用户名或密码错误");
        MESSAGE_MAPPING.put(315, "IP限制");
        MESSAGE_MAPPING.put(403, "非法操作或没有权限");
        MESSAGE_MAPPING.put(404, "对象不存在");
        MESSAGE_MAPPING.put(405, "参数长度过长");
        MESSAGE_MAPPING.put(406, "对象只读");
        MESSAGE_MAPPING.put(408, "客户端请求超时");
        MESSAGE_MAPPING.put(413, "验证失败(短信服务)");
        MESSAGE_MAPPING.put(414, "参数错误");
        MESSAGE_MAPPING.put(415, "客户端网络问题");
        MESSAGE_MAPPING.put(416, "频率控制");
        MESSAGE_MAPPING.put(417, "重复操作");
        MESSAGE_MAPPING.put(418, "通道不可用(短信服务)");
        MESSAGE_MAPPING.put(419, "数量超过上限");
        MESSAGE_MAPPING.put(422, "账号被禁用");
        MESSAGE_MAPPING.put(431, "HTTP重复请求");
        MESSAGE_MAPPING.put(500, "服务器内部错误");
        MESSAGE_MAPPING.put(503, "服务器繁忙");
        MESSAGE_MAPPING.put(514, "服务不可用");
        MESSAGE_MAPPING.put(509, "无效协议");
        MESSAGE_MAPPING.put(998, "解包错误");
        MESSAGE_MAPPING.put(999, "打包错误");
    }
    @Resource
    private Environment environment;
    
    @Resource
    private AuthCodeMapper authCodeMapper;
    
    @Resource
    StandardPasswordEncoder passwordEncoder;

    private static final ExecutorService SMS_CODE_POOL = Executors.newFixedThreadPool(10);
    private static final ExecutorService SMS_TMP_POOL = Executors.newFixedThreadPool(10);

    @Write
    @Transactional
    public DatasetSimple<String> sendSmsCode(String mobile,Integer smsType) {
    	AuthCodeExample authCodeEx = new AuthCodeExample();
    	authCodeEx.or()
    		.andUserphoneEqualTo(mobile)
    		.andSmsTypeEqualTo(smsType)
    		.andStatusNotEqualTo(AuthCode.STATUS_2);
    	authCodeEx.setLimitStart(0);
    	authCodeEx.setLimitEnd(1);
    	List<AuthCode> authCodeList = authCodeMapper.selectByExample(authCodeEx);
    	AuthCode authCodeNew = null;
    	if(authCodeList.isEmpty()){
    		authCodeNew = new AuthCode();
    		authCodeNew.setCreateTime(new Date());
    		authCodeNew.setUpdateTime(new Date());
    		authCodeNew.setSmsType(smsType);
    		authCodeNew.setStatus(AuthCode.STATUS_0);
    		authCodeNew.setUserphone(mobile);
    		authCodeNew.setCode(RandomUtil.randomNumber(4));
    	}else{
    		AuthCode authCode = authCodeList.get(0);
    		if(new Date().getTime()-authCode.getCreateTime().getTime()>10*60*1000){
    			authCode.setStatus(AuthCode.STATUS_2);
    			authCode.setUpdateTime(new Date());
        		authCodeMapper.updateByPrimaryKeySelective(authCode);
        		authCodeNew = new AuthCode();
        		authCodeNew.setCreateTime(new Date());
        		authCodeNew.setUpdateTime(new Date());
        		authCodeNew.setUserphone(mobile);
        		authCodeNew.setCode(RandomUtil.randomNumber(4));
        		authCodeNew.setSmsType(smsType);
        		authCodeNew.setStatus(AuthCode.STATUS_0);	
    		}else{
    			authCodeNew = authCode;
    		}
    	}
    	Map<String, String> buildMap = HttpClientHelper.buildMap(new String[] { "mobile","authCode" }, new String[] { mobile,authCodeNew.getCode() });
        try {
        	String result = SMS_CODE_POOL.submit(() -> {
        		if(SmsService.SMS_TYPE_0.equals(smsType)){
        			 return SmsServiceImpl.this.sendSMS(buildMap,
                             this.environment.getRequiredProperty("sms.netease.code.url"));
        		}else{
        			return SmsServiceImpl.this.sendSMSForOilCard(buildMap,
                            this.environment.getRequiredProperty("sms.netease.code.url"));
        		}
               
            }).get();
        	//对结果进行加密
        	@SuppressWarnings("unchecked")
			Map<String,Object> map = JacksonHelper.fromJSON(result, Map.class);
        	if((int)map.get("code") == 200){
        		authCodeNew.setResultCode("200");
        		authCodeNew.setResultMsg("发送成功");
        		map.put("obj", passwordEncoder.encode((String)map.get("obj")+mobile));
        	}else{
        		authCodeNew.setStatus(AuthCode.STATUS_1);
        		authCodeNew.setResultCode(String.valueOf(map.get("code")));
        		authCodeNew.setResultMsg(MESSAGE_MAPPING.get(map.get("code")));
        		map.put("msg", MESSAGE_MAPPING.get(map.get("code")));
        	}
        	if(Objects.isNull(authCodeNew.getId())){
        		authCodeMapper.insertSelective(authCodeNew);
        	}else{
        		authCodeNew.setUpdateTime(new Date());
        		authCodeMapper.updateByPrimaryKeySelective(authCodeNew);
        	}
            return DatasetBuilder.fromDataSimple(JacksonHelper.toJSON(map));
        } catch (Exception e) {
            e.printStackTrace();
            authCodeNew.setStatus(AuthCode.STATUS_1);
            authCodeNew.setResultCode("0");
    		authCodeNew.setResultMsg("发送短信接口异常");
            if(Objects.isNull(authCodeNew.getId())){
        		authCodeMapper.insertSelective(authCodeNew);
        	}else{
        		authCodeNew.setUpdateTime(new Date());
        		authCodeMapper.updateByPrimaryKeySelective(authCodeNew);
        	}
            throw new BusinessException("发送验证码短信失败", e);
        }
    }

    /**
     * 发送模板短信
     * 
     * @param templateid
     *            模板编号(由商务经理配置之后告知开发者)
     * @param mobiles
     *            接收者号码列表，JSONArray格式,如["186xxxxxxxx","186xxxxxxxx"]，限制接收者号码个数最多为100个
     * @param params
     *            短信参数列表，用于依次填充模板，JSONArray格式，如["xxx","yyy"];对于不包含变量的模板，不填此参数表示模板即短信全文内容
     * @return
     */
    public DatasetSimple<String> sendSmsTmp(String templateid, String mobiles, String params,Integer smsType) {
        try {
            return DatasetBuilder.fromDataSimple(SMS_TMP_POOL.submit(() -> {
            	if(SmsService.SMS_TYPE_0.equals(smsType)){
            		   return SmsServiceImpl.this.sendSMS(
                               HttpClientHelper.buildMap(new String[] { "templateid", "mobiles", "params" },
                                       new String[] { templateid, mobiles, params }),
                               this.environment.getRequiredProperty("sms.netease.tmp.url"));
            	}else{
            		 return SmsServiceImpl.this.sendSMSForOilCard(
                             HttpClientHelper.buildMap(new String[] { "templateid", "mobiles", "params" },
                                     new String[] { templateid, mobiles, params }),
                             this.environment.getRequiredProperty("sms.netease.tmp.url"));
            	}
             
            }).get());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("发送模板短信失败", e);
        }
    }

    /**
     * 发送短信
     * 
     * @return
     */
    private String sendSMS(Map<String, String> params, String url) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault();) {
            String nonce = RandomUtil.randomNumber(1);
            String curTime = String.valueOf(System.currentTimeMillis() / 1000L);
            String checkSum = CheckSumBuilder.getCheckSum(this.environment.getRequiredProperty("sms.netease.appsecret"),
                    nonce, curTime);
            return HttpClientHelper.httpPost(url, params,
                    HttpClientHelper.buildMap(new String[] { "AppKey", "Nonce", "CurTime", "CheckSum", "Content-Type","charset"},
                            new String[] { this.environment.getRequiredProperty("sms.netease.appkey"), nonce, curTime,
                                    checkSum, "application/x-www-form-urlencoded","utf-8"}));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParameterException("发送短信失败!", e);
        }
    }
    /**
     * 发送短信
     * 
     * @return
     */
    public String sendSMSForOilCard(Map<String, String> params, String url) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault();) {
            String nonce = RandomUtil.randomNumber(1);
            String curTime = String.valueOf(System.currentTimeMillis() / 1000L);
            String checkSum = CheckSumBuilder.getCheckSum(this.environment.getRequiredProperty("sms.netease.oilcard.appsecret"),
                    nonce, curTime);
            return HttpClientHelper.httpPost(url, params,
                    HttpClientHelper.buildMap(new String[] { "AppKey", "Nonce", "CurTime", "CheckSum", "Content-Type","charset"},
                            new String[] { this.environment.getRequiredProperty("sms.netease.oilcard.appkey"), nonce, curTime,
                                    checkSum, "application/x-www-form-urlencoded","utf-8"}));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParameterException("发送短信失败!", e);
        }
    }
    
    public static void send(JSONArray mobileArray) throws Exception{
     	 String nonce = RandomUtil.randomNumber(1);
         String curTime = String.valueOf(System.currentTimeMillis() / 1000L);
         String checkSum = CheckSumBuilder.getCheckSum("34a8ca62e2b2",
                 nonce, curTime);
         Map<String,String> param = new HashMap<>();
         //param.put("sendid", "1407");
//    	String result = HttpClientHelper.httpPost("https://api.netease.im/sms/querystatus.action", param,
//                HttpClientHelper.buildMap(new String[] { "AppKey", "Nonce", "CurTime", "CheckSum", "Content-Type","charset"},
//                        new String[] {"45b8bd6106ec44a8beef6b39f99ae3a3", nonce, curTime,
//                                checkSum, "application/x-www-form-urlencoded","utf-8"}));
       
         JSONArray params = new JSONArray();
			
         param = HttpClientHelper.buildMap(new String[] { "templateid", "mobiles", "params" },
                 new String[] { "3062206", mobileArray.toString(), params.toString()});
         System.out.println("开始发送模板短信：mobile is "+ mobileArray.toString() + " and templet is 3062206");
         String result = HttpClientHelper.httpPost("https://api.netease.im/sms/sendtemplate.action", param,
               HttpClientHelper.buildMap(new String[] { "AppKey", "Nonce", "CurTime", "CheckSum", "Content-Type","charset"},
                       new String[] {"0b5db53978ed969819803be39ae63423", nonce, curTime,
                               checkSum, "application/x-www-form-urlencoded","utf-8"}));
        
    	System.out.println(result);
    	@SuppressWarnings("unchecked")
		Map<String,Object> map = JacksonHelper.fromJSON(result, Map.class);
    	if((int)map.get("code") == 200){
    		System.out.println("ok");
    	}else{
    		System.out.println(MESSAGE_MAPPING.get(map.get("code")));
    	}
    }
    public static void main11(String[] args) throws Exception {
    	 JSONArray mobileArray = new JSONArray();
    	 mobileArray.add("17710310113");
    	 mobileArray.add("13051851080");
    	 send(mobileArray);
	}
    public static void main(String[] args) throws Exception {
    	 JSONArray mobileArray = new JSONArray();
    	 List<Map<String, Object>> data = ExcelOper.readUserExcel();
    	 int i = 0;
    	for(Map<String, Object> map : data){
    	         mobileArray.add(map.get("phone"));
    	         if(mobileArray.size()>=2){
    	        	 i++;
    	        	 System.out.println("第" + i + "次发送");
    	        	 send(mobileArray);
    	        	 mobileArray.clear();
    	        	 try {
    						Thread.sleep(2000);
    					} catch (Exception e) {
    						// TODO: handle exception
    					}
    	         }
		}
    	System.out.println("over!");
	}
}


/**
 * CheckSum算法
 * 
 * @author netease
 *
 */
class CheckSumBuilder {

    // 计算并获取checkSum
    public static String getCheckSum(String appSecret, String nonce, String curTime) {
        return encode("sha1", appSecret + nonce + curTime);
    }

    // 计算并获取md5
    public static String getMD5(String requestBody) {
        return encode("md5", requestBody);
    }

    private static String encode(String algorithm, String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };
}
