/*

CMSC 315 Project 1
Spencer Renfro

 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

import java.util.Stack;


public class ControlFileChecker {

    public static  CheckFileDelimiters parsedFile = null;
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

        }
    }

    public static void main(String [] args) throws FileNotFoundException {


        int fileConnectionMade = PromptUserForFilePath();
        ArrayList<Character> delimiterList = new ArrayList<Character>();
        delimiterList.add('{');
        delimiterList.add('}');
        delimiterList.add('(');
        delimiterList.add(')');
        delimiterList.add('[');
        delimiterList.add(']');
        delimiterList.add('\'');
        delimiterList.add('\"');
        if(fileConnectionMade == 1){
            System.out.println("File connection made");

        } else System.out.println("File connection not made, exiting program in error");

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

        int currentLineIndex = 0;

        Character c;

        while((c = parsedFile.getNextChar()) != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(c).append("\t");
            sb.append("[Current char index: ").append(parsedFile.getCharacterIndex()).append("]\t");
            sb.append("[Current line index: ").append(parsedFile.getCurrentLineIndex()).append("]\t");

            // Optional delimiter check
            if(delimiterList.contains(c)){
                sb.append("[Delimiter found: ").append(c).append("]\n");
            } else {
                sb.append("[Delimiter not found: ").append(c).append("]\n");
            }

            System.out.println(sb.toString());
            parsedFile.incrementLineIndex();
        }

        // print out the readable file at path

        try{
            parsedFile.printFile();
        } catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }

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




*  */