import java.util.ArrayList;
import java.util.HashMap;

public class SubirachsMagicSquare {
    private static final int[][] magicSquare = {
            {4, 9, 2},
            {3, 5, 7},
            {8, 1, 6}
    };

    public static void main(String[] args) {
        int targetSum = 15;
        int count4ElementCombinations = countCombinations(4, targetSum);
        System.out.println("Count of 4-element combinations with sum " + targetSum + ": " + count4ElementCombinations);

        int countAllCombinations = countAllCombinations(targetSum);
        System.out.println("Count of all combinations with sum " + targetSum + ": " + countAllCombinations);

        HashMap<Integer, Integer> sumOccurrences = countAllPossibleSums();
        int mostCommonSum = 0;
        int mostCommonCount = 0;

        for (var entry : sumOccurrences.entrySet()) {
            if (entry.getValue() > mostCommonCount) {
                mostCommonCount = entry.getValue();
                mostCommonSum = entry.getKey();
            }
            System.out.println("Sum " + entry.getKey() + " occurs " + entry.getValue() + " times.");
        }

        System.out.println("Most common sum: " + mostCommonSum + " with " + mostCommonCount + " occurrences.");
    }

    public static int countCombinations(int numElements, int targetSum) {
        ArrayList<Integer> elements = getElementsFromSquare();
        return countCombinationsRecursive(elements, numElements, targetSum, 0);
    }

    private static int countCombinationsRecursive(ArrayList<Integer> elements, int numElements, int targetSum, int index) {
        if (targetSum == 0 && numElements == 0) return 1;
        if (targetSum < 0 || numElements == 0 || index >= elements.size()) return 0;
        int countIncluding = countCombinationsRecursive(elements, numElements - 1, targetSum - elements.get(index), index + 1);
        int countExcluding = countCombinationsRecursive(elements, numElements, targetSum, index + 1);
        return countIncluding + countExcluding;
    }

    public static int countAllCombinations(int targetSum) {
        ArrayList<Integer> elements = getElementsFromSquare();
        return countAllCombinationsRecursive(elements, targetSum, 0);
    }

    private static int countAllCombinationsRecursive(ArrayList<Integer> elements, int targetSum, int index) {
        if (targetSum == 0) return 1;
        if (targetSum < 0 || index >= elements.size()) return 0;
        int countIncluding = countAllCombinationsRecursive(elements, targetSum - elements.get(index), index + 1);
        int countExcluding = countAllCombinationsRecursive(elements, targetSum, index + 1);
        return countIncluding + countExcluding;
    }

    public static HashMap<Integer, Integer> countAllPossibleSums() {
        ArrayList<Integer> elements = getElementsFromSquare();
        HashMap<Integer, Integer> sumOccurrences = new HashMap<>();
        int maxSum = elements.stream().mapToInt(Integer::intValue).sum();
        for (int targetSum = 0; targetSum <= maxSum; targetSum++) {
            int count = countAllCombinations(targetSum);
            sumOccurrences.put(targetSum, count);
        }
        return sumOccurrences;
    }

    private static ArrayList<Integer> getElementsFromSquare() {
        ArrayList<Integer> elements = new ArrayList<>();
        for (int[] row : magicSquare) {
            for (int num : row) {
                elements.add(num);
            }
        }
        return elements;
    }
}
