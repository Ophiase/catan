package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import game.*;
import gui.Assets.Menu;
import gui.gamepanel.GamePanel;
import gui.menupanel.MenuPanel;

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
        this.setIconImage(Assets.Menu.icon_logo);
        
        this.setMinimumSize(new Dimension(1000, 800));
        this.setPreferredSize(new Dimension(1280, 900));
        this.setMaximumSize(new Dimension(1920, 1080));
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                cli.Utils.exit();
            }
        });
        this.setLayout(null);
        //System.setProperty("sun.awt.noerasebackground", "true");
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
        menuPanel.clear();
        setContentPane(gamePanel);
        gamePanel.reset();
    }
    public void focusOnMenu() {
        cli.Utils.debug("Switch view to menu.");
        lookingAtMenu = true;
        gamePanel.clear();
        setContentPane(menuPanel);
        menuPanel.reset();
    }
    
}
