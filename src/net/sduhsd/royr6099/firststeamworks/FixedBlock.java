package net.sduhsd.royr6099.firststeamworks;

import java.awt.Color;
import java.awt.Graphics;

import net.sduhsd.royr6099.firststeamworks.generics.GameObject;

public class FixedBlock extends GameObject {
	public FixedBlock() {
		this(0, 0, 100, 100);
	}
	
	public FixedBlock(int x, int y, int w, int h) {
		setPos(x, y);
		setDimensions(w, h);
	}
	
	public void draw(Graphics window) {
		window.setColor(Color.WHITE);
		window.fillRect(getX(), getY(), getWidth(), getHeight());
	}
}
