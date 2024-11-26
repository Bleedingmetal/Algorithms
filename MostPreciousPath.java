import java.util.Arrays;

public class MostPreciousPath {

    public static void main(String[] args) {
        int[][] gemstones = {
                {84, 99, 68, 75, 98, 44, 33, 96},
                {93, 53, 24, 46, 86, 1, 41, 10},
                {7, 30, 51, 65, 27, 94, 97, 83},
                {12, 67, 88, 22, 64, 47, 71, 56},
                {15, 92, 70, 13, 48, 77, 11, 91},
                {63, 16, 4, 31, 25, 17, 59, 32},
                {74, 40, 37, 79, 23, 14, 5, 78},
                {21, 95, 20, 82, 66, 52, 89, 35}
        };

        int rows = gemstones.length;
        int cols = gemstones[0].length;
        int[][] dp = new int[rows][cols];
        int[][] path = new int[rows][cols];

        for (int j = 0; j < cols; j++) {
            dp[rows - 1][j] = gemstones[rows - 1][j];
        }

        for (int i = rows - 2; i >= 0; i--) {
            for (int j = 0; j < cols; j++) {
                int maxGemstones = dp[i + 1][j];
                path[i][j] = j;

                if (j > 0 && dp[i + 1][j - 1] > maxGemstones) {
                    maxGemstones = dp[i + 1][j - 1];
                    path[i][j] = j - 1;
                }

                if (j < cols - 1 && dp[i + 1][j + 1] > maxGemstones) {
                    maxGemstones = dp[i + 1][j + 1];
                    path[i][j] = j + 1;
                }

                dp[i][j] = gemstones[i][j] + maxGemstones;
            }
        }

        int maxGems = 0;
        int startCol = 0;
        for (int j = 0; j < cols; j++) {
            if (dp[0][j] > maxGems) {
                maxGems = dp[0][j];
                startCol = j;
            }
        }

        int[] pathTaken = new int[rows];
        pathTaken[0] = startCol;
        for (int i = 1; i < rows; i++) {
            pathTaken[i] = path[i - 1][pathTaken[i - 1]];
        }

        System.out.println("Starting square: Column " + (startCol + 1));
        System.out.println("Path taken: " + Arrays.toString(pathTaken));
        System.out.println("Total gemstones collected: " + maxGems);
        System.out.println("Vault number: " + (pathTaken[rows - 1] + 1));
    }
}
