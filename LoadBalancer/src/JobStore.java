import java.util.*;

public class JobStore {
    private List<Map<String, String>> jobs = new ArrayList<>();
    private List<IJobEvents> listeners = new ArrayList<IJobEvents>();

    JobStore(){
    }

    public void addListener(IJobEvents listener){
        listeners.add(listener);
    }

    public void clearJobs(){
        jobs.clear();
    }

//    Method to return all jobs in the store
    public List<Map<String, String>> getJobs(){
        return jobs;
    }

//    Method to add job to the store
    public void addJob(Map<String, String>job){
        jobs.add(job);
//        Publish the job added event
        for(IJobEvents clnt: listeners){
            clnt.jobAdded();
        }
        System.out.println("Added Job: " + job + " To Store");
    }

//    Method to remove a job from the store
    public synchronized void removeJob(Map<String, String> job){
        if(jobs.contains(job)){
            int jobIndex = jobs.indexOf(job);
            if(jobIndex != -1){
                jobs.remove(jobIndex);
//                Publish the job removed event
                for(IJobEvents clnt: listeners){
                    clnt.removeJob();
                }
                System.out.println("Removing Job " + job + " From the store");
            }

        }
    }
}
