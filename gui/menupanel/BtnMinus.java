package gui.menupanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import gui.Assets;

public class BtnMinus extends JComponent {
    
    public boolean hover = false;

    @Override
    public void paint(Graphics g) {
        final int sx = this.getSize().width;
        final int sy = this.getSize().height;
        final double cx = sx/2.0;
        final double cy = sy/2.0;

        final int s = (int)(sx > sy ? sy : sx);
        final int x = (int)(cx-s);
        final int y = (int)(cy-s);

        g.drawImage(
            hover ? (Assets.Menu.btn_minus_click) : (Assets.Menu.btn_minus),
            x,y,s,s,
            this
        );
    }
}
