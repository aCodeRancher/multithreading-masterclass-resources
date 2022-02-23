import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException{
         ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                 2,
                 5,
                 1,
                 TimeUnit.MINUTES,
                 new ArrayBlockingQueue<>(3)
         );

       for (int i=0;i<8;i++) {
           SleepingTask task = new SleepingTask(i);
           threadPoolExecutor.submit(task);
           System.out.println("Thread " + task.getId()  + " Thread pool size: " + threadPoolExecutor.getPoolSize());
           System.out.println("Thread " + task.getId()  + " Active count: " + threadPoolExecutor.getActiveCount());
           System.out.println("Thread " + task.getId() + " task count: " + threadPoolExecutor.getTaskCount());

       }
       threadPoolExecutor.shutdown();
       threadPoolExecutor.awaitTermination(1 , TimeUnit.MINUTES);
        System.out.println( "completed task count: "+ threadPoolExecutor.getCompletedTaskCount());
    }

    static class SleepingTask implements Runnable{
         private final int id;
         public SleepingTask (int id) { this.id= id;}

         public int getId() {
           return id;
         }
         @Override
         public void run(){
             try{
                 System.out.println("Task "+ id);
                 Thread.sleep(3000);
             }
             catch(InterruptedException e){
                 e.printStackTrace();
             }
         }
    }
}
