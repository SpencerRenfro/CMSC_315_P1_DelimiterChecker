import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

// add characterIndex to this file
public class CheckFileDelimiters {
    private final BufferedReader reader;
    private String currentLine = null;
    private int characterIndex = -1; // because getNextChar increments
    private int currentLineIndex;
    private final String filePath;
    private ArrayList<String> fileLines;
    public boolean insideBlockComment = false;
    public boolean mismatchedDelimiter = false;

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
            // Case 1: Inject newline at end of line
            if (currentLine != null && characterIndex == currentLine.length()) {
                System.out.println("Current line is null or character index is at end of line, inserting new line" + currentLine);
                System.out.println("CurrentLineIndex" + ": " + currentLine);
                characterIndex++; // move past '\n'
                currentLineIndex++;
                //return '\n';
            }

            // Case 2: Move to next line when needed
            // initially currentLine is null, so we need to read the first line
            while (currentLine == null || characterIndex > currentLine.length()) {
                if(currentLine == null){
                    System.out.print("current line is equal to null before readLine, characterIndex: " + characterIndex);
                }
                currentLine = reader.readLine();
                if(currentLine == null){
                    System.out.print("current line is equal to null after readLine");
                }
                else {
                    System.out.println("\ncurrentLine: " + currentLine);
                }

                // Check for end of file
                if (currentLine == null) {
                    System.out.println("End of file reached");
                    return null;  // End of file
                }

                // Handle empty lines
                if (currentLine.trim().isEmpty()) {
                    System.out.println("Empty line detected");
//                    currentLineIndex++;
//                    characterIndex = -1;
                    continue;
                }
                if(mismatchedDelimiter){
                    System.out.println("Controller detected miss match, exiting program");
                    return null;
                }
                if(insideBlockComment)System.out.println("Inside block comment skipping....");
                else System.out.println("current line: " + currentLine + "current Index: " + characterIndex);

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

    private boolean isFileValid(String fileName) {
    File file = new File("src/" + fileName + ".java");
    System.out.println("File path: " + file.getAbsolutePath());
    return file.exists() && file.isFile() && file.canRead();
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
