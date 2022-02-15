import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

public class Main {

    private static int[][] array = {
            {1, 2, 3, 1},
            {2, 1, 2, 1},
            {1, 2, 1, 3},
            {1, 1, 1, 2},
    };

    private static int[][] outputArray = {
            {1, 2, 3, 1},
            {9, 8, 9, 8},
            {35, 36, 35, 37},
            {144, 144, 144, 145},
    };

    private static  Phaser phaser = new Phaser(4 );
    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Thread t = new Thread(new WorkerThread(i));
            t.start();
            threads.add(t);
        }

        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("The final array: " + Arrays.deepToString(array));
    }

    static class WorkerThread implements Runnable {

        private final int columnId;

        public WorkerThread(int columnId) {this.columnId = columnId; }

        @Override
        public void run() {
            for (int i = 1; i < 4; i++) {
                int S = 0;

                for (int j = 0; j < 4; j++) {
                    S = S + array[i - 1][j];
                }

                array[i][columnId] = array[i][columnId] + S;
                //wait for the rest of the threads to complete
                phaser.arriveAndAwaitAdvance();
            }
            //de-register the phase when this thread finishes its tasks :
            //calculate the new values for its column.
            phaser.arriveAndDeregister();
        }
    }
}