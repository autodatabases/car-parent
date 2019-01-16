package com.emate.shop.admin.util;

public class AutoGenSeller {
	 public static void main(String[] args) {
		 String s = "INSERT INTO `seller` VALUES (%s, '测试%s', '测试%s', NULL, '13911387%s', '广东', '江门', '开平市', '', '', 0, 0, '1', 20, '', 0, 0, 0.00, 0, 0, '', '', 3, 50, '113.282707_23.152396', '2016-12-26 10:35:20', 1, 0);";
		 String s_info = "INSERT INTO `seller_info` VALUES (%s, %s, '测试%s', '', NULL, NULL, NULL, '', '', '', '', NULL, '', '', '', '', '', '', '', '', 0, NULL, '', '', 0, '', '', 0, '2016-12-26 10:35:20', '2016-12-26 18:04:38');";
		 int initId = 213;
		 for(int i=225;i<325;i++){
			 System.out.println(String.format(s, i,i,i,i));
			 System.out.println(String.format(s_info, initId++,i,i));
		 }
	 }
}
