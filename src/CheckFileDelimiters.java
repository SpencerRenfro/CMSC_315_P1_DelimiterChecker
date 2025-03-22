import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

// add characterIndex to this file
public class CheckFileDelimiters {
    private final BufferedReader reader;
    private String currentLine = null;
    private String wholeLine = null;
    private int characterIndex = -1; // because getNextChar increments
    private int currentLineIndex;
    private final String filePath;
    private ArrayList<String> fileLines;

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
            if (currentLine == null || characterIndex >= currentLine.length()) {
                currentLine = reader.readLine();
                characterIndex = 0;
            }

        char c = currentLine.charAt(characterIndex++);
        return c;
            //or  // return currentLine.charAt(index++);
            // basic version, returns every char (no filtering yet)

    } catch (Exception e) {
        return null;
    }
}
//    public char peekNextChar() {
//        if (characterIndex +1 < currentLine.length()) {
//
//        }
//    }
    public String getCurrentEntireLine() {
        return this.currentLine;
    }
    public Character getCurrentChar() {
        return this.currentLine.charAt(currentLineIndex);
    }
//public String getLineCharNums() {
//   try{}
//   catch (Exception e) {
//       return null;
//   }
//}

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
