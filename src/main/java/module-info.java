module com.rakkiz.management.task.client {
    requires static lombok;

    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.rakkiz.management.task.client to javafx.fxml, javafx.graphics;
    opens com.rakkiz.management.task.client.controller to javafx.fxml;
    opens com.rakkiz.management.task.client.view.strategy to javafx.fxml;
    opens com.rakkiz.management.task.client.view.strategy.date to javafx.fxml;
    opens com.rakkiz.management.task.client.view.strategy.duration to javafx.fxml;
    opens com.rakkiz.management.task.client.view.strategy.type to javafx.fxml;
}