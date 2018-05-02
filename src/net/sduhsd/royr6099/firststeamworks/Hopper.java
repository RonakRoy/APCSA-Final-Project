package net.sduhsd.royr6099.firststeamworks;

import java.awt.Color;
import java.awt.Graphics;

import net.sduhsd.royr6099.firststeamworks.generics.GameObject;

public class Hopper extends GameObject {
	private boolean left;
	private boolean filled = true;
	
	public Hopper() {
		this(0,0, true);
	}
	
	public Hopper(int x, int y, boolean left) {
		setPos(x, y);
		setDimensions(10, 38);
		
		this.left = left;
	}

	public void draw(Graphics window) {
		window.setColor(Color.RED);
		//window.fillRect(getX(), getY(), getWidth(), getHeight());
	
		if (filled) {
			window.setColor(Color.GREEN);
			
			if (left) {
				window.fillRect(getX() - 30, getY() - 30, 30, 30);
				window.fillRect(getX() - 30, getY() + 38, 30, 30);
			}
			else {
				window.fillRect(getX() + 10, getY() - 30, 30, 30);
				window.fillRect(getX() + 10, getY() + 38, 30, 30);
			}
		}
	}
	
	public boolean getFilled() {
		return filled;
	}
	
	public void dump() {
		filled = false;
	}
	
	public int getLeftMultiplier() {
		if (left) {
			return 1;
		}
		
		return -1;
	}
}
