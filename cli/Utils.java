package cli;

import java.io.File;
import java.util.Scanner;

public class Utils {
    
    private static final boolean CLEAR_ENABLED = false;
    private static final boolean DEBUG_ENABLED = true;

    // ------------------------------------------

    public static String makeFirstWord(String word) {
        char[] newWord = word.toLowerCase().toCharArray();
        newWord[0] = Character.toUpperCase(newWord[0]);
        return new String(newWord);
    }

    // ------------------------------------------

    public static void delim() {
        delim(60, '-', true);
    }

    public static void delim(int length, char c, boolean b) {
        if (b)
            System.out.print("* ");
        for (int i = 0; i < length; i++)
            System.out.print(c);
        if (b)
            System.out.print(" *");
        
        System.out.println();
    }

    public static void help() {
        delim();
        System.out.println(readAll("game_helper.txt"));
        delim();
    }


    public static void clear() {
        if (CLEAR_ENABLED)
        {
            System.out.print("\033[H\033[2J");  
            System.out.flush();
        }
    }

    public static void exit() {
        delim();
        System.out.println();
        System.out.println("Good Bye !");
        System.out.println();
        System.exit(0);
    }

    public static void error() {
        System.out.println("Error as occured. Try Again");
    }

    public static String input() {
        System.out.print("> ");
        return CLI.sc.nextLine();
    }

    // ------------------------------------------

    public static String readAll(String resource) { 
        StringBuilder str = new StringBuilder(); 

        try {
            String uri = 
                System.getProperty("user.dir")  + File.separator +
                "resources"                     + File.separator +
                "text"                          + File.separator +
                resource;
            
            Scanner sc = new Scanner(new File(uri));
            while (sc.hasNextLine())
                str.append(sc.nextLine() + "\n");
        
        } catch (Exception e) { System.out.println(e);  }
        
        return str.toString();
    }

    // ------------------------------------------
    // Conversions

    public static String xyToAlphabet(int x, int y) {
        throw new Error("Not implemented");
    }

    public static int alphabetToX(String alphabet) {
        throw new Error("Not implemented");
    }

    public static int alphabetToY(String alphabet) {
        throw new Error("Not implemented");
    }

    // ------------------------------------------
    // Debug

    public static void debug(String str) {
        if (DEBUG_ENABLED)
            System.out.println(str);
    }

    public static void printArray(int[] array) {
        System.out.print("[ ");
        for (int e : array)
            System.out.print(e + ", ");
        System.out.println("]");
    }
    
    public static <T> void printArray(T[] array) {
        System.out.print("[ ");
        for (T e : array)
            System.out.print(e + ", ");
        System.out.println("]");
    }
}
