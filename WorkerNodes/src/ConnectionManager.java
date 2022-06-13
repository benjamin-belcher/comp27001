import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConnectionManager implements Runnable{
    private Socket loadBalancerConnection;
    Thread t;
    ConnectionManager(){
        t = new Thread(this, "ConnectionManager");
        System.out.println("Connection Manager Instantiated");
        t.start();
    }

    public Socket connectToLoadBalancer(String hostname, int port){
        try{
            loadBalancerConnection = new Socket(hostname, port);
            System.out.println("Connected to load balancer...");
            return loadBalancerConnection;
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public String disconnectFromLoadBalancer(){
        try{
            loadBalancerConnection.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void run(){
        Socket loadBalancerConnection = connectToLoadBalancer("localhost", 7070);
        try {
            while(true) {
                InputStreamReader streamReader = new InputStreamReader(loadBalancerConnection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                String message = reader.readLine();

//                if(message != null){
//                    System.out.println(message);
//                }
                System.out.println(message);


                // use properties to restore the map
//                Properties props = new Properties();
//                props.load(new StringReader(message.substring(1, message.length() - 1).replace(", ", "\n")));
//                Map<String, String> map2 = new HashMap<String, String>();
//                for (Map.Entry<Object, Object> e : props.entrySet()) {
//                    map2.put((String) e.getKey(), (String) e.getValue());
//                }
//
//                System.out.println("Retrieved: " + map2 + " From Server");


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
