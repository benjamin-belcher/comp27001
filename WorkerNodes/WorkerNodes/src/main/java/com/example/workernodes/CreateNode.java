package com.example.workernodes;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class CreateNode implements Runnable{
    Thread t;
    private int _weight;
    private int _nodeNum;

    CreateNode(int nodeNum, int weight){
        _weight = weight;
        _nodeNum = nodeNum;
        t = new Thread(this, "WorkerNode-" +nodeNum);
        System.out.println("Started: " + t.getName());
        t.start();
    }

    public Map<String, String> getNodeDetails(){
        Map<String, String> node = new HashMap<>();
        node.put("name", t.getName());
        node.put("weight", Integer.toString(_weight));
        return node;
    }

    public Socket ConnectionForJobs(int port) throws IOException {
        ServerSocket currentNodesServer = new ServerSocket(port);
        Socket currentNode;
        while(true){
            currentNode = currentNodesServer.accept();
            InputStreamReader streamReader = new InputStreamReader(currentNode.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            String job = reader.readLine();

            System.out.println(job);
        }
    }

    public void run(){
        try {
            int port = 5000 + _nodeNum;
            ServerSocket currentNodesServer = new ServerSocket(port);
            System.out.println("Node: " + _nodeNum + " Is listening on port " + port);
            Socket currentNode;
            List<String> processedJob = new LinkedList<>();
            while (true) {
                currentNode = currentNodesServer.accept();
                InputStreamReader streamReader = new InputStreamReader(currentNode.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                String job = reader.readLine();

                System.out.println("Node " + _nodeNum + " has recieved Job: "+ job);

                // use properties to restore the map
                Properties props = new Properties();
                props.load(new StringReader(job.substring(1, job.length() - 1).replace(", ", "\n")));
                Map<String, String> jobToProcess = new HashMap<String, String>();
                for (Map.Entry<Object, Object> e : props.entrySet()) {
                    jobToProcess.put((String)e.getKey(), (String)e.getValue());
                }

                Thread.sleep(Integer.parseInt(jobToProcess.get("time")));

                PrintWriter writer = new PrintWriter(currentNode.getOutputStream());
                processedJob.add(job);
                writer.println(processedJob);
                writer.close();

            }
        } catch(IOException e){
            e.printStackTrace();
        } catch (InterruptedException  e) {
            e.printStackTrace();
        }
    }
}
