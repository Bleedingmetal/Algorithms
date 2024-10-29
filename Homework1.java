import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Homework1 {
    int greenMarkers = 3;
    int yellowMarkers = 7;
    int orangeMarkers = 5;
    int order;

    final String GREEN_COLOR = "\u001B[32m";
    final String YELLOW_COLOR = "\u001B[33m";
    final String ORANGE_COLOR = "\u001B[38;5;208m";
    final String RESET_COLOR = "\u001B[0m";

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Double Trouble with an Aditya twist (it basically trash talks)!");
        System.out.println("Choose the order of play:\n1. You go first\n2. CPU goes first\n3. Tournament mode (2n + 1)");

        while (true) {
            order = scanner.nextInt();
            if (order == 1 || order == 2 || order == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please choose 1, 2, or 3.");
            }
        }

        if (order == 3) {
            tournamentMode(scanner);
        } else {
            playGame(scanner);
        }

        scanner.close();
    }

    public void tournamentMode(Scanner scanner) {
        System.out.print("Enter number of rounds (n): ");
        int n = scanner.nextInt();
        int maxGames = 2 * n + 1;
        int playerWins = 0;
        int cpuWins = 0;
        Random random = new Random();

        for (int gameNumber = 1; gameNumber <= maxGames && playerWins <= n && cpuWins <= n; gameNumber++) {
            System.out.println("\nRound " + gameNumber + " start!");

            // Randomly determine who starts
            boolean isPlayerTurn = random.nextBoolean();
            System.out.println((isPlayerTurn ? "Player" : "CPU") + " goes first.");
            resetMarkers(); // Reset the markers for a new game

            while (greenMarkers + yellowMarkers + orangeMarkers > 0) {
                displayMarkers();
                if (isPlayerTurn) {
                    playerMove(scanner);
                } else {
                    cpuMove();
                }

                // Toggle turn after a move
                isPlayerTurn = !isPlayerTurn;
            }

            // Determine round winner
            if (!isPlayerTurn) {
                playerWins++;
                System.out.println("You win this round!");
            } else {
                cpuWins++;
                System.out.println("CPU wins this round!\n");
                cpuWinMessage();
            }

            System.out.println("Score: Player " + playerWins + " - CPU " + cpuWins);
        }

        // Display final tournament result
        if (playerWins > cpuWins) {
            System.out.println("\nCongratulations! You won the tournament with " + playerWins + " wins!");
        } else {
            System.out.println("\nThe CPU wins the tournament with " + cpuWins + " wins! Better luck next time.");
        }
    }

    public void playGame(Scanner scanner) {
        boolean isPlayerTurn = (order == 1);

        while (greenMarkers + yellowMarkers + orangeMarkers > 0) {
            displayMarkers();
            if (isPlayerTurn) {
                playerMove(scanner);
            } else {
                cpuMove();
            }

            // Toggle turn after a move
            isPlayerTurn = !isPlayerTurn;
        }

        if (!isPlayerTurn) {
            System.out.println("Game Over! You win!");
            cpuLossMessage();
        } else {
            System.out.println("Game Over! CPU wins!");
            cpuWinMessage();
        }
    }

    public void displayMarkers() {
        System.out.println("\nCurrent Markers:");
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < greenMarkers; i++) {
            output.append(GREEN_COLOR).append("|").append(RESET_COLOR);
        }
        for (int i = 0; i < yellowMarkers; i++) {
            output.append(YELLOW_COLOR).append("|").append(RESET_COLOR);
        }
        for (int i = 0; i < orangeMarkers; i++) {
            output.append(ORANGE_COLOR).append("|").append(RESET_COLOR);
        }

        System.out.println(output.toString());
    }

    public void playerMove(Scanner scanner) {
        String color;
        int count;

        while (true) {
            System.out.print("Your turn! Choose color (green, yellow, orange) and count: ");
            color = scanner.next();
            count = scanner.nextInt();
            if (isValidMove(color, count)) {
                removeMarkers(color, count);
                break;
            } else {
                System.out.println("Invalid move. Please try again.");
            }
        }
    }

    public boolean isValidMove(String color, int count) {
        switch (color.toLowerCase()) {
            case "green":
                return count > 0 && count <= greenMarkers;
            case "yellow":
                return count > 0 && count <= yellowMarkers;
            case "orange":
                return count > 0 && count <= orangeMarkers;
            default:
                return false;
        }
    }

    public void removeMarkers(String color, int count) {
        switch (color.toLowerCase()) {
            case "green":
                greenMarkers -= count;
                break;
            case "yellow":
                yellowMarkers -= count;
                break;
            case "orange":
                orangeMarkers -= count;
                break;
        }
    }

    public void cpuMove() {
        System.out.println("CPU is calculating...\n");
        try {
            TimeUnit.SECONDS.sleep(2); // Simulate CPU thinking time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String color = "";
        int count = 0;
        boolean winningMoveFound = false;

        // Check if a winning move is possible
        for (String c : new String[]{"green", "yellow", "orange"}) {
            for (int i = 1; i <= getMarkerCount(c); i++) {
                if (isValidMove(c, i) && canWinAfterMove(c, i)) {
                    color = c;
                    count = i;



                    winningMoveFound = true;
                    break;
                }
            }
            if (winningMoveFound) break;
        }

        if (!winningMoveFound) {
            // No winning move: pick a random move if no winning move is available
            Random random = new Random();
            do {
                int colorChoice = random.nextInt(3);
                count = random.nextInt(1, 3);
                switch (colorChoice) {
                    case 0: color = "green"; break;
                    case 1: color = "yellow"; break;
                    default: color = "orange"; break;
                }
            } while (!isValidMove(color, count));
        }

        System.out.println("CPU removes " + count + " " + color + " marker(s).\n");
        removeMarkers(color, count);
    }

    // Helper function to check if the move results in an immediate win
    public boolean willWinAfterThisMove(String color, int count) {
        int tempGreen = greenMarkers;
        int tempYellow = yellowMarkers;
        int tempOrange = orangeMarkers;

        switch (color.toLowerCase()) {
            case "green":
                tempGreen -= count;
                break;
            case "yellow":
                tempYellow -= count;
                break;
            case "orange":
                tempOrange -= count;
                break;
        }

        // Check if all markers are removed
        return tempGreen + tempYellow + tempOrange == 0;
    }

    public int getMarkerCount(String color) {
        switch (color.toLowerCase()) {
            case "green":
                return greenMarkers;
            case "yellow":
                return yellowMarkers;
            case "orange":
                return orangeMarkers;
            default:
                return 0;
        }
    }

    public boolean canWinAfterMove(String color, int count) {
        int tempGreen = greenMarkers;
        int tempYellow = yellowMarkers;
        int tempOrange = orangeMarkers;

        switch (color.toLowerCase()) {
            case "green":
                tempGreen -= count;
                break;
            case "yellow":
                tempYellow -= count;
                break;
            case "orange":
                tempOrange -= count;
                break;
        }

        int nimSum = tempGreen ^ tempYellow ^ tempOrange;
        return nimSum == 0;
    }

    public void resetMarkers() {
        greenMarkers = 3;
        yellowMarkers = 7;
        orangeMarkers = 5;
    }

    public void cpuWinMessage() {
        System.out.println("CPU: You think you can beat me? *Starts Hitting the Gritty*\n");
    }

    public void cpuLossMessage() {
        System.out.println("CPU: You may have won this time, but I'll be targeting you first during the AI uprising!");
    }

    public static void main(String[] args) {
        Homework1 game = new Homework1();
        game.startGame();
    }
}
