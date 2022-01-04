package gui.menupanel;

import gui.Assets;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

public class ASCII {
    public static final int zero = 48;
    public static final BufferedImage[] ASCII = new BufferedImage[] {
        // 48 decimal
        Assets.Menu.digit_0,
        Assets.Menu.digit_1,
        Assets.Menu.digit_2,
        Assets.Menu.digit_3,
        Assets.Menu.digit_4,
        Assets.Menu.digit_5,
        Assets.Menu.digit_6,
        Assets.Menu.digit_7,
        Assets.Menu.digit_8,
        Assets.Menu.digit_9,
        
        null, null, null, 
        null, null, null, 
        null,
        
        // 65 decimal
        Assets.Menu.char_u_a,
        Assets.Menu.char_u_b,
        Assets.Menu.char_u_c,
        Assets.Menu.char_u_d,
        Assets.Menu.char_u_e,
        Assets.Menu.char_u_f,
        Assets.Menu.char_u_g,
        Assets.Menu.char_u_h,
        Assets.Menu.char_u_i,
        Assets.Menu.char_u_j,
        Assets.Menu.char_u_k,
        Assets.Menu.char_u_l,
        Assets.Menu.char_u_m,
        Assets.Menu.char_u_n,
        Assets.Menu.char_u_o,
        Assets.Menu.char_u_p,
        Assets.Menu.char_u_q,
        Assets.Menu.char_u_r,
        Assets.Menu.char_u_s,
        Assets.Menu.char_u_t,
        Assets.Menu.char_u_u,
        Assets.Menu.char_u_v,
        Assets.Menu.char_u_w,
        Assets.Menu.char_u_x,
        Assets.Menu.char_u_y,
        Assets.Menu.char_u_z,

        null, null, null, 
        null, null, null,

        // 97 decimal
        Assets.Menu.char_l_a,
        Assets.Menu.char_l_b,
        Assets.Menu.char_l_c,
        Assets.Menu.char_l_d,
        Assets.Menu.char_l_e,
        Assets.Menu.char_l_f,
        Assets.Menu.char_l_g,
        Assets.Menu.char_l_h,
        Assets.Menu.char_l_i,
        Assets.Menu.char_l_j,
        Assets.Menu.char_l_k,
        Assets.Menu.char_l_l,
        Assets.Menu.char_l_m,
        Assets.Menu.char_l_n,
        Assets.Menu.char_l_o,
        Assets.Menu.char_l_p,
        Assets.Menu.char_l_q,
        Assets.Menu.char_l_r,
        Assets.Menu.char_l_s,
        Assets.Menu.char_l_t,
        Assets.Menu.char_l_u,
        Assets.Menu.char_l_v,
        Assets.Menu.char_l_w,
        Assets.Menu.char_l_x,
        Assets.Menu.char_l_y,
        Assets.Menu.char_l_z,

    };

    // -----------------------------------------

    public static String padLeft(int i) { return padLeft(i, 3); }
    public static String padLeft(int i, int length) { return padLeft(i+"", (i+"").length() + length, '0'); }
    public static String padLeft(String s, int length, char c) {
        StringBuilder o = new StringBuilder();
        for (int i = s.length(); i < length; i++)
            o.append(c);
        return new String(o.append(s));
    }


    // TODO: trouver un caractère un inconnu en cas de null
    public static BufferedImage[] toImage(String s) { return toImage(s.toCharArray()); }
    public static BufferedImage[] toImage(char[] str) {
        BufferedImage[] r = new BufferedImage[str.length];

        for (int i = 0; i < str.length; i++)
            r[i] = ASCII[str[i] + zero];

        return r;
    }


    /** obsolète : decomposition en base 10 */
    public static BufferedImage[] toImage(int i) {
        if (i < 0 || i > 999) 
            throw new InvalidParameterException();

        int x0 = i;
        int x1 = x0/10;
        int x2 = x1/10;

        x0 -= (x1*10);
        x1 -= (x2*10);

        return new BufferedImage[] {
            ASCII[x0],
            ASCII[x1],
            ASCII[x2]
        };
    }



}
