import java.io.*;
import java.util.Stack;

public class DelimiterCheckerExample {

    public static void main(String[] args) throws IOException {
        String filePath = "src/Test.java"; // Update with your file path!

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        Stack<Character> stack = new Stack<>();
        String line;
        int lineNumber = 0;

        while ((line = reader.readLine()) != null) {
            lineNumber++;

            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);

                // Skip whitespace
                if (Character.isWhitespace(ch)) continue;

                // Check for delimiters
                if (ch == '{' || ch == '(' || ch == '[') {
                    stack.push(ch);
                    System.out.println("Pushed " + ch + " from Line " + lineNumber + ", Char " + (i + 1));
                } else if (ch == '}' || ch == ')' || ch == ']') {
                    if (stack.isEmpty()) {
                        System.out.println("Unmatched closing " + ch + " at Line " + lineNumber + ", Char " + (i + 1));
                        return;
                    }
                    char open = stack.pop();
                    if (!isMatchingPair(open, ch)) {
                        System.out.println("MISMATCH: " + open + " does not match " + ch + " at Line " + lineNumber + ", Char " + (i + 1));
                        return;
                    } else {
                        System.out.println("Matched " + open + " with " + ch + " at Line " + lineNumber + ", Char " + (i + 1));
                    }
                }
            }
        }

        if (!stack.isEmpty()) {
            System.out.println("Unmatched opening delimiters remaining in stack: " + stack);
        } else {
            System.out.println("All delimiters matched successfully.");
        }

        reader.close();
    }

    private static boolean isMatchingPair(char open, char close) {
        return (open == '{' && close == '}') ||
                (open == '(' && close == ')') ||
                (open == '[' && close == ']');
    }
}
