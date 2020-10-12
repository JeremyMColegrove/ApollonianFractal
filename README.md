# ApollonianFractal
# Written by Jeremy Colegrove

# How to compile and run
    1) Navigate to the directory where you have the .java files in the cmd
    2) type 'javac FractalDrawer.java Canvas.java Circle.java Rectangle.java Triangle.java Solver.java Complex.java'
    3) to run type 'java FractalDrawer'

These are the prompts the user has to enter:
    2) Levels of the fractal    (0-8)
    3) Width/height of the fractal  (0 - infinity)
    4) If they want it colored or not (t, f)

Assumptions:
    I assume the window/canvas will be square. I only ask for the width and set the height the same.


# Additional features:

    1) Prompt for color or no color

    2) Made an Apollonian Gasket Fractal:

    It uses Discarte's Theorem and Discarte's Complex Theorem
    A little bit about the apollonian gasket and Discarte's theorem:

        Discarte's theorem states given three tangent circles, if you know the radius of curvature (1/radius), you can calculate the radius of curvature
        of the fourth circle tangent to all three of those circles.
            Discarte's Theorem:
                Where k1, k2 and k3 are the radius of curvatures of three circles, the fourth can be found by:
                k1+k2+k3±√(k1*k2+k2*k3+k3*k1)

        Discarte's Complex Theorem:
            Where k1, k2 and k3 are the radius of curvatures of three tangent circles, and c1, c2, c3 are the centers of the circles
            represented by a complex number of the form (x+yi), the center of the fourth circle can be found by
            (k1*c1+k2*c2+k3*c3±√(k1*k2*c1*c2+k2*k3*c2*c3+k1*k3*c1*c3))/(k1+k2+k3±√(k1*k2+k2*k3+k3*k1))

        Discarte's Complex Theorem looks a lot like Discarte's Theorem, which is why it is named the way it is.
        It basically is the same equation, except each radius of curvature is multiplied by its complex center and then passed into Discarte's Theorem.

        The two algorithms are how you can recursively find circles to fill in each gap, repeating as far down as you need to go.
        There are a lot of crazy images and fun things to do with the circles, and it really is a functional and unique theorem that is not talked about often!
        There is one hiccup though, due to the Graphics.fillOval() requiring integers for its x, y, and radius, the circles do not quite touch each other due to the decimal truncation.
        If you want to get a nice, perfect image you need to use SVG graphics which has smooth scaling and no truncation. (see wikibooks example, for instance)

        Implemented with the help and resources from the following websites:
        https://en.wikibooks.org/wiki/Fractals/Apollonian_fractals
        https://en.wikipedia.org/wiki/Apollonian_gasket
        http://paulbourke.net/fractals/apollony/

        Note: The code was written myself and implemented differently than the resources above.
        The resources were simply to understand the basic structure and mathematics behind what was happening.

Known bugs or defects: None
