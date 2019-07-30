package com.controller;

import com.API.WebServices;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.node.Alert;
import com.pojo.BasicDisplay;
import com.pojo.BasicStaff;
import com.util.IniUtil;
import com.util.PageUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LoginPaneController {
    public static Stage stage;
    //private static BasicWorkStation workStation=null;//工作站窗口
    private static BasicStaff StaffInfo=null;//员工
    private static List<BasicDisplay> displayInfos = null;//显示设备信息
    //账号框
    @FXML   //私有属性加@FXML
    private TextField staffIdTextField;
    //密码框
    @FXML
    private PasswordField staffPasswordField;
    //下拉框 项目
    //public ComboBox<BasicSrvGroup> itemComboBox;
    //点击登录
    public AnchorPane loginButtonPane;


    private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
    private String DEFAULT_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private String time;
    private int ONE_SECOND = 1000;
    @FXML
    public void initialize(){
        System.out.println("javafx初始化");
        bindMouseEvent();
        bindKeyEvent();

    }

    /*
    绑定鼠标事件
    * */
    public void bindMouseEvent() {
        /*loginButtonPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("事假处理函数...new内部类");
            }
        });*/
        System.out.println("绑定鼠标事件");
        loginButtonPane.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                try {
                    onLogin(staffIdTextField.getText(), staffPasswordField.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(staffIdTextField.getText()+":"+staffPasswordField.getText());
            }
        });
    }
    /*
    * 绑定键盘事件
    * */
       public void bindKeyEvent(){
           //账号框
           staffIdTextField.setOnKeyPressed(event -> {
              switch (event.getCode()){
                  case ENTER: // 回车键
                      //getStaffInfo(staffIdTextField.getText());
                      break;
                  case ESCAPE://esc键清空
                      staffIdTextField.setText("");
                      break;
              }
           });

           staffIdTextField.focusedProperty().addListener(event->{
               if (!staffIdTextField.focusedProperty().get()) {
                   //getStaffInfo(staffIdTextField.getText());
               }
           });

           staffPasswordField.setOnKeyPressed(event -> {
               switch (event.getCode()) {
                   case ESCAPE:
                       staffPasswordField.setText("");
                       break;
                   case ENTER:
                       try {
                           onLogin(staffIdTextField.getText(),
                                   staffPasswordField.getText());
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       break;
               }
           });

       }

    /*
     验证用户密码是否为空
    * */
    private void onLogin(String staffId, String staffPass) throws IOException {
        if("".equals(staffId)){
         //提示没有账号
            Alert.notifyWarning("提示", "没有输入账号", stage);
        }else if("".equals(staffPass)){
            //提示
            Alert.notifyWarning("提示", "没有输入密码", stage);

        }else{
            //获取工作站信息
            getWorkStationInfo();
            //执行登录方法
           /* Login(workStation,staffId,staffPass,basicSrvGroup.getSrvGroupId());*/
            Login(staffId,staffPass);
        }
    }
    /*
    * 执行登录方法
    *
    * */
    private  void Login(String loginName, String passwork) throws IOException {
        String s = WebServices.LoginService(loginName,passwork);
        JSONObject object= JSON.parseObject(s);
        Object result = object.get("result");
        String loginName1 = String.valueOf(object.get("LoginName"));
        System.out.println(object.get("LoginName"));

        if(result.equals(1)){
            //提示
            String resultInfo = (String) object.get("resultInfo");
               PageUtil.toWorkStation(stage,displayInfos,StaffInfo,loginName1);
            //Alert.notifyWarning("提示",resultInfo, stage);
           }else if (result.equals(0)){
            //提示
            String resultInfo = (String) object.get("resultInfo");
            Alert.notifyWarning("提示",resultInfo, stage);
           }else {
            Alert.notifyWarning("提示","连接超时!", stage);
        }
    }
    /*
    * //获取工作站信息
    * */
    private void getWorkStationInfo() {

    }
}
