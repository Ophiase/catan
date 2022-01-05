package gui.gamepanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.JComponent;
import javax.swing.JPanel;

import cli.GameLoop;
import game.*;
import game.state.Map;
import game.utils.Fnc;
import gui.Assets;
import gui.GameLoopGUI;
import gui.MainWindow;
import gui.constants.ASCII;
import gui.gamepanel.context.ActionContext;
import gui.gamepanel.context.DataContext;
import gui.gamepanel.context.InformationContext;
import gui.gamepanel.context.InteractionContext;
import gui.gamepanel.context.PartyDataContext;
import gui.math.Geometry;

public class GameScreen extends JComponent {
    GamePanel gamePanel;
    Engine engine;
    BufferedImage mapImage;

    // ---------------------

    GameLoopGUI gameLoop;

    InteractionContext interactionContext;
    ActionContext actionContext;
    DataContext dataContext;
    InformationContext informationContext;
    PartyDataContext partyDataContext;

    // ---------------------
    // INIT

    public GameScreen(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        init();
    }

    void init() {
        interactionContext  = new InteractionContext(this);
        actionContext       = new ActionContext(this);
        dataContext         = new DataContext(this);
        informationContext  = new InformationContext(this);
        partyDataContext    = new PartyDataContext(this);
    }

    void loadEngine() {
        this.engine = gamePanel.engine;
        makeMap();

        gameLoop = new GameLoopGUI(this);
    }

    // -------------------------------------------------
    // Make map part

    static void paintScaled(
        Graphics g, ImageObserver o, 
        BufferedImage text, double x1, double y1, double x2, double y2,
        double scale, double center 
    ) {
        ASCII.paintText(g, o, text,
            (int)Geometry.scale(x1, scale, center), 
            (int)Geometry.scale(y1, scale, center),
            (int)Geometry.scale(x2, scale, center),
            (int)Geometry.scale(y2, scale, center));
    }

    static void paintScaled(
        Graphics g, ImageObserver o, 
        String text, double x1, double y1, double x2, double y2,
        double scale, double center 
    ) {
        ASCII.paintText(g, o, text,
            (int)Geometry.scale(x1, scale, center), 
            (int)Geometry.scale(y1, scale, center),
            (int)Geometry.scale(x2, scale, center),
            (int)Geometry.scale(y2, scale, center));
    }

    void makeMap() {
        mapImage = new BufferedImage(700, 700, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = mapImage.getGraphics();
        
        if (false) // debug
        {
            g.setColor(Color.RED);
            g.fillRect(0, 0, mapImage.getWidth(), mapImage.getHeight());
            ASCII.paintText(g, this, "Map", mapImage.getWidth(), mapImage.getHeight());
        }

        // -------------------------------------------------------------

        Map map = engine.getMap();
        
        int[][] biomes    = map.getBiomes();
        int[][] diceIndexes = map.getDiceIndexes();

        final double tile_sx = (double)mapImage.getWidth()  / (double)biomes.length;
        final double tile_sy = (double)mapImage.getHeight() / (double)biomes.length;

        final double scale_map_down_factor = (double)biomes.length / (double)(biomes.length+1); // scale map down
        final double scale_map_down_center = (double)(mapImage.getWidth()) * 0.5;

        // Make ports
        {
            int[][] ports = map.getPorts();
            int sizePP = map.getSizePP();

            final double insetFactor = 0.6;
            final double remainder   = (1.0 - insetFactor)*0.5;
            
            final double ssx = tile_sx*insetFactor;
            final double ssy = tile_sy*insetFactor;

            final double msx = ssx*0.5;
            final double msy = ssy*0.5;

            for (int port = 0; port < ports.length; port++)
            for (int location = 0; location < ports[port].length; location++)
            {
                final int x = Fnc.conv1dto2d_x(ports[port][location], sizePP);
                final int y = Fnc.conv1dto2d_y(ports[port][location], sizePP);
                
                final double tile_x1 = tile_sx*x - msx;
                final double tile_y1 = tile_sy*y - msy;
                final double tile_x2 = tile_x1+ssx;
                final double tile_y2 = tile_y1+ssy;

                paintScaled(g, this, gui.constants.PortAsset.imgs[port],
                    tile_x1,tile_y1,tile_x2,tile_y2, 
                    scale_map_down_factor, scale_map_down_center
                );
            }
        }

        // Make biomes
        {
            
            final double tile_insetFactor = 0.8;
            final double tile_remainder   = (1.0 - tile_insetFactor)*0.5;
            
            final double tile_ssx = tile_sx*tile_insetFactor;
            final double tile_ssy = tile_sy*tile_insetFactor;

            final double tile_rsx = tile_sx*tile_remainder;
            final double tile_rsy = tile_sy*tile_remainder;

            // ------------------------------

            final double index_insetFactor = 0.6;
            final double index_remainder   = (1.0 - index_insetFactor)*0.5;
            
            final double index_ssx = tile_sx*index_insetFactor;
            final double index_ssy = tile_sy*index_insetFactor;

            final double index_rsx = tile_sx*index_remainder;
            final double index_rsy = tile_sy*index_remainder;

            // ------------------------------

            final int robberDice = engine.getDices().getRobberDice();

            for (int x = 0; x < biomes.length; x++)
            for (int y = 0; y < biomes[x].length; y++)
            {
                final double tile_x1 = tile_sx*x + tile_rsx;
                final double tile_y1 = tile_sy*y + tile_rsy;
                final double tile_x2 = tile_x1+tile_ssx;
                final double tile_y2 = tile_y1+tile_ssy;

                
                paintScaled(g, this, gui.constants.BiomeAsset.imgs[biomes[x][y]],
                    tile_x1,tile_y1,tile_x2,tile_y2, 
                    scale_map_down_factor, scale_map_down_center
                );
                
                // ------------------------------

                final double index_x1 = tile_sx*x + index_rsx;
                final double index_y1 = tile_sy*y + index_rsy;
                final double index_x2 = index_x1+index_ssx;
                final double index_y2 = index_y1+index_ssy;

                if (diceIndexes[x][y] != 0 && diceIndexes[x][y] != robberDice)
                    paintScaled(g, this, ""+diceIndexes[x][y],
                        index_x1,index_y1,index_x2,index_y2, 
                        scale_map_down_factor, scale_map_down_center
                    );
                
            }
        }

    }

    // -------------------------------------------------
    // Paint

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
