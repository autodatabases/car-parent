package com.emate.shop.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.emate.shop.exception.ParameterException;

public class HttpClientHelper {

    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            .setConnectTimeout(50000).setSocketTimeout(50000)
            .setConnectionRequestTimeout(50000).build();

    public static Map<String, String> buildMap(String[] keys, String[] values) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

    public static String httpPost(String url, Map<String, String> params,
            Map<String, String> requestHeaders) {
        try {
            CloseableHttpClient httpClient = PoolManager.getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(REQUEST_CONFIG);
            Stream.of(requestHeaders).filter(Objects::nonNull)
                    .flatMap(map -> map.entrySet().stream())
                    .forEach(entry -> httpPost.addHeader(entry.getKey(),
                            entry.getValue()));
/*            httpPost.setEntity(
                    new UrlEncodedFormEntity(buildNameValuePairs(params)));*/
            httpPost.setEntity(new UrlEncodedFormEntity(buildNameValuePairs(params),"utf-8"));
            httpPost.addHeader("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String result = EntityUtils.toString(httpResponse.getEntity(),
                    "utf-8");
            Log4jHelper.getLogger().debug(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParameterException("http post exception " + url, e);
        }
    }

    public static String urlReplace(String url, Map<String, String> getParams) {
        StringBuilder stringBuilder = new StringBuilder(url);
        getParams.entrySet().stream().forEach(entry -> {
            String value = "";
            try {
                value = URLEncoder.encode(entry.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            String temp = stringBuilder.toString();
            temp = temp.replace(entry.getKey(), value);
            stringBuilder.replace(0, stringBuilder.length(), temp);
        });
        return stringBuilder.toString();
    }

    public static String httpPost(String url, Map<String, String> params) {
        return httpPost(url, params, null);
    }

    public static String httpGet(String url,
            Map<String, String> requestHeaders) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(REQUEST_CONFIG);
            Stream.of(requestHeaders).filter(Objects::nonNull)
                    .flatMap(map -> map.entrySet().stream())
                    .forEach(entry -> httpGet.addHeader(entry.getKey(),
                            entry.getValue()));
            httpGet.addHeader("Content-Type",
                    "application/x-www-form-urlencoded;charset=utf-8");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            return EntityUtils.toString(httpResponse.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParameterException("http get exception " + url, e);
        }
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
//    	String url = "http://cvx-sh.com/vehicle/getVehicleByCarBrandName.do";
//		String url1 = "http://cvx-sh.com/vehicle/getAllCarBrands.do";
//		Map<String, String> params = new HashMap<String,String>();
//		System.out.println( URLEncoder.encode("奥迪","utf-8"));
//		params.put("brand", URLEncoder.encode(URLEncoder.encode("奥迪","utf-8"),"utf-8"));
//    	System.out.println(httpPost(url,params));
	}

    public static String httpGet(String url) {
        return httpGet(url, null);
    }

    private static List<NameValuePair> buildNameValuePairs(
            Map<String, String> params) {
        return Stream.of(params).filter(Objects::nonNull)
                .flatMap(map -> map.entrySet().stream())
                .map(entry -> new BasicNameValuePair(entry.getKey(),
                        entry.getValue()))
                .collect(Collectors.toList());
    }
}

class PoolManager {

    public static PoolingHttpClientConnectionManager clientConnectionManager = null;

    private int                                      maxTotal                = 50;

    private int                                      defaultMaxPerRoute      = 25;

    private PoolManager(int maxTotal, int defaultMaxPerRoute) {
        this.maxTotal = maxTotal;
        this.defaultMaxPerRoute = defaultMaxPerRoute;
        clientConnectionManager.setMaxTotal(maxTotal);
        clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
    }

    private PoolManager() {
        clientConnectionManager.setMaxTotal(maxTotal);
        clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
    }

    private static PoolManager poolManager = null;

    public synchronized static PoolManager getInstance() {
        if (poolManager == null) {
            clientConnectionManager = new PoolingHttpClientConnectionManager();
            poolManager = new PoolManager();

        }
        return poolManager;
    }

    public synchronized static PoolManager getInstance(int maxTotal,
            int defaultMaxPerRoute) {
        if (poolManager == null) {
            poolManager = new PoolManager(maxTotal, defaultMaxPerRoute);
        }

        return poolManager;
    }

    public static CloseableHttpClient getHttpClient() {
        if (clientConnectionManager == null) {
            clientConnectionManager = new PoolingHttpClientConnectionManager();
            getInstance();
        }
        return HttpClients.custom()
                .setConnectionManager(clientConnectionManager).build();
    }
}
