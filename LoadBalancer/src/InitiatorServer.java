import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class InitiatorServer implements Runnable{
    Thread t;
    JobStore store;

    InitiatorServer(JobStore _store){
        store = _store;
        t = new Thread(this, "InitatorHandler");
        System.out.println(t.getName() + " Has started to handle incoming jobs.");
        t.start();
    }

    public ServerSocket createSocket(int port){
        try {
            return new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Couldnt create socket "+ e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run(){
        try{
            ServerSocket ss = createSocket(7777);
            Socket s;

//            Need a while true to constantly listen for new incomming jobs
            while (true) {
                s = ss.accept();

//                Connect to the incomming stream and set up a buffer to store the incomming jobs
                InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);

//                Extracting the job from buffer
                String incommingJob = reader.readLine();

                // Convert the incomming job back to a HashMap as it loses its structure being sent
                Properties props = new Properties();
                props.load(new StringReader(incommingJob.substring(1, incommingJob.length() - 1).replace(", ", "\n")));
                Map<String, String> jobObj = new HashMap<String, String>();
                for (Map.Entry<Object, Object> e : props.entrySet()) {
                    jobObj.put((String)e.getKey(), (String)e.getValue());
                }

//                Add the job to the Job Store
                store.addJob(jobObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
