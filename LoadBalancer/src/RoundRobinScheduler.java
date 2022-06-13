import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoundRobinScheduler implements Runnable, IJobEvents, INodeEvents{
    private List<Map<String, String>> _nodes;
    private List<String> _orderedNodes = new ArrayList<String>();
    private List<Map<String, String>> _jobs;
    Thread t;
    RoundRobinScheduler(List<Map<String, String>> nodes, List<Map<String, String>> jobs){
        t = new Thread(this, "RoundRobinThread");
        _jobs = jobs;
        _nodes = nodes;
        System.out.println("Round Robin Scheduler has started");
        t.start();
    }

    public void run(){
        while(true){
            if(_orderedNodes.size() != 0){
                ScheduleJobs();
            }
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
        for(Map<String, String> job: _jobs){
            for(int n = 0; n<Integer.parseInt(job.get("weight")); n++){
                _orderedNodes.add(job.get("name"));
            }
        }
        return _orderedNodes;
    }

    public void ScheduleJobs(){
        List<String> nodes = OrderNodes();
        System.out.println("Ordered nodes "+nodes);
    }
}
