package Servlet;

import com.sun.javafx.image.BytePixelSetter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wang
 * @create 2019-09-29 20:06
 * @desc
 **/
public class jwdServlet extends HttpServlet {
    double Latitude;
    double Longitude;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Latitude = Double.valueOf(req.getParameter("Latitude")).doubleValue();
        Longitude = Double.valueOf(req.getParameter("Longitude")).doubleValue();
       /* System.out.println(Latitude);
        System.out.println(Longitude);*/
        double dis = Server.utils.getDistance(Longitude,Latitude,106.614389,29.537621);
        System.out.println(dis);
        if (dis<500){
            resp.setHeader("Statu","in");
        }else{

        }
    }
}
