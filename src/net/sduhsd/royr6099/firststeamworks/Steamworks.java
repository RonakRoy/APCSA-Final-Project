package net.sduhsd.royr6099.firststeamworks;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Steamworks extends Canvas implements KeyListener, Runnable {
	private boolean[] keys;
	private BufferedImage back;
	
	private Misfire misfire;
	
	public Steamworks() {
		setBackground(Color.BLACK);
		
		misfire = new Misfire(40, 40, 2);

		this.addKeyListener(this);
		new Thread(this).start();
		
		keys = new boolean[4];

		setVisible(true);
	}

	public void update(Graphics window) {
		paint(window);
	}

	public void paint(Graphics window) {
		Graphics2D graphics = (Graphics2D) window;

		if (back == null)
			back = (BufferedImage) (createImage(getWidth(), getHeight()));

		Graphics backgroundGraphics = back.createGraphics();

		backgroundGraphics.setColor(new Color(3, 155, 50));
		backgroundGraphics.fillRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		
		backgroundGraphics.setColor(Color.WHITE);
		backgroundGraphics.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		backgroundGraphics.drawString("FIRST STEAMWORKS", 10, 34);

		if (keys[0]) {
			misfire.setDirection(270);
			misfire.move();
		}
		else if (keys[1]) {
			misfire.setDirection(180);
			misfire.move();
		}
		else if (keys[2]) {
			misfire.setDirection(90);
			misfire.move();
		}
		else if (keys[3]) {
			misfire.setDirection(0);
			misfire.move();
		}
		
		misfire.draw(backgroundGraphics);
		
		graphics.drawImage(back, null, 0, 0);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			keys[0] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			keys[1] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			keys[2] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			keys[3] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = true;
		}
		repaint();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			keys[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			keys[1] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			keys[2] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			keys[3] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = false;
		}
		repaint();
	}

	public void keyTyped(KeyEvent e) {
		// Ignore Event
	}

	public void run() {
		try {
			while (true) {
				Thread.sleep(5);
				repaint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
