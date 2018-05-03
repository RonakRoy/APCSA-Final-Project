package net.sduhsd.royr6099.firststeamworks;

import java.awt.Color;
import java.awt.Graphics;

import net.sduhsd.royr6099.firststeamworks.generics.GameObject;

public class HighGoal extends GameObject {
	public HighGoal() {
		this(0,0);
	}
	
	public HighGoal(int x, int y) {
		setPos(x, y);
		setDimensions(30, 30);
	}

	public void draw(Graphics window) {
		window.setColor(Color.RED);
		window.fillRect(getX(), getY(), getWidth(), getHeight());
	}
}
