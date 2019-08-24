package JDBCtest;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author wang
 * @create 2019-08-14 13:45
 * @desc jdbc连接测试
 **/
public class JDBCUtils {
    private final String DBName = "faceid";
    public Connection getCon(){
        Connection con = null;
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            System.out.println("驱动连接成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+DBName+"?useSSL=false","root","mysql");
            System.out.println("数据库连接成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }

}
