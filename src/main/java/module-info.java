module com.rakkiz.taskmanagerclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.rakkiz.taskmanagerclient to javafx.fxml;
    exports com.rakkiz.taskmanagerclient;
}