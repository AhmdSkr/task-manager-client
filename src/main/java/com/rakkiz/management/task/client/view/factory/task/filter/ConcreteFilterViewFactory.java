package com.rakkiz.management.task.client.view.factory.task.filter;

import com.rakkiz.management.task.client.TaskManagerApplication;
import com.rakkiz.management.task.client.controller.FilterController;
import com.rakkiz.management.task.client.controller.TaskSackController;
import com.rakkiz.management.task.client.view.strategy.date.DateTaskFilter;
import com.rakkiz.management.task.client.view.strategy.duration.DurationTaskFilter;
import com.rakkiz.management.task.client.view.strategy.type.TypeTaskFilter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The ConcreteFilterViewFactory is responsible for loading Filter views into the application.
 */
public class ConcreteFilterViewFactory implements FilterVeiwFactory {
    private static final String FXML_PATH = "fxml/filter.fxml";
    private final FXMLLoader filterLoader;
    private FilterController filterController;

    /**
     * Retrieves FXML resource corresponding to FilterView.
     *
     * @return FXML resource's URL
     */
    private static URL getFXMLResource() {
        return TaskManagerApplication.class.getResource(FXML_PATH);
    }

    public ConcreteFilterViewFactory() {
        filterLoader = new FXMLLoader(getFXMLResource());
    }

    /**
     * Creates a new Filter view for the Task Sack.
     *
     * @param type               String to identify the type of filter
     * @param taskSackController Controller to assign the filters to the task sack
     * @return Filter view's root element
     */
    @Override
    public Node create(String type, TaskSackController taskSackController) throws IOException {
        filterLoader.setRoot(null);
        filterLoader.setController(null);
        HBox filterRoot = filterLoader.load();
        filterController = filterLoader.getController();
        filterController.setTaskSackController(taskSackController);

        switch (type) {
            case "Date" -> {
                filterController.setTaskFilter(new DateTaskFilter());
                filterController.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/date/calendar.png")).toString()));
                filterController.addDateChoiceBox();
            }
            case "Duration" -> {
                filterController.setTaskFilter(new DurationTaskFilter());
                filterController.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/duration/duration.png")).toString()));
                filterController.addDurationChoiceBox();
            }
            case "Type" -> {
                filterController.setTaskFilter(new TypeTaskFilter());
                filterController.setImage(new Image(Objects.requireNonNull(TaskManagerApplication.class.getResource("images/filters/type/type.png")).toString()));
                filterController.addTypeChoiceBox();
            }
        }
        return filterRoot;
    }

    /**
     * Adds the created Filter views to the provided HBox.
     *
     * @param node               HBox parent node of the Filter views
     * @param taskSackController Controller to assign the filters to the task sack
     * @return List of FilterController of each filter
     */
    public ArrayList<FilterController> addFilters(HBox node, TaskSackController taskSackController) throws IOException {

        ArrayList<FilterController> filterControllerArrayList = new ArrayList<>();

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
