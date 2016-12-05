package com.youzan.datashow.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.youzan.datashow.controller.packageDB;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by adrian on 16/5/17.
 */
@Service
public class PackageListService {
    static packageDB db1 = null;
    static ResultSet ret = null;

    //获取list
    public String resultSetToJson(ResultSet rs) throws SQLException,JSONException
    {
        // json数组
        JSONArray array = new JSONArray();

        // 获取列数
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // 遍历ResultSet中的每条数据
        while (rs.next()) {
            JSONObject jsonObj = new JSONObject();

            // 遍历每一列
            for (int i = 1; i <= columnCount; i++) {
                String columnName =metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value);
            }
            array.add(jsonObj);
        }

        return array.toString();
    }

    public String  getlist(String sql)throws  SQLException,IOException {
        db1 = new packageDB(sql);//创建DBHelper对象
        ret = db1.pst.executeQuery();//执行语句，得到结果集
        return resultSetToJson(ret);
    }

}
