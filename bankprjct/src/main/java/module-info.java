module bankpro {
    requires javafx.controls;
    requires javafx.fxml;

    opens bankpro to javafx.fxml;
    exports bankpro;
}