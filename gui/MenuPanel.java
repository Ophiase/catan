package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;

import game.Config;

public class MenuPanel extends JPanel{
    Config conf;
    MainWindow mainWindow;

    JPanel  layer;

    JPanel layerNPlayers;
    JTextArea nPlayersText;
    JButton lessPlayerBtn;
    JButton morePlayerBtn;


    JPanel layerNBots;
    JTextArea nBotsText;
    JButton lessBotsBtn;
    JButton moreBotsBtn;
    
    JPanel layerNdices;
    JTextArea nDicesText;
    JButton moreNDicesBtn;
    JButton lessNDicesBtn;
    
    JPanel layerSDices;
    JTextArea sDicesText;
    JButton moreSDicesBtn;
    JButton lessSDicesBtn;

    JButton playBtn;

    public MenuPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        init();
    }

    private void init() {
        conf = Config.DEFAULT();
        // -------------------
        this.setLayout(null);
        this.setBackground(new Color(40,30,20));
        // -------------------
        layer = new JPanel();
        layer.setLayout(new GridLayout(5, 1));
        GridLayout glh = new GridLayout(1, 3);

        layerNPlayers = new JPanel();
        layerNBots = new JPanel();
        layerNdices = new JPanel();
        layerSDices = new JPanel();
        playBtn = new JButton("Play");
        
        
        for (JComponent j: new JComponent[] {
            layerNPlayers, layerNBots, layerNdices, layerSDices, playBtn
        }) {
            j.setLayout(glh);
            j.setVisible(true);
            layer.add(j);
        }
        // -------------------

        lessPlayerBtn = new JButton("-");
        nPlayersText = new JTextArea("Number Of Players");
        morePlayerBtn = new JButton("+");
        layerNPlayers.add(lessPlayerBtn);
        layerNPlayers.add(nPlayersText);
        layerNPlayers.add(morePlayerBtn);
        


        layerNPlayers.setBackground(Color.RED);
        layerNBots.setBackground(Color.BLACK);
        layerNdices.setBackground(Color.BLUE);
        layerSDices.setBackground(Color.GREEN);

        // -------------------
        layer.setBackground(new Color(70, 60, 160));
        // -------------------
        this.add(layer);
        this.setVisible(true);
    }

    public void reset() {
        conf = Config.DEFAULT();
        setVisible(true);
        repaint();
    }

    public void clear() {
        setVisible(false);
    }

    @Override
    public void paint(Graphics g) {
        int sx = this.getSize().width;
        int sy = this.getSize().height;

        int cx = sx/2;
        int cy = sy/2;

        int lsx = 300, lsy = 250;
        layer.setBounds(cx-lsx, cy-lsy, 2*lsx, 2*lsy);

        super.paint(g);
    }

    // --------------------------------------
    
}
