package gdt;

/**
 * Used to perform low level actions of the game
 */
public interface GameEngine {
	
	void performMove(Actor actor, Cell destination);
	void performAttack(Actor attacker, Actor defendant);
	
}
