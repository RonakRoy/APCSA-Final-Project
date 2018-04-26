package net.sduhsd.royr6099.firststeamworks;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Misfire extends MovingObject {
	private Image image;

	public Misfire() {
		this(0, 0, 0);
	}

	public Misfire(int x, int y) {
		this(x, y, 0);
	}

	public Misfire(int x, int y, int s) {
		setPos(x, y);
		setSpeed(s);
		
		setDimensions(80, 60);
		
		try {
			image = ImageIO.read(new File("misfire.png"));
		} catch (Exception e) {
			System.out.println("Couldn't load the image \'misfire.png\'");
		}
	}

	public void draw(Graphics window) {
		window.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}

	public String toString() {
		return super.toString() + getSpeed();
	}
}
