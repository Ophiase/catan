package gui.menupanel;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import gui.Assets;

public class BtnMinus extends JComponent {
    
    BtnMinus() {
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
        final int sx = this.getSize().width;
        final int sy = this.getSize().height;
        final double cx = sx/2.0;
        final double cy = sy/2.0;

        final int s = (int)(sx > sy ? sy : sx);
        final int x = (int)(cx-(s/2.0));
        final int y = (int)(cy-(s/2.0));

        g.drawImage(
            hover ? (Assets.Menu.btn_minus) : (Assets.Menu.btn_minus_click),
            x,y,s,s,
            this
        );
    }
}
