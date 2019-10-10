package Server;

/**
 * @author wang
 * @create 2019-09-29 20:17
 * @desc
 **/
public class utils {
    private static final double PI = 3.14159265;    //老祖真理
    private static final double EARTH_RADIUS = 6378137;    //赤道半径
    private static final double RAD = Math.PI / 180.0;
    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2)
    {
        double radLat1 = lat1*RAD;
        double radLat2 = lat2*RAD;
        double a = radLat1 - radLat2;
        double b = (lng1 - lng2)*RAD;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 插入签到数据的sql
     * @return
     */

    public  static String formatSQL(String id,String time,String statu){
        String sql = "INSERT into checkinfo(id,time,statu) values ("+id+","+time+","+statu+")";
        return sql;
    }
}
