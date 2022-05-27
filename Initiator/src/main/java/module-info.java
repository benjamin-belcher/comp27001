module com.example.initiator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.initiator to javafx.fxml;
    exports com.example.initiator;
}