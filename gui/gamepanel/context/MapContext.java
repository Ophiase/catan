package gui.gamepanel.context;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
//import java.nio.Buffer;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import cli.Utils;
import game.constants.Developpement;
import game.state.Map;
import game.state.Player;
import game.state.State;
import game.utils.Fnc;
import game.utils.Trade;
import gui.Assets;
import gui.GameLoopGUI;
import gui.constants.ASCII;
import gui.constants.EntityAsset;
import gui.gamepanel.GameScreen;
import gui.math.Geometry;

public class MapContext extends JComponent {
    
    public int contextState = 0;
    
    public static final int DEFAULT_STATE = 0;
    
    public static final int PUT_FIRST_ROAD_STATE = 1;
    public static final int PUT_FIRST_COLONY_STATE = 3;

    public static final int DEV_ROAD_STATE = 8;
    
    public static final int PUT_ROAD_STATE = 2;
    public static final int PUT_COLONY_STATE = 4;
    public static final int PUT_CITY_STATE = 5;

    public static final int PUT_ROBBER_STATE = 6;
    public static final int KNIGHT_STATE = 7;
    
    // ------------------------------------------
    
    BufferedImage storedMapComponents;
    BufferedImage tmpMapComponents;
    BufferedImage voidMap;
    State state;
    Map map;
    Trade trade;
    
    // ------------------------------------------

    GameScreen gameScreen;
    public MapContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        setVisible(false);
    }
    
    private double SCALE_MAP_UP_FACTOR = 1.0;
    public void init() {
        this.removeAll();
        contextState = DEFAULT_STATE;
        //---------------------------------

        storedMapComponents = new BufferedImage(
            gameScreen.MAP_RES,
            gameScreen.MAP_RES,
            BufferedImage.TYPE_4BYTE_ABGR);

        tmpMapComponents = new BufferedImage(
            gameScreen.MAP_RES,
            gameScreen.MAP_RES,
            BufferedImage.TYPE_4BYTE_ABGR
        );
        voidMap = new BufferedImage(
            gameScreen.MAP_RES,
            gameScreen.MAP_RES,
            BufferedImage.TYPE_4BYTE_ABGR
        );

        
        state   = gameScreen.engine.getState();
        map     = gameScreen.engine.getMap();
        trade   = gameScreen.engine.getTrade();
        
        initConsts();

        makeMouseListener();
        setVisible(true);
    }

    // ------------------------------------------

    MouseInputAdapter mouseInputAdapter;
    private void makeMouseListener() {
        mouseInputAdapter = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Utils.debug("Mouse clicked on map.");
                applyState();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //Utils.debug("Mouse moved on map.");
                calculateMouseFocus(e.getX(), e.getY());
                calculateTmpMap();
            }
        };
    }

    public void listen() {
        this.addMouseListener(mouseInputAdapter);
        this.addMouseMotionListener(mouseInputAdapter);
    }

    public void stopListening() {
        this.removeMouseListener(mouseInputAdapter);
        this.removeMouseMotionListener(mouseInputAdapter);
    }

    private int     focus  = 0;
    private int     mouseX = 0;
    private int     mouseY = 0;
    private boolean mouseH = false;
    private void calculateMouseFocus(int xOnImage, int yOnImage) {
        final double x = Geometry.scale(
            gameScreen.MAP_RES*(double)xOnImage/(double)getWidth(), 
            SCALE_MAP_UP_FACTOR, gameScreen.SCALE_MAP_DOWN_CENTER
        );
        
        final double y = Geometry.scale(
            gameScreen.MAP_RES*(double)yOnImage/(double)getHeight(), 
            SCALE_MAP_UP_FACTOR, gameScreen.SCALE_MAP_DOWN_CENTER
        );

        switch (contextState) {
            case DEFAULT_STATE : {
                // nothing
            } break;
            case PUT_FIRST_ROAD_STATE : case PUT_ROAD_STATE : case DEV_ROAD_STATE: {
                /**
                 * Ne chechez pas à comprendre cette partie du code,
                 * c'est le fruit de schémas paints fait à 5h du matin
                 * et beaucoup de prises de tête..
                 */
                
                // passer dans le space de la grille à 45 (normalisé)
                final double x_r = (x/tile_sx) + 0.5; // normalisé (1 tile = 1)
                final double y_r = (y/tile_sy);       // normalisé (1 tile = 1)

                // determiner le "carré" dans le module
                final double x_d = Math.floor(x_r);
                final double y_d = Math.floor(y_r);

                final double x_m = x_r - x_d; // resultat entre 0 et 1
                final double y_m = y_r - y_d; // resultat entre 0 et 1

                int offsetX = 0; // par defaut pas d'offset
                int offsetY = 0; // par defaut pas d'offset
                mouseH = false;   // point de vue des losanges verts (verticales)

                // tout les cas sont symétriques
                if (x_m < 0.5 && y_m < 0.5) { // supp gauche
                    final double x_s = x_m;
                    final double y_s = y_m;
                    if (aboveLimit(x_s, y_s)) {
                        offsetX = -1;
                        offsetY = 0;
                        mouseH = true;
                } }
                else  if (x_m > 0.5 && y_m < 0.5) { // supp droit
                    final double x_s = 1.0-x_m;
                    final double y_s = y_m;
                    if (aboveLimit(x_s, y_s)) {
                        offsetX = 0;
                        offsetY = 0;
                        mouseH = true;
                } }
                else  if (x_m < 0.5 && y_m > 0.5) { // inf gauche
                    final double x_s = x_m;
                    final double y_s = 1.0-y_m;
                    if (aboveLimit(x_s, y_s)) {
                        offsetX = -1;
                        offsetY = 1;
                        mouseH = true;
                } }
                else  if (x_m > 0.5 && y_m > 0.5) { // inf droit
                    final double x_s = 1.0-x_m;
                    final double y_s = 1.0-y_m;
                    if (aboveLimit(x_s, y_s)) {
                        offsetX = 0;
                        offsetY = 1;
                        mouseH = true;
                } }

                // on tout ce qui faut
                if (mouseH) {
                    mouseX = (int)x_d + offsetX;
                    mouseY = (int)y_d + offsetY;

                    if (mouseX >= map.getSize()) mouseX = map.getSize()-1;
                } else {
                    mouseX = (int)x_d;
                    mouseY = (int)y_d;

                    if (mouseY >= map.getSize()) mouseY = map.getSize()-1;
                }

                if (mouseX < 0) mouseX = 0;
                if (mouseY < 0) mouseY = 0;

            } break;
            case PUT_FIRST_COLONY_STATE : case PUT_COLONY_STATE : case PUT_CITY_STATE : {
                mouseX = (int)Math.round(x / tile_sx);
                mouseY = (int)Math.round(y / tile_sy);

                if (mouseX < 0) mouseX = 0;
                if (mouseY < 0) mouseY = 0;

                if (mouseX > map.getSize()) mouseX = map.getSize();
                if (mouseY > map.getSize()) mouseY = map.getSize();
            } break;
            case PUT_ROBBER_STATE: case KNIGHT_STATE: {
                mouseX = (int)Math.floor(x / tile_sx);
                mouseY = (int)Math.floor(y / tile_sy);

                if (mouseX < 0) mouseX = 0;
                if (mouseY < 0) mouseY = 0;

                if (mouseX >= map.getSize()) mouseX = map.getSize() - 1;
                if (mouseY >= map.getSize()) mouseY = map.getSize() - 1;
            } break;
        }
    }

    private static boolean aboveLimit (double x, double y) {
        return (0.5-y) > x;
    }

    // ------------------------------------------


    public void setState(int state) {
        if (state == 0)
        {
            // apply image

            storedMapComponents.getGraphics().drawImage(
                tmpMapComponents, 0, 0, this
            );
            tmpMapComponents.setData(voidMap.getRaster());
            updateMap();

            // return to default state

            stopListening();
            contextState = 0;
            return;
        }

        contextState = state;
        focus = this.state.getFocus();
        listen();

        switch(contextState) {
            case DEFAULT_STATE : {
                // nothing
            } break;
            case DEV_ROAD_STATE: {
                outputNDevRoads = 0;
                this.state.getPlayer(focus).useDeveloppement(Developpement.ROAD);
                gameScreen.informationContext.publish(
                    "Choose a road. Click on the card again if you want to quit "
                    +"(warning: the card will still be consumed)."
                    );
                break;
            }
            case PUT_ROAD_STATE : case PUT_FIRST_ROAD_STATE: {
                gameScreen.informationContext.publish("Choose a road.");
            } break;
            case PUT_FIRST_COLONY_STATE : case PUT_COLONY_STATE : {
                gameScreen.informationContext.publish("Choose a colony.");
            } break;
            case PUT_CITY_STATE : {
                gameScreen.informationContext.publish("Choose a city.");
            } break;
            case KNIGHT_STATE : case PUT_ROBBER_STATE : {
                gameScreen.informationContext.publish("Choose where to put the robber.");
            }
        }
    }

    // ------------------------------------------
    // GRAPHICS

    public double tile_sx;
    public double tile_sy;
    public void initConsts() {
        SCALE_MAP_UP_FACTOR = 1.0 / gameScreen.SCALE_MAP_DOWN_FACTOR;

        tile_sx = (double)gameScreen.MAP_RES / (double)map.getBiomes().length;
        tile_sy = tile_sx;
    }

    public int outputNDevRoads = 0;
    public int outputState = 0;
    public int outputX     = 0;
    public int outputY     = 0;
    public boolean outputH = false;

    public void applyState() {
        final int who = focus;
        final int cx = mouseX;
        final int cy = mouseY;
        final boolean h = mouseH;

        switch(contextState) {
            case DEFAULT_STATE : {
                gameScreen.repaint();
            } break;
            
            case PUT_FIRST_ROAD_STATE : {
                if (!map.canBuyRoad(who, h, cx, cy)) 
                {
                    gameScreen.informationContext.publish("Invalid road. Choose another one!");
                    return;
                }
                // ------------

                state.addRoad(who, h, cx, cy);

                outputX = cx;
                outputY = cy;
                outputH = h;    
                outputState = PUT_ROAD_STATE;                
                setState(DEFAULT_STATE);
                gameScreen.gameLoop.flowing = true;
            } break;
            case DEV_ROAD_STATE : {
                if (!map.canBuyRoad(who, h, cx, cy)) 
                {
                    gameScreen.informationContext.publish("Invalid road. Choose another one!");
                    return;
                }
                // ------------

                state.addRoad(who, h, cx, cy);
                updateMap();

                if (++outputNDevRoads == 2)
                {
                    outputX = cx;
                    outputY = cy;
                    outputH = h;    
                    outputState = DEV_ROAD_STATE;        
                    setState(DEFAULT_STATE);
                    gameScreen.informationContext.publish("You put the two roads of your card.");
                    gameScreen.actionContext.contextState = ActionContext.FOCUS_STATE;
                }
            } break;
            case PUT_ROAD_STATE : {
                if (!map.canBuyRoad(who, h, cx, cy)) 
                {
                    gameScreen.informationContext.publish("Invalid road. Choose another one!");
                    return;
                }
                // ------------

                trade.buyRoad(who, h, cx, cy);

                outputX = cx;
                outputY = cy;
                outputH = h;    
                outputState = PUT_ROAD_STATE;                
                setState(DEFAULT_STATE);
                gameScreen.actionContext.contextState = ActionContext.FOCUS_STATE;
                gameScreen.informationContext.publish("Successfull.");
                gameScreen.informationContext.publish("Choose an action.");
            
            } break;

            case PUT_FIRST_COLONY_STATE : {
                if (map.hasColony(cx, cy) || map.hasNearColony(who, cx, cy))
                {
                    gameScreen.informationContext.publish("Invalid colony. Choose another one!");
                    return;
                }

                // ------------------------------------------------------
                // on verifie qu'on pourra bien poser une road juste après
                map.getColonies()[cx][cy] = map.makeColony(false, who);
                game.AI.RoadResponse road = gameScreen.engine.getAI().roadOnLocation(who, cx, cy);
                map.getColonies()[cx][cy] = 0;

                if (!road.valid)
                {
                    gameScreen.informationContext.publish("Invalid colony. Choose another one!");
                    return;
                }
                // ------------

                state.addColony(who, cx, cy);

                outputX = cx;
                outputY = cy;
                outputH = h;    
                outputState = PUT_FIRST_COLONY_STATE;
                setState(DEFAULT_STATE);
                
                gameScreen.gameLoop.flowing = true;

            } break;
            case PUT_COLONY_STATE : {
                if (map.hasColony(cx, cy) || map.hasNearColony(who, cx, cy))
                {
                    gameScreen.informationContext.publish("Invalid colony. Choose another one!");
                    return;
                }

                trade.buyColony(who, cx, cy);
                
                outputX = cx;
                outputY = cy;
                outputH = h;    
                outputState = PUT_COLONY_STATE;
                setState(DEFAULT_STATE);
                
                gameScreen.actionContext.contextState = ActionContext.FOCUS_STATE;
                gameScreen.informationContext.publish("Successfull.");
                gameScreen.informationContext.publish("Choose an action.");
            } break;

            case PUT_CITY_STATE : {
                if (!gameScreen.engine.getTrade().canImproveColony(who, cx, cy))
                {
                    gameScreen.informationContext.publish("Invalid colony. Choose another one to improve!");
                    return;
                }

                trade.improveColony(who, cx, cy);
                
                outputX = cx;
                outputY = cy;
                outputH = h;    
                outputState = PUT_CITY_STATE;
                setState(DEFAULT_STATE);
                gameScreen.actionContext.contextState = ActionContext.FOCUS_STATE;
                gameScreen.informationContext.publish("Successfull.");
                gameScreen.informationContext.publish("Choose an action.");
            } break;

            case PUT_ROBBER_STATE : {
                map.moveRobber(mouseX, mouseY);

                setState(DEFAULT_STATE);
                gameScreen.actionContext.contextState = ActionContext.FOCUS_STATE;
                gameScreen.informationContext.publish("Successfull.");
                gameScreen.gameLoop.flowing = true;
            } break;

            case KNIGHT_STATE : {
                map.moveRobber(mouseX, mouseY);

                setState(DEFAULT_STATE);
                gameScreen.actionContext.contextState = ActionContext.FOCUS_STATE;
                state.getPlayer(state.getFocus()).useDeveloppement(Developpement.KNIGHT);
                gameScreen.informationContext.publish("Successfull.");
                gameScreen.informationContext.publish("Choose an action.");
            } break;
        }
    }

    public void calculateTmpMap() {
        tmpMapComponents.setData(voidMap.getRaster());
        Graphics g = tmpMapComponents.getGraphics();

        switch(contextState) {
            case DEFAULT_STATE : {

            } break;
            case PUT_FIRST_ROAD_STATE : case PUT_ROAD_STATE : case DEV_ROAD_STATE:
            {
                final double insetFactor = 1.0;
                final double remainder   = (1.0 - insetFactor)*0.5;
                
                final double ssx = (mouseH ? tile_sx:tile_sx) * insetFactor;
                final double ssy = (mouseH ? tile_sx:tile_sx) * insetFactor;
    
                final double msx = ssx*0.5;
                final double msy = ssy*0.5;

                final double tile_x1 = (tile_sx*(double)(mouseH ? mouseX+0.5 : mouseX)) - msx;
                final double tile_y1 = (tile_sy*(double)(mouseH ? mouseY : mouseY+0.5)) - msy;
                final double tile_x2 = tile_x1+ssx;
                final double tile_y2 = tile_y1+ssy;

                GameScreen.paintScaled(g, this, mouseH ? EntityAsset.road_h[focus] : EntityAsset.road_v[focus],
                    tile_x1, tile_y1, tile_x2, tile_y2, 
                    gameScreen.SCALE_MAP_DOWN_FACTOR, gameScreen.SCALE_MAP_DOWN_CENTER
                );
            } break;
            
            case PUT_FIRST_COLONY_STATE : case PUT_COLONY_STATE : {
                final double insetFactor = 0.6;
                final double remainder   = (1.0 - insetFactor)*0.5;
                
                final double ssx = tile_sx*insetFactor;
                final double ssy = tile_sy*insetFactor;
    
                final double msx = ssx*0.5;
                final double msy = ssy*0.5;

                final double tile_x1 = (tile_sx*(double)(mouseX)) - msx;
                final double tile_y1 = (tile_sy*(double)(mouseY)) - msy;
                final double tile_x2 = tile_x1+ssx;
                final double tile_y2 = tile_y1+ssy;

                GameScreen.paintScaled(g, this, EntityAsset.colony[focus],
                    tile_x1, tile_y1, tile_x2, tile_y2, 
                    gameScreen.SCALE_MAP_DOWN_FACTOR, gameScreen.SCALE_MAP_DOWN_CENTER
                );

            } break;
            
            case PUT_CITY_STATE : {
                final double insetFactor = 0.6;
                final double remainder   = (1.0 - insetFactor)*0.5;
                
                final double ssx = tile_sx*insetFactor;
                final double ssy = tile_sy*insetFactor;
    
                final double msx = ssx*0.5;
                final double msy = ssy*0.5;

                final double tile_x1 = (tile_sx*(double)(mouseX)) - msx;
                final double tile_y1 = (tile_sy*(double)(mouseY)) - msy;
                final double tile_x2 = tile_x1+ssx;
                final double tile_y2 = tile_y1+ssy;

                GameScreen.paintScaled(g, this, EntityAsset.city[focus],
                    tile_x1, tile_y1, tile_x2, tile_y2, 
                    gameScreen.SCALE_MAP_DOWN_FACTOR, gameScreen.SCALE_MAP_DOWN_CENTER
                );
            } break;

            case PUT_ROBBER_STATE : case KNIGHT_STATE : 
            /*if (map.getRobberIndex() != -1)*/ {
                final double insetFactor = 0.6;
                final double remainder   = (1.0 - insetFactor)*0.5;
                
                final double ssx = tile_sx*insetFactor;
                final double ssy = tile_sy*insetFactor;
    
                final double msx = ssx*0.5;
                final double msy = ssy*0.5;

                final double tile_x1 = (tile_sx*((double)mouseX + 0.5)) - msx;
                final double tile_y1 = (tile_sy*((double)mouseY + 0.5)) - msy;
                final double tile_x2 = tile_x1+ssx;
                final double tile_y2 = tile_y1+ssy;

                GameScreen.paintScaled(g, this, Assets.Game.robber,
                    tile_x1, tile_y1, tile_x2, tile_y2, 
                    gameScreen.SCALE_MAP_DOWN_FACTOR, gameScreen.SCALE_MAP_DOWN_CENTER
                );
            } break;
        }

        repaint();
    }

    public void updateMap() {
        storedMapComponents.setData(voidMap.getRaster());
        tmpMapComponents.setData(voidMap.getRaster());

        Graphics g = storedMapComponents.getGraphics();

        for (Player p: state.getPlayers())
        {
            int who = p.getIndex();

            int size = map.getSize();
            int sizePP = map.getSizePP();

            ArrayList<Integer> roadsH   = p.getRoadH();
            ArrayList<Integer> roadsV   = p.getRoadV();
            ArrayList<Integer> colonies = p.getColonies();
            ArrayList<Integer> cities   = p.getCities();

            // draw roadH
            {
                final double insetFactor = 1.0;
                final double remainder   = (1.0 - insetFactor)*0.5;
                
                final double ssx = tile_sx * insetFactor;
                final double ssy = tile_sy * insetFactor;

                final double msx = ssx*0.5;
                final double msy = ssy*0.5;

                for (int road: roadsH) {
                    final int x = Fnc.conv1dto2d_x(road, size);
                    final int y = Fnc.conv1dto2d_y(road, size);

                    final double tile_x1 = tile_sx*(double)(x+0.5) - msx;
                    final double tile_y1 = tile_sy*(double)(y)     - msy;
                    final double tile_x2 = tile_x1+ssx;
                    final double tile_y2 = tile_y1+ssy;

                    GameScreen.paintScaled(g, this, EntityAsset.road_h[who],
                        tile_x1, tile_y1, tile_x2, tile_y2, 
                        gameScreen.SCALE_MAP_DOWN_FACTOR, gameScreen.SCALE_MAP_DOWN_CENTER
                    );
                }
            }

            // draw roadV
            {
                final double insetFactor = 1.0;
                final double remainder   = (1.0 - insetFactor)*0.5;
                
                final double ssx = tile_sx * insetFactor;
                final double ssy = tile_sy * insetFactor;

                final double msx = ssx*0.5;
                final double msy = ssy*0.5;

                for (int road: roadsV) {
                    final int x = Fnc.conv1dto2d_x(road, sizePP);
                    final int y = Fnc.conv1dto2d_y(road, sizePP);

                    final double tile_x1 = tile_sx*((double)x)      - msx;
                    final double tile_y1 = tile_sy*((double)y+0.5)  - msy;
                    final double tile_x2 = tile_x1+ssx;
                    final double tile_y2 = tile_y1+ssy;

                    GameScreen.paintScaled(g, this, EntityAsset.road_v[who],
                        tile_x1, tile_y1, tile_x2, tile_y2, 
                        gameScreen.SCALE_MAP_DOWN_FACTOR, gameScreen.SCALE_MAP_DOWN_CENTER
                    );
                }
            }

            // draw colonies
            {
                final double insetFactor = 0.6;
                final double remainder   = (1.0 - insetFactor)*0.5;
                
                final double ssx = tile_sx*insetFactor;
                final double ssy = tile_sy*insetFactor;
    
                final double msx = ssx*0.5;
                final double msy = ssy*0.5;

                for (int colony: colonies)
                {
                    final int x = Fnc.conv1dto2d_x(colony, sizePP);
                    final int y = Fnc.conv1dto2d_y(colony, sizePP);

                    final double tile_x1 = (tile_sx*(double)(x)) - msx;
                    final double tile_y1 = (tile_sy*(double)(y)) - msy;
                    final double tile_x2 = tile_x1+ssx;
                    final double tile_y2 = tile_y1+ssy;

                    GameScreen.paintScaled(g, this, EntityAsset.colony[who],
                        tile_x1, tile_y1, tile_x2, tile_y2, 
                        gameScreen.SCALE_MAP_DOWN_FACTOR, gameScreen.SCALE_MAP_DOWN_CENTER
                    );
                }
            }

            // draw cities
            {
                final double insetFactor = 0.6;
                final double remainder   = (1.0 - insetFactor)*0.5;
                
                final double ssx = tile_sx*insetFactor;
                final double ssy = tile_sy*insetFactor;
    
                final double msx = ssx*0.5;
                final double msy = ssy*0.5;

                for (int city : cities)
                {
                    final int x = Fnc.conv1dto2d_x(city, sizePP);
                    final int y = Fnc.conv1dto2d_y(city, sizePP);

                    final double tile_x1 = (tile_sx*(double)(x)) - msx;
                    final double tile_y1 = (tile_sy*(double)(y)) - msy;
                    final double tile_x2 = tile_x1+ssx;
                    final double tile_y2 = tile_y1+ssy;

                    GameScreen.paintScaled(g, this, EntityAsset.city[who],
                        tile_x1, tile_y1, tile_x2, tile_y2, 
                        gameScreen.SCALE_MAP_DOWN_FACTOR, gameScreen.SCALE_MAP_DOWN_CENTER
                    );
                }
            }

            // put robber
            if (map.getRobberIndex() != -1) {
                final double insetFactor = 0.6;
                final double remainder   = (1.0 - insetFactor)*0.5;
                
                final double ssx = tile_sx*insetFactor;
                final double ssy = tile_sy*insetFactor;
    
                final double msx = ssx*0.5;
                final double msy = ssy*0.5;

                final double robberX = Fnc.conv1dto2d_x(map.getRobberPosition(), size);
                final double robberY = Fnc.conv1dto2d_y(map.getRobberPosition(), size);

                final double tile_x1 = (tile_sx*((double)robberX + 0.5)) - msx;
                final double tile_y1 = (tile_sy*((double)robberY + 0.5)) - msy;
                final double tile_x2 = tile_x1+ssx;
                final double tile_y2 = tile_y1+ssy;

                GameScreen.paintScaled(g, this, Assets.Game.robber,
                    tile_x1, tile_y1, tile_x2, tile_y2, 
                    gameScreen.SCALE_MAP_DOWN_FACTOR, gameScreen.SCALE_MAP_DOWN_CENTER
                );
            }
        }

        gameScreen.repaint();
    }

    /** 
     * <p> /!!!\ WARNING RES OF THIS COMPONENT IS NOT EQUAL TO THE SIZE OF THE STORED IMAGE /!!!\ 
     * <p> TOUJOURS AVOIR CA EN TETE CA EVITE CERTAINS PROBLEMES
    */
    @Override
    public void paint(Graphics g) {
        /*
            g.setColor(
                new Color[] {Color.red, Color.green, Color.blue} [Fnc.rand(3)]);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        */
        // ----------------
        // BASE MAP

        g.drawImage(storedMapComponents, 0, 0, this.getWidth(), this.getHeight(), this);        
        
        // ----------------
        // TMP MAP

        g.drawImage(tmpMapComponents, 0, 0, this.getWidth(), this.getHeight(), this);        
        //g.drawImage(tmpMapComponents, 0, 0, (int)(0.1*this.getWidth()), (int)(0.1*this.getWidth()), this);

        // ----------------
        super.paint(g);
    }
}
