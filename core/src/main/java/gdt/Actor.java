package gdt;

public interface Actor extends RogueObject, Locatable {
	Cell location();
	void location(Cell location);
}
