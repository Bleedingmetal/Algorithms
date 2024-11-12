import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubirachsMagicSquare {

    static int[][] square = {
            {16, 3, 2, 13},
            {5, 10, 11, 8},
            {9, 6, 7, 12},
            {4, 15, 14, 1}
    };
    static int targetSum = 34;

    public static void main(String[] args) {
        List<Integer> elements = new ArrayList<>();
        for (int[] row : square) {
            for (int num : row) {
                elements.add(num);
            }
        }

        int fourElementCombinationsCount = countCombinationsWithSum(elements, 4, targetSum);
        System.out.println("4-element combinations with sum = " + targetSum + ": " + fourElementCombinationsCount);

        int allCombinationsCount = 0;
        for (int length = 1; length <= elements.size(); length++) {
            allCombinationsCount += countCombinationsWithSum(elements, length, targetSum);
        }
        System.out.println("All combinations with sum = " + targetSum + ": " + allCombinationsCount);

        Map<Integer, Integer> sumCountMap = new HashMap<>();
        for (int length = 1; length <= elements.size(); length++) {
            countAllSums(elements, length, sumCountMap);
        }

        int maxSum = 0;
        int maxCount = 0;
        for (Map.Entry<Integer, Integer> entry : sumCountMap.entrySet()) {
            System.out.println("Sum " + entry.getKey() + " can be formed in " + entry.getValue() + " ways.");
            if (entry.getValue() > maxCount) {
                maxSum = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        System.out.println("Sum with the greatest number of combinations: " + maxSum + " with " + maxCount + " combinations.");
    }

    private static int countCombinationsWithSum(List<Integer> elements, int length, int targetSum) {
        List<List<Integer>> combinations = new ArrayList<>();
        findCombinations(elements, new ArrayList<>(), length, 0, targetSum, combinations);
        return combinations.size();
    }

    private static void findCombinations(List<Integer> elements, List<Integer> current, int length, int start, int targetSum, List<List<Integer>> combinations) {
        if (current.size() == length) {
            int sum = current.stream().mapToInt(Integer::intValue).sum();
            if (sum == targetSum) {
                combinations.add(new ArrayList<>(current));
            }
            return;
        }

        for (int i = start; i < elements.size(); i++) {
            current.add(elements.get(i));
            findCombinations(elements, current, length, i + 1, targetSum, combinations);
            current.remove(current.size() - 1);
        }
    }

    private static void countAllSums(List<Integer> elements, int length, Map<Integer, Integer> sumCountMap) {
        List<List<Integer>> combinations = new ArrayList<>();
        findCombinations(elements, new ArrayList<>(), length, 0, Integer.MAX_VALUE, combinations);

        for (List<Integer> combination : combinations) {
            int sum = combination.stream().mapToInt(Integer::intValue).sum();
            sumCountMap.put(sum, sumCountMap.getOrDefault(sum, 0) + 1);
        }
    }
}
