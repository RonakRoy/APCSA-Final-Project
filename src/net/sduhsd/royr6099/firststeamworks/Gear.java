package net.sduhsd.royr6099.firststeamworks;

import java.awt.Graphics;
import java.awt.Image;

import net.sduhsd.royr6099.firststeamworks.generics.GameObject;

public class Gear extends GameObject {
	private Image image;
	
	public Gear() {
		setDimensions(40,40);
		setPos(0, 0);
		
		image = ImageLoader.loadImage("gear.png");
	}
	
	public Gear(Misfire misfire) {
		this();
		
		setPos(misfire.getX() + misfire.getWidth() + 3, misfire.getY() + 10);
	}
	
	public void draw(Graphics window) {
		window.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}
}
