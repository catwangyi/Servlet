package Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

/**
 * @author wang
 * @create 2019-08-24 13:28
 * @desc
 **/
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp){
        try {
            Part part = req.getPart("img");
            InputStream is = part.getInputStream();
            String filename = URLDecoder.decode(req.getParameter("imgname"),"UTF-8");
            //System.out.println(filename);
            File file = new File("C:\\Users\\wang\\Desktop\\test\\"+filename);
            FileOutputStream os = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len;
            while ((len=is.read(bytes))>-1){
                os.write(bytes,0,len);
            }
            os.close();
            is.close();
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
}
