import java.util.concurrent.*;

public class Main1 {
    public static void main(String[] args) throws InterruptedException{
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i=0;i<50000;i++) {
            Future future = executorService.submit(new Main1.SleepingTask(i));

        }
        executorService.awaitTermination(20, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println("shutting down");

        Thread.sleep(30000);

        executorService.shutdownNow();
        System.out.println("Shutting down now");
   }

    static class SleepingTask implements Runnable{

        private int id;

        public SleepingTask(int id){
            this.id= id;
        }
        @Override
        public void run(){
            try{
                System.out.println("Thread : " + id + " Task and then wait for a while");
                Thread.sleep(5000);
            }
            catch (InterruptedException e){}
        }
    }

}
