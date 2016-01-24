package object;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Asteroid extends Body {
	
	private int nPoints;
	private int[] xPoints;
	private int[] xRel;
	private int[] yPoints;
	private int[] yRel;
	
	public Asteroid(int x, int y, int minRange, int maxRange, int points, long mass) {
		super(x, y, mass);
		xPoints = new int[points];
		yPoints = new int[points];
		xRel = new int[points];
		yRel = new int[points];
		nPoints = points;
		
		Random r = new Random();
		for (int i = 0; i < points; i++) {
			int length = r.nextInt(maxRange - minRange) + minRange;
			double stepDeg = 360 / points * i;
			int xPos = (int) (Math.sin(Math.toRadians(stepDeg)) * length);
			int yPos = (int) (Math.cos(Math.toRadians(stepDeg)) * length);
			xRel[i] = xPos;
			yRel[i] = yPos;
		}
		updatePoints();
	}
	
	private void updatePoints() {
		for (int i = 0; i < nPoints; i++) {
			xPoints[i] = (int) (getX() + xRel[i]);
			yPoints[i] = (int) (getY() + yRel[i]);
		}
	}
	
	public void update() {
		super.update();
		updatePoints();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillPolygon(xPoints, yPoints, nPoints);
	}
}
