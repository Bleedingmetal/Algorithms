import java.io.*;
import java.util.*;

public class ClosedHashingGraded {
    private static final int C = 123;

    public static void main(String[] args) throws IOException {
        String[] fileNames = {
                "TheRavenB24.txt",
                "TellTaleHeartB24.txt",
                "TheCaskOfAmontilladoB24.txt",
                "Heart prepended to Cask",
                "Cask prepended to Heart"
        };

        int[] tableSizes = {1000, 790, 991, 1499, 1499};

        Scanner scanner = new Scanner(System.in);

        System.out.println("Which file(s) would you like to analyze?");
        System.out.println("1. TheRavenB24.txt");
        System.out.println("2. TellTaleHeartB24.txt");
        System.out.println("3. TheCaskOfAmontilladoB24.txt");
        System.out.println("4. Heart prepended to Cask");
        System.out.println("5. Cask prepended to Heart");
        System.out.print("Enter the numbers of the files separated by commas (e.g., 1,3,4): ");

        String userInput = scanner.nextLine();
        String[] selectedFileIndexes = userInput.split(",");

        for (String index : selectedFileIndexes) {
            int fileIndex = Integer.parseInt(index.trim()) - 1;
            if (fileIndex >= 0 && fileIndex < fileNames.length) {
                String fileName = fileNames[fileIndex];
                int tableSize = tableSizes[fileIndex];
                List<String> words;

                if (fileName.equals("Heart prepended to Cask")) {
                    words = combineFiles("TellTaleHeartB24.txt", "TheCaskOfAmontilladoB24.txt");
                } else if (fileName.equals("Cask prepended to Heart")) {
                    words = combineFiles("TheCaskOfAmontilladoB24.txt", "TellTaleHeartB24.txt");
                } else {
                    words = parseFile(fileName);
                }

                String[] hashTable = new String[tableSize];
                Arrays.fill(hashTable, null);
                for (String word : words) {
                    insertIntoHashTable(hashTable, word, tableSize);
                }

                System.out.println("Hash Table for File: " + fileName);
                displayHashTable(hashTable);
                System.out.println();

                System.out.println("Analysis for File: " + fileName);
                analyzeHashTable(hashTable);
                System.out.println();
            }
        }

        analyzePart4(scanner);
        scanner.close();
    }

    private static List<String> parseFile(String fileName) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split("[^a-zA-Z'-]+");
            for (String token : tokens) {
                if (!token.isEmpty()) {
                    words.add(token.toLowerCase());
                }
            }
        }
        reader.close();
        return words;
    }

    private static List<String> combineFiles(String file1, String file2) throws IOException {
        List<String> combinedWords = new ArrayList<>();
        combinedWords.addAll(parseFile(file1));
        combinedWords.addAll(parseFile(file2));
        return combinedWords;
    }

    private static int computeHash(String word, int tableSize) {
        int h = 0;
        for (int i = 0; i < word.length(); i++) {
            h = (h * C + word.charAt(i)) % tableSize;
        }
        return h;
    }

    private static void insertIntoHashTable(String[] hashTable, String word, int tableSize) {
        int hash = computeHash(word, tableSize);
        int index = hash;

        while (hashTable[index] != null) {
            if (hashTable[index].equals(word)) {
                return;
            }
            index = (index + 1) % tableSize;
        }
        hashTable[index] = word;
    }

    private static void displayHashTable(String[] hashTable) {
        System.out.printf("%-10s %-20s %-10s%n", "Address", "Word", "Hash Value");
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                String word = hashTable[i];
                int hashValue = computeHash(word, hashTable.length);
                System.out.printf("%-10d %-20s %-10d%n", i, word, hashValue);
            }
        }
    }

    private static void analyzeHashTable(String[] hashTable) {
        int nonEmpty = 0, longestEmpty = 0, currentEmpty = 0;
        int longestCluster = 0, currentCluster = 0;
        int farthestDrift = 0;
        String farthestWord = null;

        int[] hashValueCount = new int[hashTable.length];

        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] == null) {
                currentEmpty++;
                longestCluster = Math.max(longestCluster, currentCluster);
                currentCluster = 0;
            } else {
                nonEmpty++;
                currentCluster++;
                longestEmpty = Math.max(longestEmpty, currentEmpty);
                currentEmpty = 0;

                String word = hashTable[i];
                int hash = computeHash(word, hashTable.length);
                int drift = (i >= hash) ? (i - hash) : (hashTable.length - hash + i);
                if (drift > farthestDrift) {
                    farthestDrift = drift;
                    farthestWord = word;
                }

                hashValueCount[hash]++;
            }
        }

        longestCluster = Math.max(longestCluster, currentCluster);
        longestEmpty = Math.max(longestEmpty, currentEmpty);

        int mostCommonHashValue = -1, maxCount = 0;
        for (int i = 0; i < hashValueCount.length; i++) {
            if (hashValueCount[i] > maxCount) {
                maxCount = hashValueCount[i];
                mostCommonHashValue = i;
            }
        }

        System.out.println("1. Non-empty addresses: " + nonEmpty);
        System.out.println("2. Load factor: " + ((double) nonEmpty / hashTable.length));
        System.out.println("3. Longest empty area: " + longestEmpty);
        System.out.println("4. Longest cluster: " + longestCluster);
        System.out.println("5. Most common hash value: " + mostCommonHashValue + " (occurs " + maxCount + " times)");
        System.out.println("6. Farthest drift: " + farthestDrift + " (word: " + farthestWord + ")");
    }

    private static void analyzePart4(Scanner scanner) throws IOException {
        List<String> heartToCaskWords = combineFiles("TellTaleHeartB24.txt", "TheCaskOfAmontilladoB24.txt");
        List<String> caskToHeartWords = combineFiles("TheCaskOfAmontilladoB24.txt", "TellTaleHeartB24.txt");

        String[] heartToCaskHashTable = new String[1499];
        Arrays.fill(heartToCaskHashTable, null);
        for (String word : heartToCaskWords) {
            insertIntoHashTable(heartToCaskHashTable, word, 1499);
        }

        String[] caskToHeartHashTable = new String[1499];
        Arrays.fill(caskToHeartHashTable, null);
        for (String word : caskToHeartWords) {
            insertIntoHashTable(caskToHeartHashTable, word, 1499);
        }

        List<String> poetNames = Arrays.asList("poe", "edgar", "allan");

        System.out.println("Poet's Three Names Analysis:");
        for (String name : poetNames) {
            int heartToCaskIndex = computeHash(name, 1499);
            int caskToHeartIndex = computeHash(name, 1499);

            System.out.println(name + " in 'Heart prepended to Cask' hash table is at index: " + heartToCaskIndex);
            System.out.println(name + " in 'Cask prepended to Heart' hash table is at index: " + caskToHeartIndex);
            System.out.println();
        }

        Set<String> commonWords = new HashSet<>(heartToCaskWords);
        commonWords.retainAll(caskToHeartWords);
        System.out.println("Number of common words between 'Heart prepended to Cask' and 'Cask prepended to Heart': " + commonWords.size());
    }
}
