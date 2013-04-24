package gdt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Maintains the game state and allows interactions with the game.
 */
public class Game {

	private static final int LEVEL_WIDTH = 16 * 5;
	private static final int LEVEL_HEIGHT = 16 * 5;
	
	public final Level level;
	public final Player player;
	
	private final GameEngine engine;
	
	public Game() {
		this.level = new Level(LEVEL_WIDTH, LEVEL_HEIGHT);
		this.player = new Player();
		this.engine = new Engine();
		
		positionPlayer();
	}

	public boolean movePlayer(Direction direction) {
		return tryMoveActor(player, level.getAdjacentCell(player, direction));
	}
	
	public void runGameStep() {
		
	}
	
	private boolean tryMoveActor(Actor actor, Cell destination) {
		if(destination != null)
			return destination.interact(engine, actor, Intents.MOVE);
		else
			return false;
	}
	
	private void positionPlayer() {
		boolean playerPlaced = false;
		while(!playerPlaced) {
			playerPlaced = tryMoveActor(player, level.findRandomCell(false));
		}
	}

	public void draw(SpriteBatch batch) {
		level.draw(batch);
		Cell playerLocation = player.location();
		batch.draw(player.draw(), playerLocation.x * Constants.TILE_SIZE, playerLocation.y * Constants.TILE_SIZE);
	}
	
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
		public void performAttack(Actor attacker, Actor defendant) {
			// ?
		}
		
	}
}
