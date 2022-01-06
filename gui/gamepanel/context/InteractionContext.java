package gui.gamepanel.context;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import game.Engine;
import game.constants.Developpement;
import game.constants.Ressource;
import game.state.Map;
import game.state.Player;
import game.state.State;
import game.utils.Fnc;
import game.utils.Offer;
import game.utils.Trade;
import gui.constants.ASCII;
import gui.gamepanel.GameScreen;
import gui.menupanel.Counter;

public class InteractionContext extends JComponent{

    public int contextState;

    public static final int DEFAULT_STATE = 0;
    public static final int TRADE_WAIT_STATE = 1;
    public static final int PROPOSE_TRADE_STATE = 2;

    public static final int ROBBER_RESSOURCE_STATE = 3;

    public static final int MONOPOLY_STATE = 4;
    public static final int PLENTY_STATE = 5;

    // --------------------------------------

    JComponent btn_accept;
    JComponent btn_decline;

    String     str_fromYou = "What you propose:";
    JComponent text_fromYou;
    CounterWithButton myRessource_wood;
    CounterWithButton myRessource_sheep;
    CounterWithButton myRessource_wheat;
    CounterWithButton myRessource_brick;
    CounterWithButton myRessource_rock;

    JComponent dev_fromYou;
    CounterWithButton myDev_knight;
    CounterWithButton myDev_road;
    CounterWithButton myDev_plenty;
    CounterWithButton myDev_monopoly;
    CounterWithButton myDev_point;
    
    SelectEntity selectTo;
    JComponent text_toYou;
    CounterWithButton his_wood;
    CounterWithButton his_sheep;
    CounterWithButton his_wheat;
    CounterWithButton his_brick;
    CounterWithButton his_rock;

    // --------------------------------------

    InformationContext informationContext;
    Engine engine;
    State state;
    Map map;
    Trade trade;

    GameScreen gameScreen;
    public InteractionContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        setVisible(false);
    }

    void publish(String text) {
        gameScreen.informationContext.publish(text);
    }
    
    public void init() {
        informationContext = gameScreen.informationContext;
        engine = gameScreen.engine;
        state = engine.getState();
        map = engine.getMap();
        trade = engine.getTrade();

        this.setLayout(null);
        // ---------------
        // make the contexts
        hasTripleLayout = new ArrayList<JComponent>();
        {
            (btn_accept = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, "[Confirm]", this.getWidth(), this.getHeight());
                    super.paint(g);
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    response(true);
                }
            });

            (btn_decline = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, "[Decline]", this.getWidth(), this.getHeight());
                    super.paint(g);
                }
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    response(false);
                }
            });

            // --------------

            text_fromYou = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, str_fromYou, this.getWidth(), this.getHeight());
                }
            };

            myRessource_wood  = new CounterWithButton ("Wood  ", 0, 0, 0);
            myRessource_sheep = new CounterWithButton("Sheep ", 0, 0, 0);
            myRessource_wheat = new CounterWithButton("Wheat ", 0, 0, 0);
            myRessource_brick = new CounterWithButton("Brick ", 0, 0, 0);
            myRessource_rock  = new CounterWithButton("Rock  ", 0, 0, 0);
        
            // --------------

            dev_fromYou = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, "You developpements.", this.getWidth(), this.getHeight());
                }
            };


            myDev_knight = new CounterWithButton    ("Knight   ", 0, 0, 0);
            myDev_road = new CounterWithButton      ("Road     ", 0, 0, 0);
            myDev_plenty = new CounterWithButton    ("Plenty   ", 0, 0, 0);
            myDev_monopoly = new CounterWithButton  ("Monopoly ", 0, 0, 0);
            myDev_point = new CounterWithButton     ("Point    ", 0, 0, 0);
            
            // --------------

            text_toYou = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, "What you want:", this.getWidth(), this.getHeight());
                }
            };

            his_wood = new CounterWithButton  ("Wood  ", 0, 0, 0);
            his_sheep = new CounterWithButton ("Sheep ", 0, 0, 0);
            his_wheat = new CounterWithButton ("Wheat ", 0, 0, 0);
            his_brick = new CounterWithButton ("Brick ", 0, 0, 0);
            his_rock = new CounterWithButton  ("Rock  ", 0, 0, 0);

            selectTo = new SelectEntity();
        }

        // ---------------
        /* geré par setVisible

            for (JComponent jc : new JComponent [] {
                null
            }) this.add(jc);
        */
        // ---------------
        setVisible(true);
    }

    // --------------------------------------
    
    /**
     * @param validate show button to valid
     * @param refuse   show button to decline
     * @param give     show give ressources panel
     * @param develop  show give developpement panel
     * @param receive  show receive panel
     * ---
     */

    public void setVisible(
        boolean accept, boolean decline, boolean give, boolean develop, boolean receive
    ) {
        this.removeAll();

        btn_accept.setVisible(accept);
        btn_decline.setVisible(decline);

        if (accept) this.add(btn_accept);
        if (decline) this.add(btn_decline);

        for (JComponent jc : new JComponent [] {
            text_fromYou, 
            myRessource_wood, myRessource_sheep, myRessource_wheat,
            myRessource_brick, myRessource_rock
        }) {
            jc.setVisible(give);
            if (give)
                this.add(jc);
        }

        for (JComponent jc : new JComponent [] {
            dev_fromYou,
            myDev_knight, myDev_road, myDev_plenty,
            myDev_monopoly, myDev_point
        }) {
            jc.setVisible(develop);
            if (develop)
                this.add(jc);
        }

        for (JComponent jc : new JComponent [] {
            text_toYou,
            his_wood, his_sheep, his_wheat,
            his_brick, his_rock
        }) {
            jc.setVisible(receive);
            if (receive)
                this.add(jc);
        }

        selectTo.setVisible(receive);
        if (receive) this.add(selectTo);
    }

    // -----------------------

    public void response(boolean response) {
        switch (contextState) {
            case DEFAULT_STATE: {

            } break;

            case TRADE_WAIT_STATE: {
                if (response)
                {
                    publish("Trade was accepted");
                    trade.trade(in_offer);
                }
                else
                {
                    publish("Trade was refuse");
                }

                gameScreen.actionContext.contextState = ActionContext.FOCUS_STATE;
                setState(DEFAULT_STATE);
            } break;

            case PROPOSE_TRADE_STATE: {
                in_offer = new Offer(
                    state.getFocus(), in_playerProposed == null ? -1 : in_playerProposed.getIndex(), 
                    new int[] {
                        0,
                        myRessource_wood.getValue(),
                        myRessource_sheep.getValue(),
                        myRessource_wheat.getValue(),
                        myRessource_brick.getValue(),
                        myRessource_rock.getValue()
                    }, new int[] {
                        0,
                        his_wood.getValue(),
                        his_sheep.getValue(),
                        his_wheat.getValue(),
                        his_brick.getValue(),
                        his_rock.getValue()
                    });

                if (in_playerProposed == null) {
                    if (!trade.canBuy(in_offer)) {
                        publish("You can buy one ressource in a purchase. "+
                        "And it needs to respect the price imposed by the ports you have.");
                        return;
                    }

                    trade.buy(in_offer);
                    publish("You bougth a ressource.");

                    gameScreen.actionContext.contextState = ActionContext.FOCUS_STATE;
                    setState(DEFAULT_STATE);
                    return;
                } else {
                    if (!trade.canTrade(in_offer)) {
                        publish("Invalid trade.");
                        return;
                    }

                    in_offerStr = in_offer.toString();
                    in_playerProposing = state.getPlayer(state.getFocus());
                    setState(TRADE_WAIT_STATE);
                }
            } break;

            case ROBBER_RESSOURCE_STATE: {
                int[] rsc  = new int[Ressource.nRessources];
                int[] devs = new int[Developpement.nDeveloppements];

                rsc[Ressource.BRICK]    = myRessource_brick.getValue();
                rsc[Ressource.ROCK]     = myRessource_rock.getValue();
                rsc[Ressource.SHEEP]    = myRessource_sheep.getValue();
                rsc[Ressource.WHEAT]    = myRessource_wheat.getValue();
                rsc[Ressource.WOOD]     = myRessource_wood.getValue();

                devs[Developpement.KNIGHT]  = myDev_knight.getValue();
                devs[Developpement.MONOPOLY]= myDev_monopoly.getValue();
                devs[Developpement.PLENTY]  = myDev_plenty.getValue();
                devs[Developpement.POINT]   = myDev_point.getValue();
                devs[Developpement.ROAD]    = myDev_road.getValue();

                if (Fnc.arrSum(rsc)+Fnc.arrSum(devs) != in_nCards) {
                    publish("Error, you have to select : " + in_nCards + " cards.");
                    return;
                }

                Player p = state.getPlayer(state.getFocus());
                for (int i = 0; i < rsc.length; i++)
                    p.getRessources()[i] -= rsc[i];
                for (int i = 0; i < devs.length; i++)
                    p.getDeveloppements()[i] -= devs[i];

                setState(DEFAULT_STATE);
                gameScreen.gameLoop.flowing = true;
            } break;

            case MONOPOLY_STATE: {
                int[] rsc = new int[] {
                    0,
                    his_wood.getValue(),
                    his_sheep.getValue(),
                    his_wheat.getValue(),
                    his_brick.getValue(),
                    his_rock.getValue()
                };

                if (Fnc.arrSum(rsc) != 1) {
                    publish("You have to choose exactly one ressource.");
                    return;
                }

                int[] stolen = new int [Ressource.nRessources];
                Player p = state.getPlayer(state.getFocus());
                for (Player victim: state.getPlayers()) if (victim != p) {
                    for (int i = 0; i < stolen.length; i++)
                        if (rsc[i]==1)
                        {
                            stolen[i] += victim.getRessource(i);
                            victim.getRessources()[i] = 0;
                        }
                }

                publish("You stole the ressource to each player.");
                p.useDeveloppement(Developpement.MONOPOLY);
                setState(DEFAULT_STATE);
                gameScreen.actionContext.contextState = ActionContext.FOCUS_STATE;
            } break;

            case PLENTY_STATE: {
                int[] rsc = new int[] {
                    0,
                    his_wood.getValue(),
                    his_sheep.getValue(),
                    his_wheat.getValue(),
                    his_brick.getValue(),
                    his_rock.getValue()
                };

                if (Fnc.arrSum(rsc) != 2) {
                    publish("You have to choose exactly two ressources.");
                    return;
                }

                Player p = state.getPlayer(state.getFocus());
                p.addRessources(rsc);

                publish("Card successfuly used.");
                p.useDeveloppement(Developpement.PLENTY);
                setState(DEFAULT_STATE);
                gameScreen.actionContext.contextState = ActionContext.FOCUS_STATE;
            } break;
        }
    }

    // ------
    public String   in_offerStr = "";
    public Offer    in_offer = null;
    public Player   in_playerProposed = null;
    public Player   in_playerProposing = null;
    public int      in_nCards = 0;

    public void setState(int state) {
        contextState = state;
        switch (state) {
            case DEFAULT_STATE : {
                setVisible(false, false, false, false, false);
                gameScreen.repaint();
            } break;

            case TRADE_WAIT_STATE : {
                if (in_playerProposed.isBot()) {
                    if (engine.getAI().consent(in_playerProposed, in_offer)) {
                        publish("Trade was accepted.");
                        trade.trade(in_offer);
                    } else {
                        publish("Trade was refused.");
                    }

                    gameScreen.actionContext.contextState = ActionContext.FOCUS_STATE;
                    setState(DEFAULT_STATE);
                    return;
                }

                publish("Doe " + in_playerProposed + " accept the offer : " + in_offerStr);
                setVisible(true, true, false, false, false);
            } break;

            case PROPOSE_TRADE_STATE : {
                selectTo.tradeMode();
                publish("Propose a trade|purchase.");
                setVisible(true, false, true, false, true);
            } break;

            case ROBBER_RESSOURCE_STATE : {
                setVisible(true, false, true, true, false);
            } break;

            case MONOPOLY_STATE : {
                selectTo.monopolyMode();
                publish("Choose 1 ressource to steal to every player.");
                setVisible(true, false, false, false, true);
            } break;

            case PLENTY_STATE : {
                selectTo.plentyMode();
                publish("Choose 2 ressources from the bank.");
                setVisible(true, false, false, false, true);
            } break;
        }
        
        refreshRessourceDependencies();
    }

    void refreshRessourceDependencies() {
        Player who = state.getPlayer(state.getFocus());
        
        // ressources
        myRessource_wood    .reinit(0, 0, who.getRessource(Ressource.WOOD));
        myRessource_sheep   .reinit(0, 0, who.getRessource(Ressource.SHEEP));
        myRessource_wheat   .reinit(0, 0, who.getRessource(Ressource.WHEAT));
        myRessource_brick   .reinit(0, 0, who.getRessource(Ressource.BRICK));
        myRessource_rock    .reinit(0, 0, who.getRessource(Ressource.ROCK));
        
        // developpements
        myDev_knight    .reinit(0, 0, who.getDeveloppements()[Developpement.KNIGHT]);
        myDev_road      .reinit(0, 0, who.getDeveloppements()[Developpement.ROAD]);
        myDev_plenty    .reinit(0, 0, who.getDeveloppements()[Developpement.PLENTY]);
        myDev_monopoly  .reinit(0, 0, who.getDeveloppements()[Developpement.MONOPOLY]);
        myDev_point     .reinit(0, 0, who.getDeveloppements()[Developpement.POINT]);

        // against
        if (in_playerProposed == null) {
            his_wood    .reinit(0, 0, 1);
            his_sheep   .reinit(0, 0, 1);
            his_wheat   .reinit(0, 0, 1);
            his_brick   .reinit(0, 0, 1);
            his_rock    .reinit(0, 0, 1);
        } else {
            his_wood    .reinit(0, 0, in_playerProposed.getRessource(Ressource.WOOD));
            his_sheep   .reinit(0, 0, in_playerProposed.getRessource(Ressource.SHEEP));
            his_wheat   .reinit(0, 0, in_playerProposed.getRessource(Ressource.WHEAT));
            his_brick   .reinit(0, 0, in_playerProposed.getRessource(Ressource.BRICK));
            his_rock    .reinit(0, 0, in_playerProposed.getRessource(Ressource.ROCK));
        }
        repaint();
    }

    // -----------------------

    void updateLayout() {
        Component[] comps = this.getComponents();

        final int sx = getWidth();
        final double sy = (double)getHeight() / (double)comps.length;
        for (int i = 0; i < comps.length; i++) {
            comps[i].setBounds(
                0, (int)(sy*i), sx, (int)sy
            );
        }

        // --------------------
        for (JComponent jc : hasTripleLayout) {
            tripleLayout(jc);
        }
    }

    ArrayList<JComponent> hasTripleLayout;
    void tripleLayout(JComponent layer) {
        final double middleRatio = 0.80;
        final double sideRatio = (1.0 - middleRatio)*0.5;

        final double middleSize = middleRatio*(double)layer.getWidth();
        final double sideSize = sideRatio*(double)layer.getWidth();

        layer.getComponents()[0].setBounds(
            (int)sideSize,0,(int)middleSize,(int)layer.getHeight()
        );


        layer.getComponents()[1].setBounds(
            0,0,(int)sideSize,(int)layer.getHeight()
        );
        layer.getComponents()[2].setBounds(
            (int)(sideSize+middleSize),0,(int)sideSize,(int)layer.getHeight()
        );
    }

    // -----------------------

    @Override
    public void paint(Graphics g) {
        // ----------------
        updateLayout();
        // ----------------
        super.paint(g);
    }

    // -----------------------

    private class SelectEntity extends JComponent {
        public int value;
        public String entity = "Bank";
        
        gui.menupanel.BtnMinus minus;
        gui.menupanel.BtnPlus plus;
        
        public SelectEntity () {
            minus   = new gui.menupanel.BtnMinus(() -> {
                if (mode==TRADE_MODE)
                {
                    value--;
                    if (value < -1)
                        value = state.getnPlayers() -1;
                    
                    if (value==state.getFocus())
                    {
                        value--;
                        if (value < -1)
                            value = state.getnPlayers() -1;
                    
                    }

                    refreshName();
                }
            });
            plus    = new gui.menupanel.BtnPlus(() -> {
                if (mode==TRADE_MODE)
                {
                    value++;
                    if (value >= state.getnPlayers())
                        value = -1;

                    if (value==state.getFocus())
                    {
                        value++;
                        if (value >= state.getnPlayers())
                            value = -1;
                    }
                    
                    refreshName();
                    refreshRessourceDependencies();
                }
            });

            this.add(new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, entity, this.getWidth(), this.getHeight());
                }
            });
            this.add(minus);
            this.add(plus);
        }

        int mode = 0;

        final int TRADE_MODE = 0;
        final int MONOPOLY_MODE = 1;
        final int PLENTY_MODE = 2;

        public void plentyMode() {
            mode = PLENTY_MODE;
            value = 0;
            entity = "Bank";
            setVisible(false);
        }

        public void monopolyMode() {
            mode = MONOPOLY_MODE;
            entity = "2 ressources to steal:";
            setVisible(false);
        }

        public void tradeMode() {
            mode = PLENTY_MODE;
            value = 0;
            entity = "Bank";
            setVisible(true);
        }

        public void refreshName() {
            if (value == -1) {
                entity = "Bank";
                in_playerProposed = null;
            }
            else
            {
                in_playerProposed = state.getPlayer(value);
                entity = in_playerProposed.toString();
            }
        }

        public void tripleLayout() {
            final double middleRatio = 0.65;
            final double sideRatio = (1.0 - middleRatio)*0.5;

            final double middleSize = middleRatio*(double)(this.getWidth());
            final double sideSize   = sideRatio  *(double)(this.getWidth());

            this.getComponents()[0].setBounds(
                (int)sideSize,0,(int)middleSize,(int)this.getHeight()
            );

            this.getComponents()[1].setBounds(
                0,0,(int)sideSize,(int)this.getHeight()
            );
            this.getComponents()[2].setBounds(
                (int)(sideSize+middleSize),0,(int)sideSize,(int)(this.getHeight())
            );
        }

        @Override
        public void paint(Graphics g) {
            tripleLayout();
            super.paint(g);
        }
    }
}

class CounterWithButton extends JComponent {
    gui.menupanel.Counter counter;
    gui.menupanel.BtnMinus minus;
    gui.menupanel.BtnPlus plus;
    
    public CounterWithButton (String text, int defaultValue, int lowerLimit, int upperLimit) {
        counter = new gui.menupanel.Counter(text, defaultValue, lowerLimit, upperLimit);
        minus   = new gui.menupanel.BtnMinus(counter);
        plus    = new gui.menupanel.BtnPlus(counter);

        this.add(counter);
        this.add(minus);
        this.add(plus);
    }

    public void reinit() {
        counter.modify(0);
    }

    public void reinit(int defaultValue, int lowerLIMIT, int upperLIMIT) {
        counter.reinit(defaultValue, lowerLIMIT, upperLIMIT);
    }

    public int getValue() {
        return counter.getValue();
    }

    public void tripleLayout() {
        final double middleRatio = 0.65;
        final double sideRatio = (1.0 - middleRatio)*0.5;

        final double middleSize = middleRatio*(double)(this.getWidth());
        final double sideSize   = sideRatio  *(double)(this.getWidth());

        this.getComponents()[0].setBounds(
            (int)sideSize,0,(int)middleSize,(int)this.getHeight()
        );

        this.getComponents()[1].setBounds(
            0,0,(int)sideSize,(int)this.getHeight()
        );
        this.getComponents()[2].setBounds(
            (int)(sideSize+middleSize),0,(int)sideSize,(int)(this.getHeight())
        );
    }

    @Override
    public void paint(Graphics g) {
        tripleLayout();
        super.paint(g);
    }
}
