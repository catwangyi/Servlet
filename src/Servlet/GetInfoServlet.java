package Servlet;

import JDBCtest.JDBCUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author wang
 * @create 2019-09-29 18:54
 * @desc
 **/
public class GetInfoServlet extends HttpServlet {
    JDBCUtils jdbcUtils;
    Connection connection;
    String id;

    List<Map<String, Object>> data = new ArrayList<>();
    @Override
    public void init() throws ServletException {
        jdbcUtils = new JDBCUtils();
        connection = jdbcUtils.getCon("faceid");
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Statement sql = connection.createStatement();
            id = req.getParameter("userid");
            resp.setContentType("text/json;charset=utf-8");
            PrintWriter out;
            ResultSet res = sql.executeQuery("select time,statu from checkinfo where id = "+"'"+id+"'"+"order by time desc limit 30");

            JSONArray jsonArray = new JSONArray();
            while (res.next()){
                String time = res.getString("time");
                String statu = res.getString("statu");
                jsonArray.put(getJsonObj(time,statu));
            }
            out = resp.getWriter();
            out.print(jsonArray);

            res.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public JSONObject getJsonObj(String time, String statu) {
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("time",time);
        jsonobj.put("statu",statu);
        return jsonobj;
    }

}
