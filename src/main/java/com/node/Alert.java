package com.node;

import javafx.geometry.Pos;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.dialog.ExceptionDialog;

public class Alert {

    public static void notifyWarning(String title, String msg, Stage stage) {
        Notifications.create()
                .position(Pos.CENTER)
                .title(title)
                .text(msg)
                .owner(stage)
                .showWarning();
    }

    public static void notify(String title, String msg, Stage stage) {
        Notifications.create()
                .position(Pos.CENTER)
                .title(title)
                .text(msg)
                .owner(stage)
                .show();
    }

    public static void alertException(Throwable e, Stage stage) {
        ExceptionDialog dialog = new ExceptionDialog(e);
        dialog.initOwner( stage);
        dialog.show();
    }

}
