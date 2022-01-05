package gui.gamepanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;

import game.*;
import gui.Assets;
import gui.MainWindow;
import gui.menupanel.ASCII;

public class GameScreen extends JComponent {
    GamePanel gamePanel;
    Engine engine;
    BufferedImage mapImage;

    public GameScreen(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        init();
    }

    void init() {
        
    }

    void loadEngine() {
        this.engine = gamePanel.engine;
        makeMap();
    }

    void makeMap() {
        mapImage = new BufferedImage(600, 600, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = mapImage.getGraphics();
        
        g.setColor(Color.RED);
        g.fillRect(0, 0, mapImage.getWidth(), mapImage.getHeight());

        ASCII.paintText(g, this, "Map", mapImage.getWidth(), mapImage.getHeight());
    }

    @Override
    public void paint(Graphics g) {
        final BufferedImage background = Assets.Menu.background_main;
        final BufferedImage foreground = Assets.Menu.background_menu_2; 
        
        final int sx = this.getSize().width;
        final int sy = this.getSize().height;
        final double cx = sx/2.0;
        final double cy = sy/2.0;

        // ------------------------------------------------------------
        // MAP


        {
            // background
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
            }

            // show map
            {
                final double x1 = getWidth()*0.10;
                final double y1 = getHeight()*0.30;
                
                final double x2 = getWidth()*(1-0.10);
                final double y2 = getHeight()*(1-0.15);

                ASCII.paintText(g, this, mapImage, (int)x1, (int)y1, (int)x2, (int)y2);
            }
        }

        // ------------------------------------------------------------
        // MENU

        {

            // interaction
            {
                final double anchor = 0.20;

                final double x = sx*anchor - (Assets.Menu.panel_left.getWidth());
                final double y = cy - (Assets.Menu.panel_left.getHeight()*0.5);

                g.drawImage(Assets.Menu.panel_left, (int)x, (int)y, this);
            }

            // playing options
            {
                final double anchor = 0.85;

                final double x = cx - (Assets.Menu.panel_bot.getWidth()*0.5);
                final double y = sy*anchor;

                g.drawImage(Assets.Menu.panel_bot, (int)x, (int)y, this);
            }

            // informations
            {
                final double anchor = 0.75;

                final double x = sx*anchor;
                final double y = cy - (Assets.Menu.panel_right.getHeight()*0.5);

                g.drawImage(Assets.Menu.panel_right, (int)x, (int)y, this);
            }

            // dialog 
            {
                final double anchor = 0.3;

                final double x = cx - (Assets.Menu.panel_top.getWidth()*0.5);
                final double y = sy*anchor - (Assets.Menu.panel_top.getHeight());

                g.drawImage(Assets.Menu.panel_top, (int)x, (int)y, this);
            }

            // upper Menu
            {
                final double anchor = 0.12;

                final double ssx = sx;
                final double ssy = sy*anchor;

                final double x = cx - (Assets.Menu.panel_top.getWidth()*0.5);
                final double y = ssy - (Assets.Menu.panel_top.getHeight());

                final double insetFactor = 0.76;

                g.drawImage(Assets.Menu.panel_top, (int)x, (int)y, this);
                ASCII.paintText(g, this, Assets.Menu.text_logo, 
                    (int)(ssx*(1-insetFactor)),
                    (int)(ssy*(1-insetFactor)) - 25,
                    (int)(ssx*insetFactor),
                    (int)(ssy*insetFactor)
                );
            }

            // upper Menu Text
            {

            }
        }

        // ------------------------------------------------------------
        super.paint(g);
    }
    
}
