package com.example.initiator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class JobSender {
    JobSender(){
    }

    // Hint use localhost for the hostname and port 7777
    public Socket connectToServer(String hostname, int port){
        try{
            return new Socket(hostname, port);
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void sendDataToServer(JobRequest job, Socket connection) throws IOException {

//        Convert the JobRequest object to a HashMap, HashMaps are universal and easy to send across network
        Map<String, String> jobMap = new HashMap<>();
        jobMap.put("id", job.id.toString());
        jobMap.put("time", Integer.toString(job.time));

//        Connect to the LoadBalancer
        PrintWriter writer = new PrintWriter(connection.getOutputStream());

        // Send the data to the Load Balancer and clear the stream buffer to allow future jobs to be sent
        System.out.println("Sending Job : " + job.id);
        writer.println(jobMap);
        writer.flush();
    }

    public void closeConnection(Socket s) throws IOException {
        s.close();
    }


}
