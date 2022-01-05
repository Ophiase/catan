package gui.constants;

import gui.Assets;
import gui.math.Geometry;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import java.util.Arrays;

import javax.swing.*;
import java.awt.image.ImageObserver;

public class ASCII {
    public static final int zero = 32;
    public static final BufferedImage[] ASCII = new BufferedImage[] {
        // 32 decimal
        Assets.Menu.char_s_032,
        Assets.Menu.char_s_033,
        null, null, null, null,
        null, null,
        
        Assets.Menu.char_s_124, // TEMPORAIRE
        Assets.Menu.char_s_124, // TEMPORAIRE
        
        null, null, null, null,   
        Assets.Menu.char_s_046,
        null, 

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
        // 58
        Assets.Menu.char_s_058,
        null, null, null, null, 
        Assets.Menu.char_s_063,
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
        
        // 91 decimal 
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

        // 123 decimal
        null,
        Assets.Menu.char_s_124,
        null,
        null,
        null // 127 = DEL
    };

    // -----------------------------------------

    public static String padLeft(int i) { return padLeft(i, 3); }
    public static String padLeft(int i, int length) { return padLeft(i+"", length, '0'); }
    public static String padLeft(String s, int length, char c) {
        if (s.length() >= length)
            return s;

        StringBuilder sb = new StringBuilder();
        final int deficite = length - s.length();
        for (int i = 0; i < deficite; i++)
            sb.append(c);
        sb.append(s);

        return sb.toString();
    }


    // TODO: trouver un caractère un inconnu en cas de null
    public static BufferedImage[] toImage(String s) { return toImage(s.toCharArray()); }
    public static BufferedImage[] toImage(char[] str) {
        BufferedImage[] r = new BufferedImage[str.length];

        for (int i = 0; i < str.length; i++) {
            r[i] = ASCII[str[i] - zero];

            if (r[i]==null)
                r[i] = ASCII[0];
        }

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

    // ----------------------------------------------

    private static final int overlap = 160; // px de l'image de base
    private static final int charSize = 256;
    private static final int overlapIncrement = charSize-overlap;

    public static BufferedImage makeText(String text) {
        if (text.length() == 0) return new BufferedImage(0, 0, 0);
        if (text.length() == 1) return toImage(text)[0];

        int sx = (text.length()*charSize) - ((text.length()-1)*overlap);
        int sy = charSize;

        BufferedImage image = new BufferedImage(sx, sy, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = image.createGraphics();

        BufferedImage[] imgs = toImage(text);        
        for (int i = 0, x = 0; i < imgs.length; i++, x += overlapIncrement)
            g.drawImage(imgs[i], x, 0, null);

   
        return image;
    }

    public static void paintText(Graphics g, ImageObserver o, String text, int width, int height) {
        if (text.length() == 0) return;
        paintText(g,o, makeText(text), width, height); 
    }
    public static void paintText(Graphics g, ImageObserver o, BufferedImage text, int width, int height) {
        final double cx = width*0.5;
        final double cy = height*0.5;

        final double fx = (double)width/(double)text.getWidth();    // scale factor to fit x
        final double fy = (double)height/(double)text.getHeight(); // scale factor to fit y

        final double scale = fx > fy ? fy : fx; // take the lowest scale factor to fit both
        
        final double sx = scale*text.getWidth();
        final double sy = scale*text.getHeight();

        final int x = (int)(cx-sx*0.5);
        final int y = (int)(cy-sy*0.5);
        
        g.drawImage(text,x,y,(int)sx,(int)sy,o);
    }
    public static void paintText(
        Graphics g, ImageObserver o, 
        String text, int x1, int y1, int x2, int y2
    ) {
        paintText(g, o, makeText(text), x1, y1, x2, y2);
    }
    public static void paintText(
        Graphics g, ImageObserver o, 
        BufferedImage text, int x1, int y1, int x2, int y2
    ) {
        paintText(g, o, text, x1, y1, x2, y2, false);
    }

    public static void paintText(
        Graphics g, ImageObserver o, 
        BufferedImage text, double x1, double y1, double x2, double y2, boolean debug
    ) {
        /*
        final double width  = x2-x1;
        final double height = y2-y1;

        final double cx = (x1+x2)*0.5;
        final double cy = (y1+y2)*0.5;

        final double fx = (double)width/(double)text.getWidth();    // scale factor to fit x
        final double fy = (double)height/(double)text.getHeight(); // scale factor to fit y

        final double scale = fx > fy ? fy : fx; // take the lowest scale factor to fit both
        
        final double sx = scale*text.getWidth();
        final double sy = scale*text.getHeight();

        final double x = cx-(sx*0.5);
        final double y = cy-(sy*0.5);

        if (debug)
        {
            g.setColor(Color.red);
            g.fillRect(x1, y1, (int)width, (int)height);
        }

        g.drawImage(text,(int)(x),(int)(y),(int)sx,(int)sy,o);
        */

        Rectangle rect = Geometry.fit(text,x1,y1,x2,y2);
        g.drawImage(text,rect.x, rect.y, rect.width, rect.height,o);
        
    }
}
