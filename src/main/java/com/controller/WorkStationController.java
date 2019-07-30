package com.controller;

import com.API.WebServices;
import com.node.Alert;
import com.node.Pager;
import com.node.PersonAnchor;
import com.node.QueuePuerpaerAnchor;
import com.pojo.*;
import com.util.ConstructorBuilder;
import com.util.JsonToMapUtil;
import com.util.PageUtil;
import com.util.PropertyHelper;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class WorkStationController {
    public static Stage stage;
    public static List<BasicDisplay> displayinfo=null;
    public static List<BasicDisplay> displayInfos;
    public static BasicStaff StaffInfo;
    public static String loginName1;
    private Timer queueTimer = new Timer();
    private Item item=new Item();
    private ArrayList<BasicPlaceInfo> placeInfos = new ArrayList<BasicPlaceInfo>();
    /*获取数据添加到对象中*/
   private ObservableList<BasicPlaceInfo> queueItems = FXCollections.observableArrayList();
    public static ObservableList<PlaceInfo> placeInfo = FXCollections.observableArrayList();
   /*获取产妇信息*/
    public ObservableList<BasicPuerpaerInfo> queueItemss = FXCollections.observableArrayList();
    private static Pager p = new Pager();
    private static Pager pr = new Pager();
    static{
        p.setPageSize(12);
        p.setCurPage(1);
        pr.setPageSize(12);
        pr.setCurPage(1);
    }
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label4;
    @FXML
    private Button buttontop;

    @FXML
    private TextField textfield1;
    @FXML
    private TextField textfield2;

    @FXML
    private ComboBox<Item> combobox1;
    // 可编辑状态
    @FXML
    private Button chaxunpp;


    @FXML
    private HBox hbox1;
    @FXML
    private HBox hbox2;
    @FXML
    private HBox hbox3;
    @FXML
    private HBox hbox4;

   @FXML
   private Button gundongbtn;



   @FXML
   private VBox roomvbox11;
   @FXML
   private VBox roomvbox12;

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

    @FXML
   private Pagination pagein;
    /**
     * 初始化
     */
    @FXML
    private void initialize() {
        init();
        bindMouseEvent();
        //handleAutoUpdateQueue();
        label4.setText(loginName1);
        getWoekplaceInfo();
        showWorkupdateQueue(queueItems,p.getCurPage());
        setItem();
        getWorkStationInfo();

    }

    private void init() {
        showTime();
        hbox1.getChildren().clear();
        hbox2.getChildren().clear();
        hbox3.getChildren().clear();
        hbox4.getChildren().clear();

        String[] split = PropertyHelper.properties.getProperty("banWinNo").split(",");

        for (String s : split) {
            HBox hbox=getHBox(s);
                 hbox.setAlignment(Pos.CENTER);
            //vbox.getChildren().addAll(new PersonAnchor(PropertyHelper.properties.getProperty("notetitle"), "NOTE"));
        }
        // ip
        /*String info = IPUtils.getIPAddress() + "   " +
                IPUtils.getMACAddress() + "   " +
                workStation.getWsName() + "  " +
                StaffInfo.getStaffLoginName() + "  ";

        infoLabel.setText(info);*/

    }

    public HBox getHBox(String hboxNum) {
        switch (hboxNum) {
            case "1":
                return hbox1;
            case "2":
                return hbox2;
            case "3":
                return hbox3;
            case "4":
                return hbox4;
            default:
                return new HBox();
        }
    }


    /**
     * 设置下拉框
     *
     *
     */
    private void setItem() {

        ObservableList<Item> list = FXCollections.observableArrayList();
            list.add(new Item("1","在院"));
            //list.add(new Item("0","出院"));
        combobox1.setItems(list);
        combobox1.getSelectionModel().select(0);

    }

    /**
     * 定时更新队列列表
     * 延迟30秒，每30秒更新
     */
   /* private void handleAutoUpdateQueue() {
        queueTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateQueue());
            }
        }, 1, 1000 * 30);
    }*/

   /* public  void pagetime(){

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        //下一页
                        p.setCurPage(p.getNext());
                        init();
                        showWorkupdateQueue(queueItems,p.getCurPage());
                    }
                });
            }
        }, 1000, PropertyHelper.getShowTime());
    };*/
/*获取工作站产室信息*/
    private void getWoekplaceInfo(){
        /*获取接口数据*/
        List<Map<String, Object>> maps = null;
        try {
            maps = WebServices.callPlace();


        /*把数据添加到FX容器中*/
        queueItems.addAll(
                maps.stream().map(it ->
                        new ConstructorBuilder<BasicPlaceInfo, String>().builderObject(new BasicPlaceInfo(), it))
                        .collect(Collectors.toList()));
        //ObservableList<BasicPlaceInfo> queueItem = FXCollections.observableArrayList();
        /*for (int i = 0; i < queueItems.size(); i++) {
            String statusTypeName = queueItems.get(i).getPlaceNO();
            System.out.println(statusTypeName);
        }*/
        /*获取床位剩余*/
        String s= null;
        try {
            s = WebServices.findResidueBed();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> listnumber=JsonToMapUtil.JsonSAXReaderToMap(s);
        placeInfo.addAll(
                listnumber.stream().map(it ->
                        new ConstructorBuilder<PlaceInfo, String>().builderObject(new PlaceInfo(), it))
                        .collect(Collectors.toList()));
        /*把数据添加到实体*//*
        List<PlaceInfo> available_bed_count = listnumber.stream().map(it -> {
            PlaceInfo placeInfo = new PlaceInfo();
            placeInfo.setPlaceNO(it.get("place_NO").toString());
            placeInfo.setPlaceNumber(it.get("available_Count").toString());
            return placeInfo;
        }).collect(Collectors.toList());
        System.out.println("获取床位剩余:"+available_bed_count);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*获取产妇信息*/
   /* private void getPuerpaerInfo(){
        String selectedIndex = String.valueOf(combobox1.getSelectionModel().getSelectedIndex());//获取下拉框ID
        //获取接口数据
        String mapp=null;
        try {
            mapp=WebServices.getPuerpaerMessage(textfield1.getText(),textfield2.getText(),selectedIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> maps = JsonToMapUtil.JsonSAXReaderToMap(mapp);
        //把数据添加到FX容器中
        queueItemss.addAll(
                maps.stream().map(it ->
                        new ConstructorBuilder<BasicPuerpaerInfo, String>().builderObject(new BasicPuerpaerInfo(), it))
                        .collect(Collectors.toList()));
        System.out.println("查询:"+queueItemss.get(0).getPuerpaerName());
    }*/



    /*工作站*/
    private void showWorkupdateQueue(List<BasicPlaceInfo> arrayList,int CurPage) {

        p.setCurPage(CurPage);/*当前页*/
        //设置当前页对应的数据和总的数量
        p.setPageSize(12);/*//每页条数*/
        p.setTotalCount(arrayList.size());
        System.out.println("总页数:" + p.getTotalPages()+"\t当前页"+p.getCurPage());
        text1.setText(String.valueOf(CurPage));
        String totalPage=String.valueOf(p.getTotalPages());
        System.out.println("总页数:"+totalPage);
        text2.setText(totalPage);
        List<BasicPlaceInfo> clinicJhlishes = arrayList.subList((p.getCurPage() * 12-12), (p.getCurPage() * 12) > arrayList.size() ? arrayList.size() : (p.getCurPage() * 12));
        System.out.println("集合长度 ："+clinicJhlishes.size());

        for (int i = 0; i < (clinicJhlishes.size()); i++) {
            BasicPlaceInfo customerInfo = clinicJhlishes.get(i);
            //System.out.println(customerInfo.getPlaceNO()+":"+customerInfo.getAvailablebedCount());
            //getHBox("" +(i+1)+ "").getChildren().add(new PersonAnchor("房间号:"+customerInfo.getPlaceFlag()+""+customerInfo.getPlaceNO(),customerInfo.getPlaceNote(),"可用床位:"+customerInfo.getAvailablebedCount(),customerInfo.getPlaceStatus(),"PC"));
            if(i<3){
                getHBox("1").getChildren().add(new PersonAnchor("房间号:"+customerInfo.getPlaceNO(),customerInfo.getPlaceNote(),"总床位:"+customerInfo.getAvailablebedCount(),customerInfo.getPlaceStatus(),"PC"));
            }else if(i>=3&&i<6){
                getHBox("2").getChildren().add(new PersonAnchor("房间号:"+customerInfo.getPlaceNO(),customerInfo.getPlaceNote(),"总床位:"+customerInfo.getAvailablebedCount(),customerInfo.getPlaceStatus(),"PC"));
            }else if (i>=6&&i<9){
                getHBox("3").getChildren().add(new PersonAnchor("房间号:"+customerInfo.getPlaceNO(),customerInfo.getPlaceNote(),"总床位:"+customerInfo.getAvailablebedCount(),customerInfo.getPlaceStatus(),"PC"));
            }else if (i>=9){
                getHBox("4").getChildren().add(new PersonAnchor("房间号:"+customerInfo.getPlaceNO(),customerInfo.getPlaceNote(),"总床位:"+customerInfo.getAvailablebedCount(),customerInfo.getPlaceStatus(),"PC"));
            }
        }
        p.setList(clinicJhlishes);

       /* pagein.setPageCount(p.getTotalPages()+1);//总页数
        pagein.setCurrentPageIndex(CurPage);//定位当前页
        pagein.setMaxPageIndicatorCount(5);//最大显示页*/
        System.out.println("实体参数:"+arrayList.get(0).getPlaceNO());
    }

   /*查询产妇信息*/
   private void showWorkPuerpaer(List<BasicPuerpaerInfo> arrayList, int CurPage) {
       System.out.println("查询产妇信息:"+arrayList.get(0).getPuerpaerName());
       pr.setCurPage(CurPage);/*当前页*/
       //设置当前页对应的数据和总的数量
       pr.setPageSize(12);/*//每页条数*/
       pr.setTotalCount(arrayList.size());
       System.out.println("总页数:" + pr.getTotalPages()+"\t当前页"+pr.getCurPage());
       text1.setText(String.valueOf(CurPage));
       String totalPage=String.valueOf(pr.getTotalPages());
       System.out.println("总页数:"+totalPage);
       text2.setText(totalPage);
       List<BasicPuerpaerInfo> clinicJhlishes = arrayList.subList((pr.getCurPage() * 12-12), (pr.getCurPage() * 12) > arrayList.size() ? arrayList.size() : (pr.getCurPage() * 12));

       for (int i = 0; i < (clinicJhlishes.size()); i++) {
           BasicPuerpaerInfo customerInfo = clinicJhlishes.get(i);
           System.out.println(customerInfo.getPlaceNO()+":"+customerInfo.getPuerpaerName());
           //getHBox("" +(i+1)+ "").getChildren().add(new PersonAnchor("房间号:"+customerInfo.getPlaceFlag()+""+customerInfo.getPlaceNO(),customerInfo.getPlaceNote(),"可用床位:"+customerInfo.getAvailablebedCount(),customerInfo.getPlaceStatus(),"PC"));
           if(i<3){
               getHBox("1").getChildren().add(new QueuePuerpaerAnchor("房间号:"+customerInfo.getPlaceNO(),"床位号:"+customerInfo.getBedName(),customerInfo.getBedID(),customerInfo.getBedStatus(),customerInfo.getPuerpaerName(),customerInfo.getDisplayTypeStatus(),customerInfo.getCustomType(),customerInfo.getDisplayStatus(),"queuePC"));
           }else if(i>=3&&i<6){
               getHBox("2").getChildren().add(new QueuePuerpaerAnchor("房间号:"+customerInfo.getPlaceNO(),"床位号:"+customerInfo.getBedName(),customerInfo.getBedID(),customerInfo.getBedStatus(),customerInfo.getPuerpaerName(),customerInfo.getDisplayTypeStatus(),customerInfo.getCustomType(),customerInfo.getDisplayStatus(),"queuePC"));
           }else if (i>=6&&i<9){
               getHBox("3").getChildren().add(new QueuePuerpaerAnchor("房间号:"+customerInfo.getPlaceNO(),"床位号:"+customerInfo.getBedName(),customerInfo.getBedID(),customerInfo.getBedStatus(),customerInfo.getPuerpaerName(),customerInfo.getDisplayTypeStatus(),customerInfo.getCustomType(),customerInfo.getDisplayStatus(),"queuePC"));
           }else if (i>=9){
               getHBox("4").getChildren().add(new QueuePuerpaerAnchor("房间号:"+customerInfo.getPlaceNO(),"床位号:"+customerInfo.getBedName(),customerInfo.getBedID(),customerInfo.getBedStatus(),customerInfo.getPuerpaerName(),customerInfo.getDisplayTypeStatus(),customerInfo.getCustomType(),customerInfo.getDisplayStatus(),"queuePC"));
           }
       }
       pr.setList(clinicJhlishes);

       /* pagein.setPageCount(p.getTotalPages()+1);//总页数
        pagein.setCurrentPageIndex(CurPage);//定位当前页
        pagein.setMaxPageIndicatorCount(5);//最大显示页*/
       System.out.println("实体参数:"+arrayList.get(0).getPlaceNO());
   }
/*获取工作站信息*/
    private void getWorkStationInfo() {

    }
    /**
     * 绑定按钮鼠标事件
     */
    private void bindMouseEvent() {

        combobox1.getSelectionModel().selectedItemProperty().addListener(event -> {//监听事件
            String selected1 = String.valueOf(combobox1.getSelectionModel().getSelectedItem().getItemID());//获取下拉框ID
                 if ("0".equals(selected1)){
                     textfield2.setDisable(true);
                 }else {
                     textfield2.setDisable(false);
                 }
        });

        buttontop.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                /*窗口销毁*/
             /* Stage stage= (Stage) buttontop.getScene().getWindow();
               stage.close();*/
               /*退出程序*/
              //    Platform.exit();
                System.exit(0);
            }
        });
        /*查询*/
        chaxunpp.setOnMouseClicked(event -> {
                    if (MouseButton.PRIMARY.equals(event.getButton())) {
                         queueItemss.clear();
                          //init();
                        String text1=textfield1.getText();String text2=textfield2.getText();
                        if("".equals(text1)&&"".equals(text2)||text1.equals(null)&&text2.equals(null)){
                            Alert.notifyWarning("提示","请输入条件查询数据!", stage);
                        }else {
                            String selectedIndex = String.valueOf(combobox1.getSelectionModel().getSelectedItem().getItemID());//获取下拉框ID

                                    /*获取接口数据*/
                                    System.out.println("获取输入数据:"+textfield1.getText()+":"+textfield2.getText()+":"+selectedIndex);

                                    String mapp=null;
                                    try {
                                        mapp=WebServices.getPuerpaerMessage(text1,text2,selectedIndex);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    List<Map<String, Object>> maps = JsonToMapUtil.JsonSAXReaderToMap(mapp);
                                    //*把数据添加到FX容器中*//
                                    queueItemss.addAll(
                                            maps.stream().map(it ->
                                                    new ConstructorBuilder<BasicPuerpaerInfo, String>().builderObject(new BasicPuerpaerInfo(), it))
                                                    .collect(Collectors.toList()));
                                    QueuePuerpaerAnchor.zhaiyuanZT=selectedIndex;
                                    if(queueItemss.size()!=0){
                                        System.out.println("查询名:"+queueItemss.get(0).getPuerpaerName());
                                        init();

                                        showWorkPuerpaer(queueItemss,pr.getCurPage());
                                    }else{
                                        if(Integer.valueOf(selectedIndex).equals(1)){
                                            Alert.notifyWarning("提示","在院暂无此人!", stage);
                                        }else {
                                            Alert.notifyWarning("提示","出院暂无此人!", stage);
                                        }

                                    }


                                }


                    }
        });

        /*roomvbox11.setOnMouseClicked(event -> {
            Stage stage=new Stage();
            if (MouseButton.PRIMARY.equals(event.getButton())){
                FXMLLoader fxmlloader=new FXMLLoader();
                fxmlloader.setLocation(WorkStationController.class.getResource("fxml/properties.fxml"));
                try {
                    AnchorPane anchorPane=fxmlloader.load();//布局面板加载fxml路经
                    Scene scene=new Scene(anchorPane);//布局添加到场景
                    stage.setScene(scene);//场景添加到舞台
                    stage.setResizable(false);//窗口不可变
                    stage.getIcons().add(new Image(WorkStationController.class.getResourceAsStream("/static/xhIcon.png")));//添加窗口图标
                    stage.setTitle("xxx器");
                    //stage.setOnCloseRequest(event -> Platform.runLater(() -> System.exit(0)));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/
        /*gundongbtn.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton())){

            }
        });*/
    }
    /**
     * 4秒内不能重复点击
     *
     * @param button btn
     */
    private void setButtonDisable(Button button) {
        Platform.runLater(() -> button.setDisable(true));
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> button.setDisable(false));
        });
        thread.start();
    }
   /*设置滚动信息*/
    @FXML
    private void onclickToProperties() throws Exception {
        Stage stage = new Stage();
        //Properties.savestage=stage;
        FXMLLoader fxmlloader=new FXMLLoader();
        fxmlloader.setLocation(WorkStationController.class.getResource("/fxml/properties.fxml"));
        try {
            AnchorPane anchorPane=fxmlloader.load();//布局面板加载fxml路经
            Scene scene=new Scene(anchorPane);//布局添加到场景
            stage.setScene(scene);//场景添加到舞台
            stage.setResizable(false);//窗口不可变
            stage.setAlwaysOnTop(true);//设置窗口在上面
            stage.initModality(Modality.APPLICATION_MODAL);//模态框弹出一次
            stage.getIcons().add(new Image(WorkStationController.class.getResourceAsStream("/static/xhIcon.png")));//添加窗口图标
            stage.setTitle("配置工具");
            stage.setX(400);
            stage.setY(100);
            //stage.setOnCloseRequest(event -> Platform.runLater(() -> System.exit(0)));//关闭窗体结束
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /*分页*/
    @FXML
    private void  shouye(){
       //首页
        p.setCurPage(1);
        pr.setCurPage(1);
        init();
        if(queueItemss.size()!=0){
            showWorkPuerpaer(queueItemss,pr.getCurPage());
        }else{
            showWorkupdateQueue(queueItems,p.getCurPage());
        }


    }

    @FXML
    private void shangye(){
        //上一页
        p.setCurPage(p.getPrev());
        pr.setCurPage(pr.getPrev());
        init();
        if(queueItemss.size()!=0){
            showWorkPuerpaer(queueItemss,pr.getCurPage());
        }else {
            showWorkupdateQueue(queueItems,p.getCurPage());
        }


    }
    @FXML
    private  void xiaye(){
        //下一页
        p.setCurPage(p.getNext());
        pr.setCurPage(pr.getNext());
        init();
        if(queueItemss.size()!=0){
            showWorkPuerpaer(queueItemss,pr.getCurPage());
        }else {
            showWorkupdateQueue(queueItems,p.getCurPage());
        }


    }
    @FXML
    private void weiye(){
        //尾页
        p.setCurPage(p.getTotalPages());
        pr.setCurPage(pr.getTotalPages());
        init();
        if(queueItemss.size()!=0){
            showWorkPuerpaer(queueItemss,pr.getCurPage());
        }else {
            showWorkupdateQueue(queueItems,p.getCurPage());
        }


    }
    /*返回*/
    @FXML
    private void fanhui(){
        //首页
        p.setCurPage(1);
        init();
        queueItemss.clear();//清空查询
        showWorkupdateQueue(queueItems,p.getCurPage());
    }
/*时间显示*/
    private void showTime(){
        //label2
        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .subscribeOn(JavaFxScheduler.platform())
                .subscribe(it->{
                    Platform.runLater(()->{
                        label2.setText(sdf.format(new Date()));
                    });
                });
    }
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
