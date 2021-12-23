package cli;

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
}
