package com.util;

import java.io.*;
import java.util.Calendar;
import java.util.Properties;

public class PropertyHelper {
    /**
     * 获取自动发药窗口号
     * @return 以逗号分割的窗口号
     * **/

    public final static String windowno=getWindow();//获取窗口号 小屏

    public final static String banWinNo=getBanWinNo();//对应窗口

    public final static  String notetitle=getNoteTitle();//测试内容 请排队取药66

    public final  static  int  showTime =getShowTime();//获取刷新时间

    public static Properties properties = getProperties();
     /*加载读取properties文件*/
    public static Properties getProperties(){
        Properties properties = new Properties();
        //读取同文件夹下
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("setting.properties"));
            properties.load(inputStream);
            inputStream.close();
            if (properties.get("delayTime")==null){
                properties.setProperty("delayTime","300");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  properties;

    }

    public void setProperties(){
        try {
            FileOutputStream oFile = new FileOutputStream(new File("setting.properties"));
            System.out.println(properties);
            properties.store(oFile,"测试");

            oFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setPropertiesNext(Properties nextPropertie){
        try {
            FileOutputStream oFile = new FileOutputStream(new File("setting.properties"));
            System.out.println(nextPropertie);
            nextPropertie.store(oFile,"修改"+ Calendar.getInstance());

            oFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static String getBanWinNo(){
        try {
            //获取配置文件
            Properties properties = new Properties();
            //读取同文件夹下
            InputStream inputStream =new FileInputStream(new File("setting.properties"));
            properties.load(inputStream);
            String autoWinNos = properties.getProperty("banWinNo");
            System.out.println("---开始加载"+autoWinNos);
            inputStream.close();
            return autoWinNos;
        }catch (Exception e){
            return "";
        }

    }

    public static String getNoteTitle(){
        try {
            //获取配置文件
            Properties properties = new Properties();
            //读取同文件夹下
            InputStream inputStream =new FileInputStream(new File("setting.properties"));
            properties.load(inputStream);
            String autoWinNos = properties.getProperty("notetitle");
            System.out.println("提示"+autoWinNos);
            inputStream.close();
            return autoWinNos;
        }catch (Exception e){
            return "";
        }

    }
    /**
     * 获取刷新时间
     * @return 毫秒
     * **/
    public static int getShowTime(){
        try {
            //获取配置文件
            Properties properties = new Properties();
            //读取同文件夹下
            InputStream inputStream =new FileInputStream(new File("setting.properties"));
            properties.load(inputStream);
            int time = Integer.parseInt(properties.getProperty("showTime"));
            inputStream.close();
            return  time;
        }catch (Exception e){
            e.printStackTrace();
            return 8000;
        }
    }

    /**
     * 获取模式
     * @return 0药房条屏显示，1显示窗口
     * **/
    public static int getModel(){
        int rs;
        try{
            //获取配置文件
            Properties properties = new Properties();
            //读取同文件夹下
            InputStream inputStream =new FileInputStream(new File("setting.properties"));
            properties.load(inputStream);
            rs = Integer.parseInt(properties.getProperty("model"));
            inputStream.close();
            return rs;
        }catch (Exception e){
            return 0;
        }
    }

    public static int getLEDNO(){
        int rs;
        try{
            //获取配置文件
            Properties properties = new Properties();
            //读取同文件夹下
            InputStream inputStream =new FileInputStream(new File("setting.properties"));
            properties.load(inputStream);
            rs = Integer.parseInt(properties.getProperty("ledno"));
            inputStream.close();
            return rs;
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * 获取坐标
     * @return x,y坐标
     * **/
    public static int getXorY(String xy){
        try {
            int layoutXY;
            //获取配置文件
            Properties properties = new Properties();
            //读取同文件夹下
            InputStream inputStream =new FileInputStream(new File("setting.properties"));
            properties.load(inputStream);
            if (xy.equals("x")||xy.equals("X")){
                layoutXY = Integer.parseInt(properties.getProperty("layoutX"));
            }else{
                layoutXY = Integer.parseInt(properties.getProperty("layoutY"));
            }
            inputStream.close();
            return  layoutXY;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取窗口号
     * @return 小屏
     * **/
    public static String getWindow(){
        String rs;
        try{
            //获取配置文件
            Properties properties = new Properties();
            //读取同文件夹下
            InputStream inputStream =new FileInputStream(new File("setting.properties"));
            properties.load(inputStream);
            rs = properties.getProperty("window");
            inputStream.close();
            return rs;
        }catch (Exception e){
            return "0";
        }
    }
    //姓名脱敏
    public  static  String encodeName(String name){
        String encodedName;
        String first = "";
        String last = "";

        if (name.length() == 2 ){
            encodedName = name.substring(0,1) +"※";
        }else if (name.length()>=3){
            if (name.length()>=5){
                name = name.substring(0,5);
            }
            encodedName = name.substring(0,1)+"※"+name.substring(2,name.length());
        } else {
            encodedName = name;
        }

        return encodedName;
    }



}
