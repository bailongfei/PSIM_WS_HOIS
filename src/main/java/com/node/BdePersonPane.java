package com.node;

import com.API.WebServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.controller.PuerpaerMessageController;
import com.controller.PuerpaerSavePaneController;
import com.controller.WorkBedController;
import com.pojo.BasicDisplayStatusInfo;
import com.pojo.BasicPuerpaerInfo;
import com.util.ConstructorBuilder;
import com.util.JsonToMapUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BdePersonPane  extends AnchorPane implements Initializable {
    @FXML
    private VBox roombedvboxroot;
    @FXML
    private Label labelbed1;
    @FXML
    private Label labelbed2;
    @FXML
    private Label labelbed3;
    @FXML
    private Label labelbed4;
    @FXML
    private Label labelbed5;
    @FXML
    private Label placeNo1;
    // 项目
    ObservableList<BasicDisplayStatusInfo> list = FXCollections.observableArrayList();
    ObservableList<BasicPuerpaerInfo> readMan=FXCollections.observableArrayList();
   // public static BasicPuerpaerInfo basicPuerpaerInfo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       bindMouseEvent();
    }

    private void bindMouseEvent() {

        this.roombedvboxroot.setOnMouseClicked(event -> {
            String s= null;
            try {
                s = WebServices.getDisplayStatus();//获取状态信息
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Map<String, Object>> maps= JsonToMapUtil.JsonSAXReaderToMap(s);
            list.addAll(
                    maps.stream().map(it ->
                            new ConstructorBuilder<BasicDisplayStatusInfo, String>().builderObject(new BasicDisplayStatusInfo(), it))
                            .collect(Collectors.toList()));

            PuerpaerSavePaneController.list = this.list;
            PuerpaerSavePaneController.BedId=this.labelbed1.getText();
            System.out.println("获取床位ID:"+this.labelbed1.getText());
            PuerpaerMessageController.comboxlist=this.list;
            PuerpaerMessageController.BedId=this.labelbed1.getText();
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                Stage stage = new Stage();
                if("正在使用".equals(labelbed4.getText())){//0床位有人不可用
                    String text=labelbed3.getText();//名字
                    String str1=text.substring(0, text.indexOf(":"));
                    String str2=text.substring(str1.length()+1,text.length());
                    //WorkBedController.roomPlaceNO=str2;
                    //WorkBedController.stagebed=stage;
                    /*加载窗体*/
                    /*获取产妇查询参数*/
                    String readPuerp= null;
                    try {
                        readPuerp = WebServices.readMaternal(this.labelbed1.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JSONObject jsonObject= JSON.parseObject(readPuerp);
                    /*basicPuerpaerInfo.setBedID(String.valueOf(jsonObject.get("Place_No")));
                    basicPuerpaerInfo.setBedName();
                    basicPuerpaerInfo.setBedStatus();
                    basicPuerpaerInfo.set*/
                    PuerpaerMessageController.basicPuerpaerInfo=jsonObject;
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(this.getClass().getResource("/fxml/PuerpaerMessagePane.fxml"));
                        stage.setTitle("操作显示");
                        stage.initModality(Modality.APPLICATION_MODAL);//弹出一次
                        stage.setAlwaysOnTop(true);//设置窗口在上面
                        stage.getIcons().add(new Image(PersonAnchor.class.getResourceAsStream("/static/xhIcon.png")));
                        Scene scene = new Scene(root, 650, 450);
                        stage.setX(286);
                        stage.setY(120);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{
                    //WorkBedController.roomPlaceNO=str2;
                    //WorkBedController.stagebed=stage;
                    //PuerpaerSavePaneController.savestage=stage;
                    PuerpaerSavePaneController.RoomId=labelbed1.getText();//加载传送房间号
                    /*加载窗体*/
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(this.getClass().getResource("/fxml/PuerpaerSavePane.fxml"));
                        stage.setTitle("操作显示");
                        stage.initModality(Modality.APPLICATION_MODAL);//弹出一次
                        stage.setAlwaysOnTop(true);//设置窗口在上面
                        stage.getIcons().add(new Image(PersonAnchor.class.getResourceAsStream("/static/xhIcon.png")));
                        Scene scene = new Scene(root, 650, 450);
                        stage.setX(286);
                        stage.setY(120);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }




            }
        });

    }

    private void  loabRoomBedsXML(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxml/BedDisplayPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BdePersonPane(String placeNo,String bediId,String bedName,String puerpaerName,String bedStatus,String displayTypeStatus,String custumType,String displayStatus,String puerpaerStatus,String type){
        if (("BedPCS").equals(type)){
            //System.out.println("进入加载。。。。。。");
            loabRoomBedsXML();
            placeNo1.setText(placeNo);
            labelbed1.setText(bediId);
            labelbed2.setText(bedName);
            String subName=puerpaerName.substring(0,puerpaerName.indexOf(":"));//截取：之前数据
            String name=puerpaerName.substring(subName.length()+1,puerpaerName.length());//截取：之后
            if(!"null".equals(name)&&name!=null&&name!=""&&"1".equals(puerpaerStatus)){
                //System.out.println("截取之后："+name);
                labelbed3.setText(puerpaerName);
            }else{
                labelbed3.setText("无人");
            }

            if("0".equals(bedStatus)&&"1".equals(puerpaerStatus)){
                labelbed4.setText("正在使用");
                roombedvboxroot.setStyle("hover: #ac9fa5;-fx-background-color: rgb(197,73,38);");
            }else{
                labelbed4.setText("可以使用");

            }
            if("1".equals(puerpaerStatus)){
                if("1".equals(displayStatus)){
                    if(displayTypeStatus!=null&&displayTypeStatus!=""){
                        labelbed5.setText(displayTypeStatus);
                    }

                }else if("0".equals(displayStatus)){
                    if(custumType!=null&&custumType!=""){
                        labelbed5.setText(custumType);
                    }

                }
            }



           /* if("1".equals(displayStatus)){
                //roomvbox.setStyle("hover: #7EAC17;-fx-background-color: rgb(67,157,68);");
                //roomvbox.setPadding(new Insets(2, 2, 2, 2));
                //roomvbox.setSpacing(5);

            }else {
                roomvbox.setStyle("hover: #ac9fa5;-fx-background-color: rgb(197,73,38);");
                //roomvbox.setStyle(".roomStop");
            }*/

        }
    }
    public  BdePersonPane(){

    }



    /**
     * 5秒内不能重复点击
     *
     * @param button btn
     */
    private void setButtonDisable(Button button) {
        Platform.runLater(() -> button.setDisable(true));
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> button.setDisable(false));
        });
        thread.start();
    }
    private void setButtonDisableTrue(VBox button) {
        Platform.runLater(() -> button.setDisable(true));
    }
}
