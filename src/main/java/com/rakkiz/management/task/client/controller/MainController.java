package com.rakkiz.management.task.client.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class MainController {
    @FXML
    private AnchorPane content;

    /**
     * Change AnchorPane content to taskSack
     *
     * @throws IOException when loading the fxml
     */
    @FXML
    public void onLogoClick() throws IOException {
        String path = "/com/rakkiz/management/task/client/fxml/task-sack.fxml";
        URL fxml = getClass().getResource(path);
        FXMLLoader loader = new FXMLLoader(fxml);
        Node child = loader.load();
        ObservableList<Node> list = content.getChildren();
        list.clear();
        list.add(child);
    }
}
