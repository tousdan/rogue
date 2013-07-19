package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Player implements Actor {

	private Cell location;
	
	public Player() {
	}
	
	public Cell location() {
		return location;
	}
	
	@Override
	public Texture draw() {
		return Textures.i().character;
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
