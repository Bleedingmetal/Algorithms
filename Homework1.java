import java.util.*;

public class Homework1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to go first or second? 1/2? \n");
        int order = sc.nextInt();
        for (int i = 1; i > 0; i++) {
            if (order <= 2) {
                break;
            } else {
                System.out.println("Option is out of bounds please choose either 1 or 2\n");
                order = sc.nextInt();
                continue;
            }
        }
    }
}