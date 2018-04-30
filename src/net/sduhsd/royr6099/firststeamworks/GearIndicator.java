package net.sduhsd.royr6099.firststeamworks;

import java.awt.Graphics;
import java.awt.Image;

import net.sduhsd.royr6099.firststeamworks.generics.GameObject;

public class GearIndicator extends GameObject{
	private Image image;
	private boolean activated = false;
	
	public GearIndicator() {
		setDimensions(40,40);
		setPos(0, 0);
		
		image = ImageLoader.loadImage("gear_dark.png");
	}
	
	public GearIndicator(int rotor, int no) {
		this();
		
		setPos(Constants.BOARD_WIDTH + 10 + rotor * (getWidth() + 10), 30 + no * (getHeight() + 10));
	}
	
	public void draw(Graphics window) {
		window.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}
	
	public void activate() {
		activated = true;
		image = ImageLoader.loadImage("gear.png");
	}
	
	public boolean isActive() {
		return activated;
	}
}
