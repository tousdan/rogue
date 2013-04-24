package gdt;

public abstract class Monster implements AI, Actor {
	
	private Cell location;
	
	public Cell location() { 
		return location; 
		}
	public void location(Cell value) {
		this.location = value;
	}
}
