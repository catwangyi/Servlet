package Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author wang
 * @create 2019-10-06 22:47
 * @desc
 **/
public class PyUtils {
    public static String recogFace(String PyPath,String statu,String ImgPath,String id){
        String result = new String();
        Process proc;
        String[] str = new String[] { "D:\\anaconda\\envs\\dlib\\python.exe", PyPath, statu,ImgPath,id};
        try {
            proc = Runtime.getRuntime().exec(str);//执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine())!=null){
                System.out.println("line:"+line);
                if (line.equals("success")){
                    result = "success";
                }else if(line.equals("wrong")){
                    result = "wrong";
                }else if(line.equals("noface")){
                    result = "noface";
                }
            }
            in.close();
            proc.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

}
