package com.rakkiz.taskmanagerclient.view.factory.Filter;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.controller.FilterController;
import com.rakkiz.taskmanagerclient.view.strategy.date.DateTaskFilter;
import com.rakkiz.taskmanagerclient.view.strategy.date.PastDateTaskFilter;
import com.rakkiz.taskmanagerclient.view.strategy.duration.ShortDurationTaskFilter;
import com.rakkiz.taskmanagerclient.view.strategy.type.UnscheduledTypeTaskFilter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;

public class ConcreteFilterViewFactory implements FilterVeiwFactory {
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

        if (type == "Date") {
            filterController.setTaskFilter(new PastDateTaskFilter());
            filterController.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/date/calendar.png").toString()));
            filterController.addDateChoiceBox();
        } else if (type == "Duration") {
            filterController.setTaskFilter(new ShortDurationTaskFilter());
            filterController.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/duration/duration.png").toString()));
            filterController.addDurationChoiceBox();
        } else if (type == "Type") {
            filterController.setTaskFilter(new UnscheduledTypeTaskFilter());
            filterController.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/type/type.png").toString()));
            filterController.addTypeChoiceBox();
        }
        return filterRoot;
    }

    public void addFilters(HBox node) throws IOException {

        Node filterRoot = create("Duration");
        node.getChildren().add(filterRoot);

        filterRoot = create("Type");
        node.getChildren().add(filterRoot);

        filterRoot = create("Date");
        filterRoot.setDisable(true);
        node.getChildren().add(filterRoot);
    }
}
