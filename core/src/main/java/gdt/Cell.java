package gdt;

public abstract class Cell extends Position implements RogueObject, Locatable {
	
	private Actor actor;
	
	public Cell(int x, int y) {
		super(x, y);
	}
	
	public Cell location() {
		return this;
	}
	
	abstract boolean isSolid();

	public Actor actor() {
		return actor;
	}
	public void actor(Actor actor) {
		this.actor = actor;
	}
}
