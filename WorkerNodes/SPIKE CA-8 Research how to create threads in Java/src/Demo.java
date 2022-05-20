public class Demo {
    public static void main(String args[]){
        new ThreadDemo();
        try{
            System.out.println("Main Thread");
            Thread.sleep(100);
        } catch(InterruptedException e){
            System.out.println("The main thread is interrupted");
        }
        System.out.println("Exiting the main thread");
    }
}
