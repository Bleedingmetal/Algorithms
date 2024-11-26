public class MostPreciousPath {

    // give me the ring my precious - pls gimme bonus points for hobbit reference
    // I have read the books bonus points for hobbit slide em over fr .
    public static void main(String[] args) {
        int[][] gems = {
                {84, 99, 68, 75, 98, 44, 33, 96},
                {93, 53, 24, 46, 86, 1, 41, 10},
                {7, 30, 51, 65, 27, 94, 97, 83},
                {12, 67, 88, 22, 64, 47, 71, 56},
                {15, 92, 70, 13, 48, 77, 11, 91},
                {63, 16, 4, 31, 25, 17, 59, 32},
                {74, 40, 37, 79, 23, 14, 5, 78},
                {21, 95, 20, 82, 66, 52, 89, 35}
        };

        int rows = gems.length;
        int cols = gems[0].length;
        int[][] dp = new int[rows][cols];
        int[][] path = new int[rows][cols];

        for (int col = 0; col < cols; col++) {
            dp[rows - 1][col] = gems[rows - 1][col];
        }

        for (int row = rows - 2; row >= 0; row--) {
            for (int col = 0; col < cols; col++) {
                int maxGems = dp[row + 1][col];
                int move = col;

                if (col > 0 && dp[row + 1][col - 1] > maxGems) {
                    maxGems = dp[row + 1][col - 1];
                    move = col - 1;
                }

                if (col < cols - 1 && dp[row + 1][col + 1] > maxGems) {
                    maxGems = dp[row + 1][col + 1];
                    move = col + 1;
                }

                dp[row][col] = gems[row][col] + maxGems;
                path[row][col] = move;
            }
        }

        int maxGems = dp[0][0];
        int startCol = 0;
        for (int col = 1; col < cols; col++) {
            if (dp[0][col] > maxGems) {
                maxGems = dp[0][col];
                startCol = col;
            }
        }

        int[] finalPath = new int[rows];
        int col = startCol;
        for (int row = 0; row < rows; row++) {
            finalPath[row] = col;
            col = path[row][col];
        }

        System.out.println("Starting square row 1 obviously and (column): " + (startCol + 1));
        System.out.print("Path: ");
        for (int row = 0; row < rows; row++) {
            System.out.print("(" + (row + 1) + "," + (finalPath[row] + 1) + ") ");
        }
        System.out.println();
        System.out.println("Total gemstones collected: " + maxGems);
        System.out.println("Vault containing the Arkenstone: " + (finalPath[rows - 1] + 1));
    }
}
