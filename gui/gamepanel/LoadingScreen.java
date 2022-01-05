package gui.gamepanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;

import game.*;
import gui.Assets;
import gui.MainWindow;
import gui.constants.ASCII;

public class LoadingScreen extends JComponent {
    GamePanel gamePanel;

    public LoadingScreen(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.init();
    }

    public void init() {

    }

    public void load () {
        cli.Utils.debug("Loading a party ...");
        // make engine
        gamePanel.engine = new Engine(gamePanel.mainWindow.conf);

        // wait for assets
        try {
            //Thread.sleep(2000); // debug purpose
            while (!Assets.loaded) {
                Thread.sleep(50);
            }
        } catch (Exception e) {}

        // start game
        gamePanel.gameReady();
    }

    @Override
    public void paint(Graphics g) {
        final BufferedImage background = Assets.Menu.background_main_blur;
        final BufferedImage logo       = Assets.Menu.text_logo;

        final int sx = this.getSize().width;
        final int sy = this.getSize().height;
        final double cx = sx/2.0;
        final double cy = sy/2.0;

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

        // loading

        {
            final double x1 = getWidth()*0.25;
            final double y1 = getHeight()*0.40;
            
            final double x2 = getWidth()*(1-0.25);
            final double y2 = getHeight()*(1-0.40);

            ASCII.paintText(g, this, "Loading game ..", (int)x1, (int)y1, (int)x2, (int)y2);
        }

        // logo

        {
            final double tsx = (double)logo.getWidth(this) * 0.5;
            final double tsy = (double)logo.getHeight(this) * 0.5;
            final double tcx = (tsx)/2;
            final double tcy = (tsy)/2;

            final double x = (int)(cx-tcx);
            final double y = (int)((sy*0.35)-tcy);

            g.drawImage(logo, 
            (int)x, (int)y,
            (int)tsx, (int)tsy,
            this);
        }
    }
}
