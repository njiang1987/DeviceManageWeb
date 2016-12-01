package controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.swing.*;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.sun.glass.ui.Cursor.setVisible;

/**
 * Created by shenzhaohua on 16/7/20.
 */
public class packageData {
	static packageDB db1 = null;
	static ResultSet ret = null;

	public void executePackage(String sql) {

		// 连接数据库查数据
		db1 = new packageDB(sql);// 创建packageDB对象

		try {
			db1.pst.execute();// 执行语句，得到结果集
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 执行select语句
	public int selectPackage(String sql) {
		int count = 0;
		db1 = new packageDB(sql);// 创建packageDB对象
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				count = ret.getInt(1);
			}
			ret.close();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;

	}

	// 执行select语句
	public String selectStatus(String sql) {
		String jobstatus = "";
		db1 = new packageDB(sql);// 创建packageDB对象
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				jobstatus = ret.getString(1);
			}
			ret.close();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jobstatus;

	}

	// 执行machine单个信息
	public String selectMachineInfo(String sql) {
		String machineInfo = "";
		db1 = new packageDB(sql);// 创建packageDB对象
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				machineInfo = ret.getString(1) + "|" + ret.getString(2) + "|" + ret.getString(3) + "|"
						+ ret.getString(4) + "|" + ret.getString(5) + "|" + ret.getString(6) + "|" + ret.getString(7);
			}
			ret.close();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return machineInfo;

	}

	// 查询machine修改记录信息
	public HashMap<String, String> selectMachineModifyInfo(String sql) {
		db1 = new packageDB(sql);// 创建packageDB对象
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				map.put(ret.getString(1), ret.getString(2));
			}
			ret.close();
			db1.close();// 关闭连接
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;

	}

	// 验证build状态
	public int buildStatus(String path) throws IOException {
		int buildstatus = 0;
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			String line = null;
			String str = "";
			while ((line = in.readLine()) != null) {
				str += line;
			}

			if (str.contains("Finished: SUCCESS")) {
				buildstatus = 1;
			} else if (str.contains("Finished: FAILURE")) {
				buildstatus = 2;
			}
		} catch (IOException e) {
		}
		return buildstatus;
	}

	public int getBuildNumber(String curl) throws IOException {

		String ls_str;
		int buildNumber = 0;
		Process ls_proc = Runtime.getRuntime().exec(curl);
		DataInputStream ls_in = new DataInputStream(ls_proc.getInputStream());

		try {
			while ((ls_str = ls_in.readLine()) != null) {
				if (ls_str.equals("<html>")) {
					ls_str = "0";
					break;
				}
				buildNumber = Integer.parseInt(ls_str);
			}
		} catch (IOException e) {
		}
		return buildNumber;
	}

	// 获取apk名字
	public String getApkName(String apkPath, String keyword) throws IOException {
		String apkName = "";
		try {
			File f = new File(apkPath);
			for (File temp : f.listFiles()) {
				int ind = temp.getName().indexOf(keyword);
				if (ind != -1) {
					apkName = temp.getName();

				}
			}
		} catch (Exception e) {
		}
		return apkName;
	}

	// 生成二维码
	public void apkEncode(String filename, String filePath, int buildNumber, String url)
			throws WriterException, IOException {
		String fileName = filename + ".png";
		String content = url + buildNumber + "/apk/" + filename;
		int width = 250; // 图像宽度
		int height = 250; // 图像高度
		String format = "png";// 图像类型
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵
		Path path = FileSystems.getDefault().getPath(filePath, fileName);
//		MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像
	}

	// 替换内容
	public static void replaceContentToFile(String path, String con) {
		try {
			FileReader read = new FileReader(path);
			BufferedReader br = new BufferedReader(read);
			StringBuilder content = new StringBuilder();
			StringBuilder newcontent = new StringBuilder();

			while (br.ready() != false) {
				content.append(br.readLine());
				content.append("\r\n");
			}
			String[] part1 = content.toString().split("<remote>");
			String[] part3 = part1[1].split("</remote>");
			newcontent.append(part1[0]);
			newcontent.append(con);
			newcontent.append(part3[1]);
			br.close();
			read.close();
			FileOutputStream fs = new FileOutputStream(path);
			fs.write(newcontent.toString().getBytes());
			fs.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	// 创建文件夹
	public boolean createDirectory(String directory) {
		boolean createok = false;
		File file = new File(directory);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			System.out.println("目录不存在");
			file.mkdir();
			createok = true;
		} else {
			System.out.println("目录已存在");
		}
		return createok;

	}

	// 创建文件
	public static void createFile(String fileName, String content) throws IOException {
		File file = new File(fileName);
		// 如果文件夹不存在则创建,存在就删掉 在创建
		if (!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
			file.createNewFile();
		}
		System.out.println(fileName);
		FileWriter fw = new FileWriter(fileName);
		fw.write(content);
		fw.flush();
		fw.close();

	}

}
