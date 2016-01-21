package object;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import main.Main;

public class Planet {
	
	private float xVel;
	private float yVel;
	private float xp;
	private float yp;
	private long mass;
	private int radius;
	
	
	public Planet(int x, int y, int radius, long mass) {
		xp = x;
		yp = y;
		this.radius = radius;
		this.mass = mass;
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
	
	public void pullPlanets() {
		for (Planet planet: Main.getPlanets()) {
			if (planet != this) {
				float xd = getX() - planet.getX();
				float yd = getY() - planet.getY();
				float xSq = xd * xd;
				float ySq = yd * yd;
				float distance = (float) Math.sqrt(xSq + ySq);
				float force = Main.GRAVITATIONAL_CONSTANT * ((getMass() * planet.getMass()) / (distance * distance));
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
		move();
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
		g.fillOval((int) xp, (int) yp, radius << 1, radius << 1);
	}
}