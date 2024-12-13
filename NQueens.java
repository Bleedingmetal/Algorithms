import java.util.ArrayList;
import java.util.List;

public class NQueens {

    public static boolean isLegalPosition(int[] board, int n) {
        for (int i = 0; i < n; i++) {
            if (board[i] == 0) continue;
            for (int j = i + 1; j < n; j++) {
                if (board[j] == 0) continue;
                if (board[i] == board[j] || Math.abs(board[i] - board[j]) == Math.abs(i - j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[] NextLegalPosition(int[] board, int n) {
        int[] newBoard = board.clone();
        for (int i = n - 1; i >= 0; i--) {
            if (newBoard[i] < n) {
                newBoard[i]++;
                if (isLegalPosition(newBoard, n)) {
                    return newBoard;
                }
            }
            newBoard[i] = 0;
        }
        return new int[n];
    }

    public static int[] findFirstSolution(int n) {
        int[] board = new int[n];
        while (true) {
            if (isLegalPosition(board, n)) {
                boolean isComplete = true;
                for (int i = 0; i < n; i++) {
                    if (board[i] == 0) {
                        isComplete = false;
                        break;
                    }
                }
                if (isComplete) return board;
            }
            board = NextLegalPosition(board, n);
            if (isEmptyBoard(board)) break;
        }
        return new int[n];
    }

    public static List<int[]> findAllSolutions(int n) {
        List<int[]> solutions = new ArrayList<>();
        int[] board = new int[n];
        while (true) {
            if (isLegalPosition(board, n)) {
                boolean isComplete = true;
                for (int i = 0; i < n; i++) {
                    if (board[i] == 0) {
                        isComplete = false;
                        break;
                    }
                }
                if (isComplete) solutions.add(board.clone());
            }
            board = NextLegalPosition(board, n);
            if (isEmptyBoard(board)) break;
        }
        return solutions;
    }

    private static boolean isEmptyBoard(int[] board) {
        for (int value : board) {
            if (value != 0) return false;
        }
        return true;
    }

    public static int countSolutions(int n) {
        int[] board = new int[n];
        boolean[] cols = new boolean[n];
        boolean[] leftDiagonal = new boolean[2 * n - 1];
        boolean[] rightDiagonal = new boolean[2 * n - 1];
        return countNQueens(0, board, cols, leftDiagonal, rightDiagonal, n);
    }

    private static int countNQueens(int row, int[] board, boolean[] cols, boolean[] leftDiagonal, boolean[] rightDiagonal, int n) {
        if (row == n) return 1;

        int count = 0;
        for (int col = 0; col < n; col++) {
            if (!cols[col] && !leftDiagonal[row - col + n - 1] && !rightDiagonal[row + col]) {
                board[row] = col + 1;
                cols[col] = leftDiagonal[row - col + n - 1] = rightDiagonal[row + col] = true;

                count += countNQueens(row + 1, board, cols, leftDiagonal, rightDiagonal, n);

                board[row] = 0;
                cols[col] = leftDiagonal[row - col + n - 1] = rightDiagonal[row + col] = false;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        for (int n = 4; n <= 100; n++) {
            int[] firstSolution = findFirstSolution(n);
            System.out.println(n + "-Queens first solution: " + java.util.Arrays.toString(firstSolution));

            if (n == 32 || n == 36 || n == 15) {
                int solutions = countSolutions(n);
                System.out.println("n = " + n + ", Solutions = " + solutions);
            }
        }

        for (int n = 4; n <= 12; n++) {
            int solutionCount = countSolutions(n);
            System.out.println("Total solutions for n = " + n + ": " + solutionCount);
        }
    }
}
