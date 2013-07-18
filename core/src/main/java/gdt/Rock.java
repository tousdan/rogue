package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Rock extends Cell {

	private Texture texture;
	
	public Rock(int x, int y) {
		this(x, y, Color.DARK_GRAY);
	}

    public Rock(int x, int y, Color color) {
        super(x, y);

        Pixmap map = new Pixmap(Constants.TILE_SIZE, Constants.TILE_SIZE, Pixmap.Format.RGBA8888);
        map.setColor(color);
        map.fillRectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);

        texture = new Texture(map);
    }


	
	@Override
	public Texture draw() {
		return texture;
	}

	@Override
	public boolean interact(Game game, GameEngine engine, Actor requestor,
			String intent) {
		return false;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
