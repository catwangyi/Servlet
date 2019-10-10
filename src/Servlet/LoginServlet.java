package Servlet;


import JDBCtest.JDBCUtils;
import Utils.EncodeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StringContent;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wang
 * @create 2019-08-21 16:23
 * @desc
 **/
public class LoginServlet extends HttpServlet {
    JDBCUtils jdbcUtils;
    Connection connection;
    @Override
    public void init() throws ServletException {
         jdbcUtils = new JDBCUtils();
         connection = jdbcUtils.getCon("faceid");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //中文乱码待解决！！！
        resp.setCharacterEncoding("UTF-8");

        String et_id = req.getParameter("id");
        String et_pwd = req.getParameter("pwd");
        try {
            if (connection==null){
                init();
            }
            Statement sql = connection.createStatement();
            ResultSet res = sql.executeQuery("select pwd,name,phone from user where id = "+et_id);
            if (res.next()){
                if (et_pwd.equals(res.getString("pwd"))){
                    resp.setHeader("Statu","Success");
                    String name = res.getString("name");
                    resp.setHeader("Name",URLEncoder.encode(name,"utf-8"));
                    resp.setHeader("Phone",res.getString("phone"));
                    resp.setHeader("userid",et_id);
                    //System.out.println("Success");
                }else{
                    resp.setHeader("Statu","WrongPWD");
                    System.out.println("WrongPWD");
                }
            }else{
                resp.setHeader("Statu","NoSuchID");
                System.out.println("NoSuchID");
            }
            res.close();
        }catch (Exception e){
            System.out.println("出错了");
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String test = "get";
        resp.getWriter().write(test);
        System.out.println("get");
    }

}
