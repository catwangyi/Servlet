package Servlet;

import JDBCtest.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wang
 * @create 2019-10-17 20:54
 * @desc
 **/
public class GetPersonInfo extends HttpServlet {
    JDBCUtils jdbcUtils;
    Connection connection;
    @Override
    public void init() throws ServletException {
        jdbcUtils = new JDBCUtils();
        connection = jdbcUtils.getCon("faceid");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("下载文件");
        OutputStream out = null;
        FileInputStream fis = null;
        String id = req.getParameter("id");
        try {
            Statement sql = connection.createStatement();
            ResultSet res = sql.executeQuery("select imgpath from user where id = "+id);
            if (res.next()){
                String imgpath = res.getString("imgpath");
                File file = new File(imgpath);
                // 2.根据获取到的路径，构建文件流对象
                fis = new FileInputStream(file);
                out = response.getOutputStream();
                // 3.设置让浏览器不进行缓存，不然会发现下载功能在opera和firefox里面好好的没问题，在IE下面就是不行，就是找不到文件
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "No-cache");
                response.setDateHeader("Expires", -1);
                // 4.设置Content-type字段
                response.setContentType("image/jpeg");
                // 5.设置http响应头，告诉浏览器以下载的方式处理我们的响应信息
                response.setHeader("content-disposition", "attachment;filename=" + file.getName());
                // 6.开始写文件
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = fis.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }

        }
    }
}
