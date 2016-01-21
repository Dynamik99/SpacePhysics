package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

import object.Asteroid;
import object.Planet;

public class Main extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "Space Physics";
	public static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 255);
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	public static final int TICKS_PER_SECOND = 60;
	public static final float GRAVITATIONAL_CONSTANT = 0.000000000066726f;
	
	private static ArrayList<Asteroid> asteroids;
	private static ArrayList<Planet> planets;

	private Thread thread;
	private boolean running = false;
	private JFrame frame;

	public Main() {
		frame = new JFrame(NAME);

		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void start() {
		running = true;
		thread = new Thread(this, NAME + "_main");
		thread.start();
	}

	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wordt eenmalig geroepen bij het opstarten.
	 */
	private void init() {
		asteroids = new ArrayList<Asteroid>();
		planets = new ArrayList<Planet>();
		asteroids.add(new Asteroid(300, 300, 10, 50, 8, 597200000));
		asteroids.add(new Asteroid(500, 300, 10, 50, 8, 100));
		planets.add(new Planet(400, 300, 300, 1000));
		asteroids.get(0).setVelocity(0, 0);
		asteroids.get(1).setVelocity(0, 0);
	}

	/**
	 * Wordt een aantal keer per seconde geroepen. Dit aantal hangt af van de
	 * static variabele TICKS_PER_SECOND
	 */
	private void update() {
		for (Asteroid asteroid: asteroids) {
			asteroid.update();
		}
		for (Planet planets: planets) {
			planets.update();
		}
	}

	/**
	 * Hierin kan je de game renderen.
	 * 
	 * @param g
	 *            De Graphics die het scherm gebruikt.
	 */
	private void render(Graphics g) {
		for (Asteroid asteroid: asteroids) {
			asteroid.render(g);
		}
		for (Planet planets: planets) {
			planets.render(g);
		}
	}

	/**
	 * Maakt de BufferStrategy voor de paint() functie om te gebruiken.
	 */
	private void setupRender() {
		BufferStrategy bs = getBufferStrategy();
		while (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());

		render(g);

		g.dispose();
		bs.show();
	}

	/**
	 * Dit is de game loop.
	 */
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / ((double) TICKS_PER_SECOND);
		double delta = 0;

		init();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;

			boolean shouldRender = true;

			while (delta >= 1) {
				update();
				delta--;
				shouldRender = true;
			}

			if (shouldRender) {
				setupRender();
			}
		}
	}

	/**
	 * Functie die alles start.
	 * 
	 * @param args
	 *            Argumenten voor het programma.
	 */
	public static void main(String[] args) {
		new Main().start();
	}
	public static float timePerTick() {
		return 1f / TICKS_PER_SECOND;
	}
	
	public static ArrayList<Asteroid> getAsteroids() {
		return asteroids;
	}
	
	public static ArrayList<Planet> getPlanets() {
		return planets;
	}
}