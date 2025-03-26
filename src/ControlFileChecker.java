/*
CMSC 315 Project 1
Spencer Renfro
 */

import java.io.FileNotFoundException;
import java.util.*;

public class ControlFileChecker {

    public static CheckFileDelimiters parsedFile = null;
    public static Scanner input = new Scanner(System.in);

    public static int PromptUserForFilePath() {
        while (true) {
            System.out.println("\nEnter a file path: ");
            String filePath = input.nextLine();

            try {
                parsedFile = new CheckFileDelimiters(filePath);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            if (parsedFile != null) {
                System.out.println("File found in controller");
                return 1;
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int fileConnectionMade = PromptUserForFilePath();
        boolean insideBlockComment = false;

        // Collections
        Set<Character> leftDelimiters = new HashSet<>(Arrays.asList('{', '[', '('));
        Set<Character> rightDelimiters = new HashSet<>(Arrays.asList('}', ']', ')'));
        Map<Character, Character> matchingPairs = new HashMap<>();
        matchingPairs.put('[', ']');
        matchingPairs.put('{', '}');
        matchingPairs.put('(', ')');

        Stack<Character> delimiterStack = new Stack<>();

        if (fileConnectionMade == 1) {
            System.out.println("File connection made");
        } else {
            System.out.println("File connection not made, exiting program in error");
            return;
        }

        Character c;
        while ((c = parsedFile.getNextChar()) != null) {
            //Character nextChar = parsedFile.getNextChar();

            //
            // If inside a block comment, skip until */
            if (insideBlockComment) {
                if (c == '*' && nextChar != null && nextChar == '/') {
                    insideBlockComment = false;
                    parsedFile.getNextChar(); // consume '/'
                }
                continue;
            }

            // Detect start of block comment
            if (c == '/' && nextChar != null && nextChar == '*') {
                insideBlockComment = true;
                parsedFile.getNextChar(); // consume '*'
                continue;
            }

            // Detect single-line comment //
            if (c == '/' && nextChar != null && nextChar == '/') {

                // Skip chars until virtual newline is encountered
                StringBuilder lineComment = new StringBuilder();
                while ((c = parsedFile.getNextChar()) != null && c != '\n') {
                    // skip chars inside the comment
                    lineComment.append(c);
                }
                System.out.println("Single-line comment: " + lineComment.toString());
                continue;
            }

            // Normal delimiter processing
            if (leftDelimiters.contains(c)) {
                delimiterStack.push(c);
                System.out.println("Pushed left delimiter [" + c + "] at " + parsedFile.getCurrentPositionInfo());
            } else if (rightDelimiters.contains(c)) {
                if (delimiterStack.isEmpty()) {
                    System.out.println("Unmatched right delimiter [" + c + "] at " + parsedFile.getCurrentPositionInfo());
                } else {

                    Character left = delimiterStack.peek();
                    if (matchingPairs.get(left) != c) {
                        System.out.println("Mismatch! [" + left + "] does not match [" + c + "] at " + parsedFile.getCurrentPositionInfo());
                    }
                }
            }
        }



        if (!delimiterStack.isEmpty()) {
            System.out.println("---- UNCLOSED LEFT DELIMITERS DETECTED ----");
            while (!delimiterStack.isEmpty()) {
                System.out.println("Unmatched left delimiter: [" + delimiterStack.pop() + "]");
            }
        }

        System.out.println("\n\nFinal delimiter stack: " + delimiterStack);
    }
}
