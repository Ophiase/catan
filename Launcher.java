import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        cli.Utils.delim();
        System.out.println();
        credit();
        System.out.println();
        cli.Utils.delim();
        System.out.println();
        prompt();
    }

    static void credit() {
        System.out.println(
            "    ______           __                 "+"\n"+
            "   / ____/  ____ _  / /_  ____ _   ____ "+"\n"+
            "  / /      / __ `/ / __/ / __ `/  / __ \\"+"\n"+
            " / /___   / /_/ / / /_  / /_/ /  / / / /"+"\n"+
            " \\____/   \\__,_/  \\__/  \\__,_/  /_/ /_/ "+"\n"
        );

        System.out.println("Authors : Aaron Berriche | Jie Zhou");
        System.out.println();
        System.out.println("POO3 Project | 2021");

    }

    static void prompt() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Do you want to play using CLI ? (y/n)");
        String r = sc.nextLine();

        sc.close();

        System.out.println();
        cli.Utils.delim();
        System.out.println();

        if (!r.isEmpty() && r.toLowerCase().charAt(0) == 'y') 
            new cli.CLI();
        else 
            new gui.GUI();
    }
}
