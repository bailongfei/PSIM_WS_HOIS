package com.controller;

import com.API.WebServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.node.Alert;
import com.node.BdePersonPane;
import com.node.PersonAnchor;
import com.pojo.BasicDisplayStatusInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PuerpaerSavePaneController {
    public static Stage savestage;
    public static String RoomId;
    @FXML
    private TextField input1;
    @FXML
    private TextField input2;
    @FXML
    private ComboBox<BasicDisplayStatusInfo> input3;
    // 项目
    public static ObservableList<BasicDisplayStatusInfo> list = FXCollections.observableArrayList();
    public static String BedId=null;
    @FXML
    private TextArea input4;
    @FXML
    private Button btnsave;
    @FXML
    private void initialize() {
        input3.setItems(list);
        input3.getSelectionModel().select(0);
        bindMouseEvent();
    }
  /*点击事件*/
    private void bindMouseEvent() {
        /*Stage savestage=new Stage();
        savestage.initModality(Modality.APPLICATION_MODAL);//弹出一次
        savestage.setAlwaysOnTop(true);//设置窗口在上面*/
        btnsave.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton())) {


                String ip1= input1.getText();
                String ip2=input2.getText();
                String ip3=input3.getSelectionModel().getSelectedItem().getDisplayTypeId();
                String ip4=input4.getText();
                if(ip1.equals("")){
                    Alert.notifyWarning("提示","请输入姓名!", savestage);
                    System.out.println("请输入姓名!");
                }else if(ip2.equals("")){
                    Alert.notifyWarning("提示","请输入年龄!", savestage);
                    System.out.println("请输入年龄!");
                }else{
                    if(ip4.equals("")){
                        try {
                            String s=WebServices.savePuerpaer(ip1,ip2,BedId,ip3,ip4,"1");
                            JSONObject object= JSON.parseObject(s);
                            String result = String.valueOf(object.get("result"));
                            if(result.equals("1")){
                                /*刷新床位*/
                                WorkBedController.roomPlaceNO=RoomId;
                                WorkBedController.stagebed=savestage;
                                WorkBedController  wbc=new WorkBedController();
                                wbc.RoomID=RoomId;
                                //wbc.initialize();
                                //wbc.getWoekplaceInfo();
                                Alert.notifyWarning("提示","添加成功!", savestage);

                                /*关闭窗体*/
                                /*窗口销毁*/
                                 Stage stages= (Stage) btnsave.getScene().getWindow();
                                   stages.close();
                                /*退出程序*/
                                //    Platform.exit();

                                PersonAnchor.roomIds=RoomId;
                                PersonAnchor.shuaxin();
                                //WorkBedController.stagebed=savestage;
                                       /*Stage stage = new Stage();
                                        WorkBedController.roomPlaceNO=RoomId;
                                        WorkBedController.stagebed=stage;
                                        //加载窗体
                                        Parent root = null;
                                        try {
                                            root = FXMLLoader.load(this.getClass().getResource("/fxml/WorkBedPane.fxml"));
                                            stage.setTitle("床位显示");
                                            stage.initModality(Modality.APPLICATION_MODAL);//弹出一次
                                            stage.setAlwaysOnTop(true);//设置窗口在上面
                                            stage.getIcons().add(new Image(PersonAnchor.class.getResourceAsStream("/static/xhIcon.png")));
                                            Scene scene = new Scene(root, 650, 450);
                                            stage.setX(360);
                                            stage.setY(120);
                                            stage.setScene(scene);
                                            stage.show();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }*/
                                System.out.println("添加成功!");
                            }else{
                                Alert.notifyWarning("提示","添加失败!", savestage);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else{
                        try {
                            String s2=WebServices.savePuerpaer(ip1,ip2,BedId,ip3,ip4,"0");
                            JSONObject object= JSON.parseObject(s2);
                            String result = String.valueOf(object.get("result"));
                            if(result.equals("1")){
                                Alert.notifyWarning("提示","添加成功!", savestage);
                                /*刷新床位*/
                                WorkBedController.roomPlaceNO=RoomId;
                                WorkBedController  wbc=new WorkBedController();
                                wbc.getWoekplaceInfo();
                                Stage stage= (Stage) btnsave.getScene().getWindow();
                                stage.close();

                            }else{
                                Alert.notifyWarning("提示","添加失败!", savestage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


    }

}


