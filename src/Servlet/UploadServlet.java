package Servlet;

import JDBCtest.JDBCUtils;
import Utils.PyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wang
 * @create 2019-08-24 13:28
 * @desc
 **/
@MultipartConfig
public class UploadServlet extends HttpServlet {
    JDBCUtils jdbcUtils;
    Connection connection;
    Part part;
    String rec_flag="error";
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void init() throws ServletException {
        jdbcUtils = new JDBCUtils();
        connection = jdbcUtils.getCon("faceid");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp){
        try {
            part=req.getPart("img");//获得图片
            String id = req.getParameter("id");
            InputStream is = part.getInputStream();
            String filename = URLDecoder.decode(req.getParameter("imgname"),"UTF-8");
            //System.out.println(filename);
            File file = new File("C:\\Users\\wang\\Desktop\\face\\test\\"+filename);
            OutputStream os = null;
            os = new FileOutputStream(file,false);
            byte[] bytes = new byte[1024];
            int len;
            while ((len=is.read(bytes))>-1){
                os.write(bytes,0,len);
            }
            os.close();
            is.close();

            //执行识别程序
            rec_flag = PyUtils.recogFace("D:\\Python\\face_recognition-test\\face_project.py","2",file.getPath(),id);
            System.out.println("rec_flag:"+rec_flag);
            if (rec_flag.equals("success")){
                try {
                    if (connection==null){
                        init();
                    }
                    Statement sql = connection.createStatement();
                    long c=System.currentTimeMillis();
                    Date date = new Date(c);
                    String cur_time = df.format(date);//format将时间对象按照格式化字符串，转化为字符串
                    //System.out.println(cur_time);
                    String statu;
                    //检查是否迟到

                    if (date.getHours()<9){
                        statu = "ONTIME";
                    }else if (date.getHours()==9){
                        if (date.getMinutes()==0){
                            statu = "ONTIME";
                        }else {
                            statu = "LATE";
                        }
                    }else{
                        statu = "LATE";
                    }

                    sql.executeUpdate(formatSQL(id,cur_time,statu));
                    resp.setHeader("statu","success");
                }catch (SQLException e){
                    System.out.println("数据库出错了");
                    e.printStackTrace();
                }

            }else {

            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UploadServlet_get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("UploadServlet_post");
    }

    public  static String formatSQL(String id,String time,String statu){
        id = "'"+id+"'";
        time = "'"+time+"'";
        statu = "'"+statu+"'";
        String sql = "INSERT into checkinfo(id,time,statu) values ("+id+","+time+","+statu+")";
        return sql;
    }
}
