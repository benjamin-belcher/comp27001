import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class JobSenderThread implements Runnable{
    Thread t;
    private String _nodeName;
    private int _nodeNum;
    private Map<String, String> _job;
    private JobStore _store;

    JobSenderThread(String nodeName, int nodeNum, Map<String, String> job, JobStore store){
        _nodeName = nodeName;
        _nodeNum = nodeNum;
        _job = job;
        _store = store;
        t = new Thread(this, _nodeName+"JobSender");
        t.start();
    }
    public void run(){
        try{
//            Connect to the worker node
            Socket nodeConnection = new Socket("localhost",5000+_nodeNum);

//            Here we are sending the jobs and not waiting for a response
            PrintWriter writer = new PrintWriter(nodeConnection.getOutputStream());
            writer.println(_job);
            System.out.println("Sending Job : "+_job + " to Node " + _nodeName);
            writer.flush();

//            Once job is sent remove it from the job store
            _store.removeJob(_job);


        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
