import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
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

    public List<Map<String, String>> getJobs(){
        return jobs;
    }

    public void addJob(Map<String, String>job){
        jobs.add(job);
        for(IJobEvents clnt: listeners){
            clnt.jobAdded();
        }
        System.out.println("Added Job: " + job + " To Store");
    }

    public void sendJobs(Socket target) throws IOException {
        PrintWriter writer = new PrintWriter(target.getOutputStream());

        for(int i =0; i<jobs.size(); i++){
            writer.println(jobs.get(i));
            writer.flush();
        }
    }

    public synchronized void removeJob(Map<String, String> job){
        if(jobs.contains(job)){
            int jobIndex = jobs.indexOf(job);
            if(jobIndex != -1){
                jobs.remove(jobIndex);
                for(IJobEvents clnt: listeners){
                    clnt.removeJob();
                }
                System.out.println("Removing Job " + job + " From the store");
            }

        }
    }

    public void viewJobs(){
        for(int i = 0; i<jobs.size(); i++){
            System.out.println(jobs.get(i));
        }
    }
}
