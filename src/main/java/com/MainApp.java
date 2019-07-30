package com;

import com.util.PageUtil;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        PageUtil.toLogin(primaryStage);
        /*Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/LoginPane.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("css/applicaion.css").toExternalForm());
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Simple JavaFX");
        *//*添加窗口图标*//*
        primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("static/xhIcon.png")));
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }
     public static void main(String[] args) {
         try {
             launch();
         } catch (Exception e) {
             e.printStackTrace();
             System.exit(0);
         }
       }
}
