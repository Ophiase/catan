package gui.gamepanel;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

import game.*;
import gui.GameLoopGUI;
import gui.MainWindow;

/** This class is kind of an interface to the game. 
 * It manage the switch between Loading and the Game */
public class GamePanel extends JPanel{
    public MainWindow mainWindow;
    public Engine engine;

    public LoadingScreen loadingScreen;
    public GameScreen gameScreen;
    public boolean gameLoaded;

    public GamePanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        init();
    }

    public void init() {
        this.loadingScreen  = new LoadingScreen(this);
        this.gameScreen     = new GameScreen(this);

        this.setLayout(null);
        this.gameLoaded = false;
    }

    public void reset() {
        clear();

        this.add(loadingScreen);
        setVisible(true);
        new Thread( () -> {
                loadingScreen.load();
        }).start();
    }

    public void gameReady() {
        cli.Utils.debug("Party ready.");
        
        gameScreen.init();
        gameScreen.loadEngine();
        
        this.removeAll();
        this.add(gameScreen);
        repaint();
        this.gameLoaded = true;

        gameScreen.gameLoop = new GameLoopGUI(gameScreen);
        gameScreen.gameLoop.init();
    }

    public void clear() {
        setVisible(false);
        this.removeAll();
        this.gameLoaded = false;
    }

    @Override
    public void paint(Graphics g) {
        if (gameLoaded) 
            gameScreen.setBounds(0, 0, getWidth(), getHeight());
        else
            loadingScreen.setBounds(0, 0, getWidth(), getHeight());

        // ----------------
        super.paint(g);
    }
}
