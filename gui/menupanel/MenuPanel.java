package gui.menupanel;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.JTextComponent;

import com.ibm.jvm.trace.format.api.Component;

import cli.Utils;

import game.Config;
import gui.Assets;
import gui.MainWindow;
import gui.Assets.Menu;

public class MenuPanel extends JPanel{
    Config conf;
    MainWindow mainWindow;

    JComponent  background;
    JComponent  layer;

    JComponent layerNPlayers;
    JComponent nPlayersText;
    JComponent lessPlayerBtn;
    JComponent morePlayerBtn;


    JComponent layerNBots;
    JComponent nBotsText;
    JComponent lessBotsBtn;
    JComponent moreBotsBtn;
    
    JComponent layerNdices;
    JComponent nDicesText;
    JComponent moreNDicesBtn;
    JComponent lessNDicesBtn;
    
    JComponent layerSDices;
    JComponent sDicesText;
    JComponent moreSDicesBtn;
    JComponent lessSDicesBtn;

    JComponent playBtn;

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
            @Override
            public void paint(Graphics g) {
                final BufferedImage background = Assets.Menu.background_main_blur;
                final BufferedImage logo       = Assets.Menu.text_logo;
                
                final int sx = this.getSize().width;
                final int sy = this.getSize().height;
                final double cx = sx/2.0;
                final double cy = sy/2.0;

                double memy = 0;

                {
                    final double tsx = (double)background.getWidth(this) * 0.5;
                    final double tsy = (double)background.getHeight(this) * 0.5;
                    final double tcx = (tsx)/2;
                    final double tcy = (tsy)/2;

                    final double x = (int)(cx-tcx);
                    final double y = (int)(cy-tcy);

                    g.drawImage(background, 
                    (int)x, (int)y,
                    (int)tsx, (int)tsy,
                    this);

                    g.drawImage(background, 
                    (int)(x-(tsx*1)), (int)y,
                    (int)tsx, (int)tsy,
                    this);

                    g.drawImage(background, 
                    (int)(x+(tsx*1)), (int)y,
                    (int)tsx, (int)tsy,
                    this);

                    memy = y;
                }

                // logo

                {
                    final double tsx = (double)logo.getWidth(this) * 0.5;
                    final double tsy = (double)logo.getHeight(this) * 0.5;
                    final double tcx = (tsx)/2;
                    final double tcy = (tsy)/2;

                    final double x = (int)(cx-tcx);
                    final double y = 70+(int)((memy/2.0));

                    g.drawImage(logo, 
                    (int)x, (int)y,
                    (int)tsx, (int)tsy,
                    this);
                }

                // menu panel

                {
                    final BufferedImage menu = Assets.Menu.background_menu;
                    final int lsx = 300, lsy = 300;

                    g.drawImage(menu, (int)(cx-lsx), (int)(cy-lsy), (2*lsx), (2*lsy), this);

                }

            }
        };

        layer = new JComponent() {};
        layer.setLayout(new GridLayout(5, 1));

        layerNPlayers   = new JComponent() {};
        layerNBots      = new JComponent() {};
        layerNdices     = new JComponent() {};
        layerSDices     = new JComponent() {};
        playBtn = new JComponent() {
            {
                addMouseListener(new MouseInputAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        hover = true;
                        repaint();
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        hover = false;
                        repaint();
                    }
                });
            }

            public boolean hover = false;

            @Override
            public void paint(Graphics g) {
                final BufferedImage b0 = Assets.Menu.btn_play;
                final BufferedImage b1 = Assets.Menu.btn_play_click;
                
                final int sx = this.getSize().width;
                final int sy = this.getSize().height;
                final double cx = sx/2.0;
                final double cy = sy/2.0;
        
                final int ssx = (int)((double)b0.getWidth()*( (double)sy/(double)b0.getHeight() ));
                final int x = (int)(cx-(ssx/2.0));
                
                g.drawImage(
                    hover ? (b1) : (b0),
                    x,0,ssx,sy,
                    this
                );
            }
        };
        
        LayoutManager glh = new GridLayout(1, 3);
        for (JComponent j: new JComponent[] {
            layerNPlayers, layerNBots, layerNdices, layerSDices, playBtn
        }) {
            j.setLayout(glh);
            j.setVisible(true);
            layer.add(j);
        }

        // -------------------

            layerNPlayers.add(lessPlayerBtn = new BtnMinus());
            layerNPlayers.add(nPlayersText  = new JComponent() {
                
            });
            layerNPlayers.add(morePlayerBtn = new BtnPlus());
        
            // -----------
            
            layerNBots.add(lessBotsBtn = new BtnMinus());
            layerNBots.add(nBotsText = new JComponent() {
                
            });
            layerNBots.add(moreBotsBtn = new BtnPlus());
        
            // -----------

            layerNdices.add(lessNDicesBtn = new BtnMinus());
            layerNdices.add(nDicesText  = new JComponent() {
                
            });
            layerNdices.add(moreNDicesBtn = new BtnPlus());
        
            // -----------

            layerSDices.add(lessSDicesBtn = new BtnMinus());
            layerSDices.add(sDicesText  = new JComponent() {
                
            });
            layerSDices.add(moreSDicesBtn = new BtnPlus());
        

        // -------------------

        layerNPlayers   .setBackground(Color.RED);
        layerNBots      .setBackground(Color.BLACK);
        layerNdices     .setBackground(Color.BLUE);
        layerSDices     .setBackground(Color.GREEN);

        // -------------------
        //layer.setBackground(new Color(70, 60, 160));
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

        int lsx = 250, lsy = 250;
        layer.setBounds(cx-lsx, cy-lsy, 2*lsx, 2*lsy);
        background.setBounds(0, 0, sx, sy);

        // ----------------
        super.paint(g);
    }

    // --------------------------------------
}
