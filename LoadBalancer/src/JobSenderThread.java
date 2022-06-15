import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class JobSenderThread implements Runnable{
    private String _nodeName;
    private int _nodeNum;
    private Map<String, String> _job;

    JobSenderThread(String nodeName, int nodeNum, Map<String, String> job){
        _nodeName = nodeName;
        _nodeNum = nodeNum;
        _job = job;
    }
    public void run(){
        try{
            Socket nodeConnection = new Socket("localhost",5000+_nodeNum);

            PrintWriter writer = new PrintWriter(nodeConnection.getOutputStream());

            InputStreamReader response = new InputStreamReader((nodeConnection.getInputStream()));
            BufferedReader responseReader = new BufferedReader(response);

            writer.println(_job);
            System.out.println("Sending Job : "+_job + " to Node " + _nodeName);
            writer.flush();

            String responseMessage = responseReader.readLine();
            System.out.println(responseMessage);


        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
