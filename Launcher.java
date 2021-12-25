import java.net.URL;
import java.util.Scanner;

public class Launcher {
    public static Scanner sc = cli.CLI.sc;

    public static void main(String[] args) {
        cli.Utils.delim();
        System.out.println();
        
        credit();
        
        System.out.println();
        cli.Utils.delim();
        System.out.println();
        
        prompt();

        cli.Utils.exit();
    }

    static void credit() {
        System.out.println(cli.Utils.readAll("credit.txt"));
    }

    static void prompt() {
        System.out.println("Do you want to play using CLI ? (y/n)");
        System.out.print("> ");
        String r = sc.nextLine();

        System.out.println();
        cli.Utils.delim();
        System.out.println();

        if (!r.isEmpty() && r.toLowerCase().charAt(0) == 'y') 
            new cli.CLI();
        else 
            new gui.GUI();
    }
}
