package gui.gamepanel.context;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.event.MouseEvent;

import game.utils.Fnc;
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
    public static final int BUY_STATE = 6;

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
    JComponent btn_buy;

    JComponent btn_devKnight;
    JComponent btn_devRoad;
    JComponent btn_devPlenty;
    JComponent btn_devMonopoly;
    JComponent btn_devPoint;

    // ------------------------------------------

    GameScreen gameScreen;
    public ActionContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.setLayout(null);
        setVisible(false);
    }

    public void init() {

        (btn_end = new JComponent() {
            @Override
            public void paint(Graphics g) {
                ASCII.paintText(g, this, "X", getWidth(), getHeight());
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
                gameScreen.informationContext.publish("pick");
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
                if (contextState==FOCUS_STATE || contextState==PUT_ROAD_STATE) {
                    gameScreen.informationContext.publish("road");
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
                if (contextState==FOCUS_STATE || contextState == PUT_COLONY_STATE) {
                    gameScreen.informationContext.publish("colony");
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
                if (contextState==FOCUS_STATE || contextState==PUT_CITY_STATE) {
                    gameScreen.informationContext.publish("city");
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
                if (contextState==FOCUS_STATE || contextState==TRADE_STATE) {
                    gameScreen.informationContext.publish("trade");
                }
            }
        });
        // --------
        (btn_buy = new JComponent() {
            @Override
            public void paint(Graphics g) {
                ASCII.paintText(g, this, "|buy|", getWidth(), getHeight());
            }
        }).addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (contextState==FOCUS_STATE || contextState==BUY_STATE) {
                    gameScreen.informationContext.publish("buy");
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
                    gameScreen.informationContext.publish("knight");
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
                if (contextState==FOCUS_STATE || contextState==DEV_ROAD_STATE) {
                    gameScreen.informationContext.publish("road");
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
                    gameScreen.informationContext.publish("plenty");
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
                gameScreen.informationContext.publish("monopoly");
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
                gameScreen.informationContext.publish("point");
            }}
        });

        // ------------------------

        JComponent sep_1 = new JComponent() {
            @Override
            public void paint(Graphics g) {
                //ASCII.paintText(g, this, "|", getWidth(), getHeight());
                ASCII.paintText(g, this, EntityAsset.road_v[0], getWidth(), getHeight());
            }
        };

        JComponent sep_2 = new JComponent() {
            @Override
            public void paint(Graphics g) {
                //ASCII.paintText(g, this, "|", getWidth(), getHeight());
                ASCII.paintText(g, this, EntityAsset.road_v[0], getWidth(), getHeight());
            }
        };

        JComponent sep_3 = new JComponent() {
            @Override
            public void paint(Graphics g) {
                //ASCII.paintText(g, this, "|", getWidth(), getHeight());
                ASCII.paintText(g, this, EntityAsset.road_v[0], getWidth(), getHeight());
            }
        };

        // ------------------------

        for (JComponent jc : new JComponent[] {
            btn_devKnight, btn_devRoad, btn_devPlenty, btn_devMonopoly, btn_devPoint, sep_1,
            btn_trade, btn_buy, btn_pick, sep_2,
            btn_putRoad, btn_putColony, btn_putCity, sep_3,
            btn_end
        }) this.add(jc);

        setVisible(true);
    }

    // ------------------------------------------

    MouseInputAdapter mouseInputAdapter;
    private void makeMouseListener() {
        mouseInputAdapter = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                applyState();
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

    // ------------------------------------------
    public void applyState() {
        switch(contextState) {}
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
