package com.util;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonToMapUtil {
    /**
     * json转换Map
     * 第一个Map为消息状态信息
     *
     * @param jsonString jsonString
     * @return list
     */
    public static List<Map<String, Object>> jsonParseMap(String jsonString, String key) {
        List<Map<String, Object>> list = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("resultCode", jsonObject.getString("resultCode"));
        resultMap.put("resultInfo", jsonObject.getString("resultInfo"));
        list.add(resultMap);
        // 实际参数
        JSONObject data = jsonObject.getJSONObject("data");
        if (data != null) {
            List params = data.getObject(key, List.class);
            if (params != null) {
                list.addAll(params);
            }
        }
        return list;
    }
    /*WebServer接口json转MAP*/
    public  static List<Map<String, Object>> JsonSAXReaderToMap(String jsonString) {
        InputStream is= null;
        try {
            is = new ByteArrayInputStream(jsonString.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SAXReader reader=new SAXReader();//dom4j  SAX解析
        Document document = null;// 生成XML文档
        try {
            document = reader.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        String text = document.getRootElement().elements().get(0).elements().get(0).elements().get(0).getText();
        List<Map<String, Object>> lists = new ArrayList<>();//有序的键值对
        JSONObject jsonObject = JSONObject.parseObject(text);//Json字符串
        Map<String, Object> resultMap = new LinkedHashMap<>();//获取json里Map值添加到指定map
        resultMap.put("resultInfo",jsonObject.getString("resultInfo"));
        resultMap.put("result",jsonObject.getString("result"));
        lists.add(resultMap);
        String result = jsonObject.getString("result");
        if ("1".equals(result)){

            String data = jsonObject.getString("data");
            List list = JSONObject.parseObject(data, List.class);
            List<Map<String,Object>> mapList = new ArrayList<>();
            for (Object datas : list) {
                Map<String,Object> item = (Map) datas;
                mapList.add(item);
            }
            System.out.println("返回状态说明:"+jsonObject.getString("resultInfo"));
           return mapList;
        }else {
            System.out.println("返回状态说明:"+jsonObject.getString("resultInfo"));
        }
        return  lists;
    }
}
