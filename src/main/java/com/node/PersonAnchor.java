package com.node;

import com.controller.PuerpaerSavePaneController;
import com.controller.WorkBedController;
import com.controller.WorkStationController;
import com.pojo.PlaceInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import java.util.ResourceBundle;

public class PersonAnchor extends AnchorPane implements Initializable {
    public static String roomId;
    public static String roomIds;
    public static ObservableList<PlaceInfo> infos = FXCollections.observableArrayList();
   private Stage stage;
    @FXML
    private Label label;

    @FXML
    private Label labelPC;

    @FXML
    private Label labelNote;

    @FXML
    private VBox roomvbox;
    @FXML
    private Label roomlabel1;
    @FXML
    private Label roomlabel2;
    @FXML
    private Label roomlabel3;

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

    void  loabRoomXML(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxml/WorkRoomPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*void  loabRoomBedXML(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxml/BedDisplayPane.fxml"));

        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    void loadXML(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/person.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadXMLPC(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/personpc.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadXMLNote(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/note.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        /*初始化*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindMouseEvent();

    }
    public static void shuaxin(){
        Stage stage = new Stage();
        WorkBedController.roomPlaceNO=roomIds;
        System.out.println("刷新数据:"+roomIds);
        WorkBedController.stagebed=stage;
        /*加载窗体*/
        Parent root = null;
        try {
            root = FXMLLoader.load(PersonAnchor.class.getClass().getResource("/fxml/WorkBedPane.fxml"));
            stage.setTitle("床位显示");
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
         /*点击获取床位值*/
    private void bindMouseEvent() {
        this.roomvbox.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                stage = new Stage();
                String text=roomlabel1.getText();
                String str1=text.substring(0, text.indexOf(":"));
                String str2=text.substring(str1.length()+1,text.length());
                WorkBedController.roomPlaceNO=str2;
                WorkBedController.stagebed=stage;
                //PuerpaerSavePaneController.savestage=stage;
                /*加载窗体*/
                Parent root = null;
                try {
                    root = FXMLLoader.load(this.getClass().getResource("/fxml/WorkBedPane.fxml"));
                    stage.setTitle("床位显示");
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
        });
    }

    public PersonAnchor(String roomId,String roomNumder,String nextNumber,String status,String type){
        //WorkStationController.placeInfo=infos;
        if (("PC").equals(type)){
            loabRoomXML();
            roomlabel1.setText(roomId);
            roomlabel2.setText(nextNumber);//总床位
            roomlabel3.setText(roomNumder);//剩余床位数
            String str=nextNumber.substring(0,nextNumber.indexOf(":"));
            String number1=nextNumber.substring(str.length()+1,nextNumber.length());
            Integer integer = Integer.valueOf(number1);
            if("1".equals(status)&&integer>0){//房间状态1可用,并且可用床位大于0
                //roomvbox.setStyle("hover: #7EAC17;-fx-background-color: rgb(67,157,68);");
                //roomvbox.setPadding(new Insets(2, 2, 2, 2));
                //roomvbox.setSpacing(5);

            }else {
                roomvbox.setStyle("hover: #ac9fa5;-fx-background-color: rgb(197,73,38);");
                /*roomvbox.setStyle(".roomStop");*/
            }
            //roomvbox.setSpacing(5);
            //roomvbox.setPadding(new Insets(5));
        }else if (("NOTE").equals(type)){
            loadXMLNote();
            labelPC.getStyle();
            labelPC.setStyle("");
            labelPC.setText(roomId);
        }else{
            loadXML();
            label.setText(roomId);
        }


    }

   /* public PersonAnchor(String bediId,String bedName,String bedStatus,String puerpaerName,String displayTypeStatus,String custumType,String displayStatus,String type){
        if (("BedPC").equals(type)){
            System.out.println("进入加载。。。。。。");
            loabRoomBedXML();
            labelbed1.setText(bediId);
            labelbed2.setText(bedName);
            labelbed3.setText(puerpaerName);
           if("1".equals(bedStatus)){
                labelbed4.setText("正在使用");
            }else{
                labelbed4.setText("可以使用");
               roombedvboxroot.setStyle("hover: #ac9fa5;-fx-background-color: rgb(197,73,38);");
            }
            if("1".equals(displayStatus)){
                labelbed5.setText(displayTypeStatus);
            }else {
                labelbed5.setText(custumType);
            }

        }else {
            System.out.println("无");
        }
    }*/

    public PersonAnchor(String string, double width){
        loadXML();
        label.setText(string);
        label.setPrefWidth(width);
    }





}
