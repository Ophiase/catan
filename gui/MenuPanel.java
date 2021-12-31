package gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

import game.Config;

public class MenuPanel extends JPanel{
    MainWindow mainWindow;

    public MenuPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void reset() {
        this.setLayout(null);
        
        JButton test = new JButton("Hey");
        test.addActionListener((ActionEvente ) ->
        {
            cli.Utils.debug("!!!");
        });

        this.add(test);
        
        this.setBackground(Color.red);
        this.setPreferredSize(new DimensionUIResource(500, 500));

        setVisible(true);
    }

    public void clear() {
        setVisible(false);
    }
    
}
