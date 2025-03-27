import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

// add characterIndex to this file
public class CheckFileDelimiters {
    private final BufferedReader reader;
    private String currentLine = null; // returns the string of the entire line
    private int characterIndex = -1; // getNextChar increments
    private int currentLineIndex; // Index of current line starts at 0
    private final String filePath;
    private ArrayList<String> fileLines;
    public boolean insideBlockComment = false;
    public boolean mismatchedDelimiter = false;
    public char currentChar; // current character set in Controller

    public CheckFileDelimiters(String filePath) throws FileNotFoundException{
        try {
            this.reader = new BufferedReader(new FileReader(filePath));
            this.filePath = filePath;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File: " + filePath + " not found or unreadable.");
        }

    }

    public Character getNextChar() {
        try {
            // Case 1: Inserts newline at end of line, or if line is null (empty) not end of file
            if (currentLine != null && characterIndex == currentLine.length()) {
                System.out.println("detected end of line at Line:" +currentLineIndex + " Current line is null or character index is at end of line, inserting new line"  );
                System.out.println("TEST FOR SKIPPING NEW LINE:  CURRENT_CHAR: " + getCurrentChar() + "next char");
                characterIndex++; // move past '\n'
                currentLineIndex++;
                // return '\n'; NOT NEEDED BECAUSE OF INCREMENT?
            }


            // Case 2: Move to next line when needed
            // initially currentLine is null, so we need to read the first line
            while (currentLine == null || characterIndex > currentLine.length()) {
                // Experimental code, erase, not needed atm
                try{
                    if(!currentLine.trim().isEmpty()){
                        System.out.println(currentLine.length());
                    }
                } catch(NullPointerException e){
                    System.out.println("ERROR: Line:" + currentLineIndex + ", Char Index:" + characterIndex + " Null pointer exception caught");
                }
                // Checking before and after readLine for null with print statements, runs only one time before readLine
                if(currentLine == null){
                    System.out.print("current line is equal to null before readLine, characterIndex: " + characterIndex);
                }

                currentLine = reader.readLine();

                // Checking for end of file,CharacterIndex is only -1 at start of program
                //IF currentLine is equal to null after first iteration, this indicates end of file
                if(currentLine == null && characterIndex != -1){
                    System.out.print("current line is equal to null after readLine, End of file reached, line: " + currentLineIndex + "characterIndex: " + characterIndex);
                }

                else {
                    System.out.println("\nLine: " +currentLineIndex + " currentLine: " + currentLine);
                }

                // Check for end of file
                if (currentLine == null) {
                    System.out.println("\n\nEnd of file reached");
                    return null;  // End of file
                }

                // Handle empty lines
                if (currentLine.trim().isEmpty()) {
                    System.out.println("Empty line detected");
//                    currentLineIndex++;
//                    characterIndex = -1;
                    continue;
                }
                // Controller detected missMatch with using char and nextChar, then sets mismatchedDelimiter to true
                if(mismatchedDelimiter){
                    System.out.println("Controller detected miss match, exiting program");
                    return null;
                }
                //Controller detected insideBlockComment, then skips to next line, print statement for debugging
                if(insideBlockComment){
                    System.out.println("Inside block comment skipping....");
                    currentLineIndex++;
                    continue;
                }
                else System.out.println("current line[" + currentLineIndex + "]:  |--- " + currentLine + " ---|  current Index: " + characterIndex);

                characterIndex = 0;
                currentLineIndex++;

            }

            // Case 3: Normal char from currentLine
            return currentLine.charAt(characterIndex++);
        } catch (Exception e) {
            return null;
        }
    }

    public String getCurrentPositionInfo() {
        return "Line: " + currentLineIndex + ", Char Index: " + characterIndex;
    }

    public void setCurrentChar(char c){
        currentChar = c;
    }
    public Character getCurrentChar(){
         return currentChar;
    }

    public Character peekNextChar() {
        if (currentLine != null && characterIndex + 1 < currentLine.length()) {
            return currentLine.charAt(characterIndex);
        } else {
            return null;
        }
    }

    public void incrementLineIndex() {
        this.currentLineIndex++;
    }

    public void SetInsideBlockComment(boolean insideBlockComment) {
        this.insideBlockComment = insideBlockComment;
    }

    public void setMismatchedDelimiter() {
        this.mismatchedDelimiter = true;
    }

    public void toString(StringBuilder sb) {
        System.out.println(sb.toString());

    }

    public void printFile() throws FileNotFoundException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            throw new FileNotFoundException("File not found or unreadable.");
        }
    }
}
