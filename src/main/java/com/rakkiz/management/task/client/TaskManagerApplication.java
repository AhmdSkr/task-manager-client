package com.rakkiz.management.task.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.IOException;
import java.net.URL;

@Log
@Getter
public class TaskManagerApplication extends Application {
    private String title;
    private Image icon;

    protected URL getIconLocator() {
        final String path = "images/icon.png";
        final URL locator = getClass().getResource(path);
        if (locator == null) {
            String format, message;
            format = "could not locate resource of path: %s";
            message = String.format(format, path);
            log.warning(message);
        }
        return locator;
    }

    protected Scene buildScene() throws IOException {
        String path = "/com/rakkiz/management/task/client/fxml/scaffold.fxml";
        URL fxml = getClass().getResource(path);
        FXMLLoader loader = new FXMLLoader(fxml);
        return new Scene(loader.load());
    }

    private Image fetchIcon() {
        final URL locator = this.getIconLocator();
        if (locator == null) return null;
        try (var stream = locator.openStream()) {
            return new Image(stream);
        } catch (IOException exception) {
            String format, message;
            format = "could not fetch icon resource of locator: %s";
            message = String.format(format, locator);
            log.warning(message);
            log.warning(exception.toString());
            return null;
        }
    }

    private double fetchPrimaryScreenWidth() {
        return Screen.getPrimary()
                .getVisualBounds()
                .getWidth();
    }

    @Override
    public void init() throws Exception {
        super.init();
        this.title = "Rakkiz";
        this.icon = fetchIcon();
    }

    @Override
    public void start(Stage stage) throws IOException {
        double width = fetchPrimaryScreenWidth();
        String title = getTitle();
        Image icon = getIcon();
        Scene scene = buildScene();

        if (icon != null) stage.getIcons().add(icon);
        stage.setTitle(title);
        stage.setWidth(width);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}