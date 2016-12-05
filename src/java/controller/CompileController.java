package com.youzan.datashow.controller;

import com.alibaba.fastjson.JSON;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.SplitArea;
import com.github.abel533.echarts.data.Data;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.youzan.datashow.domain.BugProjectObject;
import com.youzan.datashow.service.OfflineBugNumService;
import com.youzan.datashow.service.OfflineBussnissBugNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by shenzhaohua on 16/8/10.
 */

@Controller
public class CompileController {
  private static ArrayList<String> filelist = new ArrayList<String>();

    public void apkEncode(String filename) throws WriterException, IOException {
      String filePath = "E:\\mass\\mass\\src\\main\\webapp\\erweima\\";
      String fileName = filename+".png";
      String content ="http://192.168.12.4:8080/android_apk/"+filename;
      int width = 350; // 图像宽度
      int height = 350; // 图像高度
      String format = "png";// 图像类型
      Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
      hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
      BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
              BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
      Path path = FileSystems.getDefault().getPath(filePath, fileName);
      MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
    }
  public String getBuildNumber() throws  IOException {

    String ls_str;
    String buildNumber ="";
    Process ls_proc = Runtime.getRuntime().exec("curl  http://192.168.12.4:8081/job/SteCreate/lastBuild/buildNumber --user shenzh:shenzh");

    DataInputStream ls_in = new DataInputStream(
            ls_proc.getInputStream());

    try {
      while ((ls_str = ls_in.readLine()) != null) {
        buildNumber =ls_str;
      }
    } catch (IOException e) {
    }
    return buildNumber;
  }

  public  LinkedHashMap getFileDir(String apkPath) throws WriterException, IOException {
    LinkedHashMap<String, String> filemap=new LinkedHashMap<String, String>();
    File f = new File(apkPath);
    Format simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    List<String> Modifiedlist =new ArrayList<String>();
    String[] filelist =new String[]{};

    for(File temp : f.listFiles()) {

      Modifiedlist.add(simpleFormat.format(new Date(temp.lastModified())) + "|" + temp.getName());
    }
    Collections.sort(Modifiedlist,Collections.reverseOrder());
    for (int i=0;i<Modifiedlist.size();i++) {
      filelist =Modifiedlist.get(i).split("\\|");
      apkEncode(filelist[1]);
      filemap.put(filelist[0],filelist[1]);
    }

    return filemap;
  }

//  @RequestMapping(value = "/compile", method = RequestMethod.GET)
  @RequestMapping(value = "/compile")
  public ModelAndView showBugPerProject(@RequestParam(value = "page" ,required = false)  String page,
                                        @RequestParam(value = "compiling" ,required = false)  boolean compiling,
                                        HttpServletRequest request) throws Exception {
    ModelAndView mv = new ModelAndView("compile");
    HashMap<String, String> filemap =getFileDir("E:\\mass\\mass\\src\\main\\webapp\\android_apk\\");
    List pagelist=new ArrayList<>();
      double userNum =filemap.size();
      //页面初始的时候page没有值
      if(null == page)
      {
          page = "1";
      }

    int pagesize = (int)Math.ceil(userNum/15);
    int beginpage =(Integer.parseInt(page)-1)*pagesize;
    int endpage = beginpage+14;

    for(int i=1;i<=pagesize;i++){
      pagelist.add(i);
    }
    String buildNumber ="";
    if(compiling){
      Runtime.getRuntime().exec("curl -X GET http://192.168.12.4:8081/job/SteCreate/build?token=12345 --user shenzh:shenzh");
      buildNumber = getBuildNumber();
    }
//    System.out.print("buildNumber="+buildNumber);
             mv.addObject("buildNumber", buildNumber);
             mv.addObject("pagelist", pagelist);
             mv.addObject("pagemax",pagelist.size());
             mv.addObject("currentPage", page);
             mv.addObject("userNum", (int)userNum);
             mv.addObject("beginpage",beginpage);
             mv.addObject("endpage",endpage);
             mv.addObject("filemap",filemap);

    return mv;
  }



}
