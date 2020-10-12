//Written by Jeremy Colegrove
//Solver class holds the equations used to calculate the center of curvature and centers of the circles tangent to three others.
import java.awt.*;

public class Solver {

    //This one is to fill 'easy' circles (k4 = fp() and c4 = fp())
    public static Circle fpp(Circle c1, Circle c2, Circle c3) {
        double k1 = c1.k, k2 = c2.k, k3 = c3.k;
        Complex cx1 = c1.complex(), cx2 = c2.complex(), cx3 = c3.complex();

        double k4 = fp(k1, k2, k3);
        Complex c4 = fp(cx1.mult(k1),cx2.mult(k2), cx3.mult(k3)).div(k4);
        Circle c = new Circle(c4, k4, Color.BLACK);
        return c;
    }

    //This one is to fill 'hard' circles (k4 = fp() and c4 = fm())
    public static Circle fpm(Circle c1, Circle c2, Circle c3) {
        double k1 = c1.k, k2 = c2.k, k3 = c3.k;
        Complex cx1 = c1.complex(), cx2 = c2.complex(), cx3 = c3.complex();

        double k4 = fp(k1, k2, k3);
        Complex c4 = fm(cx1.mult(k1),cx2.mult(k2), cx3.mult(k3)).div(k4);

        Circle c = new Circle(c4, k4, Color.BLACK);
        return c;
    }

    //This one is to compute the outer circle (k4 = fm() and c4 = fm())
    public static Circle fmm(Circle c1, Circle c2, Circle c3) {
        double k1 = c1.k, k2 = c2.k, k3 = c3.k;
        Complex cx1 = c1.complex(), cx2 = c2.complex(), cx3 = c3.complex();

        double k4 = fm(k1, k2, k3);
        Complex c4 = fm(cx1.mult(k1),cx2.mult(k2), cx3.mult(k3)).div(k4);

        Circle c = new Circle(c4, k4, Color.BLACK);
        return c;
    }


    //This takes in 3 curvature of radii, and gives out the fourth curvature of radii tangent to all three (positive solution)
    public static double fp(double a1, double a2, double a3) {
        return (a1+a2+a3)   +  (2.0*Math.sqrt(a1*a2+a2*a3+a3*a1));
    }

    //This takes in 3 curvature of radii, and gives out the fourth curvature of radii tangent to all three (negative solution)
    public static double fm(double a1, double a2, double a3) {
        return (a1+a2+a3)   -   (2.0*Math.sqrt(a1*a2+a2*a3+a3*a1));
    }

    //This is a overload where it takes in three complex numbers, and runs Discarte's theorem on the complex numbers. (Discarte's Complex Theorem) (positive solution)
    public static Complex fp(Complex a, Complex b, Complex c) {
        Complex result = a.add(
                            b.add(c))
                                .add(
                                        a.mult(b).add(b.mult(c).add(a.mult(c))).sqrt().mult(2)
                                );

        return result;
    }

    //This is a overload where it takes in three complex numbers, and runs Discarte's theorem on the complex numbers. (Discarte's Complex Theorem) (negative solution)
    public static Complex fm(Complex a, Complex b, Complex c) {
        Complex result = a.add(
                b.add(c))
                .sub(
                        a.mult(b).add(b.mult(c).add(a.mult(c))).sqrt().mult(2)
                );

        return result;
    }
}