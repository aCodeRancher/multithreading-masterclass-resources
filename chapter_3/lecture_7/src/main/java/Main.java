import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

    private static Semaphore semaphore = new Semaphore(8);
    private static  int globalcounter;
    public static void main(String[] args) throws InterruptedException {

        Executor executor = new Executor();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i< 10; i++) {
            Thread t =executor.submit( );
            t.start();
            threadList.add(t);
        }
       threadList.forEach( t -> {
               try{
                   t.join();
                }
               catch(InterruptedException e){}});

        System.out.println(" global counter: "+ globalcounter);
    }

    static class Executor {
        public Thread submit( ) throws InterruptedException {
             semaphore.acquire();
             Thread t = new Thread(() -> {
                try {
                      Thread.sleep(1);
                       Main.globalcounter++;
                      semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

           return t;
        }
    }
}
