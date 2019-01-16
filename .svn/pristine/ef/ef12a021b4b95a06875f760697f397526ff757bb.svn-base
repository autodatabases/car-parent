package com.emate.tools;

  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;  
  
public class DBHelper {  
    public static String url = "jdbc:mysql://gd.emateglobal.com:3306/ematecar?characterEncoding=utf-8&amp;useSSL=false";  
    public static String url1 = "jdbc:mysql://car.emates.cn:3306/ematecar?characterEncoding=utf-8&amp;useSSL=false";  
    
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "root";  
    public static final String password = "ikj34sM";//likkd  ikj34sM
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
  
    public DBHelper(String sql) {  
        try {  
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url1, user, password);//获取连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }
}  