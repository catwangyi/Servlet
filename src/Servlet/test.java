package Servlet;

import JDBCtest.JDBCUtils;
import Utils.EncodeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wang
 * @create 2019-08-22 18:41
 * @desc
 **/
public class test {
    public static void main(String[] args) {
       Process proc;
       String[] str = new String[] { "python", "C:\\Users\\wang\\Desktop\\test.py","C:\\Users\\wang\\Desktop\\test\\123.jpg"};
        try {
           proc = Runtime.getRuntime().exec(str);//执行py文件
           //用输入输出流来截取结果
           BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
           String line = null;
           if ((line = in.readLine())!=null){
               System.out.println(line);
           }
           in.close();
           proc.waitFor();
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}


