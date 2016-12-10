package com.youzan.datashow.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by shenzhaohua on 16/7/23.
 */
public class Test02 {

    //创建文件夹
    public static void createDirectory(String directory) {
        File file =new File(directory);
        //如果文件夹不存在则创建
        if  (!file .exists()  && !file .isDirectory())
        {
            System.out.println("目录不存在");
            file .mkdir();
        } else
        {
            System.out.println("目录已存在");
        }

    }

    //创建文件
    public static void createFile(String fileName,String content) throws  IOException{
        File file =new File(fileName);
        //如果文件夹不存在则创建,存在就删掉 在创建
        if(!file.exists()){
            file.createNewFile();
        } else {
            file.delete();
            file.createNewFile();
        }
        System.out.println(fileName);
        FileWriter fw=new FileWriter(fileName);
        fw.write(content);
        fw.flush();
        fw.close();

    }



    @Test
    public void test01()throws  WriterException,IOException{
        String filePath ="E:\\atest";
        String filename =filePath+"\\test123.txt";
        String content ="hello";
        createDirectory(filePath);
        createFile(filename,content);


    }
}