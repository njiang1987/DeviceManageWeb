package com.youzan.datashow.controller;

/**
 * Created by shenzhaohua on 16/7/20.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class packageDB {
  public static final String url = "jdbc:mysql://localhost:3306/androidapk?useUnicode=true&characterEncoding=UTF-8";
    public static final String name = "com.mysql.jdbc.Driver";
  public static final String user = "root";
  public static final String password = "";

  public Connection conn = null;
  public PreparedStatement pst = null;

  public packageDB(String sql) {
      try {
          Class.forName(name);//指定连接类型
          conn = DriverManager.getConnection(url, user, password);//获取连接
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

