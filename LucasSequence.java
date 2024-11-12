import java.util.Scanner;

public class LucasSequence {
    public static int lucas(int n) {
        if (n == 0) return 2;
        if (n == 1) return 1;
        return lucas(n - 1) + lucas(n - 2);
    }

    public static int customSequence(int n, int n0, int n1) {
        if (n == 0) return n0;
        if (n == 1) return n1;
        return customSequence(n - 1, n0, n1) + customSequence(n - 2, n0, n1);
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

        System.out.print("Enter initial values for the custom sequence (n0 and n1): ");
        int n0 = scanner.nextInt();
        int n1 = scanner.nextInt();

        int prevCustom = customSequence(0, n0, n1);
        prevTime = System.nanoTime();

        System.out.printf("\n%-5s %-15s %-20s %-20s %-20s\n", "n", "N(n)", "Time (ns)", "N(n+1)/N(n)", "Time Ratio");

        for (int n = 0; n <= maxN; n++) {
            long startTime = System.nanoTime();
            int currentCustom = customSequence(n, n0, n1);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;

            String customRatio = (n > 0) ? String.format("%.5f", (double) currentCustom / prevCustom) : "N/A";
            String timeRatio = (n > 0) ? String.format("%.5f", (double) duration / prevTime) : "N/A";

            System.out.printf("%-5d %-15d %-20d %-20s %-20s\n", n, currentCustom, duration, customRatio, timeRatio);

            prevCustom = currentCustom;
            prevTime = duration;
        }
    }
}
