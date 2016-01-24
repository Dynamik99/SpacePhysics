package object;

import java.awt.Graphics;

import main.Main;

public abstract class Body {
	
	private float xVel;
	private float yVel;
	private float xp;
	private float yp;
	private long bodyMass;
	
	
	public Body(int x, int y, long mass) {
		xp = x;
		yp = y;
		bodyMass = mass;
	}
	
	public abstract void render(Graphics g);
	
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
		accelerate(xForce / bodyMass, yForce / bodyMass);
	}
	
	public void pullBodies() {
		for (Body body: Main.getBodies()) {
			if (body != this) {
				float xd = getX() - body.getX();
				float yd = getY() - body.getY();
				float xSq = xd * xd;
				float ySq = yd * yd;
				float distance = (float) Math.sqrt(xSq + ySq);
				float force = Main.GRAVITATIONAL_CONSTANT * ((getMass() * body.getMass()) / (distance * distance));
				float angle = (float) (Math.PI + Math.atan2(-xd, yd));
				applyAngledForce(force, angle);
			}
		}
	}
	
	public void move() {
		xp += xVel * Main.timePerTick();
		yp += yVel * Main.timePerTick();
	}
	
	public void update() {
		pullBodies();
		move();
	}
	
	public long getMass() {
		return bodyMass;
	}
	
	public float getX() {
		return xp;
	}
	
	public float getY() {
		return yp;
	}
}