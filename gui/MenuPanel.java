package gui;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.JTextComponent;

import org.w3c.dom.events.MouseEvent;

import cli.Utils;

import game.Config;

public class MenuPanel extends JPanel{
    Config conf;
    MainWindow mainWindow;

    JComponent  background;
    JPanel  layer;

    JPanel layerNPlayers;
    JTextArea nPlayersText;
    JComponent lessPlayerBtn;
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
        background = new JComponent() {
            
        };

        layer = new JPanel();
        layer.setLayout(new GridLayout(5, 1));

        layerNPlayers = new JPanel();
        layerNBots = new JPanel();
        layerNdices = new JPanel();
        layerSDices = new JPanel();
        playBtn = new JButton("Play");
        
        GridLayout glh = new GridLayout(1, 3);
        for (JComponent j: new JComponent[] {
            layerNPlayers, layerNBots, layerNdices, layerSDices, playBtn
        }) {
            j.setLayout(glh);
            j.setVisible(true);
            layer.add(j);
        }

        // -------------------

        lessPlayerBtn   = new JComponent () {
            {
                this.addMouseListener(
                    new MouseInputAdapter() {

                        public void mouseEntered(java.awt.event.MouseEvent e) {
                            paintFnc = (g) -> {

                            }; repaint();
                        };

                        public void mouseExited(java.awt.event.MouseEvent e) {

                        };
                    }
                );
            }

            Consumer<Graphics> paintFnc = (g) -> {
                g.setColor(Color.CYAN);
            };

            @Override
            public void paint(Graphics g) {
                paintFnc.accept(g);
            }

        };

        nPlayersText    = new JTextArea("Number Of Players");
        morePlayerBtn   = new JButton("+");

        layerNPlayers.add(lessPlayerBtn);
        layerNPlayers.add(nPlayersText);
        layerNPlayers.add(morePlayerBtn);
        

        layerNPlayers   .setBackground(Color.RED);
        layerNBots      .setBackground(Color.BLACK);
        layerNdices     .setBackground(Color.BLUE);
        layerSDices     .setBackground(Color.GREEN);

        // -------------------
        layer.setBackground(new Color(70, 60, 160));
        // -------------------
        this.add(layer);
        this.add(background);
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
        Graphics2D g2 = (Graphics2D)g;

        int sx = this.getSize().width;
        int sy = this.getSize().height;

        int cx = sx/2;
        int cy = sy/2;

        int lsx = 300, lsy = 250;
        layer.setBounds(cx-lsx, cy-lsy, 2*lsx, 2*lsy);
        background.setBounds(0, 0, sx, sy);

        // ----------------
        super.paint(g);
    }

    // --------------------------------------
}
