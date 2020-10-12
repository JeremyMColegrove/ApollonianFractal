//Written by Jeremy Colegrove
//This is the complex number class to hold complex numbers and perform add, multiply, subtract, divide, and square root.
public class Complex {
    //This holds the real value and the imaginary value
    private double[] number = new double[] {0, 0};

    //This is the default constructor, taking in the real value and the imaginary value
    public Complex(double real, double imaginary) {
        this.number[0] = real;
        this.number[1] = imaginary;
    }

    //This returns the real value
    public double real() {
        return number[0];
    }

    //This returns the imaginary value
    public double imaginary() {
        return number[1];
    }

    //This multiplies the complex number with another complex number and returns a new complex number
    public Complex mult(Complex b) {
        double r, i;
        r = this.real()*b.real()-this.imaginary()*b.imaginary();
        i = this.real()*b.imaginary()+this.imaginary()*b.real();
        return new Complex(r,i);
    }

    //This multiplies the complex number with a double and returns a new complex number
    public Complex mult(double a) {
        return new Complex(this.real()*a,this.imaginary()*a);
    }

    //This adds the complex number with another complex number and returns a new complex number
    public Complex add(Complex b) {
        return new Complex(this.real()+b.real(), this.imaginary()+b.imaginary());
    }

    //This subtracts the complex number with another complex number and returns a new complex number
    public Complex sub(Complex b) {
        return new Complex(this.real() - b.real(), this.imaginary() - b.imaginary());
    }

    //This divides the complex number with a double and returns a new complex number
    public Complex div(double b) {
        return new Complex(this.real()/b, this.imaginary()/b);
    }

    //This calculates the square root of the complex number and returns a new complex number
    public Complex sqrt() {
        double real = Math.sqrt(Math.sqrt(this.real()*this.real()+this.imaginary()*this.imaginary())) * Math.cos(Math.atan2(this.imaginary(), this.real())/2);
        double imaginary = Math.sqrt(Math.sqrt(this.real()*this.real()+this.imaginary()*this.imaginary())) * Math.sin(Math.atan2(this.imaginary(), this.real())/2);
        return new Complex(real, imaginary);
    }

    //This is to nicely print out the complex number for debugging (too much debugging if you ask me)
    @Override
    public String toString() {
        return number[0] + " + " + number[1] + "i";
    }
}
