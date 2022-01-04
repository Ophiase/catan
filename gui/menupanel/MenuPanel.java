package gui.menupanel;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.function.Consumer;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.DimensionUIResource;
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
    Dimension layersBound;

    JComponent layerNPlayers;
    Counter nPlayersText;
    JComponent lessPlayerBtn;
    JComponent morePlayerBtn;


    JComponent layerNParticipants;
    Counter nParticipantsText;
    JComponent lessParticipantsBtn;
    JComponent moreParticipantsBtn;
    
    JComponent layerNdices;
    Counter nDicesText;
    JComponent moreNDicesBtn;
    JComponent lessNDicesBtn;
    
    JComponent layerSDices;
    Counter sDicesText;
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
                }

                // menu panel

                {
                    final BufferedImage menu = Assets.Menu.background_menu_2;
                    final int lsy = 300, lsx = (int)(lsy*1.28);

                    final double py = (sy*0.58) - lsy;

                    g.drawImage(menu, (int)(cx-lsx), (int)(py), (2*lsx), (2*lsy), this);
                    memy = py;

                }

                // logo

                {
                    final double tsx = (double)logo.getWidth(this) * 0.5;
                    final double tsy = (double)logo.getHeight(this) * 0.5;
                    final double tcx = (tsx)/2;
                    final double tcy = (tsy)/2;

                    final double x = (int)(cx-tcx);
                    final double y = 20 + (int)((memy*0.5)-tcy);

                    g.drawImage(logo, 
                    (int)x, (int)y,
                    (int)tsx, (int)tsy,
                    this);
                }

            }
        };

        layer = new JComponent() {};
        layer.setLayout(new GridLayout(5, 1));
        updateLayerBound();

        layerNPlayers   = new JComponent() {};
        layerNParticipants      = new JComponent() {};
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

                    public void mouseClicked(MouseEvent e) {
                        Utils.debug(conf.toString());
                        mainWindow.conf = conf;
                        mainWindow.focusOnGame();
                    };
                });
            }

            public boolean hover = false;

            @Override
            public void paint(Graphics g) {
                ASCII.paintText(g, this, 
                    hover ? Assets.Menu.btn_play_click : Assets.Menu.btn_play,
                    getWidth(), getHeight() 
                );
            }
        };
        
        LayoutManager glh = null;
        for (JComponent j: new JComponent[] {
            layerNPlayers, layerNParticipants, layerNdices, layerSDices, playBtn
        }) {
            j.setLayout(glh);
            j.setVisible(true);
            layer.add(j);
        }

        // -------------------

            String tnp = "Players      | ";
            String tnb = "Participants | ";
            String tnd = "Dices        | ";
            String tsd = "Dices size   | ";

            layerNPlayers.add(nPlayersText  = new Counter(
                tnp, conf.getnPlayers(), Config.MIN_PLAYER, Config.MAX_N_PARTICIPANTS,
                (x) -> { conf.setnPlayers(x); }
            ));
            layerNPlayers.add(lessPlayerBtn = new BtnMinus(nPlayersText));
            layerNPlayers.add(morePlayerBtn = new BtnPlus(nPlayersText));

            tripleLayout(layerNPlayers, layersBound);
        
            // -----------
            
            layerNParticipants.add(nParticipantsText = new Counter(
                tnb, conf.getnParticipants(), Config.MIN_N_PARTICIPANTS, Config.MAX_N_PARTICIPANTS,
                (x) -> { conf.setnParticipants(x); }
            ));
            layerNParticipants.add(lessParticipantsBtn = new BtnMinus(nParticipantsText));
            layerNParticipants.add(moreParticipantsBtn = new BtnPlus(nParticipantsText));
            
            tripleLayout(layerNParticipants, layersBound);
        
            // -----------

            layerNdices.add(nDicesText  = new Counter(
                tnd, conf.getnDices(), Config.MIN_N_DICES, 6,
                (x) -> { conf.setnDices(x); }
            ));
            
            layerNdices.add(lessNDicesBtn = new BtnMinus(nDicesText));
            layerNdices.add(moreNDicesBtn = new BtnPlus(nDicesText));

            tripleLayout(layerNdices, layersBound);
        
            // -----------

            layerSDices.add(sDicesText = new Counter(
                tsd, conf.getSizeOfDices(), Config.MIN_SIZE_OF_DICES, 20,
                (x) -> { conf.setSizeOfDices(x); }
            ));
            
            layerSDices.add(lessSDicesBtn = new BtnMinus(sDicesText));
            layerSDices.add(moreSDicesBtn = new BtnPlus(sDicesText));
            
            tripleLayout(layerSDices, layersBound);

        // -------------------
        //layer.setBackground(new Color(70, 60, 160));
        // -------------------
        this.add(layer);
        this.add(background);
        this.setVisible(true);
    }

    private void tripleLayout(JComponent layer, Dimension layerDimension) {
        final double middleRatio = 0.80;
        final double sideRatio = (1.0 - middleRatio)*0.5;

        final double middleSize = middleRatio*(double)layerDimension.getWidth();
        final double sideSize = sideRatio*(double)layerDimension.getWidth();

        layer.getComponents()[0].setBounds(
            (int)sideSize,0,(int)middleSize,(int)layerDimension.getHeight()
        );


        layer.getComponents()[1].setBounds(
            0,0,(int)sideSize,(int)layerDimension.getHeight()
        );
        layer.getComponents()[2].setBounds(
            (int)(sideSize+middleSize),0,(int)sideSize,(int)layerDimension.getHeight()
        );

    }

    public void reset() {
        conf = Config.DEFAULT();
        setVisible(true);
        repaint();
    }

    public void clear() {
        setVisible(false);
    }

    void updateLayerBound() {
        final int sx = this.getSize().width;
        final int sy = this.getSize().height;

        final int cx = sx/2;
        final int cy = sy/2;

        final int lsx = 320, lsy = 250;
        layer.setBounds(cx-lsx, (int)(sy*0.58)-lsy, 2*lsx, 2*lsy);
        layersBound = new DimensionUIResource(layer.getWidth(), layer.getHeight()/5);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        updateLayerBound();
        background.setBounds(0, 0, getWidth(), getHeight());

        // ----------------
        super.paint(g);
    }

    // --------------------------------------
}
