package gui.math;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Geometry {
    public static double scale(double x, double amount, double center) {
        return ((x-center)*amount) + center;
    }

    public static Rectangle fit(BufferedImage img, 
    double x1, double y1, double x2, double y2) {
    
        final double width  = x2-x1;
        final double height = y2-y1;

        final double cx = (x1+x2)*0.5;
        final double cy = (y1+y2)*0.5;

        final double fx = width/(double)img.getWidth();    // scale factor to fit x
        final double fy = height/(double)img.getHeight(); // scale factor to fit y

        final double scale = fx > fy ? fy : fx; // take the lowest scale factor to fit both
        
        final double sx = scale*img.getWidth();
        final double sy = scale*img.getHeight();

        final double x = cx-(sx*0.5);
        final double y = cy-(sy*0.5);

        return new Rectangle((int)x, (int)y, (int)sx, (int)sy);
    }
}
