module com.rakkiz.management.task.client {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.rakkiz.management.task.client to javafx.fxml;
    exports com.rakkiz.management.task.client;
    exports com.rakkiz.management.task.client.data.model;
    exports com.rakkiz.management.task.client.controller;
    opens com.rakkiz.management.task.client.controller to javafx.fxml;
    exports com.rakkiz.management.task.client.view.strategy;
    opens com.rakkiz.management.task.client.view.strategy to javafx.fxml;
    exports com.rakkiz.management.task.client.view.strategy.date;
    opens com.rakkiz.management.task.client.view.strategy.date to javafx.fxml;
    exports com.rakkiz.management.task.client.view.strategy.duration;
    opens com.rakkiz.management.task.client.view.strategy.duration to javafx.fxml;
    exports com.rakkiz.management.task.client.view.strategy.type;
    opens com.rakkiz.management.task.client.view.strategy.type to javafx.fxml;
}