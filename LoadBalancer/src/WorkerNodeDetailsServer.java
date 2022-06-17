import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class WorkerNodeDetailsServer implements Runnable{
    Thread t;
    WorkerNodeStore nodeStore;

    WorkerNodeDetailsServer(WorkerNodeStore _nodeStore){
        nodeStore = _nodeStore;
        t = new Thread(this, "WorkerNodeDertailsHandler");
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
            ServerSocket nodeDetailsServerSocket = createSocket(7071);
            Socket node;

            while(true){
                node = nodeDetailsServerSocket.accept();

//                Connect to the worker nodes and set up a buffer to store incomming requests
                InputStreamReader streamReader = new InputStreamReader(node.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                String message = reader.readLine();

                // Restore node details to a HashMap
                Properties props = new Properties();
                props.load(new StringReader(message.substring(1, message.length() - 1).replace(", ", "\n")));
                Map<String, String> map2 = new HashMap<String, String>();
                for (Map.Entry<Object, Object> e : props.entrySet()) {
                    map2.put((String)e.getKey(), (String)e.getValue());
                }

                nodeStore.addNode(map2);

                System.out.println("Current nodes in store: " + nodeStore.getNodes());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
