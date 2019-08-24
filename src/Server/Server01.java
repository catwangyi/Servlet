package Server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @author wang
 * @create 2019-08-12 15:12
 * @desc
 **/
public class Server01 {
    private ServerSocket serverSocket;
    public static void main(String[] args) {
        Server01 server01 = new Server01();
        server01.start();
    }
    //启动服务
    public void start(){
        try {
            serverSocket = new ServerSocket(80);
            receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器打开失败");
        }
    }
    //接受连接处理
    public void receive(){
        try {
            Socket client = serverSocket.accept();
            //获取请求协议
            System.out.println("一个客户端建立了连接");
            InputStream is = client.getInputStream();
            byte[] datas = new byte[1024*1024];
            int len = is.read(datas);
            String requestInfo = new String(datas,0,len);
            System.out.println(requestInfo);


            StringBuilder content = new StringBuilder();
            content.append("<html>");
            content.append("<head>");
            content.append("<title>");
            content.append("响应成功");
            content.append("</title>");
            content.append("</head>");
            content.append("<body>");
            content.append("这是内容");
            content.append("</body>");
            content.append("</html>");
            int size = content.toString().getBytes().length;//字节的长度，不是字符的长度
            StringBuilder responseInfo = new StringBuilder();
            String blank = " ";
            String CRLF="\r\n";//\r是回到首行 \n是回车
            //返回响应协议
            //1、响应行 HTTP/1.1 200 OK
            responseInfo.append("HTTP/1.1").append(blank);
            responseInfo.append(200).append(blank);
            responseInfo.append("OK").append(CRLF);
            //2、响应头（最后一行为空行）
            /*
            Date:Mon,31Dec209904:25:57GMT
            Server:shsxt Server/0.01;charset=GBK
            Content-type:text/html
            Content-length:564462
             */
            responseInfo.append("Date:").append(new Date()).append(CRLF);
            responseInfo.append("Server:").append("shsxt Server/0.01;charset=GBK").append(CRLF);
            responseInfo.append("Content-type:text/html").append(CRLF);
            responseInfo.append("Content-length:").append(size).append(CRLF);
            //最后一行为空行
            responseInfo.append(CRLF);
            //3、正文
            responseInfo.append(content.toString());

            //写出到客户端
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(),"GBK"));
            //java默认GBK
            writer.write(responseInfo.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端错误");
        }
    }
    //停止服务
    public void stop(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
