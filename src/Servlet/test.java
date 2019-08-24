package Servlet;

import JDBCtest.JDBCUtils;
import Utils.EncodeUtils;

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
        String test = "测试";
        System.out.println(EncodeUtils.getEncoding(test));
    }
}


