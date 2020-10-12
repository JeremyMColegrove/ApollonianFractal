//Written by Jeremy Colegrove,
// FractalDrawer class draws a fractal of a shape indicated by user input

import java.awt.Color;
import java.util.Random;
import java.util.Scanner;


public class FractalDrawer {

    private double totalArea=0;  // member variable for tracking the total area
    private Canvas canvas;
    private static String type= "";
    private Random rand;
    private boolean isColored=false;
    private int maxLevel=7;

    //The starting place of the program
    public static void main(String[] args){
        //boolean isColored;
        int levels=-1, width=-1;
        FractalDrawer fractalDrawer = new FractalDrawer();

        Scanner scanner = new Scanner(System.in);

        while (levels > 8 || levels < 0) {
            System.out.println("Enter the number of recursions (1-8):");
            if (scanner.hasNextInt())
                levels = scanner.nextInt();
            scanner.nextLine();
        }


        while (width < 1) {
            System.out.println("Enter the size of the fractal (pixels):");
            if (scanner.hasNextInt())
                width = scanner.nextInt();
            scanner.nextLine();
        }

        char answer = 0;
        String nextLine;
        while (answer != 't' && answer != 'f') {
            System.out.println("Do you want it colored? (T F):");
            if (scanner.hasNextLine()) {

                nextLine = scanner.nextLine().toLowerCase();
                if (!nextLine.isEmpty())
                    answer = nextLine.charAt(0);
            }
        }

        fractalDrawer.isColored = (answer == 't');
        fractalDrawer.maxLevel = levels;
        double area = fractalDrawer.drawFractal( width/2, width/2, width, width, levels);
        System.out.println("Total area of fractal is " + area + " square pixels");
    }
    
    public FractalDrawer() {
    }  // constructor


    public double drawFractal( int x, int y, int width, int height, int levels) {
        canvas = new Canvas(height+38, width+15);
        if (isColored)
            rand = new Random();   //If the user wants a color fractal, create a new random object for the fractal to use when picking the color
        //The x and y is not used, it just centers itself perfectly in the canvas
        drawCircleFractal(width/2, 0, 0, Color.BLACK, canvas, levels);
        return totalArea;
    }


    public void drawTriangleFractal(double width, double height, double x, double y, Color c, Canvas can, int level){

        //If it is colored, assign a random color to c, otherwise it is black
        //If it is the first triangle, scale the height and width appropriately so that it fits inside of the window
        if (level == maxLevel) {
            double totalLength = 2.0-(1.0/Math.pow(2, level));
            double smallestTriangle = width/Math.pow(2, level)/4;
            width = (width/(totalLength))*2;
            height= (height/(totalLength))*2;
            x = x-width/2;
        }

        //Create the new triangle!
        Triangle t = new Triangle(x, y, width, height);
        t.setColor(getCorrectColor());
        can.drawShape(t);
        totalArea += t.calculateArea();

        //If level is larger than 0,
        if (level>0) {
            double offset = (width/4)*0; //Remove the times 0 for another cool fractal
            drawTriangleFractal(width/2, height/2, x-width/4+offset, y-height/2+offset, c, can, level-1);
            drawTriangleFractal(width/2, height/2, x+3*width/4-offset, y-height/2+offset, c, can, level-1);
            drawTriangleFractal(width/2, height/2, x+width/4, y+height/2-offset, c, can, level-1);
        }
    }

    // drawCircleFractal draws a circle fractal using recursive techniques
    public void drawCircleFractal(double radius, double x, double y, Color c, Canvas can, int levels) {
        //Since I have to keep the attributes the same for drawCircleFractal, I used this method for recursion instead.
        //This way it will still work with whatever code is being ran to grade this project
        drawCircleRecursive(radius, can, levels, 0, null, null, null, null, false);
    }

    public void drawCircleRecursive(double radius, Canvas can, int levels, int currentLevel,Circle c0, Circle c1, Circle c2, Circle c3, boolean isHard ) {
        Circle ia, ib, ic;
        //This is the threshold for what radius the circles have to be to be drawn. Because the radius is truncated,
        // the radius has to at least be 1 for it to even be drawn. Otherwise pointless computation is done.
        double threshold = 1;

        if (currentLevel == 0) {
            Circle i0, icenter, iaa, ibb, icc;
            double a, ri, height;

            //Here is our setup with the circles

            a = 2.15470053838; //1+sqrt(2) used in the initial setup of circle fractal
            ri = radius / a;  //This is the radius of the inner three circles that are all tangent to each other
            height = ri * Math.sqrt(3);    //This is the height of the top circles relative to the bottom circle
            //These three circles are all the same radius and tangent to each other
            ia = new Circle(new Complex(radius, 2 * radius - ri), 1 / ri, getCorrectColor());
            ib = new Circle(new Complex(radius - ri, 2 * radius - ri - height), 1 / ri, getCorrectColor());
            ic = new Circle(new Complex(radius + ri, 2 * radius - ri - height), 1 / ri, getCorrectColor());

        /*
        The reason the starting setup is so complicated is because otherwise each of the three
        circles (ia, ib, ic) will draw circles on each of its sides, causing everything to be calculated and drawn two times
        This is not efficient, and so we have to start off with a couple more circles hardcoded (icenter, iaa, ibb, icc)
            Note: If you set the recursions to 0, you can see the starting setup with the circles
            ia, ib, ic --> the three large circles tangent to each other in the center
            iaa, ibb, icc --> the three smaller circles, touching two larger circles and the invisible circle outside
            icenter --> the small circle in the very middle
            i0 --> an invisible 'larger' circle that ia, ib, and ic are inside of and tangent to.
                Note:
                You can change the background color by changing the color of i0 below


        I modeled the method names the same as the method names in the wikibooks, so that
        It would be easy to follow what is going on.
        https://en.wikibooks.org/wiki/Fractals/Apollonian_fractals

        I can paraphrase here:
        Discarte's Complex Theorem has two square roots, r1 and r2, therefore we get 4 different solutions. Only three will be used, however.
        fpp() --> fills in easy gaps  (positive solutions of r1 and r2)
        fpm() --> fills in hard gaps (r1 is positive and r2 is negative)
        fmm() --> fills in the outer circle that contains all circles  (r1 and r2 are both negative)

        See this image for the circles and where they are positioned (The red circles are the hard circles)
        https://en.wikibooks.org/wiki/Fractals/Apollonian_fractals#/media/File:Apollonian_rc_6.svg
         */
            //Find the circle outside using fmm
            i0 = Solver.fmm(ia, ib, ic);
            //Creates the other four easy circles
            icenter = Solver.fpp(ia, ib, ic);
            iaa = Solver.fpp(ia, ic, i0);
            icc = Solver.fpp(ib, ic, i0);
            //Create the one hard circle
            ibb = Solver.fpm(ia, ib, i0);


            //Sets the background color
            i0.setColor(Color.WHITE);
            //Set the colors for the other ones to either a random color, or black
            ia.setColor(getCorrectColor());
            ib.setColor(getCorrectColor());
            ic.setColor(getCorrectColor());
            iaa.setColor(getCorrectColor());
            ibb.setColor(getCorrectColor());
            icc.setColor(getCorrectColor());
            icenter.setColor(getCorrectColor());

            //This draws all of the circles now that the setup is complete
            can.drawShape(i0);
            can.drawShape(ia);
            can.drawShape(ib);
            can.drawShape(ic);
            can.drawShape(icenter);
            can.drawShape(iaa);
            can.drawShape(ibb);
            can.drawShape(icc);
            //Add the area of the setup to the total area
            totalArea += i0.calculateArea()+ia.calculateArea()+ib.calculateArea()+ic.calculateArea()+iaa.calculateArea()+ibb.calculateArea()+icc.calculateArea()+icenter.calculateArea();

            /*
            Start the recursion for each of the circles iaa, ibb, icc, icenter
             */
            drawCircleRecursive(iaa.getRadius(),  can, levels, currentLevel + 1, iaa, ia, ic, i0,  false);
            drawCircleRecursive(ibb.getRadius(),  can, levels, currentLevel + 1, ibb, ia, ib, i0,  true);
            drawCircleRecursive(icc.getRadius(),  can, levels, currentLevel + 1, icc, ib, ic, i0,  false);
            drawCircleRecursive(icenter.getRadius(), can, levels, currentLevel + 1, icenter, ia, ib, ic, false);

        } else if (currentLevel < levels && c0.getRadius() >= threshold) {    //If it is not the first level, skip the setup phase and begin recursion
            boolean isHardB = false, isHardC = false;

            //Create three new circles filling in the gaps
            ia = Solver.fpp(c1, c2, c0);
            ib = Solver.fpp(c1, c0, c3);
            ic = Solver.fpp(c0, c2, c3);

            //Check to see if we need to calculate the hard solution
            if (isHard) {
                if ((currentLevel) % 3 == 0) {
                    ib = Solver.fpm(c1, c0, c3);
                    isHardB = true;
                } else {
                    ic = Solver.fpm(c0, c2, c3);
                    isHardC = true;
                }
            }

            //Set the three circles colors to the appropriate color, random or black
            ia.setColor(getCorrectColor());
            ib.setColor(getCorrectColor());
            ic.setColor(getCorrectColor());

            //Draw the three circles to the canvas
            canvas.drawShape(ia);
            canvas.drawShape(ib);
            canvas.drawShape(ic);

            //Draw the circles again, making it recursive.
            //Note: drawCircleFractal(radius, x, y, color, canvas, maxLevel, currentLevel, thisCircle, c1, c2, c3, isHardSolution) --> c1, c2, c3 are the circles that the new circle ia, ib, or ic are touching
            drawCircleRecursive(ia.getRadius(), can, levels, currentLevel + 1, ia, c1, c2, c0,  false); //This circle has no hard
            drawCircleRecursive(ib.getRadius(), can, levels, currentLevel + 1, ib, c1, c0, c3,  isHardB);
            drawCircleRecursive(ic.getRadius(), can, levels, currentLevel + 1, ic, c0, c2, c3,  isHardC);
            //Add the area to the totalArea
            totalArea += c0.calculateArea();
        }
    }

    public void drawRectangleFractal(double width, double height, double x, double y, Color c, Canvas can, int level) {

        //This checks to see if it is the first rectangle, in which case we scale it appropriately so
        //that it fits in the window specified by the user.
        if (level == maxLevel) {
            //The sum of 1/2^n from i=0 to levels is the formula 2-1/(factor^level) (The total width of the fractal)
            double totalLength = 2.0-(1.0/Math.pow(2, level));
            //Divide the total width by the width of the square, and this gives us the starting width of the first rectangle so the final fractal is the desired width
            width = width/(2*totalLength)*2;
            height= height/(2*totalLength)*2;
            //Change the x and y appropriately
            x = x-width/2;
            y = y-width/2;
        }

        Rectangle rec = new Rectangle(x, y, width, height); //Create the new rectangle!
        rec.setColor(c); //Set the color
        can.drawShape(rec); //Draw the shape

        totalArea += rec.calculateArea(); //Add the area

        //If the level is not zero, we run it again and decrease the level until it is 0

        if (level>0) {
            double newWidth=width/2, newHeight=height/2;
            double offset = newWidth/2;
            c = getCorrectColor();
            drawRectangleFractal(newWidth, newHeight, x-newWidth+offset, y-newHeight+offset, c, can, level-1);
            drawRectangleFractal(newWidth, newHeight, x+width-offset, y-newHeight+offset, c, can, level-1);
            drawRectangleFractal(newWidth, newHeight, x-newWidth+offset, y+height-offset, c, can, level-1);
            drawRectangleFractal(newWidth, newHeight, x+width-offset, y+height-offset, c, can, level-1);
        }
    }

    public Color getCorrectColor() {
        Color c;
        if (isColored) {
            float v = rand.nextFloat()/(rand.nextInt(2)+1);
            c = new Color(rand.nextFloat(),v, 1-v);
        } else {
            c = Color.BLACK;
        }
        return c;
    }
}
