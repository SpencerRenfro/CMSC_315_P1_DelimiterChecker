import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;


public class CheckFileDelimiters {

    private final BufferedReader reader;
    public String currentLine = null; // returns the string of the entire line
    private int characterIndex = -1; // getNextChar increments
    private int currentLineIndex; // Index of current line starts at 0
    public boolean insideBlockComment = false;
    public boolean mismatchedDelimiter = false;
    public char currentChar; // current character set in Controller

    public CheckFileDelimiters(String filePath) throws FileNotFoundException{
        try {
            this.reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File: " + filePath + " not found or unreadable.");
        }

    }

    public Character getNextChar() {
        try {
            // Case 1: Inserts newline at end of line, or if line is null (empty) not end of file
            if (currentLine != null && characterIndex == currentLine.length()) {
                characterIndex++; // move past \n
                currentLineIndex++;
            }
    
            // Case 2: Move to next line when needed
            // Initially, currentLine is null, so read the first line
            while (currentLine == null || characterIndex >= currentLine.length()) {
                
                currentLine = reader.readLine();  // Read next line
    
                // Check for end of file
                // if (currentLine == null) {
                //     System.out.println("\n\nEnd of file reached");
                //     return null;  // End of file
                // }
    
                // Handle empty lines
                if(currentLine.trim().isEmpty()) {
                    characterIndex = 0;
                     // if empty line is at start of file, character indexs is at -1,
                     // if this is not set to 0, this will cause an index out of bounds error
                }
    
                // Reset characterIndex to start reading from the beginning
                characterIndex = 0;
            }
    
            // Case 3: Return the next character from the current line
            return currentLine.charAt(characterIndex++);
        } catch (Exception e) {
            System.out.println("Error reading character: " + e.getMessage());
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

    public String getCurrentLine(){
        return currentLine;
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

}
