package Servlet;

import JDBCtest.JDBCUtils;
import Utils.PyUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author wang
 * @create 2019-10-17 12:56
 * @desc
 **/
public class RegActivity extends HttpServlet {
    JDBCUtils jdbcUtils;
    Connection connection;
    Part part;
    String rec_flag = "error";
    @Override
    public void init() throws ServletException {
        jdbcUtils = new JDBCUtils();
        connection = jdbcUtils.getCon("faceid");
    }
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp){
        String type = req.getParameter("type");
        try {
            if (type.equals("reg")){
                part=req.getPart("file");//获得图片
                InputStream is = part.getInputStream();
                String id = req.getParameter("id");
                String num = req.getParameter("regnum");
                if (!num.equals("5")){
                    if (connection==null){
                        init();
                    }
                    Statement sql = connection.createStatement();
                    ResultSet res = sql.executeQuery("select pwd,name,phone from user where id = "+id);
                    if (res.next()){
                        resp.setHeader("statu","exist");
                    }else{
                    File fatherpath = new File("C:\\Users\\wang\\Desktop\\face\\data\\"+id);
                    fatherpath.mkdirs();
                    File file = new File(fatherpath,num+".jpg");
                    System.out.println("num:"+num);
                    OutputStream os;
                    os = new FileOutputStream(file,false);
                    byte[] bytes = new byte[1024];
                    int len;
                    while ((len=is.read(bytes))>-1){
                        os.write(bytes,0,len);
                    }
                    os.close();
                    is.close();
                    resp.setHeader("statu", "success");}
                }else {
                    String pwd = req.getParameter("pwd");
                    String name = req.getParameter("name");
                    String phone = req.getParameter("phone");
                    String email = req.getParameter("email");
                    File fatherpath = new File("C:\\Users\\wang\\Desktop\\face\\data\\"+id);
                    fatherpath.mkdirs();
                    File file = new File(fatherpath,num+".jpg");
                    OutputStream os;
                    os = new FileOutputStream(file,false);
                    byte[] bytes = new byte[1024];
                    int len;
                    while ((len=is.read(bytes))>-1){
                        os.write(bytes,0,len);
                    }
                    os.close();
                    is.close();

                    if (connection==null){
                        init();
                    }
                    Statement sql = connection.createStatement();
                    ResultSet res = sql.executeQuery("select pwd,name,phone from user where id = "+id);
                    if (res.next()){
                        resp.setHeader("statu","exist");
                    }else {
                        String sb=file.getPath();
                        sb=sb.replaceAll("\\\\", "\\\\\\\\");
                        rec_flag = PyUtils.recogFace("D:\\Python\\face_recognition-test\\face_project.py","1","0","");
                        System.out.println("模型结果"+rec_flag);
                        if (rec_flag.equals("success")){
                            sql.executeUpdate(formatSQL(id,pwd,name,phone,email,sb));
                            resp.setHeader("statu", "success");
                        }else{
                        }
                    }
                }
            }else if (type.equals("update")){
                part=req.getPart("file");//获得图片
                String id = req.getParameter("id");
                String pwd = req.getParameter("pwd");
                String name = req.getParameter("name");
                String num = req.getParameter("regnum");
                String phone = req.getParameter("phone");
                String email = req.getParameter("email");
                InputStream is = part.getInputStream();
                File fatherpath = new File("C:\\Users\\wang\\Desktop\\face\\data\\"+id);
                fatherpath.mkdirs();
                File file = new File(fatherpath,num+".jpg");
                OutputStream os;
                os = new FileOutputStream(file,false);
                byte[] bytes = new byte[1024];
                int len;
                while ((len=is.read(bytes))>-1){
                    os.write(bytes,0,len);
                }
                os.close();
                is.close();

                if (connection==null){
                    init();
                }
                Statement sql = connection.createStatement();
                String sb=file.getPath();
                sb=sb.replaceAll("\\\\", "\\\\\\\\");
                int result = sql.executeUpdate(formatSQL(id,pwd,name,phone,email,sb,"0"));
                if (result==1){
                    resp.setHeader("state","success");
                }else {
                    resp.setHeader("state","error");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    public  static String formatSQL(String id,String pwd,String name, String phone, String email,String imgpath){
        id = "'"+id+"'";
        pwd = "'"+pwd+"'";
        name = "'"+name+"'";
        phone = "'"+phone+"'";
        email = "'"+email+"'";
        imgpath = "'"+imgpath+"'";
        String sql = "INSERT into user(id,pwd,name,phone,email,status,imgpath) values ("+id+","+pwd+","+name+","+phone+","+email+",'staff',"+imgpath+")";
        return sql;
    }

    public  static String formatSQL(String id,String pwd,String name, String phone, String email,String path,String forupdate){
        id = "'"+id+"'";
        pwd = "'"+pwd+"'";
        name = "'"+name+"'";
        phone = "'"+phone+"'";
        email = "'"+email+"'";
        path = "'"+path+"'";
        String sql = "update user set pwd ="+pwd+",name = "+name+",phone ="+phone+",email ="+email+",imgpath ="+path+" where id = "+id;
        return sql;
    }

}
