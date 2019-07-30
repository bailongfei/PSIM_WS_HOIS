package com.controller;

import com.API.WebServices;
import com.node.BdePersonPane;
import com.node.Pager;
import com.node.PersonAnchor;
import com.pojo.BasicBedInfo;
import com.pojo.BasicPlaceInfo;
import com.util.ConstructorBuilder;
import com.util.JsonToMapUtil;
import com.util.PropertyHelper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class WorkBedController {
    public static String roomPlaceNO;
    public  String RoomID;
    public static Stage stagebed;
    private ArrayList<BasicBedInfo> placeInfos = new ArrayList<BasicBedInfo>();
    /*获取数据添加到对象中*/
    private  ObservableList<BasicBedInfo> queueItems = FXCollections.observableArrayList();
    private Pager ps = new Pager();

    {
        ps.setPageSize(12);
        ps.setCurPage(1);
    }
    @FXML
    private VBox roombedvbox1;
    @FXML
    private VBox roombedvbox2;
    @FXML
    private VBox roombedvbox3;
    @FXML
    private VBox roombedvbox4;
    @FXML
    private VBox roombedvbox5;
    @FXML
    private VBox roombedvbox6;
    @FXML
    private VBox roombedvbox7;
    @FXML
    private VBox roombedvbox8;
    @FXML
    private VBox roombedvbox9;
    @FXML
    private VBox roombedvbox10;
    @FXML
    private VBox roombedvbox11;
    @FXML
    private VBox roombedvbox12;

    @FXML
    private TextField text1;
    @FXML
    private TextField text2;
    @FXML
    private Label syla1;
    @FXML
    private Label syla2;
    @FXML
    private Label syla3;
    @FXML
    private Label syla4;

    /*初始化*/
    @FXML
    public  void initialize() {
        init();
        bindMouseEvent();
        getWoekplaceInfo();
        //System.out.println("分页错误!"+ps.getCurPage());
        //bug 第一次在别的房间选择第二页，在点击另外房间就是参数二，然而分页在这房间只有一页
        showWorkBedQueue(queueItems,ps.getCurPage());
    }



    /*显示初始化*/
    @FXML
    private void init() {

            roombedvbox1.getChildren().clear();
            roombedvbox2.getChildren().clear();
            roombedvbox3.getChildren().clear();
            roombedvbox4.getChildren().clear();
            roombedvbox5.getChildren().clear();
            roombedvbox6.getChildren().clear();
            roombedvbox7.getChildren().clear();
            roombedvbox8.getChildren().clear();
            roombedvbox9.getChildren().clear();
            roombedvbox10.getChildren().clear();
            roombedvbox11.getChildren().clear();
            roombedvbox12.getChildren().clear();


        String[] split = PropertyHelper.properties.getProperty("banWinNo").split(",");

        for (String s : split) {
            VBox vbox=getVBox(s);
            vbox.setAlignment(Pos.CENTER);
            //vbox.getChildren().addAll(new PersonAnchor(PropertyHelper.properties.getProperty("notetitle"), "NOTE"));
        }
    }
    public VBox getVBox(String vboxNum) {
        switch (vboxNum) {
            case "1":
                return roombedvbox1;
            case "2":
                return roombedvbox2;
            case "3":
                return roombedvbox3;
            case "4":
                return roombedvbox4;
            case "5":
                return roombedvbox5;
            case "6":
                return roombedvbox6;
            case "7":
                return roombedvbox7;
            case "8":
                return roombedvbox8;
            case "9":
                return roombedvbox9;
            case "10":
                return roombedvbox10;
            case "11":
                return roombedvbox11;
            case "12":
                return roombedvbox12;
            default:
                return new VBox();
        }
    }


    /**
     * 定时器
     * 5秒
     **/
    public void time() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        init();
                        getWoekplaceInfo();
                    }
                });
            }
        },2000,9000);
    }
/*刷新*/
    /*@FXML
    public void  shuaxing() {
        init();
        getWoekplaceInfo();
    }*/

    /*获取工作站产室信息*/
    public void getWoekplaceInfo(){
        System.out.println("房间号获取工作站产室信息:"+roomPlaceNO);
        /*获取接口数据*/
        String s= null;
        try {
            s = WebServices.callBed(roomPlaceNO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> maps=JsonToMapUtil.JsonSAXReaderToMap(s);
        //List<Map<String, Object>> maps = WebServices.callPlace();
        /*把数据添加到FX容器中*/
        if(queueItems.size()!=0){
            System.out.println("是否进入方法====================");
            queueItems.clear();

            queueItems.addAll(maps.stream().map(it ->
                    new ConstructorBuilder<BasicBedInfo, String>().builderObject(new BasicBedInfo(), it))
                    .collect(Collectors.toList()));
            showWorkBedQueue(queueItems,ps.getCurPage());


        }else {
            System.out.println(queueItems.size()+":"+"是否进入方法>>>>>>>>");
            queueItems.addAll(maps.stream().map(it ->
                    new ConstructorBuilder<BasicBedInfo, String>().builderObject(new BasicBedInfo(), it))
                    .collect(Collectors.toList()));
            System.out.println(queueItems.size()+":"+"是否进入方法>>>>>>>>");
        }




    }

    /*床位工作站*/
    private void showWorkBedQueue(List<BasicBedInfo> arrayLists, int CurPage) {

                ps.setCurPage(CurPage);/*当前页*/
                //设置当前页对应的数据和总的数量
                ps.setTotalCount(arrayLists.size());
                text1.setText(String.valueOf(CurPage));
                String totalPage=String.valueOf(ps.getTotalPages());
                text2.setText(totalPage);
                System.out.println("总页数:" + ps.getTotalPages()+"\t当前页"+ps.getCurPage());
                List<BasicBedInfo> basicBedInfo=null;
                if(ps.getTotalPages()>=CurPage){//判断总页数是否大于当前页
                    basicBedInfo = arrayLists.subList((ps.getCurPage() * 12-12), (ps.getCurPage() * 12) > arrayLists.size()?arrayLists.size():(ps.getCurPage() * 12));

                }else {
                    ps.setCurPage(1);
                    basicBedInfo = arrayLists.subList((ps.getCurPage() * 12-12), (ps.getCurPage() * 12) > arrayLists.size()?arrayLists.size():(ps.getCurPage() * 12));

                }
                System.out.println("集合长度 ："+basicBedInfo.size());
                for (int j = 0; j < (basicBedInfo.size()); j++) {
                    BasicBedInfo customerInfo = basicBedInfo.get(j);
                    getVBox("" +(j+1)+ "").getChildren().add(new BdePersonPane(customerInfo.getPlaceNO(),customerInfo.getBedID(),"床位:"+customerInfo.getBedName(),"姓名:"+customerInfo.getPuerpaerName(),customerInfo.getBedStatus(),customerInfo.getDisplayTypeStatus(),customerInfo.getCustomType(),customerInfo.getDisplayStatus(),customerInfo.getPuerpaerStatus(),"BedPCS"));
                }
                ps.setList(basicBedInfo);
                //System.out.println("实体参数2:"+arrayLists.get(0).getPuerpaerName());



    }

    /*点击初始化*/
    private void bindMouseEvent() {

    }



    /*分页*/
    @FXML
    private void  shouyes(){
        //首页
        ps.setCurPage(1);
        init();
        showWorkBedQueue(queueItems,ps.getCurPage());
    }

    @FXML
    private void shangyes(){
        //上一页
        ps.setCurPage(ps.getPrev());
        init();
        showWorkBedQueue(queueItems,ps.getCurPage());
    }
    @FXML
    private  void xiayes(){
        //下一页
        ps.setCurPage(ps.getNext());
        init();
        showWorkBedQueue(queueItems,ps.getCurPage());
    }
    @FXML
    private void weiyes(){
        //尾页
        ps.setCurPage(ps.getTotalPages());
        init();
        showWorkBedQueue(queueItems,ps.getCurPage());
    }
}
