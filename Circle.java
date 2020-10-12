//Written by Jeremy Colegrove

import java.awt.*;

public class Circle {
    private Complex complex; //A complex number to store the x and y in form (x+yi)
    public double k; //Store the radius of curvature (1/radius)
    public Color color; //Store the color of the fractal

    //Default constructor
    public Circle(Complex complex, double k, Color color) {
        this.complex = complex;
        this.k = k;
        this.color = color;
    }

    public Complex complex() {
        return complex;
    }   //Returns the complex position (x+yi)
    public double real() {
        return complex.real();
    }   //Returns only the real component (x)
    public double imaginary() {
        return complex.imaginary();
    } //Returns only the imaginary component (y)


    //All of the required material
    public void setColor(Color color) {
        this.color = color;
    }   //Sets the color
    public Color getColor() {
        return color;
    }   //Returns the color
    public double getRadius() { return 1/k; }   //Returns the radius (1/radius of curvature)
    public double getXPos() { return this.complex.real(); } //Returns the x position
    public double getYPos() { return this.complex.imaginary(); }    //Returns the y position
    public double calculatePerimeter() { return (1/k)*Math.PI; }    //Calculates the perimeter
    public double calculateArea() { return Math.PI*Math.pow((1/k), 2); }    //Calculates the area
}
