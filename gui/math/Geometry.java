package gui.math;

public class Geometry {
    public static double scale(double x, double amount, double center) {
        return ((x-center)*amount) + center;
    }
}
