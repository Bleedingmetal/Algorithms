import java.util.Scanner;

public class LucasSequence {
    public static int lucas(int n) {
        if (n == 0) return 2;
        if (n == 1) return 1;
        return lucas(n - 1) + lucas(n - 2);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the maximum value of n to compute Lucas numbers: ");
        int maxN = scanner.nextInt();

        int prevLucas = lucas(0);
        long prevTime = System.nanoTime();

        System.out.printf("%-5s %-15s %-20s %-20s %-20s\n", "n", "L(n)", "Time (ns)", "L(n+1)/L(n)", "Time Ratio");

        for (int n = 0; n <= maxN; n++) {
            long startTime = System.nanoTime();
            int currentLucas = lucas(n);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;

            String lucasRatio = (n > 0) ? String.format("%.5f", (double) currentLucas / prevLucas) : "N/A";
            String timeRatio = (n > 0) ? String.format("%.5f", (double) duration / prevTime) : "N/A";

            System.out.printf("%-5d %-15d %-20d %-20s %-20s\n", n, currentLucas, duration, lucasRatio, timeRatio);

            prevLucas = currentLucas;
            prevTime = duration;
        }
    }
}
