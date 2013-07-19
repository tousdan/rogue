package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Rock extends Cell {

	public Rock(int x, int y) {
		this(x, y, Color.DARK_GRAY);
	}

    public Rock(int x, int y, Color color) {
        super(x, y);

    }

	@Override
	public Texture draw() {
		return Textures.i().rock;
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
