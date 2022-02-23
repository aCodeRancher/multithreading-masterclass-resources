import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(4);
       // threadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        threadPoolExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
       //  threadPoolExecutor.schedule(() -> System.out.println("Task"), 5, TimeUnit.SECONDS);
        for (int i=0;i<50000;i++) {
            ScheduledFuture<?> future = threadPoolExecutor.schedule(new SleepingTask(i), 5, TimeUnit.SECONDS);

        }

        // threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(20, TimeUnit.SECONDS);
        threadPoolExecutor.shutdown ();
        System.out.println("shutting down");

        Thread.sleep(30000);

        //threadPoolExecutor.awaitTermination(50, TimeUnit.SECONDS);
        threadPoolExecutor.shutdownNow();
        System.out.println("Shutting down now");

    //   threadPoolExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);

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
