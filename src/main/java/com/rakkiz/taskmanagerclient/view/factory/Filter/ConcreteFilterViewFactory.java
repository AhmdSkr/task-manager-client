package com.rakkiz.taskmanagerclient.view.factory.Filter;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.controller.FilterController;
import com.rakkiz.taskmanagerclient.view.strategy.DateTaskFilter;
import com.rakkiz.taskmanagerclient.view.strategy.DurationTaskFilter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Box;

import java.io.IOException;
import java.net.URL;

public class ConcreteFilterViewFactory implements FilterVeiwFactory{
    private static final String FXML_PATH = "fxml/filter.fxml";
    private final FXMLLoader filterLoader;

    private static URL getFXMLResource() {
        return TaskManagerApplication.class.getResource(FXML_PATH);
    }

    public ConcreteFilterViewFactory() {
        filterLoader = new FXMLLoader(getFXMLResource());
    }
    @Override
    public Node create(String type) throws IOException {
        filterLoader.setRoot(null);
        filterLoader.setController(null);
        HBox filterRoot = filterLoader.load();
        FilterController filterController = filterLoader.getController();
        filterController.setLabel(type);
        if (type == "Date") {
            filterController.setTaskFilter(new DateTaskFilter());
            filterController.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/date/calendar.png").toString()));
            filterController.addDatePicker();
        } else if (type == "Duration") {
            filterController.setTaskFilter(new DurationTaskFilter());
            filterController.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/duration/duration.png").toString()));
        }
        return filterRoot;
    }

    public void addFilters(HBox node) throws IOException {
        Node filterRoot = create("Date");
        node.getChildren().add(filterRoot);

        filterRoot = create("Duration");
        node.getChildren().add(filterRoot);
    }
}
