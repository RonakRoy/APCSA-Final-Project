package net.sduhsd.royr6099.firststeamworks;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import net.sduhsd.royr6099.firststeamworks.generics.GameObject;
import net.sduhsd.royr6099.firststeamworks.generics.MovingObject;

public class Misfire extends MovingObject {
	private Image image;
	
	private int fuel_count = 0;
	private boolean has_gear = false;

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
		
		image = ImageLoader.loadImage("misfire.png");
	}

	public void draw(Graphics window) {
		window.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}
	
	public void depositGear() {
		if (hasGear()) {
			image = ImageLoader.loadImage("misfire.png");
			
			has_gear = false;
		}
	}
	
	public void pickUpGear() {
		if (!hasGear()) {
			image = ImageLoader.loadImage("misfire_with_gear.png");
			
			has_gear = true;
		}
	}
	
	public boolean isIn(GameObject zone) {
		return this.getX() + this.getWidth() > zone.getX() && this.getX() < zone.getX() + zone.getWidth() &&
				this.getY() + this.getHeight() > zone.getY() && this.getY() < zone.getY() + zone.getHeight();
	}
	
	public boolean hasGear() {
		return has_gear;
	}
}
