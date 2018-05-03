package net.sduhsd.royr6099.firststeamworks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import net.sduhsd.royr6099.firststeamworks.generics.GameObject;
import net.sduhsd.royr6099.firststeamworks.generics.MovingObject;

public class Ball extends MovingObject {	
	public Ball() {
		setDimensions(10,10);
		setPos(0, 0);
	}
	
	public Ball(Hopper h) {
		this();

		setPos(h.getX() + h.getLeftMultiplier() * (int) (Math.random() * 80) + 3, h.getY() + (int) (Math.random() * 240) - 100);
	}
	
	public Ball(Misfire m, boolean left) {
		this();

		setPos(m.getX() - 5, m.getY() + m.getHeight() + 5);
		setSpeed(2);
		
		
		double r = Math.random();
		int direction = 0;
		
		if (r > 0.8) {
			direction = 15;
		}
		else if (r < 0.2) {
			direction = -15;
		}
		
		if (left) {
			direction += 180;
		}
		else {
			direction += 90;
		}
		
		setDirection(direction);
		
	}
	
	public void draw(Graphics window) {
		window.setColor(Color.GREEN);
		window.fillArc(getX(), getY(), getWidth(), getHeight(), 0, 360);
	}
}
