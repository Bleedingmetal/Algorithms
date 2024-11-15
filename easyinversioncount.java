import java.util.Scanner;

public class easyinversioncount {
    public static int countInversionsNaive(int[] arr) {
        int count = 0;
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i] > arr[j]) count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of elements in the array (default is 8):");
        int n = scanner.hasNextInt() ? scanner.nextInt() : 8;
        int[] arr = new int[n];
        System.out.println("Enter " + n + " integers:");
        for (int i = 0; i < n; i++) arr[i] = scanner.nextInt();
        int inversions = countInversionsNaive(arr);
        System.out.println("The number of inversions is: " + inversions);
        scanner.close();
    }
}
