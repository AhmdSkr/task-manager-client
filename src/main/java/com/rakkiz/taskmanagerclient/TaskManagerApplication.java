package com.rakkiz.taskmanagerclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class TaskManagerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource("fxml/MainScaffold.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setTitle("Rakkiz");
        Image icon = new Image(getClass().getResourceAsStream("images/icon.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setWidth(bounds.getWidth());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}