package gdt;

public abstract class Monster implements AI, Actor {
	
	private Cell location;
	private boolean dead = false;
	
	public Cell location() { 
		return location; 
	}
	
	public void location(Cell value) {
		this.location = value;
	}
	
	public boolean alive() {
		return !dead;
	}
	
	public void alive(boolean value) {
		this.dead = !value;
	}
	
	public void kill() {
		this.dead = true;
	}
}
