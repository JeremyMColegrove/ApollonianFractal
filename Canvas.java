//Written by Jeremy Colegrove

import java.awt.*;
import java.util.concurrent.*;
 
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ListIterator;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class Canvas extends JApplet {

	
	private int height;
	private int width;
	private CopyOnWriteArrayList<Circle> circles;
	
	public Canvas() {
		JFrame f = new JFrame("Canvas");
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {System.exit(0);}
		});
		height = 800;
		width = 800;
		f.setSize(width, height);
		f.getContentPane().add(this);
		circles = new CopyOnWriteArrayList<Circle>();
		f.setVisible(true);
	}
	
	public Canvas(int h, int w) {
		JFrame f = new JFrame("Canvas");
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {System.exit(0);}
		});
		height = h;
		width = w;
		f.setSize(width, height);
		f.getContentPane().add(this);
		circles = new CopyOnWriteArrayList<Circle>();
		f.setVisible(true);
	}
	
	public void paint(Graphics g) {
		Image buffer = createImage(width,height);
		drawToBuffer(buffer.getGraphics());
		g.drawImage(buffer,0,0,null);
	}

	public void drawToBuffer(Graphics g){	
		ListIterator<Circle> circItr = circles.listIterator();

		while (circItr.hasNext()) {
			Circle curCircle = circItr.next();
			g.setColor(curCircle.getColor());
			int curRadius = (int)curCircle.getRadius();
			g.fillOval((int)curCircle.getXPos() - curRadius, (int)curCircle.getYPos()
					- curRadius, 2 * curRadius, 2 * curRadius);

		}
	}
	
	
	public void drawShape(Circle circ){
		circles.add(circ);
	}
	
	public void clear(){
		circles.clear();
	}
	

}
