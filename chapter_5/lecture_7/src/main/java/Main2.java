import java.util.concurrent.*;

public class Main2 {
    public static void main(String[] args) throws InterruptedException{
        ExecutorService executorService = Executors.newFixedThreadPool(5, new MyThreadFactory());

        for (int i=0;i<50000;i++) {
           Future<?> future= executorService.submit(new Main2.SleepingTask(i));

        }
        executorService.awaitTermination(20, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println("shutting down");

        Thread.sleep(30000);

        executorService.shutdownNow();
        System.out.println("Shutting down now");
    }

    static class MyThreadFactory implements ThreadFactory {


        @Override
        public Thread newThread(Runnable r){
             Thread t = new Thread(r);
              t.setPriority(1);
             return t;
        }
    }

    static class SleepingTask implements Runnable{

        private int id;

        public SleepingTask(int id){
            this.id= id;
        }
        @Override
        public void run(){
            try{
                System.out.println("Thread : " + id + " with priority: "+ Thread.currentThread().getPriority()
                        + " Task and then wait for a while");
                Thread.sleep(5000);
            }
            catch (InterruptedException e){}
        }
    }
}
