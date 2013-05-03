package gdt;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Maintains the game state and allows interactions with the game.
 */
public class Game {

	private static final int LEVEL_WIDTH = 80;
	private static final int LEVEL_HEIGHT = 60;
	
	public final Level level;
	public final Player player;
	
	private final GameEngine engine;
	
	public final List<Monster> monsters = new ArrayList<>();
	
	public Game() {
		this.level = new Level(LEVEL_WIDTH, LEVEL_HEIGHT);
		this.player = new Player();
		
		this.engine = new Engine();
		
		positionPlayer();
		addRats(25);
	}
	
	public boolean moveActor(Actor actor, Direction direction) {
		return tryMoveActor(actor, level.getAdjacentCell(actor, direction));
	}
	
	public void runGameStep() {
		for(AI ai : monsters) {
			ai.step(this);
		}
	}
	
	private boolean tryMoveActor(Actor actor, Cell destination) {
		if(destination != null)
			return destination.interact(this, engine, actor, Intents.MOVE);
		else
			return false;
	}
	
	private void positionPlayer() {
		boolean playerPlaced = false;
		while(!playerPlaced) {
			playerPlaced = tryMoveActor(player, level.findRandomCell(false));
		}
	}
	private void addRats(int count) {
		while(count --> 0) {
			Rat rat = new Rat();
			monsters.add(rat);
			while(rat.location() == null) {
				tryMoveActor(rat, level.findRandomCell(false));
			}
		}
	}

	public void draw(SpriteBatch batch) {
		level.draw(this, batch);
		Cell playerLocation = player.location();
		batch.draw(player.draw(), playerLocation.x * Constants.TILE_SIZE, playerLocation.y * Constants.TILE_SIZE);
		for(Monster monster : monsters) {
			Cell monsterLocation = monster.location();
			batch.draw(monster.draw(), monsterLocation.x * Constants.TILE_SIZE, monsterLocation.y * Constants.TILE_SIZE);
		}
	}
	
	
	// These are low-level operations intended to be performed by RogueObjects in their interact method
	private class Engine implements GameEngine {

		@Override
		public void performMove(Actor actor, Cell destination) {
			Cell source = actor.location();
			if(source != null)
				source.actor(null);
			actor.location(destination);
			destination.actor(actor);
			
		}

		@Override
		public void performAttack(Actor attacker, Actor defender) {
			if(defender instanceof Rat) {
				((Rat) defender).kill();
			}
			
		}
		
	}
}
