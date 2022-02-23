import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,
                1,
                1,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(2),
                new ThreadPoolExecutor.DiscardOldestPolicy()

         );

        threadPoolExecutor.submit(new SleepingTask(1));
        threadPoolExecutor.submit(new SleepingTask(2));

        System.out.println("[1] Pool size: " + threadPoolExecutor.getPoolSize());

        //These two tasks are in the queue waiting.
        threadPoolExecutor.submit(new SleepingTask(3));
        threadPoolExecutor.submit(new SleepingTask(4));

        //create one more thread as the queue is full now.
        threadPoolExecutor.submit(new SleepingTask(5));
        System.out.println("[2] Pool size: " + threadPoolExecutor.getPoolSize());
        //queue is still full and no more thread can be created. It is rejected.

        threadPoolExecutor.submit(new SleepingTask(6));
    }

    static class SleepingTask implements Runnable {

        private final int id;

        public SleepingTask(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(70000);
                System.out.println("Thread " + id  + " runs.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
