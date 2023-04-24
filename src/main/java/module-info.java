module com.rakkiz.taskmanagerclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.rakkiz.taskmanagerclient to javafx.fxml;
    exports com.rakkiz.taskmanagerclient;

    exports com.rakkiz.taskmanagerclient.controller;
    opens com.rakkiz.taskmanagerclient.controller to javafx.fxml;
}