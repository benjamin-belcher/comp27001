package com.example.workernodes;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NodesController {
    @FXML
    private NumberField nodeWeight;

    @FXML
    private GridPane myAnchorPane;

    @FXML
    static Socket loadBalancerConnection;

    @FXML
    private Button loadBalancerConnectionBtn;

    @FXML
    private Label loadBalancerConnectionText;

    private int countNodes = 0;

    @FXML
    private Socket connectToLoadBalancer(){
        if(loadBalancerConnection == null){
            try{
                loadBalancerConnection = new Socket("localhost", 7070);
                System.out.println("Connected to load balancer...");
                loadBalancerConnectionText.setText("Connected to load balancer");
                loadBalancerConnectionBtn.setText("Disconnect");
                return loadBalancerConnection;
            } catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
        loadBalancerConnectionText.setText("Disconnected from load balancer");
        loadBalancerConnectionBtn.setText("Reconnect to load balancer");
        return disconnectFromLoadBalancer();
    }

    @FXML
    private Socket disconnectFromLoadBalancer(){
        try{
            loadBalancerConnection.close();
            return loadBalancerConnection;
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private Socket nodeDetailsConnection(){
        try{
            return new Socket("localhost", 7071);
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

//    Send the name of each node to the server, sending one node at a time
    public void sendNodeDetails(CreateNode node, Socket connection) throws IOException {
        PrintWriter writer = new PrintWriter(connection.getOutputStream());
        Map<String, String> nodeMap = node.getNodeDetails();
        // just make sure you send data and do not wait for response
        System.out.println("Sending Job : " + nodeMap);
        writer.println(nodeMap);
        writer.flush();
    }

    @FXML
    protected void onStartNodesClicked() {
        try {
            if (loadBalancerConnection != null) {
//                Looping through the number of nodes the user entered and creating a new node and sending its details to the load balancer server
//                for (int i = 0; i < Integer.parseInt(numNodes.getText()); i++) {
//                    CreateNode node = new CreateNode(i);
//                    sendNodeDetails(node, nodeDetailsConnection());
//                }
                CreateNode node = new CreateNode(countNodes, Integer.parseInt(nodeWeight.getText()));
                sendNodeDetails(node, nodeDetailsConnection());
                countNodes ++;
            } else {
                //            Display an error popup if the user is not connected to the main load balancer
                PopupBox popup = new PopupBox(
                        "Please connect to the load balancer",
                        "Error",
                        Alert.AlertType.ERROR,
                        (Stage) myAnchorPane.getScene().getWindow());
                popup.showpopup();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}