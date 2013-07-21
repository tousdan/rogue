package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Wall extends Cell{
	
	public Wall(int x, int y) {
		super(x, y);
	}
	
	@Override
	public Texture draw() {
		return Textures.i().wall;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public boolean interact(Game game, GameEngine engine, Actor requestor, String intent) {
		return false;
	}
}
