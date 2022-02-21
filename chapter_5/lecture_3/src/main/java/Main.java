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

       for (int i=0;i<20;i++)
           threadPoolExecutor.submit( new SleepingTask(i));

       threadPoolExecutor.shutdown();
       threadPoolExecutor.awaitTermination(1 , TimeUnit.MINUTES);
    }

    static class SleepingTask implements Runnable{
         private final int id;
         public SleepingTask (int id) { this.id= id;}

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
