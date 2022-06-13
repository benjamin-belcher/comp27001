package com.example.workernodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class CreateNode implements Runnable{
    Thread t;
    private int _weight;
    Socket nodeConnectionToLoadBalancer;
    CreateNode(int nodeNum, int weight){
        _weight = weight;
        t = new Thread(this, "WorkerNode" +nodeNum);
        System.out.println("Started: " + t.getName());
        t.start();
    }

    public Map<String, String> getNodeDetails(){
        Map<String, String> node = new HashMap<>();
        node.put("name", t.getName());
        node.put("weight", Integer.toString(_weight));
        return node;
    }

    public void run(){
        if(nodeConnectionToLoadBalancer != null){
            System.out.println(nodeConnectionToLoadBalancer.isConnected());

        }
        else{
        }
    }
}
