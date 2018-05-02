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
	
	public Ball(Misfire m) {
		this();

		setPos(m.getX() - 5, m.getY() + m.getHeight() + 5);
		setSpeed(2);
		setDirection(135);
	}
	
	public void draw(Graphics window) {
		window.setColor(Color.GREEN);
		window.fillArc(getX(), getY(), getWidth(), getHeight(), 0, 360);
	}
}
