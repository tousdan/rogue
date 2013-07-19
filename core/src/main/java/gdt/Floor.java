package gdt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Floor extends Cell {

	private Texture texture;
	
	public Floor(int x, int y) {
		super(x, y);
	}
	
	@Override
	public Texture draw() {
		return Textures.i().floor;
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
