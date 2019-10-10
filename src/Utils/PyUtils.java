package Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author wang
 * @create 2019-10-06 22:47
 * @desc
 **/
public class PyUtils {
    public static String recogFace(String PyPath,String ImgPath){
        String result = "error";
        Process proc;
        String[] str = new String[] { "D:\\anaconda\\envs\\tensorflow\\python.exe", PyPath, ImgPath};
        try {
            proc = Runtime.getRuntime().exec(str);//执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            if ((line = in.readLine())!=null){
                if (line.equals("success")){
                    result = "success";
                }else{
                    result = "wrong";
                }
            }
            in.close();
            proc.waitFor();
        }catch (Exception e){
            e.printStackTrace();

            result = "error";
        }finally {
            return result;
        }
    }
}
