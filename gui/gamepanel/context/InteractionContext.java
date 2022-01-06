package gui.gamepanel.context;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import game.utils.Fnc;
import gui.constants.ASCII;
import gui.gamepanel.GameScreen;
import gui.menupanel.Counter;

public class InteractionContext extends JComponent{

    public int contextState;

    private static final int DEFAULT_STATE = 0;
    private static final int TRADE_WAIT_STATE = 1;
    private static final int PROPOSE_TRADE_STATE = 2;

    private static final int ROBBER_RESSOURCE_STATE = 3;

    private static final int MONOPOLY_STATE = 4;
    private static final int PLENTY_STATE = 5;

    // --------------------------------------

    JComponent btn_accept;
    JComponent btn_decline;

    String     str_fromYou;
    JComponent text_fromYou;
    CounterWithButton myRessource_wood;
    CounterWithButton myRessource_sheep;
    CounterWithButton myRessource_wheat;
    CounterWithButton myRessource_brick;
    JComponent myRessource_rock;

    JComponent dev_fromYou;
    CounterWithButton myDev_knight;
    CounterWithButton myDev_road;
    CounterWithButton myDev_plenty;
    CounterWithButton myDev_monopoly;
    CounterWithButton myDev_point;
    
    JComponent text_toYou;
    CounterWithButton his_wood;
    CounterWithButton his_sheep;
    CounterWithButton his_wheat;
    CounterWithButton his_brick;
    CounterWithButton his_rock;

    // --------------------------------------


    GameScreen gameScreen;
    public InteractionContext(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        setVisible(false);
    }
    
    public void init() {
        this.setLayout(null);
        // ---------------
        // make the contexts
        hasTripleLayout = new ArrayList<JComponent>();
        {
            (btn_accept = new JComponent() {
                
            }).addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    response(true);
                }
            });

            (btn_decline = new JComponent() {
                
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
                    ASCII.paintText(g, this, str_fromYou, getWidth(), getHeight());
                }
            };

            myRessource_wood = new CounterWithButton ("Wood  ", 0, 0, 0);
            myRessource_sheep = new CounterWithButton("Sheep ", 0, 0, 0);
            myRessource_wheat = new CounterWithButton("Wheat ", 0, 0, 0);
            myRessource_brick = new CounterWithButton("Brick ", 0, 0, 0);
            myRessource_rock  = new CounterWithButton("Rock  ", 0, 0, 0);
        
            // --------------

            JComponent dev_fromYou = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, "You developpements.", getWidth(), getHeight());
                }
            };


            myDev_knight = new CounterWithButton    ("Knight   ", 0, 0, 0);
            myDev_road = new CounterWithButton      ("Road     ", 0, 0, 0);
            myDev_plenty = new CounterWithButton    ("Plenty   ", 0, 0, 0);
            myDev_monopoly = new CounterWithButton  ("Monopoly ", 0, 0, 0);
            myDev_point = new CounterWithButton     ("Point    ", 0, 0, 0);
            
            // --------------

            JComponent text_toYou = new JComponent() {
                @Override
                public void paint(Graphics g) {
                    ASCII.paintText(g, this, "What you want:", getWidth(), getHeight());
                }
            };

            CounterWithButton his_wood = new CounterWithButton  ("Wood  ", 0, 0, 0);
            CounterWithButton his_sheep = new CounterWithButton ("Sheep ", 0, 0, 0);
            CounterWithButton his_wheat = new CounterWithButton ("Wheat ", 0, 0, 0);
            CounterWithButton his_brick = new CounterWithButton ("Brick ", 0, 0, 0);
            CounterWithButton his_rock = new CounterWithButton  ("Rock  ", 0, 0, 0);
        }

        // ---------------
        for (JComponent jc : new JComponent [] {
            null
        }) this.add(jc);
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
    }

    // -----------------------

    public void response(boolean response) {
        switch (contextState) {
            case DEFAULT_STATE: {

            } break;

            case TRADE_WAIT_STATE: {

            } break;

            case PROPOSE_TRADE_STATE: {

            } break;

            case ROBBER_RESSOURCE_STATE: {

            } break;

            case MONOPOLY_STATE: {

            } break;

            case PLENTY_STATE: {

            } break;
        }
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
}

class CounterWithButton extends JComponent {
    gui.menupanel.Counter counter;
    gui.menupanel.BtnMinus minus;
    gui.menupanel.BtnPlus plus;
    
    public CounterWithButton (String text, int defaultValue, int lowerLimit, int upperLimit) {
        counter = new gui.menupanel.Counter(text, defaultValue, lowerLimit, upperLimit);
        
        minus   = new gui.menupanel.BtnMinus(counter);
        plus    = new gui.menupanel.BtnPlus(counter);

        this.add(minus);
        this.add(counter);
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

    static void tripleLayout(JComponent layer) {
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

    @Override
    public void paint(Graphics g) {
        tripleLayout(this);

        super.paint(g);
    }
}
