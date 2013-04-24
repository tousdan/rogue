package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Wall extends Cell{
	private Texture texture;
	
	public Wall(int x, int y) {
		super(x, y);
		
		Pixmap map = new Pixmap(Constants.TILE_SIZE, Constants.TILE_SIZE, Pixmap.Format.RGBA8888);
		map.setColor(Color.DARK_GRAY);
		map.fillRectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
		
		texture = new Texture(map);
	}
	
	@Override
	public Texture draw() {
		return texture;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

	@Override
	public boolean interact(GameEngine engine, Actor requestor, String intent) {
		return false;
	}
}
