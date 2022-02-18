import java.util.*;
import java.util.concurrent.Phaser;
import java.util.concurrent.locks.ReentrantLock;


public class Exercise {
    private static int finalSum = 0;

    private static int[] array = new int[100];
    private static Phaser phaser = new Phaser();
    private static ReentrantLock lock = new ReentrantLock();

    public static void main (String... args) throws InterruptedException{
        System.out.println( Exercise.exchangeValues());
    }
    public static int exchangeValues() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new WorkerThread(i));
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }

      return finalSum;
    }

    static class WorkerThread implements Runnable {

        private final int id;
        private int receivedValuesSum;
        private int sum;
        public WorkerThread(int id) {
            this.id = id;
            this.receivedValuesSum = id;
            phaser.register();
        }

        @Override
        public void run() {
            //each thread puts its value to fill in the array.
            array[this.id] = receivedValuesSum;
            //each thread waits for others to complete.
            phaser.arriveAndAwaitAdvance();
            for (int j=0;j<array.length;j++) {
                    sum = sum + array[j];
             }
            //after each thread adds up its array elements,
            //it adds up the final sum
            lock.lock();
            finalSum = finalSum + sum;
            lock.unlock();
            phaser.arriveAndDeregister();
       }
    }
}
