package com.util;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IniUtil {

    public static Map<String,String> getConfig(){
        Map<String, String> map;
        try {
            File file = new File("IPConfig.ini");
            if (!file.exists() && !file.isDirectory()) {
                return null;
            } else {
                map = new HashMap<>();
                Ini.Section section = new Ini(file).get("section");

                if (section!=null){
                    String ipAddress = section.get("ipAddress");
                    String SocketIP=section.get("socketIP");
                    String listenPort=section.get("listenPort");
                    String wsId=section.get("WSID");
                    String Queue_Pos=section.get("QueuePos");
                    map.put("ipAddress", ipAddress);
                    map.put("WSID",wsId);
                    map.put("Queue_Pos",Queue_Pos);
                    map.put("socketIP",SocketIP);
                    map.put("listenPort",listenPort);
                } else {
                    return null;
                }
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*public static void main(String[] args) {
        System.out.println(getConfig());
        System.out.println(getConfig().get("loginName")+":"+getConfig().get("ipAddress"));
    }*/

}
