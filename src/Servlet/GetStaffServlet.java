package Servlet;

import JDBCtest.JDBCUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wang
 * @create 2019-10-17 19:34
 * @desc
 **/
public class GetStaffServlet extends HttpServlet {
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
        String type = req.getParameter("type");
        Statement sql = null;
        try {
            sql = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if ("select".equals(type)){
            try {
                id = req.getParameter("userid");
                resp.setContentType("text/json;charset=utf-8");
                PrintWriter out;
                ResultSet res = sql.executeQuery("select id,name,status from user where id != "+"'"+id+"'");

                JSONArray jsonArray = new JSONArray();
                while (res.next()){
                    String id = res.getString("id");
                    String name = res.getString("name");
                    String status = res.getString("status");
                    jsonArray.put(getJsonObj(id,status,name));
                }
                out = resp.getWriter();
                out.print(jsonArray);

                res.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if ("delete".equals(type)){
            try {
                id = req.getParameter("userid");
                resp.setContentType("text/json;charset=utf-8");
                int res = sql.executeUpdate("delete  from user where id = "+"'"+id+"'");
                if (res==1){
                    resp.setHeader("state","success");
                }else{
                    resp.setHeader("state","error");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if("setleader".equals(type)){
            try {
                id = req.getParameter("userid");
                resp.setContentType("text/json;charset=utf-8");
                int res = sql.executeUpdate("update user set status='leader' where id = "+"'"+id+"'");
                if (res==1){
                    resp.setHeader("state","success");
                }else{
                    resp.setHeader("state","error");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if("setstaff".equals(type)){
            try {
                id = req.getParameter("userid");
                resp.setContentType("text/json;charset=utf-8");
                int res = sql.executeUpdate("update user set status='staff' where id = "+"'"+id+"'");
                if (res==1){
                    resp.setHeader("state","success");
                }else{
                    resp.setHeader("state","error");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject getJsonObj(String id, String status, String name) {
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("id",id);
        jsonobj.put("name",name);
        jsonobj.put("status",status);
        return jsonobj;
    }
}
