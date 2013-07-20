package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Floor extends Cell {

	private Texture texture;
	
	public Floor(int x, int y) {
		super(x, y);

        this.texture = Textures.i().floor;
	}

    public Floor(int x, int y, Color color) {
        this(x, y);

        this.texture = Textures.i().coloredFloor(color);
    }
	
	@Override
	public Texture draw() {
		return texture;
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public boolean interact(Game game, GameEngine engine, Actor requestor, String intent) {
		if(Intents.MOVE.equals(intent) && actor() == null) {
			engine.performMove(requestor, this);
			return true;
		}

		if(actor() != null) {
			return actor().interact(game, engine, requestor, intent);
		}
		
		return false;
	}
}
