package gui;

import javax.swing.*;
import java.awt.*;

import game.*;
import gui.Assets.Menu;

public class MainWindow extends JFrame {
    public boolean lookingAtMenu;
    public Config conf;
    // -----------------
    public MenuPanel menuPanel;
    public GamePanel gamePanel;
    // -----------------
    public MainWindow() {
        cli.Utils.debug("Window constructor.");

        this.setTitle("Catan");
        this.setMinimumSize(new Dimension(600, 300));
        this.setPreferredSize(new Dimension(1280, 720));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        // ---------------
        menuPanel = new MenuPanel(this);
        gamePanel = new GamePanel(this);
        focusOnMenu();
        // ---------------
        this.pack();
        this.setVisible(true);
    }
    // -----------------

    public void focusOnGame() {
        if (conf==null) return;

        cli.Utils.debug("Switch view to game.");
        lookingAtMenu = false;
        gamePanel.reset();
        setContentPane(gamePanel);
        menuPanel.clear();
    }
    public void focusOnMenu() {
        cli.Utils.debug("Switch view to menu.");
        lookingAtMenu = true;
        menuPanel.reset();
        setContentPane(menuPanel);
        gamePanel.clear();
    }
    
}
