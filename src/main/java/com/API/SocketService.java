package com.API;

import com.pojo.BasicSockteInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketService {
     /*建立Socket客户端*/
    private Socket socket;//信息发收码头
    private BufferedWriter outputStream;//带缓冲区的读取流

    /**
     * socket 实例
     *
     * @param ip      ip
     * @param portStr port
     */
    private SocketService(String ip, String portStr) {
        Integer port = Integer.parseInt(portStr);
        socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(ip, port);
        try {
            socket.connect(socketAddress, 1000);
            socket.setSoTimeout(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    /**
     * 发送json
     *
     * @param jsonString json
     * @throws IOException e
     */
    public void sendJson(String jsonString) throws IOException {
        outputStream.write(jsonString);
        outputStream.flush();
        outputStream.close();
        socket.close();
    }

    /**
     * 获取实例
     *
     * @param ip   ip
     * @param port port
     * @return socket
     * @throws IOException e
     */
    public SocketService getSocketService(String ip, String port) throws IOException {
        SocketService socketService = new SocketService(ip, port);
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "gbk"));
        return socketService;
    }

    /**
     * 获取实例
     *
     * @param displayInfo displayInfo
     * @return socket
     * @throws IOException e
     */
    public SocketService getSocketService(BasicSockteInfo displayInfo) throws IOException {
        return null;
    }

    /**
     * socket 实例
     *
     * @param displayInfo displayInfo
     */
    public SocketService(BasicSockteInfo displayInfo) throws IOException{
        Integer port = Integer.parseInt(displayInfo.getPort());
        socket = new Socket();
        SocketAddress socketAddress = new InetSocketAddress(displayInfo.getConfigIP(),port);
        socket.connect(socketAddress,10010);//链接主机，端口
        socket.setSoTimeout(2000);//设置超时时间
        outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "gbk"));//向服务器输出信息
    }

   /* public static void main(String[] args) {
        BasicSockteInfo displayInfo = new BasicSockteInfo();
        displayInfo.setConfigIP("192.168.0.15");
        displayInfo.setPort("8080");
        try {
            new SocketService(displayInfo).sendJson("自定义》》》》");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
