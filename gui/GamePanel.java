package gui;

import javax.swing.JPanel;

import game.*;

public class GamePanel extends JPanel{
    MainWindow mainWindow;
    public GamePanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void reset() {
        setVisible(true);
    }

    public void clear() {
        setVisible(false);
    }
    
}
