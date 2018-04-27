package net.sduhsd.royr6099.firststeamworks;

import java.awt.Color;
import java.awt.Graphics;
import net.sduhsd.royr6099.firststeamworks.generics.GameObject;

public class LoadingZone extends GameObject {
	public LoadingZone() {
		setPos(465, 60);
		setDimensions(100, 120);
	}

	public void draw(Graphics window) {
		window.setColor(Color.WHITE);
		window.fillRect(getX(), getY(), getWidth(), getHeight());
	}
}
