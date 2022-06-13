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
        System.out.println(t.getName() + " Has started to handle incoming jobs.");
        t.start();
    }

    @Override
    public void jobAdded(){
        jobs = store.getJobs();
    }

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

    public void handleNewClient(Socket client) throws IOException {
        PrintWriter writer = new PrintWriter(client.getOutputStream());
        while(true){
            for(int i = 0; i<store.getJobs().size(); i++){
                System.out.println("Sending: "+store.getJobs().get(i) + "to nodes");
                writer.println(store.getJobs().get(i));
                writer.flush();
            }
        }

    }

    @Override
    public void run(){
        try{
            ServerSocket ss = createSocket(7070);
            while(true){
                Socket newClient = ss.accept();

                handleNewClient(newClient);
            }
//            Socket s;
//            s = ss.accept();
//            if(s.isConnected()){
//                for(int i = 0; i<10; i++){
//                    System.out.println(i);
//                    PrintWriter writer = new PrintWriter(s.getOutputStream());
//                    writer.println("Hi " + i);
//                    writer.flush();
//
//                }
//            }
//            s.close();

//            JobStore store = new JobStore();

//            for(int i = 0; i<10; i++){
//                System.out.println(i);
//                s = ss.accept();
//                PrintWriter writer = new PrintWriter(s.getOutputStream());
//                writer.println("Hi " + i);
//                writer.flush();
//            }

//            while (store.getJobs().size() != 0) {
//                s = ss.accept();
//                System.out.println("Accepting connections");
//                store.sendJobs(s);
//            }
//            store.clearJobs();
//            //we need to store incomming requests to process, and return them
//        } catch (IOException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleInitatorRequests(){
        try{
            ServerSocket ss = createSocket(7777);
            Socket s;

            //we need to store incomming requests to process, and return them
            List<String> integerList = new LinkedList<>();
            while (true) {
                s = ss.accept();

                InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                String message = reader.readLine();

                // use properties to restore the map
                Properties props = new Properties();
                props.load(new StringReader(message.substring(1, message.length() - 1).replace(", ", "\n")));
                Map<String, String> map2 = new HashMap<String, String>();
                for (Map.Entry<Object, Object> e : props.entrySet()) {
                    map2.put((String)e.getKey(), (String)e.getValue());
                }

                System.out.println("Back to map job: "+map2);

                PrintWriter writer = new PrintWriter(s.getOutputStream());
                integerList.add(message);
                writer.println(integerList);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
