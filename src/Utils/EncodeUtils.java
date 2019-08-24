package Utils;

import java.io.UnsupportedEncodingException;

/**
 * @author wang
 * @create 2019-08-24 14:27
 * @desc
 **/
public class EncodeUtils {
    public static String getEncoding(String str)
    {
        String encode;

        encode = "UTF-16";
        try
        {
            if(str.equals(new String(str.getBytes(), encode)))
            {
                return "它是"+encode;
            }
        }
        catch(Exception ex) {}

        encode = "ASCII";
        try
        {
            if(str.equals(new String(str.getBytes(), encode)))
            {
                return "字符串<< " + str + " >>中仅由数字和英文字母组成，无法识别其编码格式";
            }
        }
        catch(Exception ex) {}

        encode = "ISO-8859-1";
        try
        {
            if(str.equals(new String(str.getBytes(), encode)))
            {
                return "它是"+encode;
            }
        }
        catch(Exception ex) {}

        encode = "GB2312";
        try
        {
            if(str.equals(new String(str.getBytes(), encode)))
            {
                return "它是"+encode;
            }
        }
        catch(Exception ex) {}

        encode = "UTF-8";
        try
        {
            if(str.equals(new String(str.getBytes(), encode)))
            {
                return "它是"+encode;
            }
        }
        catch(Exception ex) {}

        /*
         *......待完善
         */

        return "未识别编码格式";
    }
    public static String GB2312toUTF8(String s){
        String str= null;
        try {
            str = new String(s.getBytes("GBK"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
