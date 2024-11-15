import java.util.ArrayList;
import java.util.List;

public class AlgoRhythmicsGrayCode {

    private static final String[] MUSICIANS = {"Alexiev", "Barack", "Clarice", "Darlene", "Eduardo"};


    public static List<String> brgc(int n) {
        if (n == 0) {
            List<String> base = new ArrayList<>();
            base.add("0");
            return base;
        }
        List<String> smallerGrayCode = brgc(n - 1);
        List<String> result = new ArrayList<>();
        for (String code : smallerGrayCode) {
            result.add("0" + code);
        }
        for (int i = smallerGrayCode.size() - 1; i >= 0; i--) {
            result.add("1" + smallerGrayCode.get(i));
        }
        return result;
    }


    public static List<String> generateActions(List<String> grayCode, String[] musicians) {
        List<String> actions = new ArrayList<>();
        int prevCode = 0;
        for (String code : grayCode) {
            int currentCode = Integer.parseInt(code, 2);
            int diff = currentCode ^ prevCode;
            int changedBit = Integer.numberOfTrailingZeros(diff);
            if (changedBit < musicians.length) {
                if ((currentCode & (1 << changedBit)) != 0) {
                    actions.add(musicians[changedBit] + " Joins");
                } else {
                    actions.add(musicians[changedBit] + " Fades");
                }
            } else {
                actions.add("None");
            }
            prevCode = currentCode;
        }
        return actions;
    }


    public static void printTable(List<String> grayCode, List<String> actions, String[] musicians) {
        System.out.println("Index\tGray Code\tPlayers Playing\t\t\tAction");
        int index = 0;
        List<String> playing = new ArrayList<>();
        for (String code : grayCode) {
            String action = actions.get(index);
            if (action.endsWith("Joins")) {
                playing.add(action.split(" ")[0]);
            } else if (action.endsWith("Fades")) {
                playing.remove(action.split(" ")[0]);
            } else {
                action = "Silent Stage";
            }
            System.out.printf("%d\t%s\t%-25s\t%s\n", index, code, String.join(", ", playing), action);
            index++;
        }
    }

    public static void main(String[] args) {

        int n = 5;
        List<String> grayCode = brgc(n);
        List<String> actions = generateActions(grayCode, MUSICIANS);
        printTable(grayCode, actions, MUSICIANS);
    }
}
