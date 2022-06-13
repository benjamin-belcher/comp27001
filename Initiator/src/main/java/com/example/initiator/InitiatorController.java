package com.example.initiator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class InitiatorController {
    @FXML
    private Label titleText;

    @FXML
    private TextField numJobs;

    @FXML
    private TextField port;

    @FXML
    private TextField hostname;

    @FXML
    protected void onSendBtnClicked() throws IOException {
        try {

            for(int i = 0 ; i < Integer.parseInt(numJobs.getText()); i++) {
                JobRequest job = new JobRequest();
                JobSender sender = new JobSender();

                sender.sendDataToServer(
                        job,
                        sender.connectToServer(
                                hostname.getText(),
                                Integer.parseInt(port.getText())
                        )
                );
            }
        }
        catch (IOException ex) {
            ex.printStackTrace(); // (**)
        }





//        JobSender send = new JobSender();
//        send.connectToServer("localhost", 8080);
//        for(int i = 0; i<Integer.parseInt(numJobs.getText()); i++){
//            JobRequest job = new JobRequest();
//            int Ijob = i;
//            try{
//                send.sendDataToServer(Ijob, send.socket);
//            } catch(IOException e){
//                e.printStackTrace();
//            }
//        }
//        send.closeConnection(send.socket);
    }
}