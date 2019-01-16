package com.emate.shop.common;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.util.StringUtils;
import com.emate.shop.encrypt.EncrypAES;

/**
 * @author likk
 * @time 2014-7-30 上午10:04:05
 * 
 */

public final class TokenUtil {

    /** 连接符 */
    private static final String CONNECTOR      = "$";

    private static final String SPLIT          = "\\$";

    private static final long   expireDuration = 24 * 60 * 60 * 1000;

    public static final String  SYSID_ADMIN    = "admin";

    /**
     * 生成KToken
     * 
     * @param sysId
     *            发起认证平台编码
     * @param userId
     *            用户唯一标识
     * @param username
     *            用户名称
     * @param expireTime
     *            失效时间,yyyy-MM-dd HH:mm:ss
     * @param ip
     *            用户登录
     * @return null 如果生成失败
     */
    public static String generateToken(String sysId, Long userId,
            String username) {
        try {
            Date now = new Date(System.currentTimeMillis() + expireDuration);
            String expireTime = TimestampUtil.parserDateToString(now);
            // digest
            String digest = generateDigest(sysId, userId, username, expireTime);
            // auth
            StringBuilder sb = new StringBuilder();
            sb.setLength(0);
            sb.append(userId).append(CONNECTOR);
            sb.append(username).append(CONNECTOR);
            sb.append(expireTime).append(CONNECTOR);
            sb.append(digest);
            String auth = new String(EncrypAES.getIns().Encrytor(sb.toString()),
                    "utf-8");
            // token
           // return sysId + auth;
            return URLEncoder.encode(sysId + auth, "UTF-8");
            // return sysId + auth;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解释token
     * 
     * @param sysId
     * @param sysKey
     * @param token
     * @return Token,<br/>
     *         {@link Token#getStatusCode()}为200表示正确返回,不返回null值
     */
    public static Token getToken(String sysId, String token) {
        if (token == null || token.length() == 0) {
            return new Token(602);// 非法token
        }
        if (!token.startsWith(sysId)) {
            return new Token(601);// Token的生成平台与当前平台不一致
        }
        try {
        	token = unescape(token);
            token = URLDecoder.decode(token, "UTF-8");
            //token = token.substring(SYSID_ADMIN.length());
            String auth = token.substring(sysId.length());
            String mingwen = new String(
                    EncrypAES.getIns().Decryptor(auth.getBytes()));
            String[] fields = mingwen.split(SPLIT);
            // 解释token
            Token tokenObj = new Token();
            tokenObj.setSysId(sysId);
            tokenObj.setUserId(Long.parseLong(fields[0]));
            tokenObj.setUsername(fields[1]);
            tokenObj.setExpireTime(fields[2]);

            String digestFromToken = fields[3];
            if (digestFromToken == null || digestFromToken.length() == 0) {
                return new Token(602);
            }
            String digestFromGeneration = tokenObj.generateDigest();

            if (!digestFromToken.equals(digestFromGeneration)) {
                return new Token(603);
            }
            // 校验失效日期
            Date expireTime = TimestampUtil
                    .parserToDate(tokenObj.getExpireTime());
            if (System.currentTimeMillis() > expireTime.getTime()) {
                tokenObj.setStatusCode(604);
                return tokenObj;
            }
            tokenObj.setStatusCode(200);
            return tokenObj;
        } catch (Exception e) {
            e.printStackTrace();
            return new Token(604);// 解密失败
        }

    }

    /**
     * 解释token
     * 
     * @param token
     * @return Token,<br/>
     * {@link Token#getStatusCode()}为200表示正确返回,不返回null值
     */
    public static Token getTokenByCookie(HttpServletRequest request) {
        String token = CookieUtil.getCookie(request, "token");
        //        try {
        //            token = URLDecoder.decode(token, "UTF-8");
        //        } catch (UnsupportedEncodingException e1) {
        //            e1.printStackTrace();
        //            throw new RuntimeException(e1);
        //        }
        if (StringUtils.isEmpty(token)) {
            return new Token(201);
        }
        try {
            String sysId = token.substring(0, SYSID_ADMIN.length());
            //            token = token.substring(SYSID_ADMIN.length());
            return getToken(sysId, token);
        } catch (Exception e) {
            return new Token(-1);
        }
    }

    /**
     * 生成摘要,是哈希值,不可逆
     * 
     * @param sysId
     * @param userId
     * @param username
     * @param userType
     * @param passwordType
     * @param expireTime
     * @param ip
     * @return
     * @throws Exception
     */
    public static String generateDigest(String sysId, Long userId,
            String username, String expireTime) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(sysId).append(userId).append(username);
        sb.append(expireTime);
        return String.valueOf(sb.toString().hashCode());
    }

    public static void main(String[] args) {

        String s = TokenUtil.generateToken("admin", 12L, "likk");
        System.out.println(s);
        System.out.println(TokenUtil.getToken("admin","adminhWzFCV%252BPHvEPb%252BlAgROVgGO9BBgkNDEjLZhNzpvXPHl9Ge%252BeOf3YHg%253D%253D"));
        
    }
    
    public static String unescape(String src) {  
        StringBuffer tmp = new StringBuffer();  
        tmp.ensureCapacity(src.length());  
        int lastPos = 0, pos = 0;  
        char ch;  
        while (lastPos < src.length()) {  
            pos = src.indexOf("%", lastPos);  
            if (pos == lastPos) {  
                if (src.charAt(pos + 1) == 'u') {  
                    ch = (char) Integer.parseInt(src  
                            .substring(pos + 2, pos + 6), 16);  
                    tmp.append(ch);  
                    lastPos = pos + 6;  
                } else {  
                    ch = (char) Integer.parseInt(src  
                            .substring(pos + 1, pos + 3), 16);  
                    tmp.append(ch);  
                    lastPos = pos + 3;  
                }  
            } else {  
                if (pos == -1) {  
                    tmp.append(src.substring(lastPos));  
                    lastPos = src.length();  
                } else {  
                    tmp.append(src.substring(lastPos, pos));  
                    lastPos = pos;  
                }  
            }  
        }  
        return tmp.toString();  
    }  

}
