module com.rakkiz.taskmanagerclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.rakkiz.taskmanagerclient to javafx.fxml;
    exports com.rakkiz.taskmanagerclient;
    exports com.rakkiz.taskmanagerclient.data.model;
    exports com.rakkiz.taskmanagerclient.controller;
    opens com.rakkiz.taskmanagerclient.controller to javafx.fxml;
    exports com.rakkiz.taskmanagerclient.view.strategy;
    opens com.rakkiz.taskmanagerclient.view.strategy to javafx.fxml;
    exports com.rakkiz.taskmanagerclient.view.strategy.duration;
    opens com.rakkiz.taskmanagerclient.view.strategy.duration to javafx.fxml;
    exports com.rakkiz.taskmanagerclient.view.strategy.type;
    opens com.rakkiz.taskmanagerclient.view.strategy.type to javafx.fxml;
}