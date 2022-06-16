import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class WorkerNodeServer implements Runnable, IJobEvents, INodeEvents{
    Thread t;
    JobStore store;
    WorkerNodeStore nodeStore;
    private List<Map<String, String>> nodes = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> jobs = new ArrayList<Map<String, String>>();

    WorkerNodeServer(JobStore _store, WorkerNodeStore _nodeStore){
        store = _store;
        nodeStore = _nodeStore;
        t = new Thread(this, "WorkerNodeHandler");
        System.out.println(t.getName() + " Is ready to start the load balancer");
        t.start();
    }

    @Override
    public void jobAdded(){
        jobs = store.getJobs();
    }

    @Override
    public void removeJob(){}

    @Override
    public void nodeAdded(){
        nodes = nodeStore.getNodes();
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
            ServerSocket ss = createSocket(7070);
            while(true){
                Socket newClient = ss.accept();
                System.out.println("Connected to Worker Nodes " + newClient);

                InputStreamReader streamReader = new InputStreamReader(newClient.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                String message = reader.readLine();

                if(message != null){
                    // use properties to restore the map
                    Properties props = new Properties();
                    props.load(new StringReader(message.substring(1, message.length() - 1).replace(", ", "\n")));
                    Map<String, String> map2 = new HashMap<String, String>();
                    for (Map.Entry<Object, Object> e : props.entrySet()) {
                        map2.put((String)e.getKey(), (String) e.getValue());
                    }

                    if(Boolean.parseBoolean(map2.get("start"))){
                        RoundRobinScheduler rr = new RoundRobinScheduler(store, nodeStore);
//                        store.addListener(rr);
                    }
                } else{
                    nodeStore.clearNodes();
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
