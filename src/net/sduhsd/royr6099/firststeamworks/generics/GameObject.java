package net.sduhsd.royr6099.firststeamworks.generics;

public abstract class GameObject implements Locatable, Collidable {
	private int x, y;
	private int width, height;
	
	public void setPos(int x, int y) {
		setX(x);
		setY(y);
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setDimensions(int w, int h) {
		setWidth(w);
		setHeight(h);
	}
	
	public void setWidth(int w) {
		width = w;
	}
	
	public void setHeight(int h) {
		height = h;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean didCollideLeft(Collidable obj) {
		GameObject other = (GameObject) obj;
		
		if (other == null) return false;
		
		return isInYRange(other)
				&& getX() + getWidth() > other.getX()
				&& getX() <= other.getX() + other.getWidth();
	}

	public boolean didCollideRight(Collidable obj) {
		GameObject other = (GameObject) obj;
		
		if (other == null) return false;
		
		return isInYRange(other)
				&& getX() < other.getX() + other.getWidth()
				&& getX() + getWidth() >= other.getX();
	}

	public boolean didCollideTop(Collidable obj) {
		GameObject other = (GameObject) obj;
		
		if (other == null) return false;
		
		return isInXRange(other) 
				&& other.getY() < getY() + getHeight()
				&& other.getY() + other.getHeight() >= getY();
	}

	public boolean didCollideBottom(Collidable obj) {
		GameObject other = (GameObject) obj;
		
		if (other == null) return false;
		
		return isInXRange(other) 
				&& other.getY() < getY() + getHeight()
				&& other.getY() + other.getHeight() >= getY();
	}
	
	private boolean isInXRange(GameObject other) {
		return getX() >= other.getX() && getX() + this.getWidth() <= other.getX() + other.getWidth();
	}
	
	private boolean isInYRange(GameObject other) {
		return getY() >= other.getY() && getY() + this.getHeight() <= other.getY() + other.getHeight();
	}

}
