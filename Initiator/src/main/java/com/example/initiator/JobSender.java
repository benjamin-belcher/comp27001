package com.example.initiator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class JobSender {
    Socket socket;
    JobSender(){
    }

    // Hint use localhost for the hostname and port 8080
    public Socket connectToServer(String hostname, int port){
        try{
            return new Socket(hostname, port);
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void sendDataToServer(JobRequest job, String hostname, int port) throws IOException {

        Map<String, String> jobMap = new HashMap<>();
        jobMap.put("id", job.id.toString());
        jobMap.put("time", Integer.toString(job.time));

        Socket sc = new Socket(hostname, port);
        PrintWriter writer = new PrintWriter(sc.getOutputStream());

        // just make sure you send data and do not wait for response
        System.out.println("Sending Job : " + job.id);
        writer.println(jobMap);
        writer.flush();
    }

    public void closeConnection(Socket s) throws IOException {
        s.close();
    }


}
