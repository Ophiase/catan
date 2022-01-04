package gui.menupanel;

import gui.Assets;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

public class Digit {
    public static final BufferedImage[] imgs = new BufferedImage[] {
        Assets.Menu.digit_0,
        Assets.Menu.digit_1,
        Assets.Menu.digit_2,
        Assets.Menu.digit_3,
        Assets.Menu.digit_4,
        Assets.Menu.digit_5,
        Assets.Menu.digit_6,
        Assets.Menu.digit_7,
        Assets.Menu.digit_8,
        Assets.Menu.digit_9
    };

    /** decomposition en base 10 */
    public static BufferedImage[] toImage(int i) {
        if (i < 0 || i > 999) 
            throw new InvalidParameterException();

        int x0 = i;
        int x1 = x0/10;
        int x2 = x1/10;

        x0 -= (x1*10);
        x1 -= (x2*10);

        return new BufferedImage[] {
            imgs[x0],
            imgs[x1],
            imgs[x2]
        };
    }


}
