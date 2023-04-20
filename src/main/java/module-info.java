module com.rakkiz.taskmanagerclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.rakkiz.taskmanagerclient to javafx.fxml;
    exports com.rakkiz.taskmanagerclient;
    exports com.rakkiz.taskmanagerclient.view;
    opens com.rakkiz.taskmanagerclient.view to javafx.fxml;
}