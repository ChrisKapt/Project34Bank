module groupbank {
    requires javafx.controls;
    requires javafx.fxml;

    opens groupbank to javafx.fxml;
    exports groupbank;
}