module com.example.helloworldfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.helloworldfx to javafx.fxml;
    exports com.example.helloworldfx;
}