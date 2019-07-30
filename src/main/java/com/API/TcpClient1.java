package com.API;

import com.util.IniUtil;

import java.io.*;
import java.net.Socket;

public class TcpClient1 {

    public static String ClienetSocket(String Infos) {

        Socket socket=null;
        PrintWriter out=null;
        BufferedReader in=null;
        String info=null;
        try {
            socket=new Socket(IniUtil.getConfig().get("socketIP"),Integer.valueOf(IniUtil.getConfig().get("listenPort")));
            socket.setSoTimeout(8000);//设置超时时间
            PrintWriter outs = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8")),true);
            //out=new PrintWriter(socket.getOutputStream(),true);//创建写入
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));//获取服务器发送过来的数据
            outs.println(Infos);//向服务器发送消息
            info=in.readLine();   //接受服务器返回的消息
            System.out.println("服务器说:"+info);
            /*while (true){
                    outs.println(Infos);//向服务器发送消息
                    info=in.readLine();   //接受服务器返回的消息
                    System.out.println("服务器说:"+info);
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }

      return info;
    }

}
