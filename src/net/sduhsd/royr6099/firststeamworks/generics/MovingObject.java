package net.sduhsd.royr6099.firststeamworks.generics;

public abstract class MovingObject extends GameObject {
	private int speed;
	private int direction;

	public void setDirection(int d) {
		direction = d % 360;
	}
	
	public void changeDirection(int delD) {
		setDirection(getDirection() + delD);
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setSpeed(int s) {
		speed = s;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void move() {
		int xSpd = (int) (1.0 * speed * Math.cos(Math.toRadians(direction)));
		int ySpd = (int) (1.0 * speed * Math.sin(Math.toRadians(direction)));
		
		setX(xSpd + getX());
		setY(ySpd + getY());
	}
}
