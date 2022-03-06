import java.util.Arrays;
import java.util.Date;
public class Main1 {
    public static void main(String[] args) {

        Date start = new Date();

        int[][] m1 = MatrixGeneratorUtil.generateMatrix(3, 3);
        int[][] m2 = MatrixGeneratorUtil.generateMatrix(3, 3);

        for (int i = 0;i<3;i++){
            for (int j=0;j<3;j++){
                System.out.print(m1[i][j] + " ");
            }
            System.out.println(" ");
        }
        System.out.println(" ");
        for (int i = 0;i<3;i++){
            for (int j=0;j<3;j++){
                System.out.print(m2[i][j] + " ");
            }
            System.out.println(" ");
        }

        int[][] result = new int[m1.length][m2[0].length];
        ParallelThreadCreator.multiply(m1, m2, result);

        Date end = new Date();
        System.out.println("\nTime taken in milli seconds: " + (end.getTime() - start.getTime()));


        for (int i = 0;i<3;i++){
            for (int j=0;j<3;j++){
                System.out.print(result[i][j] + " ");
            }
            System.out.println(" ");
        }

    }
}
