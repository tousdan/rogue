package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Player implements Actor {
	
	private Texture character;
	private Cell location;
	
	public Player() {
		Pixmap map = new Pixmap(Constants.TILE_SIZE, Constants.TILE_SIZE, Pixmap.Format.RGBA8888);
		map.setColor(Color.RED);
		map.fillRectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
		map.setColor(Color.ORANGE);
		map.drawLine(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
		map.drawLine(Constants.TILE_SIZE, 0, 0, Constants.TILE_SIZE);
		
		character = new Texture(map);
	}
	
	public Cell location() {
		return location;
	}
	
	@Override
	public Texture draw() {
		return character;
	}
	@Override
	public boolean interact(Game game, GameEngine engine, Actor requestor, String intent) {
		return false;
	}
	@Override
	public void location(Cell location) {
		this.location = location;
	}
}
