import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoundRobinScheduler implements Runnable, IJobEvents, INodeEvents{
    JobStore store;
    WorkerNodeStore nodeStore;
    private List<String> _orderedNodes = new ArrayList<String>();
    private List<Map<String, String>> jobs = new ArrayList<Map<String, String>>();
    private List<Map<String, String>> nodes = new ArrayList<Map<String, String>>();
    Thread t;
    RoundRobinScheduler(JobStore _store, WorkerNodeStore _nodeStore){
        t = new Thread(this, "RoundRobinThread");
        store = _store;
        nodeStore = _nodeStore;
        System.out.println("Round Robin Scheduler has started");
        t.start();
    }

    public void run(){
        nodes = nodeStore.getNodes();
        while(true){
            if(jobs.size() == 0){
                jobs = store.getJobs();
            }
            ScheduleJobs();
            break;
        }
    }

    @Override
    public void jobAdded(){

    }

    @Override
    public void nodeAdded(){
        System.out.println("Node added in RR");
        OrderNodes();
    }

    public List<String> OrderNodes(){
        for(Map<String, String> node: nodes){
            for(int n = 0; n<Integer.parseInt(node.get("weight")); n++){
                _orderedNodes.add(node.get("name"));
            }
        }
        return _orderedNodes;
    }

    public void ScheduleJobs(){
        List<String> nodes = OrderNodes();
        System.out.println("Ordered nodes "+nodes);
        int nodeIndex = 0;
        for(int i = 0; i<jobs.size(); i++){
            if(nodeIndex == nodes.size()){
                nodeIndex = 0;
            }
            String nodeName = nodes.get(nodeIndex);

            String[] decomposedNodeName = nodeName.split("-");
            int nodeNumber = Integer.parseInt(decomposedNodeName[1]);
            try{
                Socket nodeConnection = new Socket("localhost",5000+nodeNumber);
                PrintWriter writer = new PrintWriter(nodeConnection.getOutputStream());
                writer.println(jobs.get(i));
                System.out.println("Sending Job : "+jobs.get(i) + " to Node " + nodeName);
                writer.flush();
            } catch(IOException e){
                e.printStackTrace();
            }
            nodeIndex ++;
        }
    }
}
