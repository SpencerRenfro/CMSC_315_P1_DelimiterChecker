import java.io.*;
import java.util.*;

public class DelimiterCheckerTwoStage {

    public static void main(String[] args) throws IOException {
        String filePath = "src/Test.java"; // Update this with your real path

        List<Character> delimiters = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int lineNumber = 0;

        // Stage 1: Extract delimiters into the list
        while ((line = reader.readLine()) != null) {
            lineNumber++;

            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);

                // Skip whitespaces
                if (Character.isWhitespace(ch)) continue;

                // Extract only the delimiters
                if (ch == '{' || ch == '(' || ch == '[' || ch == '}' || ch == ')' || ch == ']') {
                    delimiters.add(ch);
                }
            }
        }

        reader.close();
        System.out.println("Extracted delimiters: " + delimiters);

        // Stage 2: Validate using a stack
        Stack<Character> stack = new Stack<>();
        boolean isValid = true;

        for (char d : delimiters) {
            if (d == '{' || d == '(' || d == '[') {
                stack.push(d);
            } else {
                if (stack.isEmpty()) {
                    System.out.println("Unmatched closing delimiter: " + d);
                    isValid = false;
                    break;
                }
                char open = stack.pop();
                if (!isMatchingPair(open, d)) {
                    System.out.println("Mismatch: opened with " + open + " but closed with " + d);
                    isValid = false;
                    break;
                }
            }
        }

        if (isValid && stack.isEmpty()) {
            System.out.println("All delimiters matched successfully!");
        } else if (isValid) {
            System.out.println("Unmatched opening delimiter(s) left in stack: " + stack);
        }
    }

    private static boolean isMatchingPair(char open, char close) {
        return (open == '{' && close == '}') ||
                (open == '(' && close == ')') ||
                (open == '[' && close == ']');
    }
}
