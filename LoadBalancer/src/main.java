public class main {
    static JobStore store = new JobStore();
    static WorkerNodeStore nodeStore = new WorkerNodeStore();

    public static void main(String arg[]) {
        System.out.println("Load Balancer Started: ");
        new InitiatorServer(store);
        WorkerNodeServer nodeServer = new WorkerNodeServer(store, nodeStore);
        WorkerNodeDetailsServer nodeDetailsServer = new WorkerNodeDetailsServer(store, nodeStore);
        RoundRobinScheduler roundRobin = new RoundRobinScheduler(nodeStore.getNodes(), store.getJobs());
        nodeStore.addListener(roundRobin);
        store.addListener(nodeServer);
        nodeStore.addListener(nodeServer);

    }
}
