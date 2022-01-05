package gui.math;

public class Vect2 {
    public double x;
    public double y;

    Vect2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    void addIt(Vect2 v2) {
        x += v2.x;
        y += v2.y;
    }

    Vect2 add(Vect2 v2) {
        return new Vect2(x+v2.x, y+v2.y);
    }

    Vect2 add(double ax, double ay) {
        return new Vect2(x+ax, y+ay);
    }

    Vect2 scale(double cx, double cy, double factor) {
        return new Vect2(
            Geometry.scale(x, factor, cx),
            Geometry.scale(y, factor, cy)
        );
    }
    Vect2 scale(Vect2 center, double factor) {
        return new Vect2(
            Geometry.scale(x, factor, center.x),
            Geometry.scale(y, factor, center.y)
        );
    }
}
