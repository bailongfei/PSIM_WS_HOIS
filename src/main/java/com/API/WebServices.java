package com.API;

import com.alibaba.fastjson.JSONObject;
import com.util.IniUtil;
import com.util.JsonToMapUtil;
import okhttp3.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebServices {
    /*登录*/
    public static String LoginService(String loginName, String loginPassWord) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "    <soap:Body>" +
                "       <ns:login xmlns:ns= \"http://NurseService.xh.com\">" +
                "        \t<Staff_LoginName>" + loginName + "</Staff_LoginName>" +
                "        \t<Staff_password>" + loginPassWord + "</Staff_password>" +
                "        </ns:login>" +
                "    </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml;charset=utf-8")
                .build();

        Response response = client.newCall(request).execute();
        String s = new String(response.body().bytes(), "utf-8");
        System.out.println(s);
        InputStream is = new ByteArrayInputStream(s.getBytes("utf-8"));
            SAXReader reader = new SAXReader();
            Document document = null;// 生成XML文档
            try {
                document = reader.read(new BufferedReader(new InputStreamReader(is, "utf-8")));
            } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document.getRootElement().elements().get(0).elements().get(0).elements().get(0).getText();
    }


    /*获取产房信息*/
    public static List<Map<String, Object>> callPlace() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "    <soap:Body>" +
                "       <ns:call_Place xmlns:ns= \"http://NurseService.xh.com\">" +
                "       </ns:call_Place>" +
                "    </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();

        Response response = client.newCall(request).execute();
        String s = new String(response.body().bytes(), "utf-8");
        System.out.println(s);
        InputStream is = new ByteArrayInputStream(s.getBytes("utf-8"));
        SAXReader reader = new SAXReader();
        Document document = null;// 生成XML文档
        try {
            document =reader.read(new BufferedReader(new InputStreamReader(is, "utf-8")));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        /*解析数组*/
        String map = document.getRootElement().elements().get(0).elements().get(0).elements().get(0).getText();
        JSONObject jsonObject = JSONObject.parseObject(map);
        String result = jsonObject.getString("result");
        List<Map<String, Object>> mapList = new ArrayList<>();
        if ("1".equals(result)) {
            System.out.println(jsonObject.getString("resultInfo"));
            String data = jsonObject.getString("data");
            List list = JSONObject.parseObject(data, List.class);

            for (Object o : list) {
                Map<String, Object> item = (Map) o;
                //System.out.println(item);
                mapList.add(item);
            }
        }
        return mapList;
    }

    /*获取床位信息*/
    public static String callBed(String PlaceID) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "   <soap:Body>" +
                "        <ns:call_Bed xmlns:ns= \"http://NurseService.xh.com\">" +
                "        \t<Place_NO>" + PlaceID + "</Place_NO>" +
                "        </ns:call_Bed>" +
                "    </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();

        Response response = client.newCall(request).execute();

        return new String(response.body().bytes(), "utf-8");
    }

    /*产妇查询*/
    public static String getPuerpaerMessage(String puerpaerName, String bedName, String puerpaerStatus) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "    <soap:Body>" +
                "        <ns:findMaternal xmlns:ns= \"http://NurseService.xh.com\">" +
                "       \t<Puerpaer_Name>" + puerpaerName + "</Puerpaer_Name>" +
                "        \t<Bed_Name>" + bedName + "</Bed_Name>" +
                "        \t<Puerpaer_Status>" + puerpaerStatus + "</Puerpaer_Status>" +
                "        </ns:findMaternal>" +
                "    </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();
        Response response = client.newCall(request).execute();
        String str = new String(response.body().bytes(), "utf-8");
        return str;
    }

    /*获取状态进度信息*/
    public static String getDisplayStatus() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n    <soap:Body>\n        <ns:call_DisplayType xmlns:ns= \"http://NurseService.xh.com\">\n        </ns:call_DisplayType>\n    </soap:Body>\n</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();

        Response response = client.newCall(request).execute();
        return new String(response.body().bytes(), "utf-8");
    }

    /*添加产妇信息*/
    public static String savePuerpaer(String name, String age, String BedId, String DisplayTypeId, String CustomType, String DisplayStatus) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "    <soap:Body>" +
                "        <ns:add_Maternal xmlns:ns= \"http://NurseService.xh.com\">" +
                "        \t<Puerpaer_Name>" + name + "</Puerpaer_Name>" +
                "        \t<Puerpaer_Sex>1</Puerpaer_Sex>" +
                "        \t<Puerpaer_Age>" + age + "</Puerpaer_Age>" +
                "        \t<Bed_ID>" + BedId + "</Bed_ID>" +
                "        \t<DisplayType_ID>" + DisplayTypeId + "</DisplayType_ID>" +
                "        \t<Custom_Type>" + CustomType + "</Custom_Type>" +
                "        \t<Custom_Broadcast>请X到护士台</Custom_Broadcast>" +
                "        \t<Puerpaer_Status>1</Puerpaer_Status>" +
                "       \t<DisplayStatus>" + DisplayStatus + "</DisplayStatus>" +
                "        </ns:add_Maternal>" +
                "    </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();

        Response response = client.newCall(request).execute();
        String s = new String(response.body().bytes(), "utf-8");
        InputStream is = new ByteArrayInputStream(s.getBytes("utf-8"));
        SAXReader reader = new SAXReader();
        Document document = null;// 生成XML文档
        try {
            document =reader.read(new BufferedReader(new InputStreamReader(is, "utf-8")));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document.getRootElement().elements().get(0).elements().get(0).elements().get(0).getText();
    }

    /*获取产妇查询参数*/
    public static String readMaternal(String bedId) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "   <soap:Body>" +
                "        <ns:read_Maternal xmlns:ns= \"http://NurseService.xh.com\">" +
                "       \t<Bed_ID>" + bedId + "</Bed_ID>" +
                "        </ns:read_Maternal>" +
                "    </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();

        Response response = client.newCall(request).execute();
        String s = new String(response.body().bytes(), "utf-8");
        InputStream is = new ByteArrayInputStream(s.getBytes("utf-8"));
        SAXReader reader = new SAXReader();
        Document document = null;// 生成XML文档
        try {
            document =reader.read(new BufferedReader(new InputStreamReader(is, "utf-8")));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document.getRootElement().elements().get(0).elements().get(0).elements().get(0).getText();
    }

    /*修改状态信息*/
    public static String updateStatusPuerpaer(String bedID, String displayId) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "   <soap:Body>" +
                "        <ns:updateMaternal xmlns:ns= \"http://NurseService.xh.com\">" +
                "        \t<Bed_ID>" + bedID + "</Bed_ID>" +
                "       \t<Display_ID>" + displayId + "</Display_ID>" +
                "        </ns:updateMaternal>" +
                "    </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();

        Response response = client.newCall(request).execute();
        String s = new String(response.body().bytes(), "utf-8");
        InputStream is = new ByteArrayInputStream(s.getBytes("utf-8"));
        SAXReader reader = new SAXReader();
        Document document = null;// 生成XML文档
        try {
            document =reader.read(new BufferedReader(new InputStreamReader(is, "utf-8")));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document.getRootElement().elements().get(0).elements().get(0).elements().get(0).getText();
    }

    /*修改自定义状态信息*/
    public static String updateCustomPuerpaer(String bedId, String customType) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "   <soap:Body>" +
                "       <ns:update_CustomType xmlns:ns= \"http://NurseService.xh.com\">" +
                "        \t<Bed_ID>" + bedId + "</Bed_ID>" +
                "        \t<Custom_Type>" + customType + "</Custom_Type>" +
                "        </ns:update_CustomType>" +
                "   </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();

        Response response = client.newCall(request).execute();
        String s = new String(response.body().bytes(), "utf-8");
        InputStream is = new ByteArrayInputStream(s.getBytes("utf-8"));
        SAXReader reader = new SAXReader();
        Document document = null;// 生成XML文档
        try {
            document =reader.read(new BufferedReader(new InputStreamReader(is, "utf-8")));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document.getRootElement().elements().get(0).elements().get(0).elements().get(0).getText();
    }

    /*修改自定义呼叫信息*/
    public static String updateCustomBroadcastPuerpaer(String bedId, String customBroadcast) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "    <soap:Body>" +
                "        <ns:update_Broadcast xmlns:ns= \"http://NurseService.xh.com\">" +
                "        \t<Bed_ID>" + bedId + "</Bed_ID>" +
                "       \t<Custom_Broadcast>" + customBroadcast + "</Custom_Broadcast>" +
                "        </ns:update_Broadcast>" +
                "    </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();

        Response response = client.newCall(request).execute();
        String s = new String(response.body().bytes(), "utf-8");
        InputStream is = new ByteArrayInputStream(s.getBytes("utf-8"));
        SAXReader reader = new SAXReader();
        Document document = null;// 生成XML文档
        try {
            document =reader.read(new BufferedReader(new InputStreamReader(is, "utf-8")));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document.getRootElement().elements().get(0).elements().get(0).elements().get(0).getText();
    }

    /*出院*/
    public static String updatePuerpaerStatus(String bedId, String puerpaerName) throws IOException {

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "   <soap:Body>" +
                "        <ns:updateStatus xmlns:ns= \"http://NurseService.xh.com\">" +
                "        \t<Bed_ID>" + bedId + "</Bed_ID>" +
                "        \t<Puerpaer_Name>" + puerpaerName + "</Puerpaer_Name>" +
                "        </ns:updateStatus>" +
                "    </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();

        Response response = client.newCall(request).execute();
        String s = new String(response.body().bytes(), "utf-8");
        InputStream is = new ByteArrayInputStream(s.getBytes("utf-8"));
        SAXReader reader = new SAXReader();
        Document document = null;// 生成XML文档
        try {
            document =reader.read(new BufferedReader(new InputStreamReader(is, "utf-8")));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document.getRootElement().elements().get(0).elements().get(0).elements().get(0).getText();
    }

    /*查询设备滚动信息*/
    public static String selectGUn(String displayIp) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "   <soap:Body>" +
                "       <ns:disPlay_Title xmlns:ns= \"http://NurseService.xh.com\">" +
                "        \t<Display_IP>" + displayIp + "</Display_IP>" +
                "        </ns:disPlay_Title>" +
                "    </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();

        Response response = client.newCall(request).execute();
        String s = new String(response.body().bytes(), "utf-8");
        InputStream is = new ByteArrayInputStream(s.getBytes("utf-8"));
        SAXReader reader = new SAXReader();
        Document document = null;// 生成XML文档
        try {
            document =reader.read(new BufferedReader(new InputStreamReader(is, "utf-8")));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document.getRootElement().elements().get(0).elements().get(0).elements().get(0).getText();
    }

    /*修改滚动*/
    public static String updateGun(String displayId, String displayTitle, String displayScrollTitle) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "    <soap:Body>" +
                "        <ns:updateDisplayTitle xmlns:ns= \"http://NurseService.xh.com\">" +
                "        \t<Display_IP>" + displayId + "</Display_IP>" +
                "       \t<Display_Title>" + displayTitle + "</Display_Title>" +
                "        \t<Display_Scroll_Text>" + displayScrollTitle + "</Display_Scroll_Text>" +
                "       </ns:updateDisplayTitle>" +
                "    </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();

        Response response = client.newCall(request).execute();
        String s = new String(response.body().bytes(), "utf-8");
        InputStream is = new ByteArrayInputStream(s.getBytes("utf-8"));
        SAXReader reader = new SAXReader();
        Document document = null;// 生成XML文档
        try {
            document =reader.read(new BufferedReader(new InputStreamReader(is, "utf-8")));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document.getRootElement().elements().get(0).elements().get(0).elements().get(0).getText();
    }

    public static String findResidueBed() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(mediaType, "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "    <soap:Body>" +
                "       <ns:find_AvailableBed xmlns:ns= \"http://NurseService.xh.com\">" +
                "        </ns:find_AvailableBed>" +
                "    </soap:Body>" +
                "</soap:Envelope>");
        Request request = new Request.Builder()
                .url("http://" + IniUtil.getConfig().get("ipAddress") + "/PSIM_WS_Maternity/services/NurseService/soap")
                .post(body)
                .addHeader("Content-Type", "application/xml")
                .build();
        Response response = client.newCall(request).execute();
        return new String(response.body().bytes(), "utf-8");
    }
    //public static void main(String[] args) throws IOException {
    //数组
       /* List<Map<String, Object>> map=toGzz();
        System.out.println(map+"参数");
        for (int i = 0; i < map.size(); i++) {
            System.out.println(map.get(i).get("Priority_Level"));
        }*/

       /*
        //解析数组
       List list = JSONObject.parseObject(map, List.class);
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Object o : list) {
            Map<String,Object> item = (Map) o;
            System.out.println(item);
            mapList.add(item);
        }*/
    /*解析Map*/
       /* String map = toCallNormal(2,"1");
        JSONObject object= JSON.parseObject(map);

        Object Priority_ID = object.get("Priority_ID");
        Object Queue_No = object.get("Queue_No");*/
    //登录测试
     /*   String maps=LoginService("4321","123");
        System.out.println("===="+maps);
        JSONObject object= JSON.parseObject(maps);
        Object result = object.get("result");
        System.out.println(result+":"+object.get("LoginName"));*/

     /*//调用接口添加到对象
        String str = "{\"result\":\"1\",\"resultInfo\":\"服务调用成功！\",\"data\":[{\"available_bed_Count\":5,\"place_Flag\":\"A1\",\"place_NO\":301,\"place_Name\":\"1号楼3层\",\"place_Note\":\"\",\"place_Status\":1,\"place_Type\":\"1\"},{\"available_bed_Count\":6,\"place_Flag\":\"A2\",\"place_NO\":302,\"place_Name\":\"1号楼3层\",\"place_Note\":\"\",\"place_Status\":1,\"place_Type\":\"1\"},{\"available_bed_Count\":6,\"place_Flag\":\"A3\",\"place_NO\":303,\"place_Name\":\"1号楼3层\",\"place_Note\":\"\",\"place_Status\":1,\"place_Type\":\"1\"}]}";
        JSONObject jsonObject = JSONObject.parseObject(str);
        String result = jsonObject.getString("result");
        if ("1".equals(result)){
            String data = jsonObject.getString("data");
            List list = JSONObject.parseObject(data, List.class);
            List<Map<String,Object>> mapList = new ArrayList<>();
            for (Object o : list) {
                Map<String,Object> item = (Map) o;
                mapList.add(item);
            }
            List<Item> available_bed_count = mapList.stream().map(it -> {
                Item item = new Item();
                item.setItemName(it.get("available_bed_Count").toString());
                return item;
            }).collect(Collectors.toList());
            System.err.println();
            System.out.println(available_bed_count);

            System.err.println();
        } else {
            System.err.println(jsonObject.getString("resultInfo"));

        }*/

        /*List<Map<String, Object>> map=callPlace();
        System.out.println(map+"参数");
        for (int i = 0; i < map.size(); i++) {
            System.out.println(map.get(i).get("place_NO"));
        }*/

    //返回多条
      /* String s=getPuerpaerMessage("小","","1");
        List<Map<String, Object>> list=JsonToMapUtil.JsonSAXReaderToMap(s);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).get("puerpaer_Name"));
        }*/

      /*//返回单条数据
      String s=updateGun("1","第一人民医院测试2","待产家属需准备的用品信息: 待产物品：手机、吸管抽纸湿巾各一包、产垫30片、婴儿被和衣服各一套、尿片5片、矿泉水3瓶、运动型饮料2瓶、面包若干、女便盆一个、面盆2个、毛巾2条");
       JSONObject jsonObject=JSON.parseObject(s);
        Object result = jsonObject.get("result");
        System.out.println(result);*/
    //  }
     /*public static void main(String[] args) {
         String s= null;
         try {
             s = findResidueBed();
         } catch (IOException e) {
             e.printStackTrace();
         }
         List<Map<String, Object>> list=JsonToMapUtil.JsonSAXReaderToMap(s);
         List<BasicPlaceInfo> available_bed_count = list.stream().map(it -> {
             BasicPlaceInfo item = new BasicPlaceInfo();
             item.setPlaceNumber(it.get("available_Count").toString());
             return item;
         }).collect(Collectors.toList());
         System.err.println();
         System.out.println(available_bed_count.get(0).getPlaceNumber());
       }*/
//    public static void main(String[] args) {
//        String s= null;
//        try {
//            s = callBed("A3301");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        List<Map<String, Object>> list=JsonToMapUtil.JsonSAXReaderToMap(s);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).get("displayStatus")+":"+list.get(i).get("custom_Type"));
//        }
//      }
}
