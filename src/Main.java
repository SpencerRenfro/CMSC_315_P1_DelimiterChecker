import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("src/Test.java");
        Scanner myReader = new Scanner(file);
        String[] delimiters = {"[", "]", "{", "}", "(", ")"};
        String delimiter = "[";



        if(file.exists()){
            System.out.println("File found");

        } else {
            System.out.println("File not found");
        }



//        while(myReader.hasNextLine()){
//            String data = myReader.nextLine();


            //            String data = myReader.nextLine();
//            System.out.println(data);

//            if(myReader.hasNext()){
//                int i = 0;
//                Character c = myReader.next().charAt(i);
//                System.out.println(c);
//            }

//            if(myReader.hasNext()){
//                String s = myReader.next();
//                if(!s.isEmpty()){
//                    for(int i = 0; i <= s.length(); i++){
//                        Character c = s.charAt(i);
//                        System.out.println(c);
//                    };
//                }
//
//            }

//            if(myReader.hasNextLine()){
//                String s = myReader.nextLine();
//                if(s.contains("[")){
//                    System.out.println("Delimiter found");
//                }
//            }
            //Character c = myReader.next();
                    //.charAt(0);
     //   }
    }
}