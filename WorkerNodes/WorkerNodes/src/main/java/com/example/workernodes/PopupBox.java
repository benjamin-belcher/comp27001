package com.example.workernodes;


import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupBox{
    private String _contentText;
    private String _headerText;
    private Alert.AlertType _popupType;
    private Stage _stage;

    PopupBox(
            String contentText,
            String headerText,
            Alert.AlertType popupType,
            Stage stage
    ){
        _contentText = contentText;
        _headerText = headerText;
        _popupType = popupType;
        _stage = stage;
    }

    public void showpopup(){
        Alert alert = new Alert(_popupType, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(_stage);
        alert.getDialogPane().setContentText(_contentText);
        alert.getDialogPane().setHeaderText(_headerText);
        alert.showAndWait();
    }
}