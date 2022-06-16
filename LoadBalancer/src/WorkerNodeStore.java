import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkerNodeStore {
    private List<Map<String, String>> nodes = new ArrayList<Map<String, String>>();
    private List<IJobEvents> listeners = new ArrayList<IJobEvents>();

    WorkerNodeStore(){}

    public void addNode(Map<String, String> name){
        nodes.add(name);
        System.out.println("Added Node " + name + " To Node store");
    }

    public void addListener(IJobEvents listener){
        listeners.add(listener);
    }

    public void clearNodes(){
        nodes.clear();
        System.out.println("Connection to nodes lost. Reset current nodes " + nodes);
    }

    public List<Map<String, String>> getNodes(){
        return nodes;
    }
}
