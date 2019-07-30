package com.controller;

import com.API.TcpClient1;
import com.API.WebServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.node.Alert;
import com.pojo.BasicDisplayStatusInfo;
import com.pojo.BasicPuerpaerInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class PuerpaerMessageController {
    public static Stage savestage;
    public static String BedId;//床位ID
    public static ObservableList<BasicDisplayStatusInfo> comboxlist = FXCollections.observableArrayList();//下拉状态
    //public static ObservableList<BasicPuerpaerInfo>  puerpaerInfo = FXCollections.observableArrayList();//产妇信息
    public static JSONObject basicPuerpaerInfo;//产妇信息
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private TextField textField3;
    @FXML
    private ComboBox<BasicDisplayStatusInfo> textField4;
    @FXML
    private TextArea textField5;
    @FXML
    private TextArea textField6;
    @FXML
    private Button perpaerbedbutton1;
    @FXML
    private Button perpaerbedbutton2;
    @FXML
    private Button perpaerbedbutton3;
    @FXML
    private Button perpaerbedbutton4;

    @FXML
    private void initialize(){
        textField4.setItems(comboxlist);

       Integer puerpaerTYpeID=Integer.valueOf(String.valueOf(basicPuerpaerInfo.get("DisplayStatus")));
        System.out.println("默认选中值:"+puerpaerTYpeID);
        if(basicPuerpaerInfo.get("DisplayType_Status")!=null&&!"".equals(basicPuerpaerInfo.get("DisplayType_Status"))){
            textField4.getSelectionModel().select(puerpaerTYpeID);
        }else {
            textField4.getSelectionModel().select(0);
        }
        //textField4.getSelectionModel().select(0);
        System.out.println("查询产妇信息::"+basicPuerpaerInfo.get("Puerpaer_Name"));
        textField1.setText(String.valueOf(basicPuerpaerInfo.get("Place_No")));
        textField2.setText(String.valueOf(basicPuerpaerInfo.get("Bed_Name")));
        textField3.setText(String.valueOf(basicPuerpaerInfo.get("Puerpaer_Name")));
        textField5.setText(String.valueOf(basicPuerpaerInfo.get("Custom_Type")));
        textField6.setText(String.valueOf(basicPuerpaerInfo.get("Custom_Broadcast")));
        bindMouseEvent();
    }
     /*点击事件*/
    private void bindMouseEvent() {
       /*修改下拉框状态*/
        perpaerbedbutton1.setOnMouseClicked(event -> {
            String selectedItem = textField4.getSelectionModel().getSelectedItem().getDisplayTypeId();
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                String s= null;
                try {
                    s = WebServices.updateStatusPuerpaer(BedId,selectedItem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject= JSON.parseObject(s);
                Integer result = Integer.valueOf(String.valueOf(jsonObject.get("result")));
                System.out.println("选择参数:"+selectedItem);
                System.out.println("房间ID:"+BedId+"选择ID:"+selectedItem+"返回状态:"+result);
                if(result.equals(1)){
                    System.out.println("操作成功!");
                    Alert.notifyWarning("提示","操作成功!", savestage);
                }else {
                    Alert.notifyWarning("提示","操作失败!", savestage);
                    System.out.println("操作失败!");
                }
            }
        });
        /*自定义状态*/
        perpaerbedbutton2.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                String text5=textField5.getText();
                if(text5.equals("")){
                    Alert.notifyWarning("提示","请输入自定义状态!", savestage);
                }else {
                    String s= null;
                    try {
                        s = WebServices.updateCustomPuerpaer(BedId,textField5.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JSONObject jsonObject= JSON.parseObject(s);
                    Integer result = Integer.valueOf(String.valueOf(jsonObject.get("result")));
                    if(result.equals(1)){
                        System.out.println("操作成功!");
                        Alert.notifyWarning("提示","操作成功!", savestage);
                    }else {
                        System.out.println("操作失败!");
                        Alert.notifyWarning("提示","操作失败!", savestage);
                    }
                }

            }
        });

        /*自定义呼叫*/
        perpaerbedbutton3.setOnMouseClicked(event -> {

            if (MouseButton.PRIMARY.equals(event.getButton())) {
                String text6=textField6.getText();
                String s1 = TcpClient1.ClienetSocket(text6);//发送Socket
                if(text6.equals("")){
                    Alert.notifyWarning("提示","请输入自定义呼叫!", savestage);
                }else{
                    String s= null;
                    try {
                        s = WebServices.updateCustomBroadcastPuerpaer(BedId,textField6.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JSONObject jsonObject= JSON.parseObject(s);
                    Integer result = Integer.valueOf(String.valueOf(jsonObject.get("result")));
                    System.out.println("房间ID:"+BedId+"自定义:"+textField6.getText()+"返回状态:"+result);
                    if(result.equals(1)&&!"".equals(s1)&&s1!=null){
                        System.out.println("socket返回信息::"+s1);
                        Alert.notifyWarning("提示","操作成功!", savestage);
                        System.out.println("操作成功!");
                    }else {
                        System.out.println("操作失败!");
                        Alert.notifyWarning("提示","操作失败!", savestage);
                    }
                }

            }
        });
        /*出院*/
        perpaerbedbutton4.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                String s= null;
                try {
                    s = WebServices.updatePuerpaerStatus(BedId,textField3.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject= JSON.parseObject(s);
                Integer result = Integer.valueOf(String.valueOf(jsonObject.get("result")));
                System.out.println(textField3.getText()+"房间ID:"+BedId+"返回状态:"+result);
                if("1".equals(result)){
                    System.out.println("操作成功!");
                    Alert.notifyWarning("提示","操作成功!", savestage);
                    Stage stage= (Stage) perpaerbedbutton4.getScene().getWindow();
                    stage.close();
                }else {
                    Alert.notifyWarning("提示","操作失败!", savestage);
                    Stage stage= (Stage) perpaerbedbutton4.getScene().getWindow();
                    stage.close();

                }
                setButtonDisable(perpaerbedbutton4);
            }
        });
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
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> button.setDisable(false));
        });
        thread.start();
    }

}
