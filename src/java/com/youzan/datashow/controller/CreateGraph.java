package com.youzan.datashow.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenzhaohua on 16/7/20.
 */
public class CreateGraph {
    static DBHelper db1 = null;
    static ResultSet ret = null;


    public String CreateGraphDo (String sql,String Graph_Title){
        //各平台未解决bug分布图生成
        double Platform_Max = 0;
        double BugNum = 0;
        String Graph_Create ="";
        String Graph_column = "";
        String Graph_rank = "";

//连接数据库查数据
        db1 = new DBHelper(sql);//创建DBHelper对象
        List xList = new ArrayList<>();
        List yList = new ArrayList<>();

        try {
            ret = db1.pst.executeQuery();//执行语句，得到结果集
            while (ret.next()) {
                xList.add(ret.getString(1));
                yList.add(ret.getString(2));

            }//显示数据
            ret.close();
            db1.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < yList.size(); i++) {
            if (Platform_Max <= Double.parseDouble(yList.get(i).toString())) {
                Platform_Max = Double.parseDouble(yList.get(i).toString());
            }
        }
//        System.out.println("Platform_Max =" + Platform_Max);

        for (int i = 0; i < yList.size(); i++) {
            BugNum = Double.parseDouble(yList.get(i).toString());
            Graph_column = Graph_column + "<td width=\"60\" height=\"220\" valign=\"bottom\">" + yList.get(i) + "<table style=\"font-size:0px;line-height:0px;background:#0080FF;overflow:hidden;bottom:3px;height:" + Math.round((BugNum / Platform_Max) * 200) + ";width:40;\"><tr><td>&nbsp;</td></tr></table></td>";
            Graph_rank = Graph_rank + "<td width=\"60\">" + xList.get(i) + "</td>";
        }

        Graph_Create = "<tr style=\"background:#e1e1e1;\" align=\"left\"><td width=\"800\">"+Graph_Title+"</td></tr><tr style=\"background:#e1e1e1;\" align=\"left\"><td width=\"500\"><table style=\"position:relative;width:200px;height:220px;border:0px solid; border-color:#e1e1e1 #e1e1e1 #e1e1e1 #e1e1e1;background:#e1e1e1;\"><tr align=\"center\">"
                + Graph_column + "</tr><tr align=\"center\">" + Graph_rank + "</tr></table></td></tr>";
        if (yList.size() ==0) {
            Graph_Create ="";
        }
            return Graph_Create;

    }
    public String ccList (String sql){
        db1 = new DBHelper(sql);//创建DBHelper对象
        String eamilList = "";

        try {
            ret = db1.pst.executeQuery();//执行语句，得到结果集
            while (ret.next()) {
                eamilList =eamilList+ret.getString(1)+",";
            }//显示数据
            ret.close();
            db1.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eamilList;

    }
}
