/*
CMSC 315 Project 1
Spencer Renfro
 */

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class ControlFileChecker {

    public static CheckFileDelimiters parsedFile = null;
    public static Scanner input = new Scanner(System.in);
    private static boolean singleQuoteFlag = false;

    public static int PromptUserForFilePath() {
        while (true) {
            System.out.println("\nEnter a file path: ");
            String filePath = input.nextLine();

            // Test if file exists, and is a .java file
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File not found. Please try again.");
                continue;
            } else if(!file.getName().endsWith(".java")){
                System.out.println("File is not a .java file. PLease try again.");
                //continue;
            }

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

    public static boolean containsSingleQuote(char[] content) {
        for (char c : content) {
            if (c == '\'') {
                return true;
            }
        }
        return false;
        
    }

    public static void main(String[] args) throws FileNotFoundException {
        int fileConnectionMade = PromptUserForFilePath();

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
        } 

        Character c;
        while ((c = parsedFile.getNextChar()) != null) {
            // experimental for getting current char in CheckFIleDelimiters
            parsedFile.setCurrentChar(c);
            Character nextChar = parsedFile.peekNextChar();
           
            //This statement prints returned char and next char for every line
           // System.out.println(parsedFile.getCurrentPositionInfo() +  "Current char: " + c + " Next char: " + nextChar);

            // Check for line or block comments and skip until end of comment or new line
            if(c == '/' && nextChar != null && nextChar == '/') {
               // System.out.println("Line comment detected, skip to new line");
                parsedFile.incrementLineIndex();
                continue;
            }
            if(c == '/' && nextChar != null && nextChar == '*'){
                //System.out.println("Block comment detected, skip until end of comment, setting inside block comment to true");
                parsedFile.SetInsideBlockComment(true);
                while((c = parsedFile.getNextChar()) != null){
                    // skip chars inside the comment
                   nextChar = parsedFile.peekNextChar();
                   // System.out.println("skipped" + c);
                    if(c == '*' && nextChar != null && nextChar == '/'){
                        //System.out.println("End of block comment detected, setting inside block comment to false");
                        parsedFile.SetInsideBlockComment(false);
                        break;
                    }
                }
                continue;
            }
            // Skip until the next single quote
            // single quotes can only have one character
            // single quotes cannot take double quotes inside them, unless escaped
            if(c == '\''){
                delimiterStack.push(c);
                singleQuoteFlag = true;
               // System.out.println("Single quote detected, skip until end of string, setting inside string to true");    

                char [] singleQuotesContent = new char[20];
                for(int i = 0; i < 3;i++){
                   singleQuotesContent[i] = parsedFile.getNextChar();
                   if(singleQuotesContent[i] == '\'' || singleQuotesContent == null){
                       //System.out.println("End of single quote detected, setting inside string to false");
                       delimiterStack.pop();
                       singleQuoteFlag = false;
                       break;
                   }
                };
                if(singleQuoteFlag){
                    parsedFile.setMismatchedDelimiter();
                    System.out.println("Mismatch! Single quote not closed at " + parsedFile.getCurrentPositionInfo());
                    System.exit(1);
                }
                
                continue;
            }
            //continues until closing double quote is found or end of file
            if(c == '\"'){
                //System.out.println("Double quote detected");    
                while(c != '\"' || c != null){ 

                    c = parsedFile.getNextChar();
                    //System.out.println("skipped" + c);
                    if(c == '\"'){
                        //System.out.println("End of double quote detected, setting inside string to false");
                        break;
                    }

                    if(c == null || c == '\n'){
                        System.out.println("Mismatch! Double quote not closed at " + parsedFile.getCurrentPositionInfo());
                        parsedFile.setMismatchedDelimiter();
                        System.exit(1);
                    }
                }
                continue;
            }

            // Normal delimiter processing
            if (leftDelimiters.contains(c)) {
                delimiterStack.push(c);
                //Print line for showing each left delimiter being pushed 
                //System.out.println("Pushed left delimiter `" + c + "`  at " + parsedFile.getCurrentPositionInfo());
            } else if (rightDelimiters.contains(c)) {
                if (delimiterStack.isEmpty()) {
                    System.out.println("MisMatch! Extra right delimiter `" + c + "` at " + parsedFile.getCurrentPositionInfo());
                } else {
                    Character lastLeft = delimiterStack.pop();
                    if (matchingPairs.get(lastLeft) == c) {
                        System.out.println("Matched delimiters: `" + lastLeft + "` and `" + c + "` at " + parsedFile.getCurrentPositionInfo());
                    } else {
                        System.out.println("Mismatch! `" + lastLeft + "` does not match `" + c + "`  at " + parsedFile.getCurrentPositionInfo());
                        parsedFile.setMismatchedDelimiter();
                    }
                }
            }

            if(parsedFile.getCurrentLine() == null){
                System.out.println("End of file reached, checking for unmatched delimiters");
            }
   
        }

        if(!delimiterStack.isEmpty()){
            System.out.println("MisMatch! Open Delimiter(s) not closed: ");
            for(char delimiter : delimiterStack){
                System.out.print(delimiter + " ");
            }
            System.out.println("at " + parsedFile.getCurrentPositionInfo());
        }
                 // print stack
                //  for( char delimiter : delimiterStack){
                //     System.out.print(delimiter + " ");
                // }
    }
}

