package com.emate.shop.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author likk
 * @time 2014-7-29 下午11:21:29
 * @修改获取真实IP地址信息的方法
 */

public final class IPUtil {
	public static final String getLocalhostIP(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface intf = (NetworkInterface) en.nextElement();
				Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
				while (enumIpAddr.hasMoreElements()) {
					InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
							&& inetAddress.isSiteLocalAddress()) {
						sb.append(inetAddress.getHostAddress().toString() + "\n");
					}
				}
			}
		} catch (SocketException e) {

		}
		return sb.toString();
	}

	public static final String getIP(HttpServletRequest request) {
		/*
		 * 由于F5做负载 透传的IP都需要转发IP地址 所以先取X-Forwarded-For
		 */
		String clientIp = request.getHeader("X-Forwarded-For");
		if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getHeader("X-Real-IP");
		}
		if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getHeader("Proxy-Client-IP");
		}
		if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
			clientIp = request.getRemoteAddr();
		}
		/*
		 * 对于获取到多ip的情况下，找到公网ip.
		 */
		String sIP = null;
		if (clientIp != null && clientIp.indexOf("unknown") == -1 && clientIp.indexOf(",") > 0) {
			String[] ipsz = clientIp.split(",");
			sIP = ipsz[0].trim();
			clientIp = sIP;
		}
		if (clientIp != null && clientIp.indexOf("unknown") != -1) {
			clientIp = clientIp.replaceAll("unknown,", "");
			clientIp = clientIp.trim();
		}
		if ("".equals(clientIp) || null == clientIp) {
			clientIp = "127.0.0.1";
		}
		return clientIp;
	}

}
