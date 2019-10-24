package Servlet;

import JDBCtest.JDBCUtils;
import Utils.EncodeUtils;

import java.io.*;
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
        File file = new File("C:\\Users\\wang\\Desktop\\face\\data\\2.jpg");

        String sb=file.getPath();
        sb=sb.replaceAll("\\\\", "\\\\\\\\");
        System.out.println(sb);
    }
}


