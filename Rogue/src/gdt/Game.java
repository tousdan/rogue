package gdt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {

	private static final int LEVEL_WIDTH = 16 * 5;
	private static final int LEVEL_HEIGHT = 16 * 5;
	
	public final Level level;
	public final Player player;
	
	public Game() {
		this.level = new Level(LEVEL_WIDTH, LEVEL_HEIGHT);
		this.player = new Player();
		
		positionPlayer();
	}

	public boolean moveActor(Actor actor, Cell destination) {
		if(destination.interact(actor, Intents.MOVE)) {
			Cell source = actor.location();
			if(source != null)
				source.actor(null);
			actor.location(destination);
			destination.actor(actor);
			return true;
		}
		return false;
	}
	
	public Cell getAdjacentCell(Locatable locatable, Direction direction) {
		Position location = locatable.location();
		Position adjacent = location.translated(direction.dx, direction.dy);
		
		if(level.inBounds(adjacent))
			return level.cell(adjacent);
		else
			return null;
	}
	
	public List<Cell> getAdjacentCells(Locatable locatable, Direction direction, int count) {
		List<Cell> result = new ArrayList<>();
		
		if(count < 1) {
			return result;
		}
		
		Position initial = locatable.location();
		Position current = initial.translated(direction.dx, direction.dy);
		while(count --> 0 && level.inBounds(current)) {
			result.add(level.cell(current));
			current = current.translated(direction.dx, direction.dy);
		}
		
		return result;
	}
	
	public List<Cell> getAdjacentCells(Locatable locatable, int radius, boolean round) {
		List<Cell> result = new ArrayList<>();
		
		if(radius < 1) {
			return result;
		}
		
		Position initial = locatable.location();
		Position topLeft = initial.translated(-radius, -radius);
		Position bottomRight = initial.translated(radius, radius);
		
		for(int x=topLeft.x; x < bottomRight.x; x++) {
			for(int y = topLeft.y; y < bottomRight.y; y++) {
				Position current = new Position(x, y);
				if(level.inBounds(current)) {
					result.add(level.cell(current));
				}
			}
		}
		
		if(round) {
			Cell current = null;
			for(Iterator<Cell> it=result.iterator(); it.hasNext(); current=it.next()) {
				if(current.distanceTo(initial) > radius)
					it.remove();
			}
		}
		
		return result;
		
	}
	
	
	private void positionPlayer() {
		boolean playerPlaced = false;
		while(!playerPlaced) {
			playerPlaced = moveActor(player, level.findRandomCell(false));
		}
	}

	public void draw(SpriteBatch batch) {
		level.draw(player.location(), batch);
		Cell playerLocation = player.location();
		batch.draw(player.draw(), playerLocation.x * Constants.TILE_SIZE, playerLocation.y * Constants.TILE_SIZE);
	}
}
