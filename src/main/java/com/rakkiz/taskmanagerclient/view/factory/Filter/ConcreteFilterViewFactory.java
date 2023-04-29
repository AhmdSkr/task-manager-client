package com.rakkiz.taskmanagerclient.view.factory.Filter;

import com.rakkiz.taskmanagerclient.TaskManagerApplication;
import com.rakkiz.taskmanagerclient.controller.FilterController;
import com.rakkiz.taskmanagerclient.controller.TaskSackController;
import com.rakkiz.taskmanagerclient.view.strategy.date.DateTaskFilter;
import com.rakkiz.taskmanagerclient.view.strategy.duration.DurationTaskFilter;
import com.rakkiz.taskmanagerclient.view.strategy.type.TypeTaskFilter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ConcreteFilterViewFactory implements FilterVeiwFactory {
    private static final String FXML_PATH = "fxml/filter.fxml";
    private final FXMLLoader filterLoader;
    private FilterController filterController;

    private static URL getFXMLResource() {
        return TaskManagerApplication.class.getResource(FXML_PATH);
    }

    public ConcreteFilterViewFactory() {
        filterLoader = new FXMLLoader(getFXMLResource());
    }

    @Override
    public Node create(String type, TaskSackController taskSackController) throws IOException {
        filterLoader.setRoot(null);
        filterLoader.setController(null);
        HBox filterRoot = filterLoader.load();
        filterController = filterLoader.getController();
        filterController.setTaskSackController(taskSackController);

        if (type == "Date") {
            filterController.setTaskFilter(new DateTaskFilter());
            filterController.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/date/calendar.png").toString()));
            filterController.addDateChoiceBox();
        } else if (type == "Duration") {
            filterController.setTaskFilter(new DurationTaskFilter());
            filterController.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/duration/duration.png").toString()));
            filterController.addDurationChoiceBox();
        } else if (type == "Type") {
            filterController.setTaskFilter(new TypeTaskFilter());
            filterController.setImage(new Image(TaskManagerApplication.class.getResource("images/filters/type/type.png").toString()));
            filterController.addTypeChoiceBox();
        }
        return filterRoot;
    }

    public ArrayList<FilterController> addFilters(HBox node, TaskSackController taskSackController) throws IOException {

        ArrayList<FilterController> filterControllerArrayList = new ArrayList<FilterController>();

        Node filterRoot = create("Duration", taskSackController);
        filterControllerArrayList.add(filterController);
        node.getChildren().add(filterRoot);

        filterRoot = create("Type", taskSackController);
        filterRoot.setId("TypeFilterRoot");
        filterControllerArrayList.add(filterController);
        node.getChildren().add(filterRoot);

        filterRoot = create("Date", taskSackController);
        filterRoot.setId("DateFilterRoot");
        filterControllerArrayList.add(filterController);
        filterRoot.setDisable(true);
        node.getChildren().add(filterRoot);

        return filterControllerArrayList;
    }
}
