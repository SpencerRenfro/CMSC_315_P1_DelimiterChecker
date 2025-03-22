/*

CMSC 315 Project 1
Spencer Renfro

 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.ArrayList;

public class ControlFileChecker {

    public static  CheckFileDelimiters parsedFile = null; //I THINK I CAN REMOVE THE PUBLIC FROM ALL SINCE NO CONSTRUCTOR
    public static Scanner input = new Scanner(System.in);
    public static String filePath;


    public static int PromptUserForFilePath() {

        while(true){
            System.out.println("\nEnter a file path: ");
            String filePath = input.nextLine();

           try {
               parsedFile = new CheckFileDelimiters(filePath);
           } catch(FileNotFoundException e){
               System.out.println(e.getMessage());
           }
            if(parsedFile != null){
                    System.out.println("File found in controller");

                    Stack<Character> stack = new Stack<>();
                    String line;
                    int lineNumber = 0;

                    return 1;
              }

        }
    }

    public static int loopUntilEndOfLineBlockComment(int startCommentIndex){
        return 0;
    }

    public static void main(String [] args) throws FileNotFoundException {
        int fileConnectionMade = PromptUserForFilePath();
        boolean insideBlockComment = false;

        // indexes
        int multiBlockCommentIndexStart = 0;
        int multiBlockCommentIndexEnd = 0;

        //Collections
        Set<Character> leftDelimiters = new HashSet<>(Arrays.asList('{', '[', '('));
        Set<Character> rightDelimiters = new HashSet<>(Arrays.asList('(', ')', '[', ']'));
        Set<Character> quoteDelimiters = new HashSet<>(Arrays.asList('"', '\''));
        Set<Character> allDelimiters = new HashSet<>(leftDelimiters);
        allDelimiters.addAll(rightDelimiters);
        allDelimiters.addAll(quoteDelimiters);
        Map<Character, Character> matchingPairs = new HashMap<>();
        matchingPairs.put('[',']');
        matchingPairs.put('{','}');
        matchingPairs.put('"','"');
        matchingPairs.put('\'','\'' );
        Map<String, String> ignoreMatchingPairs = new HashMap<>();
        ignoreMatchingPairs.put("/","/");
        ignoreMatchingPairs.put("/*","*/");

        Stack<Character> delimiterStack = new Stack<>();
        if(fileConnectionMade == 1){
            System.out.println("File connection made");

        } else System.out.println("File connection not made, exiting program in error");

        Character c;
        while((c = parsedFile.getNextChar()) != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(c).append("\t");
            sb.append("[Current char index: ").append(parsedFile.getCharacterIndex()).append("]\t");
            sb.append("[Current line index: ").append(parsedFile.getCurrentLineIndex()).append("]\t");

            // check for comment
            if(parsedFile.getCurrentEntireLine().contains("//")||parsedFile.getCurrentEntireLine().contains("/*")) {
                String commentLine = parsedFile.getCurrentEntireLine();
                int idx = parsedFile.getCharacterIndex() - 1;

                for (int i = idx; i < commentLine.length() - 1; i++) {

                    if (i + 1 < commentLine.length()) {
                        if (!insideBlockComment && commentLine.charAt(i) == '/' && commentLine.charAt(i + 1) == '/') {

                            System.out.println("Single-line comment found at index: " + i + " comment line length: " + commentLine.length());
                           // parsedFile.incrementLineIndex();
                            break;                  }
                        else if (!insideBlockComment && commentLine.charAt(i) == '/' && commentLine.charAt(i + 1) == '*') {
                            insideBlockComment = true;
                            i += 2;
                        }

                        // block comment logic starts here
                        while (insideBlockComment) {
                            for (; i < commentLine.length() - 1; i++) {
                                if (commentLine.charAt(i) == '*' && commentLine.charAt(i + 1) == '/') {
                                    multiBlockCommentIndexEnd = i;
                                    insideBlockComment = false;
                                    System.out.println("Multi-line comment found from index: " + multiBlockCommentIndexStart + " to " + multiBlockCommentIndexEnd);
                                    i = multiBlockCommentIndexEnd + 1;
                                    break;
                                }
                            }
                            // <<< moved this part OUTSIDE the for loop
                            if (insideBlockComment) {
                                parsedFile.incrementLineIndex();
                                commentLine = parsedFile.getCurrentEntireLine();
                                i = 0;
                                if (commentLine == null) {
                                    System.out.println("Unterminated multi-line comment detected.");
                                    insideBlockComment = false;
                                    break;
                                }
                            }
                        }
                    }
                }
                // once for-loop finishes
                parsedFile.incrementLineIndex();
                parsedFile.setCharacterIndex(-1); // reset character index to start of next line
                continue; // go back to while((c = parsedFile.getNextChar()) != null)

            }
            // check for left delimiter

            System.out.println("Current whole line: " + parsedFile.getCurrentEntireLine());
            System.out.println("Current char: " + c);
            if(!insideBlockComment){
                if(leftDelimiters.contains(c)){
                    sb.append("[Left Delimiter found: ").append(c).append("\n");
                    delimiterStack.push(c);
                }
                // check for right delimiter
                if(rightDelimiters.contains(c) && delimiterStack.peek() == c){
                    System.out.println("----DELIMITER MISS MATCH FOUND----" + "[" + c + "]" +  "[" + delimiterStack.peek() + "]" +"\n" );
                }
            }



            System.out.println(sb.toString());
            parsedFile.incrementLineIndex();
            System.out.println("\nwhole line: [" + parsedFile.getCurrentEntireLine() + "]");
        }

        // print out the readable file at path
//        try{
//            parsedFile.printFile();
//        } catch(FileNotFoundException e){
//            System.out.println(e.getMessage());
//        }
        // print out delimiter stack
        System.out.println("\n\n" + delimiterStack.toString());

    }

}


/*
if the character is a
left delimiter it should be pushed onto a delimiter stack. If it is a right delimiter, the stack should
be popped and a check should be made to ensure that the delimiters are of a matching type. If the
delimiters do not match, a message should be displayed indicating what delimiter was
encountered and at what position. You may use the defined Java Stack class.





*     public static boolean TestFilePath(String input) {
        File file = new File(input);

        return file.exists() && file.isFile() && file.canRead();
    }









//        while(true){
//            Character c = parsedFile.getNextChar();
//            if(c == null){
//                System.out.println("Reached end of file or mismatched delimiters in controller");
//                break;
//            }
//            System.out.println(c);
//        }
//        while(true){
//            Character c = parsedFile.getNextChar();
//
//            System.out.println(parsedFile.getLineCharNums());
//        }


       //IF parsedfile != null
                    //It should then repeatedly call the method that returns the next character until it returns a null character
                  //indicating the end of the file or until a mismatch of delimiters is encountered.
                  //  Character c = parsedFile.getNextChar();
//                    while(c != null){
//                        System.out.println(c);
//                        c = parsedFile.getNextChar();
//                    }
                  // while (c != null || parsedFile.isMismatched()){ {
                  //    IF c === delimiter.LEFT THEN push onto delimiter stack
                    //    ELSE IF c === delimiter.RIGHT THEN pop from delimiter stack
                             //    IF stack is empty THEN mismatch (a message should be displayed indicating what delimiter was
                  //encountered and at what position. You may use the defined Java Stack class.

                    //    ELSE IF c === null THEN break


*  */