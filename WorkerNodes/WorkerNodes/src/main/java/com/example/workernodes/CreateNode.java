package com.example.workernodes;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class CreateNode implements Runnable{
    Thread t;
    private int _weight;
    private int _nodeNum;
    public boolean nodeIsAlive;

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
        nodeIsAlive = t.isAlive();
        try {
            int port = 5000 + _nodeNum;
            ServerSocket currentNodesServer = new ServerSocket(port);
            System.out.println("Node: " + _nodeNum + " Is listening on port " + port);
            Socket currentNode;
            List<String> processedJobs = new LinkedList<>();
            boolean recievedAllJobs = false;
            while (true) {
                currentNode = currentNodesServer.accept();
                InputStreamReader streamReader = new InputStreamReader(currentNode.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                String job = reader.readLine();

                System.out.println("Node " + _nodeNum + " has recieved Job: "+ job);

                // use properties to restore the map
                Properties props = new Properties();
                props.load(new StringReader(job.substring(1, job.length() - 1).replace(", ", "\n")));
                Map<String, String> jobObject = new HashMap<String, String>();
                for (Map.Entry<Object, Object> e : props.entrySet()) {
                    jobObject.put((String)e.getKey(), (String)e.getValue());
                }

                int processTime = Integer.parseInt(jobObject.get("time")) / _weight;

                Thread.sleep(processTime);

                System.out.println("Node " + _nodeNum + " has completed " + jobObject.get("id") + " and  it took "+ processTime +" miliseconds and is now FREE");

                PrintWriter writer = new PrintWriter(currentNode.getOutputStream());
                processedJobs.add(job);
                writer.println("Job Complete: " + job);
                writer.close();

                if(Boolean.parseBoolean(jobObject.get("jobsEnd"))){
                    System.out.println("Finished those jobs...");
                    recievedAllJobs = true;
                    nodeIsAlive = false;
                }

            }
        } catch(IOException e){
            e.printStackTrace();
        } catch (InterruptedException  e) {
            e.printStackTrace();
        }
    }
}
