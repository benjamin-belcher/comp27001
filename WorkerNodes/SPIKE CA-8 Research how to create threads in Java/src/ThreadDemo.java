import java.util.concurrent.TimeUnit;

class ThreadDemo implements  Runnable {
    Thread t;
    ThreadDemo(){
        t = new Thread(this, "Thread");
        System.out.println("Thread Created: "+t);
        t.start();
    }
    public void run(){
        boolean running = true;
        while(running){
//            Here the running varible is changed to the return value of doWork
            running = doWork();
            if(Thread.interrupted()){
                return;
            }
        }
    }

//    This is a dummy method and will be replaced by the initiator when we add it but the method content can be re-used
    public boolean doWork(){
        for(int i = 0; i<100; i++){
            try{
                TimeUnit.MILLISECONDS.sleep(500);
            } catch(InterruptedException e){
                System.out.println("For loop interruppted on loop "+i);
            }
            System.out.println("Loop instance "+i);
        }
        return false;
    }

}
