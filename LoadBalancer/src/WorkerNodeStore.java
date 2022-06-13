import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkerNodeStore {
    private List<Map<String, String>> nodes = new ArrayList<Map<String, String>>();
    private List<IJobEvents> listeners = new ArrayList<IJobEvents>();

    WorkerNodeStore(){}

    public void addNode(Map<String, String> name){
        nodes.add(name);
    }

    public void addListener(IJobEvents listener){
        listeners.add(listener);
    }

    public List<Map<String, String>> getNodes(){
        return nodes;
    }
}
