package ServletTest;


import jdk.nashorn.internal.parser.JSONParser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wang
 * @create 2019-08-21 16:23
 * @desc
 **/
public class ServletTest  extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        String pwd = req.getParameter("pwd");


        resp.setHeader("statu","登录成功");
        resp.setHeader("statu","用户名不存在");
        resp.setHeader("statu","密码错误");
        System.out.println("id:"+id+"---"+"pwd:"+pwd);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().write("get");
        System.out.println("get");
    }

}
