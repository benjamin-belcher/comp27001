import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server implements Runnable{
    Thread t;

    Server(){
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
