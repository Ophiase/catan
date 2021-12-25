package cli;

import java.io.File;
import java.util.Scanner;

public class Utils {
    
    public static void delim() {
        delim(30, '-', true);
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
        readAll("gameHelper.txt");
        delim();
    }

    public static void exit() {
        delim();
        System.out.println();
        System.out.println("Good Bye !");
        System.out.println();
        System.exit(0);
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
}
