package com.example.helloworldfx;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField text1;

    @FXML
    private ComboBox testCombo1;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(text1.getText() +" "+ testCombo1.getValue());
    }
}