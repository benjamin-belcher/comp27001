import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class RoundRobinScheduler implements Runnable, IJobEvents, INodeEvents{
    JobStore store;
    WorkerNodeStore nodeStore;
    private List<String> _orderedNodes = new ArrayList<String>();
    public List<Map<String, String>> jobs = new ArrayList<Map<String, String>>();
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
//        List<String> orderedNodes = OrderNodes();
//        System.out.println("Ordered nodes "+orderedNodes);
//        Needs to be a while true to cont
        if(jobs.size() == 0){
            jobs = store.getJobs();
        }
        ScheduleJobs();
        while (true) {
        }
    }

    @Override
    public void jobAdded(){
        jobs = store.getJobs();
        ScheduleJobs();
    }

    @Override
    public void removeJob(){

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

    public boolean ScheduleJobs(){
//        This list is a list of available nodes, taken into account each nodes weight ( capability to process taksks )
        List<String> orderedNodes = OrderNodes();
//        System.out.println("Ordered nodes "+orderedNodes);

//        Set up a counter to track the position of the node list
        int nodeIndex = 0;
        if(jobs.size() == 0){
//            System.out.println("There are no jobs to send...");
        } else {
            for (int i = 0; i < jobs.size(); i++) {
                //            Reset the counter if the index of the job list is the same as the last index in the node list
                if (nodeIndex == orderedNodes.size()) {
                    nodeIndex = 0;
                }

                String nodeName = orderedNodes.get(nodeIndex);
                String[] decomposedNodeName = nodeName.split("-");
                int nodeNumber = Integer.parseInt(decomposedNodeName[1]);
                Map<String, String> jobToSend = jobs.get(i);

                JobSenderThread sender = new JobSenderThread(nodeName, nodeNumber, jobToSend, store);
                nodeIndex++;
            }
        }
        store.clearJobs();
        return true;
    }
}
