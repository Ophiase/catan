package gui.gamepanel.context;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import cli.Utils;

import java.awt.event.MouseEvent;

import game.Engine;
import game.constants.Developpement;
import game.constants.Ressource;
import game.state.Map;
import game.state.Player;
import game.state.State;
import game.utils.Fnc;
import game.utils.Offer;
import game.utils.Trade;
import gui.Assets;
import gui.constants.ASCII;
import gui.constants.EntityAsset;
import gui.gamepanel.GameScreen;

public class ActionContext extends JComponent{

    public int contextState = 0;
    public static final int DEFAULT_STATE = 0;
    public static final int FOCUS_STATE = 1;
    
    public static final int PUT_ROAD_STATE = 2;
    public static final int PUT_COLONY_STATE = 3;
    public static final int PUT_CITY_STATE = 4;

    public static final int TRADE_STATE = 5;

    public static final int DEV_KNIGHT_STATE = 7;
    public static final int DEV_ROAD_STATE = 8;
    public static final int DEV_PLENTY_STATE = 9;
    public static final int DEV_MONOPOLY_STATE = 10;

    // ------------------------------------------

    JComponent btn_end;
    JComponent btn_pick;

    JComponent btn_putRoad;
    JComponent btn_putColony;
    JComponent btn_putCity;

    JComponent btn_trade;
    //JComponent btn_buy;

    JComponent btn_devKnight;
    JComponent btn_devRoad;
    JComponent btn_devPlenty;
    JComponent btn_devMonopoly;
    JComponent btn_devPoint;

    // ------------------------------------------

    GameScreen gameScreen;
    InformationContext informationContext;
    Engine engine;
    State state;
    Map map;
    Trade trade;

    public ActionContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.setLayout(null);
        setVisible(false);
    }

    public void publish(String s) {
        informationContext.publish(s);
    }

    public void init() {

        informationContext = gameScreen.informationContext;
        engine = gameScreen.engine;
        state = engine.getState();
        map = engine.getMap();
        trade = engine.getTrade();


        // --------------------------------------------
        // Components init
        {
            (btn_end = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, ">", getWidth(), getHeight());
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {if (contextState==FOCUS_STATE) {
                    gameScreen.informationContext.publish("end");
                    contextState = DEFAULT_STATE;
                    gameScreen.gameLoop.flowing = true;
                }}
            });

            // --------
            (btn_pick = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, "|pick|", getWidth(), getHeight());
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { if (contextState==FOCUS_STATE) {
                    if (!trade.canBuyDevelop(state.getFocus())) {
                                gameScreen.informationContext.publish(
                                    "You don't have enough ressources. (1 Wheat, 1 Sheep, 1 Rock)");
                                return;
                            }

                    int d = trade.buyDevelop(state.getFocus());
                    publish("You bought the card : " + Utils.makeFirstWord(Developpement.toString(d)));
                }}
            });

            // --------
            (btn_putRoad = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    //ASCII.paintText(g, this, "|road|", getWidth(), getHeight());
                    ASCII.paintText(g, this, EntityAsset.road_h[2], getWidth(), getHeight());
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (contextState==FOCUS_STATE)
                    {
                        if (!state.getPlayer(state.getFocus()).hasRessources(
                            Offer.makeRessources(
                                Ressource.BRICK, 1,
                                Ressource.WOOD, 1
                            ))) {
                                gameScreen.informationContext.publish(
                                    "You don't have enough ressources. (1 Brick and 1 Wood)");
                                return;
                            }

                            contextState = PUT_ROAD_STATE;
                            gameScreen.mapContext.setState(MapContext.PUT_ROAD_STATE);
                    } else if (contextState==PUT_ROAD_STATE) {
                        contextState = FOCUS_STATE;
                        gameScreen.mapContext.contextState = MapContext.DEFAULT_STATE;
                        gameScreen.informationContext.publish("Choose an action.");
                    }
                }
            });

            // --------
            (btn_putColony = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    //ASCII.paintText(g, this, "|colony|", getWidth(), getHeight());
                    ASCII.paintText(g, this, EntityAsset.colony[2], getWidth(), getHeight());
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (contextState==FOCUS_STATE)
                    {
                        if (!state.getPlayer(state.getFocus()).hasRessources(
                            Offer.makeRessources(
                                Ressource.BRICK, 1,
                                Ressource.WOOD, 1,
                                Ressource.SHEEP, 1,
                                Ressource.WHEAT, 1
                            ))) {
                                gameScreen.informationContext.publish(
                                    "You don't have enough ressources. (1 Brick, 1 Wood, 1 Sheep, 1 Wheat)");
                                return;
                            }
                            contextState = PUT_COLONY_STATE;
                            gameScreen.mapContext.setState(MapContext.PUT_COLONY_STATE);
                    } else if (contextState==PUT_COLONY_STATE) {
                        contextState = FOCUS_STATE;
                        gameScreen.mapContext.contextState = MapContext.DEFAULT_STATE;
                        gameScreen.informationContext.publish("Choose an action.");
                    }
                }
            });

            // --------
            (btn_putCity = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    //ASCII.paintText(g, this, "|city|", getWidth(), getHeight());
                    ASCII.paintText(g, this, EntityAsset.city[2], getWidth(), getHeight());
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (contextState==FOCUS_STATE)
                    {
                        if (!state.getPlayer(state.getFocus()).hasRessources(
                            Offer.makeRessources(
                                Ressource.ROCK, 4,
                                Ressource.WHEAT, 2
                            ))) {
                                gameScreen.informationContext.publish(
                                    "You don't have enough ressources. (4 Rocks and 2 Wheats)");
                                return;
                            }
                            contextState = PUT_CITY_STATE;
                            gameScreen.mapContext.setState(MapContext.PUT_CITY_STATE);
                    } else if (contextState==PUT_CITY_STATE) {
                        contextState = FOCUS_STATE;
                        gameScreen.mapContext.contextState = MapContext.DEFAULT_STATE;
                        gameScreen.informationContext.publish("Choose an action.");
                    }
                }
            });

            // --------
            (btn_trade = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, "|trade|", getWidth(), getHeight());
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (contextState==FOCUS_STATE) {
                        gameScreen.interactionContext.setState(InteractionContext.PROPOSE_TRADE_STATE);
                        contextState = TRADE_STATE;
                    } else if (contextState==TRADE_STATE) {
                        gameScreen.interactionContext.setState(InteractionContext.DEFAULT_STATE);
                        gameScreen.informationContext.publish("Choose an action.");
                        contextState = FOCUS_STATE;
                    }
                }
            });

            // --------
            (btn_devKnight = new JComponent() {
                @Override
                public void paint(Graphics g) {
                        ASCII.paintText(g, this, "|knight|", getWidth(), getHeight());
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (contextState==FOCUS_STATE) {
                        Player p = state.getPlayer(state.getFocus());
                        if (!p.hasDeveloppement(Developpement.KNIGHT)) {
                            publish("You don't have this card.");
                            return;
                        }

                        contextState = DEFAULT_STATE;
                        gameScreen.mapContext.setState(MapContext.KNIGHT_STATE);
                    }
                }
            });
            // --------
            (btn_devRoad = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, "|road|", getWidth(), getHeight());
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (contextState==FOCUS_STATE) {
                        Player p = state.getPlayer(state.getFocus());
                        if (!p.hasDeveloppement(Developpement.ROAD)) {
                            publish("You don't have this card.");
                            return;
                        }

                        gameScreen.mapContext.setState(MapContext.DEV_ROAD_STATE);
                        contextState = DEV_ROAD_STATE;
                    } else if (contextState==DEV_ROAD_STATE) {
                        gameScreen.mapContext.setState(MapContext.DEFAULT_STATE);
                        contextState = FOCUS_STATE;
                    }
                }
            });
            // --------
            (btn_devPlenty = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, "|plenty|", getWidth(), getHeight());
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (contextState==FOCUS_STATE) {
                        Player p = state.getPlayer(state.getFocus());
                        if (!p.hasDeveloppement(Developpement.PLENTY)) {
                            publish("You don't have this card.");
                            return;
                        }

                        contextState = DEV_PLENTY_STATE;
                        gameScreen.interactionContext.setState(InteractionContext.PLENTY_STATE);
                    }
                }
            });
            // --------
            (btn_devMonopoly = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, "|monopoly|", getWidth(), getHeight());
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { if (contextState==FOCUS_STATE) {
                    Player p = state.getPlayer(state.getFocus());
                    if (!p.hasDeveloppement(Developpement.MONOPOLY)) {
                        publish("You don't have this card.");
                        return;
                    }

                    contextState = DEV_MONOPOLY_STATE;
                    gameScreen.interactionContext.setState(InteractionContext.MONOPOLY_STATE);
                }}
            });

            // --------
            (btn_devPoint = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, "|point|", getWidth(), getHeight());
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { if (contextState==FOCUS_STATE) {
                    Player p = state.getPlayer(state.getFocus());
                    if (!p.hasDeveloppement(Developpement.POINT)) {
                        publish("You don't have this card.");
                        return;
                    }

                    p.useDeveloppement(Developpement.POINT);
                    publish("You gained one point. Now you have :"+(++p.getRessources()[Ressource.POINT])+"$");
                    gameScreen.repaint();
                }}
            });
        }
        // ------------------------

        JComponent sep_1 = new JComponent() {
            @Override
            public void paint(Graphics g) {
                //ASCII.paintText(g, this, "|", getWidth(), getHeight());
                ASCII.paintText(g, this, Assets.Menu.separator_v_0, getWidth(), getHeight());
            }
        };

        JComponent sep_2 = new JComponent() {
            @Override
            public void paint(Graphics g) {
                //ASCII.paintText(g, this, "|", getWidth(), getHeight());
                ASCII.paintText(g, this, Assets.Menu.separator_v_0, getWidth(), getHeight());
            }
        };

        JComponent sep_3 = new JComponent() {
            @Override
            public void paint(Graphics g) {
                ASCII.paintText(g, this, "|", getWidth(), getHeight());
                ASCII.paintText(g, this, Assets.Menu.separator_v_0, getWidth(), getHeight());
            }
        };

        // ------------------------

        for (JComponent jc : new JComponent[] {
            btn_devKnight, btn_devRoad, btn_devPlenty, btn_devMonopoly, btn_devPoint, sep_1,
            btn_trade, btn_pick, sep_2,
            btn_putRoad, btn_putColony, btn_putCity, sep_3,
            btn_end
        }) this.add(jc);

        setVisible(true);
    }

    // ------------------------------------------
    public void applyState() {
        switch(contextState) {

        }
    }
    // ------------------------------------------

    void updateLayout() {
        Component[] comps = this.getComponents();

        final double sx = (double)getWidth() / (double)comps.length;
        final int sy = getHeight();
        for (int i = 0; i < comps.length; i++) {
            comps[i].setBounds(
                (int)(sx*i), 0, (int)(sx), sy
            );
        }
    }

    @Override
    public void paint(Graphics g) {
        updateLayout();
        
        //----------------
        super.paint(g);

        /*
            // debug bounding boxes
            g.setColor(Color.red);
            g.fillRect(0, 0, getWidth(), getHeight());

            int d = 0;
            Color[] cols = new Color[] {
                Color.RED, Color.green, Color.blue, 
                Color.pink, Color.orange, Color.cyan,
                Color.DARK_GRAY, Color.MAGENTA, Color.YELLOW,
                Color.lightGray
            };
            cli.Utils.debug("----------");
            for (Component c : getComponents())
            {
                g.setColor(cols[(d++)%cols.length]);
                g.fillRect(c.getX(), c.getY(), c.getWidth(), c.getHeight());
                cli.Utils.debug(c.getBounds());
            }
        */
    }
    
}
