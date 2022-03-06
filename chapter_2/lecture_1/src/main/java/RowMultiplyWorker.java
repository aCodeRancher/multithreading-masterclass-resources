public class RowMultiplyWorker implements Runnable {

        private final  int[][] result;
        private int[][] matrix1;
        private int[][] matrix2;
        private final int row;
        private final int col;
    public RowMultiplyWorker(int[][] result, int[][] matrix1, int[][] matrix2, int row, int col) {
            this.result = result;
            this.matrix1 = matrix1;
            this.matrix2 = matrix2;
            this.row = row;
            this.col = col;

        }

        @Override
        public void run() {
            result[row][col]=0;
            for (int i = 0; i < matrix1.length; i++) {
                 result[row][col] += matrix1[row][i] * matrix2[i][col];
                }
        }
}
