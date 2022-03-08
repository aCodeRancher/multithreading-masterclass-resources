import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelThreadCreator {
    // creating 10 threads and waiting for them to complete then again repeat steps.
    public static void multiply(int[][] matrix1, int[][] matrix2, int[][] result) {
        //This machine has 8 CPU cores.
        int parallelism = 8;
        ExecutorService threadPool = Executors.newFixedThreadPool(parallelism);
        int rows1 = matrix1.length;
        for (int i = 0; i < rows1; i++) {
            for (int j=0;j<matrix2[0].length;j++) {
                RowMultiplyWorker task = new RowMultiplyWorker(result, matrix1, matrix2, i, j);
                threadPool.submit(task);
            }
        }
        threadPool.shutdown();
    }

}
