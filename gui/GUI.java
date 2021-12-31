package gui;

import javax.swing.*;
import java.awt.*;

public class GUI {
    public MainWindow mainWindow;

    /** Define an instance of the game 
     * using a graphical interface  */
    public GUI() {
        System.out.println("Launching the graphical user interface ...");
        
        // load assets
        new Thread(
            () -> Assets.load()
        ).start();

        // open window
        EventQueue.invokeLater(() -> {
            try {
                while (!Assets.menuLoaded) {
                    Thread.sleep(50);
                }
            } catch (Exception e) {}

            new MainWindow();
            cli.Utils.exit();
        });
    }
}
