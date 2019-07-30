package com.node;

import com.API.WebServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.controller.PuerpaerMessageController;
import com.pojo.BasicDisplayStatusInfo;
import com.util.ConstructorBuilder;
import com.util.JsonToMapUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class QueuePuerpaerAnchor extends AnchorPane implements Initializable {
    public static String zhaiyuanZT;
    @FXML
    private AnchorPane anchorPane1;
    @FXML
    private Label roomlabel1;
    @FXML
    private Label roomlabel2;
    @FXML
    private Label roomlabel3;
    @FXML
    private Label roomlabel4;
    @FXML
    private Label roomlabel5;
    @FXML
    private Label roomlabel6;

    ObservableList<BasicDisplayStatusInfo> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindMouseEvent();
    }

    private void bindMouseEvent() {
        /*获取产妇查询参数*/
        Integer zyzt=Integer.valueOf(zhaiyuanZT);
        if(zyzt.equals(1)){
            this.anchorPane1.setOnMouseClicked(event -> {
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
                PuerpaerMessageController.comboxlist=this.list;
                PuerpaerMessageController.BedId=this.roomlabel3.getText();
                if (MouseButton.PRIMARY.equals(event.getButton())) {
                    Stage stage = new Stage();
                    /*加载窗体*/

                        String readPuerp= null;
                        try {
                            readPuerp = WebServices.readMaternal(this.roomlabel3.getText());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        JSONObject jsonObject= JSON.parseObject(readPuerp);
                        PuerpaerMessageController.basicPuerpaerInfo=jsonObject;
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(this.getClass().getResource("/fxml/PuerpaerMessagePane.fxml"));
                            stage.setTitle("操作显示");
                            stage.initModality(Modality.APPLICATION_MODAL);//弹出一次
                            stage.setAlwaysOnTop(true);//设置窗口在上面
                            Scene scene = new Scene(root, 650, 450);
                            stage.getIcons().add(new Image(PersonAnchor.class.getResourceAsStream("/static/xhIcon.png")));

                            stage.setY(120);
                            stage.setX(360);

                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                }
            });
        }else {
            roomlabel6.setText("出院");
        }
    }

    void  loabQueueBedXML(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxml/queuePuerpaerPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueuePuerpaerAnchor(String placeNo,String bedName,String bediId,String bedStatus,String puerpaerName,String displayTypeStatus,String custumType,String displayStatus,String type){
        if (("queuePC").equals(type)){
            //System.out.println("进入加载。。。。。。");
            loabQueueBedXML();
            roomlabel1.setText(placeNo);
            roomlabel2.setText(bedName);
            roomlabel3.setText(bediId);
            roomlabel4.setText(puerpaerName);
            if("1".equals(bedStatus)){
                //System.out.println("床位状态可用1可用0不可用");
            }else{
                //anchorPane1.setStyle("hover: #ac9fa5;-fx-background-color: rgb(197,73,38);");
                anchorPane1.setStyle("hover: #ac9fa5;-fx-background-color: rgb(197,73,38);");
            }
            if("1".equals(displayStatus)){
                roomlabel5.setText(displayTypeStatus);
            }else {
                roomlabel5.setText(custumType);
            }

        }else {
            System.out.println("无");
        }
    }
}
