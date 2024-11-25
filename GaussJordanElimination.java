import java.util.*;

public class GaussJordanElimination {

    public static void gaussJordanElimination(double[][] augmentedMatrix) {
        int n = augmentedMatrix.length;

        for (int i = 0; i < n; i++) {
            // Step 1: Partial Pivoting
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(augmentedMatrix[k][i]) > Math.abs(augmentedMatrix[maxRow][i])) {
                    maxRow = k;
                }
            }

            double[] temp = augmentedMatrix[i];
            augmentedMatrix[i] = augmentedMatrix[maxRow];
            augmentedMatrix[maxRow] = temp;


            System.out.println("Matrix after row swap for pivoting at row " + i + ":");
            printMatrix(augmentedMatrix);


            double pivot = augmentedMatrix[i][i];
            if (pivot == 0) {
                System.out.println("Matrix is singular or near-singular! Cannot proceed.");
                return;
            }
            for (int j = 0; j <= n; j++) {
                augmentedMatrix[i][j] /= pivot;
            }


            System.out.println("Matrix after normalizing pivot row " + i + ":");
            printMatrix(augmentedMatrix);

            //elimination of the other rows but like we skip the pivot rows coz otherwise it is stupid or smth

            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmentedMatrix[k][i];
                    for (int j = 0; j <= n; j++) {
                        augmentedMatrix[k][j] -= factor * augmentedMatrix[i][j];
                    }
                }
            }


            System.out.println("Matrix after elimination for row " + i + ":");
            printMatrix(augmentedMatrix);
        }
    }


    private static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }


    private static boolean validateSolution(double[][] augmentedMatrix, double[] solution) {
        for (int i = 0; i < augmentedMatrix.length; i++) {
            double sum = 0;
            for (int j = 0; j < augmentedMatrix[0].length - 1; j++) {
                sum += augmentedMatrix[i][j] * solution[j];
            }
            if (Math.abs(sum - augmentedMatrix[i][augmentedMatrix[0].length - 1]) > 1e-6) {
                return false;

        }
        return true;
    }

    public static void main(String[] args) {

            double[][] augmentedMatrix = {
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2047},
                    {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3},
                    {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 12},
                    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 48},
                    {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 384},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1536},
                    {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 5},
                    {0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 50},
                    {0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1952},
                    {11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 4083},
                    {11, -10, 9, -8, 7, -6, 5, -4, 3, -2, 1, 459}
            };


            gaussJordanElimination(augmentedMatrix);


        double[] solution = new double[augmentedMatrix.length];
        for (int i = 0; i < augmentedMatrix.length; i++) {
            solution[i] = augmentedMatrix[i][augmentedMatrix[0].length - 1];
            solution[i] = Math.round(solution[i]);
        }


        System.out.println("Solution:");
        for (int i = 0; i < solution.length; i++) {
            System.out.printf("x%d = %.0f\n", i, solution[i]);
        }


        if (validateSolution(augmentedMatrix, solution)) {
            System.out.println("Solution validated successfully!");
        } else {
            System.out.println("Solution validation failed. Check your implementation.");
        }
    }
}
