package net.sduhsd.royr6099.firststeamworks.generics;

public interface Collidable {	
	public boolean didCollideLeft(Collidable obj);
	public boolean didCollideRight(Collidable obj);

	public boolean didCollideTop(Collidable obj);
	public boolean didCollideBottom(Collidable obj);
}
