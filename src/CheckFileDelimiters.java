import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// add characterIndex to this file
public class CheckFileDelimiters {
    private final BufferedReader reader;
    private Character currentChar = null;
    private String wholeLine = null;
    private int characterIndex = -1; // because getNextChar increments
    private int currentLineIndex = 1;
    private final String filePath;
    private Character previousChar;


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
            //{/r,/n,}
            // Case 1: Inject virtual newline at end of line
            int char_v = this.reader.read();
            
            if (char_v != -1 && (char) char_v != '\n') {
                characterIndex++; // move past virtual '\n'
                return (char)char_v;
            }
              
            // if characterIndex is equal to new line, increment currentLine and go to next line, set character Index to the first char
            else if((char) char_v == '\n'){
                currentLineIndex++;
                characterIndex = 1;
                return (char)char_v;
            }
            //
            else return null;

        } catch (Exception e) {
            return null;
        }
    }


    public String getCurrentPositionInfo() {
        return "Line: " + currentLineIndex + ", Char Index: " + characterIndex;
    }


    /*public Character peekNextChar() {
       getNextChar();
        
    }
    */


    public int getCharacterIndex() {return this.characterIndex;}
    public void setCharacterIndex(int characterIndex) {this.characterIndex = characterIndex;}
    public int getCurrentLineIndex() {return this.currentLineIndex;}
    public void incrementLineIndex() {this.currentLineIndex++;}
    private void incrementCharacterIndex() {this.characterIndex++;}

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



    public void toString(StringBuilder sb) {
       System.out.println(sb.toString());

    }




    private boolean isFileValid(String fileName) {
    File file = new File("src/" + fileName + ".java");

    System.out.println("File path: " + file.getAbsolutePath());
    return file.exists() && file.isFile() && file.canRead();
    }


}
