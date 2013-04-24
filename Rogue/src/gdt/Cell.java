package gdt;

public abstract class Cell implements RogueObject {
	public final int x;
	public final int y;
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	abstract boolean isSolid();
}
