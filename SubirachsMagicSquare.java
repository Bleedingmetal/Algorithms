import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubirachsMagicSquare {
    private static final int[][] square = {
            {1, 14, 14, 4},
            {11, 7, 6, 9},
            {8, 10, 10, 5},
            {13, 2, 3, 15}
    };
    private static final int TARGET_SUM = 33;

    public static void main(String[] args) {
        List<Integer> allElements = new ArrayList<>();
        for (int[] row : square) {
            for (int val : row) {
                allElements.add(val);
            }
        }

        int fourElementCombos = countFourElementCombinations(allElements, TARGET_SUM);
        int allCombos = countAllCombinations(allElements, TARGET_SUM);
        Map<Integer, Integer> sumCounts = countAllPossibleSums(allElements);

        System.out.println("Number of 4-element combinations with sum 33: " + fourElementCombos);
        System.out.println("Number of all combinations with sum 33: " + allCombos);

        int maxCount = 0;
        int mostCommonSum = 0;
        for (Map.Entry<Integer, Integer> entry : sumCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonSum = entry.getKey();
            }
        }
        System.out.println("Most common sum: " + mostCommonSum + " with " + maxCount + " combinations");
    }

    private static int countFourElementCombinations(List<Integer> elements, int targetSum) {
        return countCombinations(elements, 4, targetSum);
    }

    private static int countAllCombinations(List<Integer> elements, int targetSum) {
        int count = 0;
        for (int size = 1; size <= elements.size(); size++) {
            count += countCombinations(elements, size, targetSum);
        }
        return count;
    }

    private static int countCombinations(List<Integer> elements, int size, int targetSum) {
        return countCombinationsRecursive(elements, size, targetSum, 0, 0, 0);
    }

    private static int countCombinationsRecursive(List<Integer> elements, int size, int targetSum, int start, int depth, int currentSum) {
        if (depth == size) {
            return currentSum == targetSum ? 1 : 0;
        }
        int count = 0;
        for (int i = start; i < elements.size(); i++) {
            count += countCombinationsRecursive(elements, size, targetSum, i + 1, depth + 1, currentSum + elements.get(i));
        }
        return count;
    }

    private static Map<Integer, Integer> countAllPossibleSums(List<Integer> elements) {
        Map<Integer, Integer> sumCounts = new HashMap<>();
        for (int size = 1; size <= elements.size(); size++) {
            countSumsRecursive(elements, size, 0, 0, 0, sumCounts);
        }
        return sumCounts;
    }

    private static void countSumsRecursive(List<Integer> elements, int size, int start, int depth, int currentSum, Map<Integer, Integer> sumCounts) {
        if (depth == size) {
            sumCounts.put(currentSum, sumCounts.getOrDefault(currentSum, 0) + 1);
            return;
        }
        for (int i = start; i < elements.size(); i++) {
            countSumsRecursive(elements, size, i + 1, depth + 1, currentSum + elements.get(i), sumCounts);
        }
    }
}
