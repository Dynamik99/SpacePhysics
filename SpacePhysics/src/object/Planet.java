package object;

import java.awt.Color;
import java.awt.Graphics;

public class Planet extends Body {
	
	private int planetRadius;
	
	public Planet(int x, int y, int radius, long mass) {
		super(x, y, mass);
		planetRadius = radius;
	}

	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillOval((int) getX() - planetRadius, (int) getY() - planetRadius, planetRadius << 1, planetRadius << 1);
	}
}