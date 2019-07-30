package com.controller;

import com.API.WebServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.node.Alert;
import com.node.Logging;
import com.util.IniUtil;
import com.util.PropertyHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Properties implements Initializable {
    //public static Stage savestage;

    @FXML
    TextField textField1;

    @FXML
    TextArea textField2;

    @FXML
    Button nowButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String s= null;
        try {
            s = WebServices.selectGUn(IniUtil.getConfig().get("WSID"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject= JSON.parseObject(s);
        textField1.setText(String.valueOf(jsonObject.get("Display_Title")));//窗口编号
        textField2.setText(String.valueOf(jsonObject.get("Display_Scroll_Text")));//滚动语言
    }
    Stage savestage = new Stage();
    public void setPropertiesNow(){
        PropertyHelper.properties.setProperty("notetitle",textField1.getText());
        Logging.log("设置立即生效"+ PropertyHelper.properties);
        new PropertyHelper().setProperties();
        /*修改滚动信息*/
        String s= null;
        try {
            s = WebServices.updateGun(IniUtil.getConfig().get("WSID"),textField1.getText(),textField2.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject= JSON.parseObject(s);
        Integer result = Integer.valueOf(String.valueOf(jsonObject.get("result")));
        if(result.equals(1)){
            System.out.println("123操作成功!");
            Alert.notifyWarning("提示","操作成功!", savestage);
        }else {
            Alert.notifyWarning("提示","操作失败!", savestage);


        }
        /*关闭窗口*/
        Stage window = (Stage) nowButton.getScene().getWindow();
        window.close();
    }


}
