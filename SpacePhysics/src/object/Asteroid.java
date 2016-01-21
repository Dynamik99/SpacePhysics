package object;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import main.Main;

public class Asteroid {
	
	private int nPoints;
	private int[] xPoints;
	private int[] xRel;
	private int[] yPoints;
	private int[] yRel;
	private float xVel;
	private float yVel;
	private float xp;
	private float yp;
	private long mass;
	
	
	public Asteroid(int x, int y, int minRange, int maxRange, int points, long mass) {
		xPoints = new int[points];
		yPoints = new int[points];
		xRel = new int[points];
		yRel = new int[points];
		nPoints = points;
		xp = x;
		yp = y;
		this.mass = mass;
		
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
			xPoints[i] = (int) (xp + xRel[i]);
			yPoints[i] = (int) (yp + yRel[i]);
		}
	}
	
	public void setVelocity(float x, float y) {
		xVel = x;
		yVel = y;
	}
	
	public void accelerate(float x, float y) {
		xVel += x * Main.timePerTick();
		yVel += y * Main.timePerTick();
	}
	
	public void applyAngledForce(float force, float angle) {
		float xForce = (float) (Math.sin(angle) * force);
		float yForce = (float) (Math.cos(angle) * force);
		applyForce(xForce, yForce);
	}
	
	public void applyForce(float xForce, float yForce) {
		accelerate(xForce / mass, yForce / mass);
	}
	
	public void pullAsteroids() {
		for (Asteroid asteroid: Main.getAsteroids()) {
			if (asteroid != this) {
				float xd = getX() - asteroid.getX();
				float yd = getY() - asteroid.getY();
				float xSq = xd * xd;
				float ySq = yd * yd;
				float distance = (float) Math.sqrt(xSq + ySq);
				float force = Main.GRAVITATIONAL_CONSTANT * ((getMass() * asteroid.getMass()) / (distance * distance));
				float angle = (float) Math.atan2(-xd, -yd);
				applyAngledForce(force, angle);
			}
		}
	}
	
	public void move() {
		xp += xVel * Main.timePerTick();
		yp += yVel * Main.timePerTick();
	}
	
	public void update() {
		pullAsteroids();
		move();
		updatePoints();
	}
	
	public long getMass() {
		return mass;
	}
	
	public float getX() {
		return xp;
	}
	
	public float getY() {
		return yp;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillPolygon(xPoints, yPoints, nPoints);
	}
}
