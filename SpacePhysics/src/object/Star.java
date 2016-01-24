package object;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Star extends Body {
	
	private int starRadius;
	
	private ArrayList<Body> bodies;
	private ArrayList<Integer> distances;
	private ArrayList<Float> velocities;
	private ArrayList<Float> positions;
	
	public Star(int x, int y, int radius, long mass) {
		
		super(x, y, mass);
		starRadius = radius;
		bodies = new ArrayList<Body>();
		distances = new ArrayList<Integer>();
		velocities = new ArrayList<Float>();
		positions = new ArrayList<Float>();
	}
	
	public void update() {
		updateBodies();
		move();
	}
	
	public void updateBodies() {
		for(int i = 0;i < bodies.size(); i++) {
			bodies.get(i).update();
			float loop = (float) (2f * Math.PI * ((float) distances.get(i)));
			float offset = positions.get(i);
			offset += velocities.get(i) / loop;
			while(offset > 1f) {
				offset -= 1f;
			}
			while(offset < 0f) {
				offset += 1f;
			}
			positions.set(i, offset);
			float angle = (float) (offset * 2f * Math.PI);
			bodies.get(i).setPosition((int) (getX() + (Math.cos(angle) * distances.get(i))), (int) (getY() + (Math.sin(angle) * distances.get(i))));
		}
	}

	@Override
	public void render(Graphics g) {
		for(int i = 0;i < bodies.size(); i++) {
			bodies.get(i).render(g);
		}
		g.setColor(Color.orange);
		g.fillOval((int) getX() - starRadius, (int) getY() - starRadius, starRadius << 1, starRadius << 1);
	}
	
	public void addBody(Body body, int distance, float velocity, float offset) {
		bodies.add(body);
		distances.add(distance);
		velocities.add(velocity);
		positions.add(offset);
	}
	
	public ArrayList<Body> getBodies() {
		return bodies;
	}
}