package com.rakkiz.management.task.client;

import com.rakkiz.management.task.client.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

//TODO: clean this code, i.e. start method should only contain stage initialization
public class TaskManagerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        final String APP_TITLE = "Rakkiz";
        final String APP_ICON_PATH = "images/icon.png";
        final String APP_MAIN_FXML = "fxml/scaffold.fxml";


        stage.setTitle(APP_TITLE);
        InputStream input = getClass().getResourceAsStream(APP_ICON_PATH);
        if (input != null) {
            stage.getIcons().add(new Image(input));
            input.close();
        }
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setWidth(bounds.getWidth());
        stage.setMaximized(true);

        FXMLLoader fxmlLoader = new FXMLLoader(TaskManagerApplication.class.getResource(APP_MAIN_FXML));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);

        MainController mainController = fxmlLoader.getController();
        stage.setOnShowing(event -> {
            try {
                mainController.onTaskSackClick();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}