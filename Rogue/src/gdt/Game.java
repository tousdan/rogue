package gdt;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {

	private static final int LEVEL_WIDTH = 256;
	private static final int LEVEL_HEIGHT = 256;
	
	private final Level level;
	private final Player player;
	
	public Game() {
		this.level = new Level(LEVEL_WIDTH, LEVEL_HEIGHT);
		this.player = new Player();
		
		positionPlayer();
	}

	private void positionPlayer() {
		while(true) {
			Cell candidate = level.findRandomCell(false);
			if(candidate.interact(player, Intents.MOVE)) {
				player.setNewLocation(candidate);
				break;
			}
		}
	}

	public void draw(SpriteBatch batch) {
		level.draw(batch);
		Cell playerLocation = player.location();
		batch.draw(player.draw(), playerLocation.x * Constants.TILE_SIZE, playerLocation.y * Constants.TILE_SIZE);
	}
	
}
